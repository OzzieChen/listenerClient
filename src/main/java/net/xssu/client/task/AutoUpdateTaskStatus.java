package net.xssu.client.task;

import net.xssu.client.common.Constants;
import net.xssu.client.entity.ScanStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bonult on 2017/5/6.
 *
 * 定时器，每隔一定时间发送最新的任务状态到主控服务器
 *
 * 使用到了spring的 cron 表达式，参考资料 http://blog.csdn.net/u010648555/article/details/52162840
 */
@Component
@Lazy(false)
public class AutoUpdateTaskStatus {

	private static final Map<String,ScanStatus> taskStatusUpdatingMap = new ConcurrentHashMap<>();

	public static void updateTaskStatus(Integer taskId, ScanStatus scanStatus){
		taskStatusUpdatingMap.put(taskId + "-" + scanStatus.getShardId(), scanStatus);
	}

	/**
	 * 每5秒执行一次
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	public void autoUpdateTaskStatus(){
		//System.out.println("定时器执行了一次");
		ScanStatus sr = null;
		Set<String> set = taskStatusUpdatingMap.keySet();
		if(set.size() == 0){
			try{
				sendPOST(Constants.MAIN_SERVER_URL + "/a/updt2", "node_id=" + URLEncoder.encode(Constants.NODE_ID, "UTF-8"));
			}catch(Exception e){
				//e.printStackTrace();
			}
		}else
			for(String taskidshardid : set){
				try{
					sr = taskStatusUpdatingMap.remove(taskidshardid);
					if(sr != null){
						sendPOST(Constants.MAIN_SERVER_URL + "/a/updt", "node_id=" + URLEncoder.encode(Constants.NODE_ID, "UTF-8") + "&task_id=" + sr.getTaskId() + "&prog=" + URLEncoder.encode(sr.getProg() + "", "UTF-8") + "&result_count=" + sr.getResultCount() + "&rate=" + URLEncoder.encode(sr.getRate() + "", "UTF-8") + "&remaining=" + URLEncoder.encode(sr.getRemaining() + "", "UTF-8") + "&shard=" + sr.getShards() + "&shardId=" + sr.getShardId());
						//taskStatusUpdatingMap.remove(taskidshardid);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}

	}

	private static String sendPOST(String URL, String param) throws IOException{
		if(param.length() > 30)
			System.out.println("POST " + URL + "?" + param);
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		URL realUrl = new URL(URL);
		// 打开和URL之间的连接
		URLConnection conn = realUrl.openConnection();
		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		out = new PrintWriter(conn.getOutputStream());
		// 发送请求参数
		out.print(param);
		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while((line = in.readLine()) != null){
			result += line;
		}
		if(out != null){
			out.close();
		}
		if(in != null){
			in.close();
		}
		return result;
	}

	/**
	 * 每天两点执行任务，删除两天前的文件
	 */
	@Scheduled(cron = "0 0 2 ? * *")
	public void dingshiDeleteOldFiles(){
		File d = new File("/masscan/output");
		if(d.exists() && d.isDirectory()){
			File[] fs = d.listFiles();
			long curr = System.currentTimeMillis();
			for(File f : fs){
				try{
					if(curr - f.lastModified() > 1000 * 3600 * 24 * 2)
						f.delete();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

	}
}
