package com.kbk.fep.mngr.dao;

public interface FepApiDao {
	
	public void cmdDownGw(String gwName) throws Exception;
	
	public void cmdBootGw(String gwName) throws Exception;

}
