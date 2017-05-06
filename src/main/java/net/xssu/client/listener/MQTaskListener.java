package net.xssu.client.listener;

import net.xssu.client.entity.ScanTask;
import net.xssu.client.service.IMQSendResultService;
import net.xssu.client.service.IScanService;
import net.xssu.client.util.SpringContextsUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class MQTaskListener {
	@Autowired
	IMQSendResultService sendResultService;

	public void taskHandler(ScanTask task) {
		String rootPath = this.getClass().getClassLoader().getResource("").getPath();
		IScanService scanService;

		if (task.getServiceDetect()) {
			scanService = (IScanService) SpringContextsUtil.getBean("serviceDetectService");
		} else {
			scanService = (IScanService) SpringContextsUtil.getBean("normalScanService");
		}

		String resourcesFilePath = null;
		try {
			resourcesFilePath = URLDecoder.decode(rootPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		task.setOutputFilename(resourcesFilePath + "files/output/output_sd_" + task.getTaskId() + ".txt");
		List<String> commands = scanService.generateScanConfig(task);
		File fp = scanService.scan(task, commands, resourcesFilePath, "masscan");
		sendResultService.sendResult(task.getTaskId(), fp);
	}
}