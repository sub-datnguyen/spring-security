package vn.elca.training.common.jms;

import vn.elca.training.business.basicdata.service.IEnginService;
import vn.elca.training.common.constants.CommonConstants;
import vn.elca.training.common.exceptions.ProjectJmsException;
import vn.elca.training.enums.ErrorCodeEnum;
import ch.vd.technical.esb.EsbMessage;
import ch.vd.technical.esb.jms.EsbJmsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.training.engine.Engin;

import java.io.ByteArrayInputStream;

/**
 * Listener for engin queue to handle creation/update of {@link Engin} entities from ActiveMQ.
 *
 * @author vlp
 */
@Component
@Slf4j
public class EnginQueueListener extends AbstractJmsQueueNotificationListener {
    
    private IEnginService enginService;
    
    @Autowired
    public EnginQueueListener(EsbJmsTemplate esbJmsTemplate, IEnginService enginService) {
        super(esbJmsTemplate);
        this.enginService = enginService;
    }
    
    @Override
    protected void onHandleMessage(EsbMessage esbMessage) {
        try {
            enginService.createOrUpdateEnginByXml(new ByteArrayInputStream(esbMessage.getBodyAsByteArray()), esbMessage);
        } catch (ProjectJmsException e) {
            log.error("résultat: {}, errorCode: {}, errorMessage: {}", CommonConstants.FAIL_STATUS,
                    e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            log.error("résultat: {}, errorCode: {}, errorMessage: {}", CommonConstants.FAIL_STATUS,
                    ErrorCodeEnum.TECHNICAL_ERROR_CODE.getCode(), e.toString());
        }
    }
    
    protected void onStartProcessing(EsbMessage esbMessage) {
        log.info("Process import Engins from XML beginning!");
    }
    
    protected void onFinishProcessing(EsbMessage esbMessage) {
        log.info("résultat: {}", CommonConstants.SUCCESS_STATUS);
    }
    
    @Override
    protected String getInterfaceLogName() {
        return "I1";
    }
    
}
