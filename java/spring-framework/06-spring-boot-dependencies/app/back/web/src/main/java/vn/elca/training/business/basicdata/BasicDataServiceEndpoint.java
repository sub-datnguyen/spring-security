package vn.elca.training.business.basicdata;

import vn.elca.training.common.service.IEsbService;
import vn.elca.training.common.utils.ProjectConstants;
import ch.vd.technical.esb.EsbMessage;
import ch.vd.technical.esb.EsbMessageFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.UUID;

import com.google.common.io.Resources;
import vn.elca.training.common.AbstractServiceEndpoint;

@RestController
@RequestMapping(AbstractServiceEndpoint.BASIC_DATA_PATH)
@Slf4j
public class BasicDataServiceEndpoint extends AbstractServiceEndpoint {

    @Value("${spring.activemq.user}")
    private String businessUser;

    @Value("${jms.queue.engin}")
    private String enginQueueName;

    @Autowired
    private IEsbService esbService;

    @GetMapping(value = "createEngins/{filename}")
    public void createEngins(@PathVariable String filename) throws Exception {
        EsbMessage m = EsbMessageFactory.createMessage();
        m.setBody(Resources.toString(Resources.getResource("files/" + filename),
                Charset.forName(ProjectConstants.PROJECT_ENCODING)));
        //((EsbMessageImpl) m).setDomain("tim"); // do we need to set this field?
        m.setServiceDestination(enginQueueName);
        m.setBusinessUser(businessUser);
        m.setBusinessId(UUID.randomUUID().toString());
        m.setBusinessCorrelationId(m.getBusinessId());
        m.setContext("engins");
        esbService.sendMessage(m);
    }
}
