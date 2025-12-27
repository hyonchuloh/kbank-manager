package com.kbk.fep.mngr.svc;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.kbk.fep.mngr.dao.vo.FepAllineVo;
import com.kbk.fep.mngr.dao.vo.FepSmsInfoVo;

public interface FepManagerSvc {
	
	public int updateObject(String sql) throws Exception; 
	
	public List<List<String>> selectObject(String sql) throws Exception;
	
	public void selectObject_excel(HttpServletResponse response, String sql) throws Exception;

	public String testOut(String url, String data);
	
	public ResponseEntity<String> testOutHttp(String url, String header, String body, String method, String param);
	
	public String testIn(String Ip, String port, String lengthStyle, String inputData);
	
	public String testInForUtf8(String Ip, String port, String lengthStyle, String inputData);
	
	public String testInAsync(String Ip, String port, String lengthStyle, String inputData) ;
	
	public List<FepSmsInfoVo> getSmsList(String filePath);
	
	public int [][] getCalendarTable(Calendar calendar, int year, int month) ;
	
	public Map<String, String> loadMap(String filePath) ;
	
	public int saveMap(String filePath, String key, String value);
	
	public String ftpView(String pathView) throws Exception;
	
	public void decryptPI(String infile, String outfile) throws Exception;
	
	public String selectLineIpFor089CRD();
	
	public List<FepAllineVo> selectLineListForCrdU2l();
	
	public List<FepAllineVo> selectLineListForCrdU2lHistory();
	
	public String selectUrlForCrdU2l();
	
	public int updateLineUrlForCrdU2l(String gwname, String symbname, String linename, String luname, String requestIp) ;
	
	public int updateLineIpForCrdU2l(String gwname, String symbname, String linename, String luname, String requestIp);
	
}
