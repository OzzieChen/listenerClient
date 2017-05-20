package net.xssu.client.service.impl;

import com.sun.istack.internal.NotNull;
import net.xssu.client.common.Constants;
import net.xssu.client.entity.ScanStatus;
import net.xssu.client.entity.ScanTask;
import net.xssu.client.service.IRedisService;
import net.xssu.client.service.IScanService;
import net.xssu.client.task.AutoUpdateTaskStatus;
import net.xssu.client.util.CastUtil;
import net.xssu.client.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *  Docker volume hierarchy
 *  ----------------------------
 *    |- / (root)
 *    |---- masscan/
 *    |-------------- masscan
 *    |-------------- conf/
 *    |-------------- output/
 *    |-------------- pattern/
 *  ----------------------------
 */
public class ServiceDetectScanServiceImpl implements IScanService {
	@Autowired
	private IRedisService redisService;

	private List<String> commands = new ArrayList<String>();

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

	public List<String> generateScanConfig(@NotNull ScanTask task){
		Properties configProp = Constants.getConfigProperties();

        /* targets */
		commands.add("--range");
		commands.add(task.getIps());
		commands.add("--ports");
		commands.add(task.getPorts());

        /* shards & seed */
		commands.add("--shards");
		commands.add(task.getShards());
		commands.add("--seed");
		commands.add(CastUtil.castString(task.getSeed()));

        /* adapter */

		commands.add("--adapter-port");
		commands.add(configProp.getProperty("adapter-port"));

    	/* output */
		commands.add("--output-format");
		commands.add(configProp.getProperty("output-format"));
		commands.add("--output-filename");
		commands.add(task.getOutputFilename());

    	/* service detect */
		commands.add("--service-detect");
		commands.add("true");
		commands.add("--hello-first");
		commands.add(task.getHelloFirst() ? "true" : "false");

		commands.add("--service-patterns-file");
		String patternFilePath = generatePatternsFile(String.valueOf(task.getPatternId()));
		commands.add(patternFilePath != null ? patternFilePath : " ");

    	/* others */
		commands.add("--rate");
		commands.add(String.valueOf(task.getRate()));

		commands.add("--output-banner");
		commands.add(String.valueOf(task.getBanner()));
		commands.add("--output-status");//TODO
		commands.add(String.valueOf(task.getOthers()));

		commands.add("--hex_or_string");
		commands.add(task.getHexOrString());

		commands.add("--banners");


		return commands;
	}

