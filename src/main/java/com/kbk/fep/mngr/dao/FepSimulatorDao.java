package com.kbk.fep.mngr.dao;

import java.util.Map;

public interface FepSimulatorDao {
	
	public void saveItem(String key, String value) ;
	
	public Map<String, String> loadItem() ;

}
