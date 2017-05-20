package net.xssu.client.listener;

import net.xssu.client.entity.ScanTask;
import net.xssu.client.service.IMQSendResultService;
import net.xssu.client.service.IScanService;
import net.xssu.client.util.SpringContextsUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class MQTaskListener {
	@Autowired
	IMQSendResultService sendResultService;

	public void taskHandler(ScanTask task) {
		IScanService scanService;
		System.out.println("收到一次任务");
		try{
			if(task.getServiceDetect()){
				scanService = (IScanService)SpringContextsUtil.getBean("serviceDetectService");
			}else{
				scanService = (IScanService)SpringContextsUtil.getBean("normalScanService");
			}

			task.setOutputFilename("/masscan/output/output_sd_" + task.getTaskId() + ".txt");
			List<String> commands = scanService.generateScanConfig(task);
			File fp = scanService.scan(task, commands, "/masscan", "masscan");
			sendResultService.sendResult(task.getTaskId(), task.getShards(), task.getShardId(), fp);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}