	public File scan(ScanTask task, List<String> commands, String processDirectory, String processName){
		commands.add(0, "./" + processName);
		File processFile = new File(processDirectory);
		ProcessBuilder pBuilder = new ProcessBuilder(commands);
		pBuilder.directory(processFile);
		LOGGER.debug("[log4j][ServiceDetectServiceImpl] pBuilder.directory = " + pBuilder.directory());
		pBuilder.redirectErrorStream(true);
		Process p = null;
		try{
			p = pBuilder.start();
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			ScanStatus sr = new ScanStatus();
			sr.setTaskId(task.getTaskId());
			sr.setShardId(task.getShardId());
			sr.setShards(task.getShards());
			System.out.println("开始扫描");
			CharArrayWriter charArrayWriter = new CharArrayWriter(10);
			String dataLine = "";
			int i = 0;
			try{
				while((lineStr = inBr.readLine()) != null){
					System.out.println(lineStr);
					if(lineStr.startsWith("rate")){
						dataLine = lineStr;
						if(i > 4){
							parse(lineStr, sr, charArrayWriter);
							AutoUpdateTaskStatus.updateTaskStatus(task.getTaskId(), sr);
							i = 0;
						}else
							i++;
					}
				}
				if(i > 0){
					parse(dataLine, sr, charArrayWriter);
					AutoUpdateTaskStatus.updateTaskStatus(task.getTaskId(), sr);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			if(p.waitFor() != 0){
				if(p.exitValue() == 1){
					LOGGER.error("[log4j][ServiceDetectServiceImpl] Execute process error");
					return null;
				}
			}
			File outputFile = new File(task.getOutputFilename());
			return outputFile;
		}catch(IOException e){
			e.printStackTrace();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return null;
	}

	private void parse(String lineStr, ScanStatus sr, CharArrayWriter charArrayWriter){
		//rate:  0.00-kpps, 100.00% done, waiting 4-secs, found=17
		//rate:  0.10-kpps, 82.42% done,   0:00:01 remaining, found=14
		double prog, rate;
		int resultCount;
		String remaining;
		int i = 5;
		int c = 0;
		while(i < lineStr.length()){
			c = lineStr.charAt(i);
			i++;
			if((c >= '0' && c <= '9') || c == '.'){
				charArrayWriter.write(c);
			}else if(c == '-')
				break;
		}
		charArrayWriter.flush();
		rate = Double.parseDouble(new String(charArrayWriter.toCharArray()));
		i = i + 6;
		charArrayWriter.reset();
		while(i < lineStr.length()){
			c = lineStr.charAt(i);
			i++;
			if((c >= '0' && c <= '9') || c == '.'){
				charArrayWriter.write(c);
			}else if(c == '%')
				break;
		}
		charArrayWriter.flush();
		prog = Double.parseDouble(new String(charArrayWriter.toCharArray()));
		charArrayWriter.reset();
		i = i + 6;
		while(i < lineStr.length()){
			c = lineStr.charAt(i);
			if(!(c >= '0' && c <= '9')&&c!='-')
				i++;
			else
				break;
		}
		if(c=='-'){
			charArrayWriter.write(c);
			c = lineStr.charAt(++i);
		}
		while(i < lineStr.length()){
			i++;
			if((c >= '0' && c <= '9') || c == ':'){
				charArrayWriter.write(c);
			}else if(c == '-' || c == ' ')
				break;
			c = lineStr.charAt(i);
		}
		charArrayWriter.flush();
		remaining = new String(charArrayWriter.toCharArray());
		charArrayWriter.reset();
		i = i + 12;
		while(i < lineStr.length()){
			c = lineStr.charAt(i);
			i++;
			if((c >= '0' && c <= '9')){
				charArrayWriter.write(c);
			}else if(c == ' ')
				break;
		}
		charArrayWriter.flush();
		resultCount = Integer.parseInt(new String(charArrayWriter.toCharArray()));
		charArrayWriter.reset();

		sr.setResultCount(resultCount);
		sr.setProg(prog>100.00001?100.0:prog);
		sr.setRemaining(remaining.length() > 2 ? remaining : (remaining.startsWith("-")?"0":remaining) + "秒");
		sr.setRate(rate);
	}

	/**
	 * Generate patterns file
	 *
	 * @return pattern's file path, null if generation failed
	 */
	public String generatePatternsFile(String patternId){
		/* Retrieve pattern string from slave redis */
		Properties redisProp = Constants.getRedisProperties();
		String hKey = redisProp.getProperty("redis.patterns.key");
		String patternStr = redisService.getPatternString(hKey, patternId);
        
    	/* Generate pattern file */
		String patternsFileDirectory = "/masscan/pattern/";
		try{
            String filename = "pattern_" + patternId + ".txt";
            File patternFile = new File(patternsFileDirectory + filename);
			if(!patternFile.exists()){
				if(!patternFile.createNewFile()){
					return null;
				}
			}
			Writer writer = new OutputStreamWriter(new FileOutputStream(patternFile));
			writer.append(patternStr);
			writer.close();
			return patternFile.getAbsolutePath();
		}catch(IOException e){
			e.printStackTrace();
			LOGGER.error("[log4j][ServiceDetectServiceImpl] pattern file generate failed");
		}
		return null;
	}
}

















