package com.kbk.fep.mngr.svc;

import java.util.List;

public interface FepSimulatorSvc {
	
	public List<String> getAppList();
	public List<String> getMsgList(String app);
	
	public String getTxName(String app, String msg);
	public String getReqCols(String app, String msg);
	public String getResCols(String app, String msg);
	public String getRule(String app, String msg);
	public String getPort(String app, String msg);
	public String getLengStl(String app, String msg);
	
	public void save(String app, String msg, String msgname, String reqcols, String rescols, String rule, String port, String lengstl) ; 
	public byte [] agenyResponse(byte [] input, String rule);
	
	public void startThread(String app, String msg, String port);
	public void stopThread(String app, String msg);
	public List<String> getThreadList();
	
	
}
