package com.kbk.fep.mngr.dao.vo;

import java.io.Serializable;

public class FepLineInfoVo implements Serializable {
	
	private int seqNo;		// 연번
	private String extCd;	// 기관코드
	private String extNm;	// 기관명

	private String bizCd; 	// 업무코드
	private String bizNm;	// 업무명
	private String bizType;	// 업무유형(온라인/배치)
	private String bizClcd;	// 업무분류코드

	private String nwLine;	// 전용회선정보
	private String nwRouter;// 라우터정보
	private String fwVpn;	// VPN 정보

	private String devClcd; // 개발운영 구분코드
	private String kbkIp;	// 당행서버IP
	private String kbkNatIp;// 당행 NAT IP
	private String kbkPort;	// 당행 포트
	private String extIp;	// 기관 서버IP
	private String extPort;	// 기관 포트
	private String srType;	// 송수신 유형

	private String extUser;	// 상대기관 담당자
	private String history;	// 이력
	
	
	@Override
	public String toString() {
		return "FepLineInfoVo [seqNo=" + seqNo + ", extCd=" + extCd
				+ ", extNm=" + extNm + ", bizCd=" + bizCd + ", bizNm=" + bizNm
				+ ", bizType=" + bizType + ", bizClcd=" + bizClcd + ", nwLine="
				+ nwLine + ", nwRouter=" + nwRouter + ", fwVpn=" + fwVpn
				+ ", devClcd=" + devClcd + ", kbkIp=" + kbkIp + ", kbkNatIp="
				+ kbkNatIp + ", kbkPort=" + kbkPort + ", extIp=" + extIp
				+ ", extPort=" + extPort + ", srType=" + srType + ", extUser="
				+ extUser + ", history=" + history + "]";
	}
	public String getDevClcd() {
		return devClcd;
	}
	public void setDevClcd(String devClcd) {
		this.devClcd = devClcd;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getExtCd() {
		return extCd;
	}
	public void setExtCd(String extCd) {
		this.extCd = extCd;
	}
	public String getExtNm() {
		return extNm;
	}
	public void setExtNm(String extNm) {
		this.extNm = extNm;
	}
	public String getBizCd() {
		return bizCd;
	}
	public void setBizCd(String bizCd) {
		this.bizCd = bizCd;
	}
	public String getBizNm() {
		return bizNm;
	}
	public void setBizNm(String bizNm) {
		this.bizNm = bizNm;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getBizClcd() {
		return bizClcd;
	}
	public void setBizClcd(String bizClcd) {
		this.bizClcd = bizClcd;
	}
	public String getNwLine() {
		return nwLine;
	}
	public void setNwLine(String nwLine) {
		this.nwLine = nwLine;
	}
	public String getNwRouter() {
		return nwRouter;
	}
	public void setNwRouter(String nwRouter) {
		this.nwRouter = nwRouter;
	}
	public String getFwVpn() {
		return fwVpn;
	}
	public void setFwVpn(String fwVpn) {
		this.fwVpn = fwVpn;
	}
	public String getKbkIp() {
		return kbkIp;
	}
	public void setKbkIp(String kbkIp) {
		this.kbkIp = kbkIp;
	}
	public String getKbkNatIp() {
		return kbkNatIp;
	}
	public void setKbkNatIp(String kbkNatIp) {
		this.kbkNatIp = kbkNatIp;
	}
	public String getKbkPort() {
		return kbkPort;
	}
	public void setKbkPort(String kbkPort) {
		this.kbkPort = kbkPort;
	}
	public String getExtIp() {
		return extIp;
	}
	public void setExtIp(String extIp) {
		this.extIp = extIp;
	}
	public String getExtPort() {
		return extPort;
	}
	public void setExtPort(String extPort) {
		this.extPort = extPort;
	}
	public String getSrType() {
		return srType;
	}
	public void setSrType(String srType) {
		this.srType = srType;
	}
	public String getExtUser() {
		return extUser;
	}
	public void setExtUser(String extUser) {
		this.extUser = extUser;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
}
