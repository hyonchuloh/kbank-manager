package com.kbk.fep.mngr.dao.vo;

public class FepSmsInfoVo {
	
	private String id;
	private String instName;
	private String applName;
	private String startTime;
	private String endTime;
	private String holydayYn;
	private String target;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	public String getApplName() {
		return applName;
	}
	public void setApplName(String applName) {
		this.applName = applName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getHolydayYn() {
		return holydayYn;
	}
	public void setHolydayYn(String holydayYn) {
		this.holydayYn = holydayYn;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
