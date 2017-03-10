import junit.framework.TestCase;
import net.xssu.client.entity.ScanTask;
import net.xssu.client.listener.MQTaskListener;
import net.xssu.client.service.impl.ServiceDetectScanServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springConfig/spring-context.xml")
public class MQTaskListenerTest {
    @Autowired
    private MQTaskListener listener;

    /**
     * 测试IScanServie是否注入正确的实现类
     * （测试后具体实现已修改，如继续测试时需要将局部变量scanService提到外面）
     */
    @Test
    public void IScanServiceShouldInjectCorrect() {
        // ScanTask msg1 = new ScanTask(23, 1, "192.168.0.3/24", "80", true, true, 1);
        // listener.taskHandler(msg1);
        // Assert.assertEquals("msg1 failed", listener.scanService.getClass().getSimpleName(), "ServiceDetectScanServiceImpl");
        // ScanTask msg2 = new ScanTask(null, 1, "192.168.0.3/24", "80", false, false, null);
        // listener.taskHandler(msg2);
        // Assert.assertEquals("msg2 failed", listener.scanService.getClass().getSimpleName(), "NormalScanServiceImpl");
    }

    @Test
    public void testScan() {
        // ScanTask msg1 = new ScanTask(23, 1, "192.168.0.3/24", "80", true, true, 1);
        // listener.taskHandler(msg1);

    }
}
