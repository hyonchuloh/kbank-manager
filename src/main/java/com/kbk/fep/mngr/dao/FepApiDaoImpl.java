package com.kbk.fep.mngr.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * 실제 서버에 커멘드를 내리는 역할 수행
 * @author ohhyonchul
 *
 */
@Repository
public class FepApiDaoImpl implements FepApiDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void cmdBootGw(String gwName) throws Exception {
		logger.info("--- ANYLINK COMMAND BOOT START ["+ gwName +"]");
		long startMs = System.currentTimeMillis();
		String [] cmd = {"sh", "-c", "echo", gwName};	// echo 대신 tmboot를
		if ( isWindows() == true ) {
			cmd[0] = "cmd.exe";
			cmd[1] = "/c";
		}
		
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			while ( (line = br.readLine() ) != null ) {
				logger.info("------ OUTPUT : " +  line);
			}
		} catch ( Exception e ) {
			if ( process != null ) process.destroy();
			throw e;
		} finally {
			logger.info("--- ANYLINK COMMAND BOOT END ["+ gwName +"] elapse time ["+ ( System.currentTimeMillis() - startMs )+"] ms");
		}
	}
	
	@Override
	public void cmdDownGw(String gwName) throws Exception {
		logger.info("--- ANYLINK COMMAND DOWN START ["+ gwName +"]");
		long startMs = System.currentTimeMillis();
		Process process = null;
		String [] cmd = {"sh", "-c", "echo", gwName};	// echo 대신 tmdown을
		if ( isWindows() == true ) {
			cmd[0] = "cmd.exe";
			cmd[1] = "/c";
		}
		
		try {
			process = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			while ( (line = br.readLine() ) != null ) {
				logger.info("------ OUTPUT : " +  line);
			}
		} catch ( Exception e ) {
			if ( process != null )  process.destroy();
			throw e;
		} finally {
			logger.info("--- ANYLINK COMMAND DOWN END ["+ gwName +"] elapse time ["+ ( System.currentTimeMillis() - startMs )+"] ms");
		}
		
	}
	
	public boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().startsWith("windows");
	}

}
