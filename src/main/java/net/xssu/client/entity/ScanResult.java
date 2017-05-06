package net.xssu.client.entity;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class ScanResult implements Serializable{
    private Integer taskId;
    private String clientId;
    private byte[] resultFileBytes; // JSON file
    private Integer resultCount;//扫描到的结果数量
    private Double prog;//扫描进度，小数点后四位有效位
    private Double rate;//扫描速度 kpps
    private String remaining;

    public ScanResult() {}

    public ScanResult(Integer taskId, String clientId, File resultFile) {
        this.taskId = taskId;
        this.clientId = clientId;

        try {
            byte[] fileBytes = IOUtils.toByteArray(new FileInputStream(resultFile));
            this.resultFileBytes = fileBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public byte[] getResultFileBytes() {
        return resultFileBytes;
    }

    public void setResultFileBytes(byte[] resultFileBytes) {
        this.resultFileBytes = resultFileBytes;
    }

    public Integer getResultCount(){
        return resultCount;
    }
    public void setResultCount(Integer resultCount){
        this.resultCount = resultCount;
    }
    public Double getProg(){
        return prog;
    }
    public void setProg(Double prog){
        this.prog = prog;
    }
    public Double getRate(){
        return rate;
    }
    public void setRate(Double rate){
        this.rate = rate;
    }
    public String getRemaining(){
        return remaining;
    }
    public void setRemaining(String remaining){
        this.remaining = remaining;
    }
}
