package net.xssu.client.entity;

import java.io.Serializable;

public class ScanTask implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer taskId;
    private String ips;
    private String ports;
    private String shards;
    private long seed;
    private Boolean isServiceDetect;
    private Boolean isHelloFirst;
    //private long patternId;
    private String pattern;
    private Integer shardId;
    private Integer rate;
    private Boolean banner;
    private Boolean others;
    private String hexOrString;

    private String outputFilename;

    public ScanTask() {}

    public ScanTask(Integer taskId, String ips, String ports, String shards, long seed,
                    Boolean isServiceDetect, Boolean isHelloFirst, long patternId) {
        this.taskId = taskId;
        this.ips = ips;
        this.ports = ports;
        this.shards = shards;
        this.seed = seed;
        this.isServiceDetect = isServiceDetect;
        this.isHelloFirst = isHelloFirst;
        //this.patternId = patternId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getShards() {
        return shards;
    }

    public void setShards(String shards) {
        this.shards = shards;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public Boolean getServiceDetect() {
        return isServiceDetect;
    }

    public void setServiceDetect(Boolean serviceDetect) {
        isServiceDetect = serviceDetect;
    }

    public Boolean getHelloFirst() {
        return isHelloFirst;
    }

    public void setHelloFirst(Boolean helloFirst) {
        isHelloFirst = helloFirst;
    }

//    public long getPatternId() {
//        return patternId;
//    }
//
//    public void setPatternId(long patternId) {
//        this.patternId = patternId;
//    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
    public Integer getShardId(){
        return shardId;
    }
    public void setShardId(Integer shardId){
        this.shardId = shardId;
    }
    public Integer getRate(){
        return rate;
    }
    public void setRate(Integer rate){
        this.rate = rate;
    }
    public Boolean getBanner(){
        return banner;
    }
    public void setBanner(Boolean banner){
        this.banner = banner;
    }
    public Boolean getOthers(){
        return others;
    }
    public void setOthers(Boolean others){
        this.others = others;
    }
    public String getHexOrString(){
        return hexOrString;
    }
    public void setHexOrString(String hexOrString){
        this.hexOrString = hexOrString;
    }
    public String getPattern(){
        return pattern;
    }
    public void setPattern(String pattern){
        this.pattern = pattern;
    }
}
