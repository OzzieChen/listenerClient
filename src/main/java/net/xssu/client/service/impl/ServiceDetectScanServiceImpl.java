package net.xssu.client.service.impl;

import com.sun.istack.internal.NotNull;
import net.xssu.client.entity.ScanResult;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServiceDetectScanServiceImpl implements IScanService {
    @Autowired
    private IRedisService redisService;

    private List<String> commands = new ArrayList<String>();

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    public List<String> generateScanConfig(@NotNull ScanTask task) {
        Properties configProp = PropertiesUtil.loadProps("properties/config.properties");

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
        commands.add(configProp.getProperty("rate")); // TODO: Rate should configured by users
        commands.add("--banners");
        commands.add("true");

        return commands;
    }

    public File scan(ScanTask task, List<String> commands, String processDirectory, String processName) {
        commands.add(0, "./" + processName);
        File processFile = new File(processDirectory);
        ProcessBuilder pBuilder = new ProcessBuilder(commands);
        pBuilder.directory(processFile);
        LOGGER.debug("[log4j][ServiceDetectServiceImpl] pBuilder.directory = " + pBuilder.directory());
        pBuilder.redirectErrorStream(true);
        Process p = null;
        try {
            p = pBuilder.start();
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            ScanResult sr = new ScanResult();
            sr.setTaskId(task.getTaskId());
            try{
                while((lineStr = inBr.readLine()) != null){
                    if(lineStr.startsWith("rate")){
                        //rate:  0.00-kpps, 100.00% done, waiting 4-secs, found=17
                        //rate:  0.10-kpps, 82.42% done,   0:00:01 remaining, found=14
                        int idxOf1stComma = lineStr.indexOf(',');
                        int idxOfPercentSign = lineStr.indexOf('%');
                        int idxOfEqualSign = lineStr.indexOf('=');
                        sr.setResultCount(Integer.parseInt(lineStr.substring(idxOfEqualSign+1)));
                        sr.setProg(Double.parseDouble(lineStr.substring(idxOf1stComma+2,idxOfPercentSign)));
                        AutoUpdateTaskStatus.updateTaskStatus(task.getTaskId(), sr);
                    }
                    System.out.println(lineStr);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            while ((lineStr = inBr.readLine()) != null){
                System.out.println(lineStr);
            }
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1) {
                    LOGGER.error("[log4j][ServiceDetectServiceImpl] Execute process error");
                    return null;
                }
            }
            File outputFile = new File(task.getOutputFilename());
            return outputFile;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generate patterns file
     *
     * @return pattern's file path, null if generation failed
     */
    public String generatePatternsFile(String patternId) {
        /* Retrieve pattern string from slave redis */
        Properties redisProp = PropertiesUtil.loadProps("properties/redis.properties");
        String hKey = redisProp.getProperty("redis.patterns.key");
        String patternStr = redisService.getPatternString(hKey, patternId);

        /* Generate pattern file */
        String rootPath = this.getClass().getClassLoader().getResource("").getPath();
        String patternsFileDirectory = rootPath + redisProp.getProperty("redis.patterns.filepath");
        try {
            String directory = URLDecoder.decode(rootPath, "UTF-8");
            String filename = "pattern_" + patternId;
            File patternFile = new File(directory + filename);
            if (!patternFile.exists()) {
                if (!patternFile.createNewFile()) {
                    return null;
                }
            }
            Writer writer = new OutputStreamWriter(new FileOutputStream(patternFile));
            writer.append(patternStr);
            writer.close();
            return patternFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("[log4j][ServiceDetectServiceImpl] pattern file generate failed");
        }
        return null;
    }
}

















