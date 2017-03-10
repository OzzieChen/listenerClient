import net.xssu.client.service.impl.ServiceDetectScanServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springConfig/spring-context.xml")
public class RedisTest {
    @Autowired
    ServiceDetectScanServiceImpl serviceDetectScanService;

    @Test
    public void patternFileGenerateTest() {
        String result = serviceDetectScanService.generatePatternsFile("4");
        System.out.println(result);
        assertNotNull(result);
    }
}
