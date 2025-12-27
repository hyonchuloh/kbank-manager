package com.kbk.fep.mngr.svc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbk.fep.mngr.dao.FepSimulatorDao;

@Service
public class FepSimulatorSvcImpl implements FepSimulatorSvc {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String, String> data;
	@Autowired
	private FepSimulatorDao simDao;
	
	private Map<String, FepSimulatorThread> threadMap = new HashMap<String, FepSimulatorThread>();
	
	@Override
	public List<String> getThreadList() {
		List<String> retValue = new ArrayList<String>();
		for ( String key : threadMap.keySet() ) {
			retValue.add(key);
		}
		return retValue;
	}
	
	@Override
	public void startThread(String app, String msg, String port) {
		FepSimulatorThread thread = new FepSimulatorThread(this.getRule(app, msg), Integer.parseInt(port));
		this.threadMap.put(app + "_" + msg, thread);
		thread.start();
	}
	
	@Override
	public void stopThread(String app, String msg) {
		FepSimulatorThread thread = this.threadMap.get(app + "_" + msg);
		thread.destroy();
		this.threadMap.remove(app+"_"+msg);
	}
	
	public List<String> getAppList() {
		if ( data == null ) data = simDao.loadItem();
		List<String> retValue = new ArrayList<String>();
		String tempData = "";
		for ( String key : data.keySet() ) {
			tempData = key.substring(4,8);
			if ( !retValue.contains(tempData) ) {
				retValue.add(tempData);
			}
		}
		return retValue;
	}
	
	public List<String> getMsgList(String app) {
		if ( data == null ) data = simDao.loadItem();
		List<String> retValue = new ArrayList<String>();
		String tempData = "";
		for ( String key : data.keySet() ) {
			if ( key.startsWith("sim." + app) ) {
				tempData = key.split("\\.")[2];
				if ( !retValue.contains(tempData) ) {
					retValue.add(tempData);
				}
			}
		}
		return retValue;
	}
	
	@Override
	public String getReqCols(String app, String msg) {
		if ( data == null ) data = simDao.loadItem();
		return data.get("sim." + app + "." + msg + ".reqcols");
	}
	
	@Override
	public String getResCols(String app, String msg) {
		if ( data == null ) data = simDao.loadItem();
		return data.get("sim." + app + "." + msg + ".rescols");
	}
	
	@Override
	public String getRule(String app, String msg) {
		if ( data == null ) data = simDao.loadItem();
		return data.get("sim." + app + "." + msg + ".rule");
	}
	
	@Override
	public String getTxName(String app, String msg) {
		if ( data == null ) data = simDao.loadItem();
		return data.get("sim." + app + "." + msg + ".msgname");
	}
	
	@Override
	public String getPort(String app, String msg) {
		if ( data == null ) data = simDao.loadItem();
		return data.get("sim." + app + "." + msg + ".port");
	}
	
	@Override
	public String getLengStl(String app, String msg) {
		if ( data == null ) data = simDao.loadItem();
		return data.get("sim." + app + "." + msg + ".lengstl");
	}
	
	@Override
	public void save(String app, String msg, String msgname, String reqcols, String rescols, String rule, String port, String lengstl) {
		simDao.saveItem("sim." + app + "." + msg + ".msgname", msgname);
		simDao.saveItem("sim." + app + "." + msg + ".reqcols", reqcols);
		simDao.saveItem("sim." + app + "." + msg + ".rescols", rescols);
		simDao.saveItem("sim." + app + "." + msg + ".rule", rule);
		simDao.saveItem("sim." + app + "." + msg + ".port", port);
		simDao.saveItem("sim." + app + "." + msg + ".lengstl", lengstl);
		this.data = simDao.loadItem();
	}
	
	@Override
    public byte [] agenyResponse(byte [] input, String rule) {
          
          byte [] output = null;
          
          /* 전문의 최종길이를 구한다 */
          int totalSizeOfResponseMssage = 0;
          for ( String k : rule.split("\\$\\{|\\}") )  {
                 if ( k.length() == 0 )
                        continue;
                 if ( k.indexOf(",") > 0 ) {
                        totalSizeOfResponseMssage += Integer.parseInt(k.split(",")[1]) - Integer.parseInt(k.split(",")[0]);
                 } else if ( k.startsWith("'") ) {
                        byte [] col = k.replaceAll("'", "").getBytes();
                        totalSizeOfResponseMssage += col.length;
                 }
          }
          
          /* 요청전문에 대한 대행응답 전문을 매핑한다 */
          output = new byte[totalSizeOfResponseMssage];
          int outputMsgPoint = 0;
          for ( String k : rule.split("\\$\\{|\\}") )  {
                 if ( k.length() == 0 )
                        continue;
                 if ( k.indexOf(",") > 0 ) {
                        String [] indexs = k.split(",");
                        int startIndex = Integer.parseInt(indexs[0]);
                        int endIndex = Integer.parseInt(indexs[1]);
                        System.arraycopy(input, startIndex, output, outputMsgPoint, endIndex-startIndex);
                        outputMsgPoint += endIndex-startIndex;
                 } else if ( k.startsWith("'") ) {
                        byte [] col = k.replaceAll("'", "").getBytes();
                        System.arraycopy(col, 0, output, outputMsgPoint, col.length);
                        outputMsgPoint += col.length;
                 }
          }
          
          return output;
    }

	
	@Deprecated
	public byte[] agencyResponse(byte[] input, String app, String msg) {
		
		if ( data == null ) data = simDao.loadItem();
		String rule = data.get("sim." + app + "." + msg + ".rule");
		byte [] output = null;
		
		logger.info("--- SIM INFO RULE ["+rule+"]");
		
		/* 전문의 최종 길이를 구한다. */
		int totalSizeOfResponseMessage = 0;
		for ( String k : rule.split("\\$\\{|\\}") ) {
			if ( k.length() == 0 ) 
				continue;
			if ( k.indexOf(",") > 0 ) {
				totalSizeOfResponseMessage += Integer.parseInt(k.split(",")[1]) - Integer.parseInt(k.split(",")[0]);
			} else if ( k.startsWith("'") ) {
				byte [] col = k.replaceAll("'", "").getBytes();
				totalSizeOfResponseMessage += col.length;
			}
		}
		
		/* 요청전문에 대한 대행응답 전문을 매핑한다. */
		output = new byte[totalSizeOfResponseMessage];
		int outputMsgPoint = 0;
		int startIndex, endIndex = 0;
		String [] indexes = null; 
		byte [] col = null;
		for ( String k : rule.split("\\$\\{|\\}") ) {
			if ( k.length() == 0 ) 
				continue;
			if ( k.indexOf(",") > 0 ) {
				indexes = k.split(",");
				startIndex = Integer.parseInt(indexes[0]);
				endIndex = Integer.parseInt(indexes[1]);
				System.arraycopy(input, startIndex, output, outputMsgPoint, endIndex-startIndex);
				outputMsgPoint += endIndex - startIndex;
			} else if ( k.startsWith("'") ) {
				col = k.replaceAll("'", "").getBytes();
				System.arraycopy(col, 0, output, outputMsgPoint, col.length);
				outputMsgPoint += col.length;
			}
		}
		return output;
	}
	
}
