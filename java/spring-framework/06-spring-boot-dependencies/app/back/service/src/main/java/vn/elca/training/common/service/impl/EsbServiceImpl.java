package vn.elca.training.common.service.impl;

import vn.elca.training.common.exceptions.ProjectRuntimeException;
import vn.elca.training.common.service.IEsbService;
import ch.vd.technical.esb.EsbMessage;
import ch.vd.technical.esb.EsbMessageImpl;
import ch.vd.technical.esb.jms.EsbJmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author vlp
 */
@Service
@Transactional(rollbackOn = {Throwable.class})
public class EsbServiceImpl implements IEsbService {
    @Value("${esb-client.domain}")
    private String esbDomain;
    
    @Value("${jms.queue.engin}")
    private String esbQueue;
    @Value("${esb-client.application}")
    private String esbApplicationName;
    
    private EsbJmsTemplateService esbJmsTemplateService;
    
    @Autowired
    public EsbServiceImpl(EsbJmsTemplateService esbJmsTemplateService) {
        this.esbJmsTemplateService = esbJmsTemplateService;
    }
    
    @Override
    public boolean sendMessage(EsbMessage message) {
        message.setBusinessId(UUID.randomUUID().toString());
        ((EsbMessageImpl)message).setApplication(esbApplicationName);
        try {
            esbJmsTemplateService.sendMessage(message);
        } catch (Exception e) {
            throw new ProjectRuntimeException(
                    "Unexpected error happened when sending message to ActiveMQ: " + e.getMessage(), e);
        }
        return true;
    }
    
    @Override
    public boolean receiveMessage(EsbMessage message) {
        //TODO VLP: implement this method
        return false;
    }
}
