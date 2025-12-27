package com.kbk.fep.mngr.dao.vo;

import com.kbk.fep.util.PropUtil;

public class FepAlbatStatVo {
	
	private String instCode;
	private String applCode;
	private String fileCode;
	private String srFlag;
	private String strtDate;
	private String strtTime;
	private String extProcFlag;
	private String endDate;
	private String endTime;
	private long extFinNo;
	private int failCode;
	private int strtSendType;
	private String fileName;
	private long totFileNo;
	private String resCode;
	private int filler1;
	private String filler2;
	
	/* ����¡ ó��*/
	private String startRowNum;
	private String selectCount;

	private long elapsTime;
	private String errMsg;
	
	/* 2022.01.31 eai interface id*/
	private String eaiIntfId;
	
	public FepAlbatStatVo() {
	}

	
	public String getEaiIntfId() {
		return eaiIntfId;
	}


	public void setEaiIntfId(String eaiIntfId) {
		this.eaiIntfId = eaiIntfId;
	}


	public String getStartRowNum() {
		return startRowNum;
	}


	public void setStartRowNum(String startRowNum) {
		this.startRowNum = startRowNum;
	}


	public String getSelectCount() {
		return selectCount;
	}


	public void setSelectCount(String selectCount) {
		this.selectCount = selectCount;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getInstCode() {
		return instCode;
	}


	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}


	public String getApplCode() {
		return applCode;
	}


	public void setApplCode(String applCode) {
		this.applCode = applCode;
	}


	public String getFileCode() {
		return fileCode;
	}


	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}


	public String getSrFlag() {
		return srFlag;
	}


	public void setSrFlag(String srFlag) {
		this.srFlag = srFlag;
	}


	public String getStrtDate() {
		return strtDate;
	}


	public void setStrtDate(String strtDate) {
		this.strtDate = strtDate;
	}


	public String getStrtTime() {
		return strtTime;
	}


	public void setStrtTime(String strtTime) {
		this.strtTime = strtTime;
	}


	public String getExtProcFlag() {
		return extProcFlag;
	}


	public void setExtProcFlag(String extProcFlag) {
		this.extProcFlag = extProcFlag;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public long getExtFinNo() {
		return extFinNo;
	}


	public void setExtFinNo(long extFinNo) {
		this.extFinNo = extFinNo;
	}


	public int getFailCode() {
		return failCode;
	}


	public void setFailCode(int failCode) {
		this.failCode = failCode;
		String msg = PropUtil.getPropertiesValue("error", "BT." + Integer.toString(failCode));
		if ( msg != null ) {
			this.errMsg = msg;
		}
	}


	public int getStrtSendType() {
		return strtSendType;
	}


	public void setStrtSendType(int strtSendType) {
		this.strtSendType = strtSendType;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public long getTotFileNo() {
		return totFileNo;
	}


	public void setTotFileNo(long totFileNo) {
		this.totFileNo = totFileNo;
	}


	public String getResCode() {
		return resCode;
	}


	public void setResCode(String resCode) {
		this.resCode = resCode;
	}


	public int getFiller1() {
		return filler1;
	}


	public void setFiller1(int filler1) {
		this.filler1 = filler1;
	}


	public String getFiller2() {
		return filler2;
	}


	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}


	public long getElapsTime() {
		return elapsTime;
	}


	public void setElapsTime(long elapsTime) {
		this.elapsTime = elapsTime;
	}


	public String getErrMsg() {
		return errMsg;
	}


	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	

}
