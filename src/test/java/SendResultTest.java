import net.xssu.client.service.IMQSendResultService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springConfig/spring-context.xml")
public class SendResultTest {
    @Autowired
    IMQSendResultService sendResultService;

    @Test
    public void sendResultTest() {
        File outputFile = new File("/Users/ozziechen/Desktop/研究生毕设/listenerClient/out/artifacts/listenerClient_war_exploded/WEB-INF/classes/files/output/output_sd_2.txt");

        boolean result = sendResultService.sendResult(12, "1/4", outputFile);
        Assert.assertTrue(result);
    }
}
