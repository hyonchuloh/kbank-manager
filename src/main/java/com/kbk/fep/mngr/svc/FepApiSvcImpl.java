package com.kbk.fep.mngr.svc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbk.fep.mngr.dao.FepApiDao;

@Service
public class FepApiSvcImpl implements FepApiSvc {
	
	@Autowired
	public FepApiDao dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean restartGw(String gwName) {
		
		boolean retValue = false;
		
		logger.info("--- API RESTART GW ["+gwName+"]");
		
		try {
			
			logger.info("--- API DOWN GW ["+gwName+"] ");
			dao.cmdDownGw(gwName);
			Thread.sleep(1000);
			logger.info("--- API BOOT GW ["+gwName+"]");
			dao.cmdBootGw(gwName);
			
		} catch ( Exception e ) {
			logger.error("--- ERROR : ", e);
		}
		
		return retValue;
	}

}
