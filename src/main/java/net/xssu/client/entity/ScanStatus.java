package net.xssu.client.entity;

public class ScanStatus {
	private Integer taskId;
	private int resultCount;//扫描到的结果数量
	private double prog;//扫描进度，小数点后四位有效位
	private double rate;//扫描速度 kpps
	private String remaining;
	private String shards;

	public ScanStatus(){
	}

	public Integer getTaskId(){
		return taskId;
	}
	public void setTaskId(Integer taskId){
		this.taskId = taskId;
	}
	public int getResultCount(){
		return resultCount;
	}
	public void setResultCount(int resultCount){
		this.resultCount = resultCount;
	}
	public double getProg(){
		return prog;
	}
	public void setProg(double prog){
		this.prog = prog;
	}
	public double getRate(){
		return rate;
	}
	public void setRate(double rate){
		this.rate = rate;
	}
	public String getRemaining(){
		return remaining;
	}
	public void setRemaining(String remaining){
		this.remaining = remaining;
	}
	public String getShards(){
		return shards;
	}
	public void setShards(String shards){
		this.shards = shards;
	}
}
