package net.xssu.client.task;

import net.xssu.client.common.Constants;
import net.xssu.client.entity.ScanResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
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

	private static final Map<Integer,ScanResult> taskStatusUpdatingMap = new ConcurrentHashMap<>();

	static{//test
		ScanResult sr = new ScanResult();
		sr.setProg(0.66);
		sr.setResultCount(700);
		taskStatusUpdatingMap.put(0, sr);
		ScanResult sr2 = new ScanResult();
		sr2.setProg(0.33);
		sr2.setResultCount(400);
		taskStatusUpdatingMap.put(0, sr);
		ScanResult sr3 = new ScanResult();
		sr3.setProg(0.88);
		sr3.setResultCount(900);
		taskStatusUpdatingMap.put(0, sr);
	}

	public static void updateTaskStatus(int taskId, ScanResult scanResult){
		taskStatusUpdatingMap.put(taskId, scanResult);
	}

	/**
	 * 每五秒执行一次
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	public void autoUpdateTaskStatus(){
		System.out.println("定时器发送了一次数据");
		ScanResult sr = null;
		for(Integer taskid : taskStatusUpdatingMap.keySet()){
			sr = taskStatusUpdatingMap.get(taskid);
			try{
				sendPOST(Constants.MAIN_SERVER_URL + "/a/updt", "node_id=" + URLEncoder.encode(Constants.NODE_ID, "UTF-8") + "&task_id=" + taskid + "&prog=" + URLEncoder.encode(sr.getProg().toString(), "UTF-8") + "&result_count=" + sr.getResultCount());
				taskStatusUpdatingMap.remove(taskid);
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}

	private static String sendPOST(String URL, String param){
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try{
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
		}catch(Exception e){
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}finally{
			try{
				if(out != null){
					out.close();
				}
				if(in != null){
					in.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
}
