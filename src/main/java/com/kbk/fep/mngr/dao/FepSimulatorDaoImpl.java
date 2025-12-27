package com.kbk.fep.mngr.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kbk.fep.mngr.dao.vo.FepCommPropVo;

@Repository
public class FepSimulatorDaoImpl implements FepSimulatorDao {
	
	@Autowired
	private FepCommPropVo prop;
	private String dbPath = "sim.dat";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, String> resultMap;

	@Override
	public void saveItem(String key, String value) {
		
		if ( this.resultMap == null ) {
			this.dbPath = prop.getFepSimDb();
			resultMap = loadItem();
		}
		
		ObjectOutputStream oos = null;
		resultMap.put(key, value);
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(dbPath));
			oos.writeObject(resultMap);
			
		} catch ( Exception e ) {
			logger.error("--- 에러발생 : ", e);
		} finally {
			try {
				if ( oos != null ) oos.close();
			} catch ( Exception e2) {
				logger.error("--- 에러발생 : ", e2);
			}
		}
	}
	
	@Override
	public Map<String, String> loadItem() {
		
		if ( this.resultMap == null ) {
			this.dbPath = prop.getFepSimDb();
		}
		
		Map<String, String> retValue = null;
		ObjectInputStream ois = null;
		
		try {
			if ( new File(dbPath).exists() ) {
				ois = new ObjectInputStream(new FileInputStream(dbPath));
				retValue = (Map<String, String>) ois.readObject();
			} else {
				retValue = new HashMap<String, String>();
			}
			
		} catch ( Exception e ) {
			logger.error("--- 에러발생 : ", e);
		} finally {
			try {
				if ( ois != null ) ois.close();
			} catch ( Exception e2) {
				logger.error("--- 에러발생 : ", e2);
			}
		}
		return retValue;
		
	}
}
