package vn.elca.training.business.basicdata.service.impl;

import vn.elca.training.business.basicdata.service.IEnginService;
import vn.elca.training.common.service.impl.AbstractServiceImpl;
import vn.elca.training.common.service.impl.MessageManager;
import vn.elca.training.dto.EnginJmsDto;
import vn.elca.training.engine.Engin;
import vn.elca.training.engine.Header;
import vn.elca.training.engine.ZgenDGMRvehicleReturn;
import vn.elca.training.entity.CategorieExploitationErEntity;
import vn.elca.training.entity.EnginEntity;
import vn.elca.training.entity.SecteurEntity;
import vn.elca.training.enums.ErrorCodeEnum;
import vn.elca.training.mapper.EnginJmsMapper;
import vn.elca.training.repository.ICategorieExploitationErRepository;
import vn.elca.training.repository.IEnginRepository;
import vn.elca.training.repository.ISecteurRepository;
import vn.elca.training.util.MessageUtil;
import vn.elca.training.util.ValidationMessagesKey;
import ch.vd.technical.esb.EsbMessage;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
@Slf4j
public class EnginServiceImpl extends AbstractServiceImpl implements IEnginService {
    private IEnginRepository enginRepository;
    private EnginJmsMapper enginMapper;
    private JAXBContext vehicleEngineJaxbContext;
    private ISecteurRepository secteurRepository;
    private ICategorieExploitationErRepository categorieExploitationErRepository;
    private MessageManager messageManager;

    @Value("${max-element}")
    private int maxEnginNumber;

    @Autowired
    public EnginServiceImpl(IEnginRepository enginRepository, EnginJmsMapper enginMapper,
                            @Qualifier("vehicleEngineJaxbContext") JAXBContext vehicleEngineJaxbContext,
                            ISecteurRepository secteurRepository,
                            ICategorieExploitationErRepository categorieExploitationErRepository,
                            MessageManager messageManager) {
        super();
        this.enginRepository = enginRepository;
        this.enginMapper = enginMapper;
        this.vehicleEngineJaxbContext = vehicleEngineJaxbContext;
        this.secteurRepository = secteurRepository;
        this.categorieExploitationErRepository = categorieExploitationErRepository;
        this.messageManager = messageManager;
    }

    @Override
    public Optional<EnginEntity> getEnginByNumeroIdentification(Long numeroIdentification) {
        return enginRepository.getEnginByNumeroIdentification(numeroIdentification);
    }

    @Override
    public EnginEntity saveEngin(EnginEntity enginEntity) {
        return enginRepository.save(enginEntity);
    }

    @Override
    public List<EnginEntity> createOrUpdateEnginByXml(InputStream inputStream, EsbMessage esbMessage) {
        if (inputStream == null || esbMessage == null) {
            MessageUtil.handleJmsException(ErrorCodeEnum.TECHNICAL_ERROR_CODE, ValidationMessagesKey.XSD_NOT_VALID);
        }
        List<EnginEntity> result = new LinkedList<>();
        try {
            ZgenDGMRvehicleReturn response = parseResponse(inputStream);
            String synchroDrive = getSynchroDrive(response);
            mapEnginDtoToEnginEntity(result, response, synchroDrive);
        } catch (SAXException | JAXBException e) {
            MessageUtil.handleJmsException(ErrorCodeEnum.TECHNICAL_ERROR_CODE, ValidationMessagesKey.XSD_NOT_VALID);
        }
        return result;
    }

    private void mapEnginDtoToEnginEntity(List<EnginEntity> result, ZgenDGMRvehicleReturn response,
                                          String synchroDrive) {
        List<Engin> engins = response.getListeEngins().getEngin();

        if (engins.size() > maxEnginNumber) {
            MessageUtil.handleJmsException(ErrorCodeEnum.BUSINESS_ERROR_CODE, ValidationMessagesKey.MAX_ENGIN_IMPORT_ERROR,
                    engins.size(), maxEnginNumber);
        }

        // map EnginDto into EnginEntity
        List<EnginJmsDto> enginJmsDtos =
                engins.stream().map(engin -> enginMapper.toEnginDto(engin)).collect(Collectors.toList());
        var count = 0;
        for (EnginJmsDto enginJmsDto : enginJmsDtos) {
            validateEnginDto(enginJmsDto);
            CategorieExploitationErEntity categorieExploitationEr = categorieExploitationErRepository
                    .getCategorieExploitationErByCode(enginJmsDto.getCategorieExploitationErCode()).orElse(null);
            SecteurEntity secteur =
                    secteurRepository.getSecteurByCodeEntiteSecteur(enginJmsDto.getLocalisationSecteurCodeEntSect())
                            .orElse(null);
            EnginEntity enginByNumeroIdentification =
                    getEnginByNumeroIdentification(Long.valueOf(enginJmsDto.getNumeroIdentification()))
                            .orElse(new EnginEntity());
            enginMapper.updateEnginEntityFromEnginDto(enginJmsDto, enginByNumeroIdentification, synchroDrive, secteur, categorieExploitationEr);
            // Simulate failure after half item is processed
            if (count == enginJmsDtos.size() / 2) {
                enginByNumeroIdentification.setNumPlaque(Strings.padEnd(enginByNumeroIdentification.getNumPlaque(), 41, '0'));
            }
            result.add(saveEngin(enginByNumeroIdentification));
            count++;
        }
    }

    private String getSynchroDrive(ZgenDGMRvehicleReturn response) {
        Header header = response.getHeader();
        if (header == null) {
            MessageUtil.handleJmsException(ErrorCodeEnum.TECHNICAL_ERROR_CODE, ValidationMessagesKey.XSD_NOT_VALID);
        }
        String dateMessage = header.getDateMessage();
        String heureMessage = header.getHeureMessage();
        if (StringUtils.isBlank(dateMessage) || StringUtils.isBlank(heureMessage)) {
            log.warn(messageManager.getMessage(ValidationMessagesKey.EMPTY_DATE_OR_TIME_MESSAGE, dateMessage,
                    heureMessage));
            return StringUtils.EMPTY;
        }
        return StringUtils.joinWith("T", dateMessage, heureMessage);
    }

    private ZgenDGMRvehicleReturn parseResponse(InputStream inputStream) throws SAXException, JAXBException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(
                new StreamSource(getClass().getResourceAsStream("/xsd/engine/ZGEN_DGMRVEHICULER_RET-v2.xsd")));

        Unmarshaller unmarshaller = vehicleEngineJaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
        return (ZgenDGMRvehicleReturn) unmarshaller.unmarshal(inputStream);
    }

    private void validateEnginDto(EnginJmsDto enginDto) {
        if (StringUtils.isBlank(enginDto.getEntiteCode())) {
            MessageUtil.handleJmsException(ErrorCodeEnum.BUSINESS_ERROR_CODE, ValidationMessagesKey.ENTITE_NOT_FOUND,
                    enginDto.getEntiteCode(), enginDto.getNumeroIdentification());
        }
    }

}
