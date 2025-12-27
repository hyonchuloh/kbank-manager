package com.kbk.fep.mngr.ctl;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.svc.FepManagerSvc;
import com.kbk.fep.util.FepSessionUtil;
import com.kbk.fep.util.FepStaticDataInfo;

@Controller
@RequestMapping("/manager")
public class FepManagerCtl {
	
	@Autowired
	private FepCommPropVo prop;
	@Autowired
	private FepManagerSvc svc;
	@Autowired
	private FepSessionUtil session;
	@Autowired 
	private RestTemplateBuilder restTemplateBuilder;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/telnet")
	public String telnetTest(
			@RequestParam(value="test_ip", required=false) String test_ip, 
			@RequestParam(value="test_port", required=false) String test_port,
			@RequestParam(value="history", required=false) String history,
			Model model,
			HttpServletRequest request, HttpServletResponse response
			) {
		if ( test_ip == null ) 
			return "test/telnet";
		logger.info("---------------------------------------");
		Socket socket = new Socket();
		String result = "";
		
		try {
			
			/* 2022.06.22 세션검증*/
			String sessionUserName = session.getSessionInfo(request, response, "userName");
			logger.info("--- ACCESS USERNAME : " + sessionUserName);
			if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
				return "redirect:/login";
			}
			model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");

			socket.setSoTimeout(15000);
			SocketAddress addr = new InetSocketAddress(test_ip, Integer.parseInt(test_port));
			
			logger.info("--- TRY TO CONNECT TO REMOTE HOST");
			logger.info("--- HOST=["+test_ip+"], PORT=["+test_port+"]");
			logger.info("--- TRYING...");
//			socket.bind(new InetSocketAddress(47710));
			socket.connect(addr, 10000);
			logger.info("--- CONNECT COMPLATE!!!");
			result = "연결성공!!!";
		} catch ( Exception e ) {
			logger.info("--- CONNECT FAILURE!");
			result = "연결실패! ("+e.getMessage()+")";
		} finally {
			try {
				if ( socket != null ) socket.close();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		logger.info("---------------------------------------");
		model.addAttribute("result", result);
		model.addAttribute("history", "　　" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " : ["+test_ip+"]["+test_port+"]" + result + "<br/>" + history);
		model.addAttribute("test_ip", test_ip);
		model.addAttribute("test_port", test_port);
		return "test/telnet";
	}
	
	
	@RequestMapping(value="/ftp/list")
	@ResponseBody
	public String ftplist(
			@RequestParam(value="path") String path,
			HttpServletRequest request, HttpServletResponse response) {
		if ( path.length() > 2 && path.endsWith("/") ) {
			path = path.substring(0, path.length()-1);
		} else if ( path.startsWith("//") ) {
			path = path.substring(1);
		}
		/* dev = anylinkb */
		if ( "dev".equals(prop.getProfiles()) ) {
			if ( !path.contains("anylinkb") )
				path = path.replaceAll("anylink", "anylinkb");
		}
		logger.info("---------------------------------------");
		logger.info("--- URL : /ftp/list (?)");
		logger.info("--- REQUEST PARAM [path]	=["+path+"]");
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		
		File [] list = new File(path).listFiles();
		Map<String, String> folderlist = new TreeMap<String, String>();
		Map<String, String> filelist = new TreeMap<String, String>();
		for ( File file : list ) {
			if ( file.isDirectory() ) {
				folderlist.put(file.getName(), NumberFormat.getInstance().format(file.length()));
			} else {
				filelist.put(file.getName(), NumberFormat.getInstance().format(file.length()));
			}
		}
		String pathback = path;
		String [] pathItems = path.split("\\/");
		if ( pathItems.length > 2 ) 
			pathback = path.substring(0, path.lastIndexOf("/"));
		StringBuffer retValue = new StringBuffer();
		retValue.append("<html><body><style>body { font-family: d2coding; font-size: 10pt;}</style>");
		retValue.append("<img src='/images/folder1.png' style='width: 15px;' />");
		retValue.append("<a href='/manager/ftp/list?path="+pathback+"'>" + path + "</a>");
		retValue.append("<br/>");
		for ( String folder : folderlist.keySet() ) {
			retValue.append("&nbsp;&nbsp;<img src='/images/folder1.png' style='width: 15px;' />");
			retValue.append("<a href='/manager/ftp/list?path=" + path + "/" + folder + "'>" + folder +"</a>");
			retValue.append("<br/>");
		}
		for ( String file : filelist.keySet() ) {
			retValue.append("&nbsp;&nbsp;<img src='/images/file1.png' style='width: 15px;' />");
			retValue.append("<a href='/manager/ftp/download?path=" + path + "/" + file + "'>" + file +"</a> ("+filelist.get(file)+")");
			retValue.append("<br/>");
		}
		retValue.append("</body></html>");
		logger.info("---------------------------------------");
		return retValue.toString();
	}
	
	@RequestMapping(value="/ftp/download")
	public ResponseEntity<Resource> download(
			@RequestParam(value="path") String path,
			HttpServletRequest request, HttpServletResponse response)  throws Exception {
		logger.info("---------------------------------------");
		logger.info("--- URL : /manager/download (GET)");
		logger.info("--- REQUEST PARAM [path]	=["+path+"]");
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return null;
		}
		File file = new File(path);
		if ( file.getName().endsWith("enc") ) {
			svc.decryptPI(file.getAbsolutePath(), "/tmp/" + file.getName() + ".dec");
			file = new File("/tmp/" + file.getName() + ".dec");
		}
		Resource resource = new InputStreamResource(new FileInputStream(file));
		logger.info("---------------------------------------");
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
				.body(resource);
	}
	
	@GetMapping(value="/ftp/file")
	public ResponseEntity<Resource> file(
			@RequestParam(value="path", defaultValue="/kbksw/swdpt/anylink") String path,
			@RequestParam(value="filename") String filename,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("---------------------------------------");
		logger.info("--- URL : /manager/download (GET)");
		logger.info("--- REQUEST PARAM [path]	=["+path+"]");
		logger.info("--- REQUEST PARAM [filename]	=["+filename+"]");
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return null;
		}
		File file = new File(path + "/" + filename);	
		if ( path.equals("/") ) {
			file = new File(path + filename);
		}
		if ( filename.endsWith("enc") ) {
			svc.decryptPI(path + "/" + filename, "/tmp/" + filename + ".dec");
			file = new File("/tmp/" + filename + ".dec");
		}
		Resource resource = new InputStreamResource(new FileInputStream(file));
		logger.info("---------------------------------------");
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.body(resource);
	}
	
	@RequestMapping(value="/ftp")
	public String ftp (
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="path", defaultValue="/kbkdat") String path,
			@RequestParam(value="pathview", defaultValue="") String pathview,
			@RequestParam(value="checkdown", defaultValue="") String checkdown,
			Model model
			) {
		if ( path.length() > 2 && path.endsWith("/") ) {
			path = path.substring(0, path.length()-1);
		} else if ( path.startsWith("//") ) {
			path = path.substring(1);
		}
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /ftp");
		logger.info("--- REQUEST PARAM [path]	=["+path+"]");
		logger.info("--- REQUEST PARAM [pathview]	=["+pathview+"]");
		logger.info("--- REQUEST PARAM [checkdown]	=["+checkdown+"]");
		logger.info("--- ACCESS IP ["+request.getRemoteAddr()+"]");
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		
		/* 관리자가 아닌데 다른 경로를 access 하는 경우 /kbkdat로 고정 */
		if ( !"0.0.0.0".equals(request.getRemoteAddr()) ||
				!"0.0.0.0".equals(request.getRemoteAddr())) {
			if ( !path.startsWith("\\/kbkdat") || !path.startsWith("\\/kbklog") ) {
				path = "/kbkdat";
			}
		}
		
		File [] list = new File(path).listFiles();
		Map<String, String> folderlist = new TreeMap<String, String>();
		Map<String, String> filelist = new TreeMap<String, String>();
		boolean isDown = false;
		for ( File file : list ) {
			if ( file.isDirectory() ) {
				folderlist.put(file.getName(), NumberFormat.getInstance().format(file.length()));
			} else {
				filelist.put(file.getName(), NumberFormat.getInstance().format(file.length()));
				if ( pathview.length() > 0 && pathview.equals(file.getName())) {
					if ( file.length() > 2000000 ) {
						isDown = true;
					}
				}
			}
		}
		model.addAttribute("path", path);
		String [] pathItems = path.split("\\/");
		if ( pathItems.length > 2 ) 
			model.addAttribute("pathback", path.substring(0, path.lastIndexOf("/")));
		else 
			model.addAttribute("pathback", path);
		
		model.addAttribute("folderlist", folderlist);
		model.addAttribute("filelist", filelist);
		if ( isDown || checkdown.equals("on") ) 
			return "redirect:/manager/ftp/file?path=" + path + "&filename=" + pathview;
		else if ( !new File(path + "/" + pathview).isDirectory() ){
			String viewtext;
			try {  
				viewtext = svc.ftpView(path + "/" + pathview);
			} catch (Exception e) {
				viewtext = e.toString();
			}
			model.addAttribute("viewtext", viewtext);
		}
			
		logger.info("---------------------------------------");
		return "ftp";
	}
	
	@PostMapping(value="/test-out-http")
	public String testOutHttpPost(
			@RequestParam("req_url") String req_url,
			@RequestParam("req_headers") String req_headers,
			@RequestParam("req_body") String req_body,
			@RequestParam("req_method") String req_method,
			@RequestParam("req_params") String req_params,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /test-out-http");
		logger.info("--- REQUEST PARAM [req_url]	=["+req_url+"]");
		logger.info("--- REQUEST PARAM [req_headers]=["+req_headers+"]");
		logger.info("--- REQUEST PARAM [req_body]	=["+req_body+"]");
		logger.info("--- REQUEST PARAM [req_method]	=["+req_method+"]");
		logger.info("--- REQUEST PARAM [req_params]	=["+req_params+"]");
		ResponseEntity<String> result = svc.testOutHttp(req_url,req_headers,req_body,req_method,req_params);
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("req_url", req_url);
		model.addAttribute("req_headers", req_headers);
		model.addAttribute("req_body", req_body);
		model.addAttribute("req_method", req_method);
		model.addAttribute("req_params", req_params);
		logger.info("--- RESPONSE [res_code]	=["+result.getStatusCode().toString()+"]");
		logger.info("--- RESPONSE [res_headers]	=["+result.getHeaders()+"]");
		logger.info("--- RESPONSE [res_body]	=["+result.getBody()+"]");
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		model.addAttribute("res_code", result.getStatusCode().toString());
		model.addAttribute("res_headers", result.getHeaders());
		model.addAttribute("res_body", result.getBody());
		logger.info("---------------------------------------");
		return "test/test-out-http";
	}	
	
	@GetMapping(value="/test-out-http")
	public String testOutHttpGet(Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("req_body", "{\"dummy\":\"\"}");
		return "test/test-out-http";
	}
	
	@GetMapping(value="/query")
	public String queryGet(Model model,
			HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("sql", "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='DFEP' LIMIT 100");
		model.addAttribute("result", "RESULT");
		model.addAttribute("env", prop.getProfiles());
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		logger.info("--- ACCESS USERID : " + session.getSessionInfo(request, response, "userId") + "(" + prop.getFep_admin_user() + ")");
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login?history=/manager/query";
		} else if ( !prop.getFep_admin_user().contains(session.getSessionInfo(request, response, "userId")) )  {
			return "redirect:/errorauth";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		return "query";
	}
	
	@PostMapping(value="/query")
	public String queryPost(Model model,
			@RequestParam("sql") String sql,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /query");
		logger.info("--- REQUEST PARAM [sql]	=["+sql+"]");
		try {
			model.addAttribute("sql", sql);
			model.addAttribute("env", prop.getProfiles());
			/* 2022.06.22 세션검증*/
			String sessionUserName = session.getSessionInfo(request, response, "userName");
			logger.info("--- ACCESS USERNAME : " + sessionUserName);
			if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
				return "redirect:/login";
			}
			
			if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
				return "redirect:/login?history=/manager/query";
			} else if ( !prop.getFep_admin_user().contains(session.getSessionInfo(request, response, "userId")) )  {
				return "redirect:/errorauth";
			}
			model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
			if ( sql.startsWith("INSERT") || sql.startsWith("UPDATE")  || sql.startsWith("insert") || sql.startsWith("update") || sql.startsWith("delete") || sql.startsWith("DELETE") ) {
				model.addAttribute("affect", svc.updateObject(sql));
			} else {
				model.addAttribute("result", svc.selectObject(sql));
			}
			
		} catch ( Exception e ) {
			model.addAttribute("result", e.toString());
			logger.error("--- 에러발생 : ", e);
		}  finally {
			logger.info("---------------------------------------");
		}
		return "query";
	}
	
	/**
	 * 쿼리 결과를 엑셀로 저장
	 * @param response
	 * @param sql
	 */
	@RequestMapping(value="/query-excel")
	public void queryPost_excel(HttpServletResponse response,@RequestParam("sql") String sql) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /query-excel");
		logger.info("--- REQUEST PARAM [sql]	=["+sql+"]");
		try {
			if ( sql.startsWith("INSERT") || sql.startsWith("UPDATE") || sql.startsWith("insert") || sql.startsWith("update") ) {
				throw new Exception("INSERT나 UPDATE는 수행할수 없습니다.");
			} else {
				svc.selectObject_excel(response, sql);
			}
			
		} catch ( Exception e ) {
			logger.error("--- 에러발생 : ", e);
		} finally {
			logger.info("---------------------------------------");
		}
	}
	
	@RequestMapping("calender")
	public String calender(Model model) {
		return "calendar/calender";
	}
	
	@GetMapping("/test-out")
	public String testOut(Model model, 
			HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("url", "http://172.20.192.11:9091/a041135");
		model.addAttribute("data","");
		model.addAttribute("result", "");
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		return "test/test-out";
	}

	@PostMapping("/test-out")
	public String testOut(Model model, 
			@RequestParam("url") String url, 
			@RequestParam("data") String data,
			HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /test-out");
		logger.info("--- REQUEST PARAM [url]	=["+url+"]");
		logger.info("--- REQUEST PARAM [data]	=["+data+"]");
		
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		
		svc.testOut(url, data);
		
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("url", url);
		model.addAttribute("data", data);
		model.addAttribute("result", "");
		logger.info("---------------------------------------");
		
		return "test/test-out";
	}
	
	@GetMapping("/test-in")
	public String testIn(Model model, 
			HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("targetIp", "172.20.192.11");
		model.addAttribute("targetPort", "56015");
		model.addAttribute("lengthStyle", "0000");
		model.addAttribute("charEncoding", "EUC-KR");
		model.addAttribute("inputData", "");
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		return "test/test-in";
	}
	
	@PostMapping("/test-in")
	public String testIn(Model model, 
			@RequestParam("targetIp") String targetIp, 
			@RequestParam("targetPort") String targetPort, 
			@RequestParam("lengthStyle") String lengthStyle, 
			@RequestParam("inputData") String inputData,
			@RequestParam("charEncoding") String charEncoding,
			HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /test-in");
		logger.info("--- REQUEST PARAM [targetIp]	=["+targetIp+"]");
		logger.info("--- REQUEST PARAM [targetPort]	=["+targetPort+"]");
		logger.info("--- REQUEST PARAM [lengthStyle]	=["+lengthStyle+"]");
		logger.info("--- REQUEST PARAM [inputData]	=["+inputData+"]");
		logger.info("--- REQUEST PARAM [inputData]	=["+inputData+"]");
		logger.info("--- REQUEST PARAM [charEncoding]	=["+charEncoding+"]");
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		
		if ( charEncoding!=null && charEncoding.equalsIgnoreCase("utf-8")) {
			svc.testInForUtf8(targetIp, targetPort, lengthStyle, inputData);
		} else {
			svc.testIn(targetIp, targetPort, lengthStyle, inputData);
		}
		
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("targetIp", targetIp);
		model.addAttribute("targetPort", targetPort);
		model.addAttribute("lengthStyle", lengthStyle);
		model.addAttribute("inputData", inputData);
		model.addAttribute("charEncoding", charEncoding);
		model.addAttribute("outputData", response);
		logger.info("---------------------------------------");
		
		return "test/test-in";
	}
	
	/**
	 * sms 목록 조횐
	 * @param model
	 * @return
	 */
	@GetMapping("/sms")
	public String sms(Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /sms");
		logger.info("--- DEFAULT PARAM [target1]  = ["+prop.getFep_sms1()+"]");
		logger.info("--- DEFAULT PARAM [target2]  = ["+prop.getFep_sms2()+"]");
		model.addAttribute("sms1", prop.getFep_sms1());
		model.addAttribute("sms2", prop.getFep_sms2());
		model.addAttribute("online", svc.getSmsList(prop.getFep_sms1()));
		model.addAttribute("batch", svc.getSmsList(prop.getFep_sms2()));
		model.addAttribute("env", prop.getProfiles());
		logger.info("---------------------------------------");
		return "sms";
	}
	
	
	@GetMapping("/calfind")
	public String calfind(
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="year", required=false) String year,
			@RequestParam(value="searchkey", required=false) String searchKey,
			Model model
			) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /calendar/" + name + "/" + year + "/" + searchKey);
		model.addAttribute("name", name);
		model.addAttribute("year", year);
		model.addAttribute("searchkey", searchKey);
		if ( name == null || year == null ) {
			model.addAttribute("result", new HashMap<String, String>());
			logger.info("---------------------------------------");
			return "calendar/calfind";
		} 
		Map<String, String> data = svc.loadMap("/kbksw/swdpt/anylink/fep-manager/config/calendar."+name+"."+year+".dat");
		Map<String, String> result = new TreeMap<String, String>();
		for ( String key : data.keySet() ) {
			if ( data.get(key).contains(searchKey) ) {
				logger.info("--- RESULT : ["+key+"]=["+data.get(key)+"]");
				result.put(key, data.get(key).replaceAll(searchKey, "<span style='background-color: yellow;'>" + searchKey + "</span>"));
			}
		}
		model.addAttribute("result", result);
		logger.info("---------------------------------------");
		return "calendar/calfind";
	}
	
	/**
	 * 캘린더 새로운 버전
	 * @param name
	 * @param year
	 * @param month
	 * @param model
	 * @return
	 */
	@GetMapping("/calendar/{name}")
	public String calelndar2(
			@PathVariable(value="name") String name,
			@RequestParam(value="year", required=false) String year,
			@RequestParam(value="month", required=false) String month,
			@RequestParam(value="startDay", required=false, defaultValue="0") int startDay,
			@RequestParam(value="filterKey", required=false) String filterKey,
			HttpServletRequest request, HttpServletResponse response, 
			Model model) {
		String remoteIp = request.getRemoteAddr();
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /calendar/" + name);
		logger.info("--- DEFAULT PARAM [year] = ["+year+"]");
		logger.info("--- DEFAULT PARAM [month] = ["+month+"]");
		logger.info("--- DEFAULT PARAM [startDay] = ["+startDay+"]");
		logger.info("--- INPUT PARAM : [filterKey]=["+filterKey+"]");
		logger.info("--- ACCESS IP : " + remoteIp);
		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		int monthInt = cal.get(Calendar.MONTH)+1;
		int dayInt = cal.get(Calendar.DAY_OF_MONTH);
		if ( year != null && month != null )  {
			yearInt = Integer.parseInt(year);
			monthInt = Integer.parseInt(month);
			if ( monthInt == 13 )  {
				yearInt += 1;
				monthInt = 1;
			} else if ( monthInt == 0 )  {
				yearInt -= 1;
				monthInt = 12;
			}
		}
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 )
			sessionUserName = "K-bank";
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		model.addAttribute("sessionUserName", sessionUserName);
		model.addAttribute("yearInt", yearInt);
		model.addAttribute("monthInt", monthInt);
		model.addAttribute("dayInt", dayInt);
		model.addAttribute("name", name);
		model.addAttribute("dayTable", svc.getCalendarTable(cal, yearInt, monthInt));
		Map<String, String> result = svc.loadMap("/kbksw/swdpt/anylink/fep-manager/config/calendar."+name+"."+yearInt+".dat");
		if ( filterKey != null && filterKey.trim().length() > 0 ) {
	 		String temp = "";
			String [] lines = null;
			StringBuffer tempResult = null;
			for ( String key : result.keySet() ) {
				temp = result.get(key);
				
				temp = temp.replaceAll("<br>", "\n");
				temp = temp.replaceAll("<br/>", "\n");
				temp = temp.replaceAll("<br />", "\n");
				temp = temp.replaceAll("\t", "");
				temp = temp.replaceAll("<div", "\n<div");
				temp = temp.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				temp = temp.replaceAll("\n\n", "\n");
				
				lines = temp.split("\n");
				tempResult = new StringBuffer();
				for ( String line : lines ) {
					if ( line.trim().length() == 0 ) continue;
					if ( line.contains(filterKey) ) {
						tempResult.append("<span style='background-color: #ffeedd;'>" + line + "</span><br/>");
					} else {
						tempResult.append("<span style='color: #CCCCCC;'>" + line + "</span><br/>");
					}
				}
				result.put(key, tempResult.toString());
			}
		}
		model.addAttribute("contents", result);
		model.addAttribute("startDay", startDay);
		model.addAttribute("filterKey", filterKey);
		logger.info("---------------------------------------");
		return "calendar/calendar2";
	}
	
	/**
	 * 캘린더 새로운 버전
	 * @param name
	 * @param year
	 * @param month
	 * @param model
	 * @return
	 */
	@PostMapping("/calendar/{name}")
	public String calelndarPost2(
			@PathVariable(value="name") String name,
			@RequestParam(value="year") String year,
			@RequestParam(value="month") String month,
			@RequestParam(value="key") String key,
			@RequestParam(value="value") String value,
			@RequestParam(value="startDay", required=false) String startDay,
			HttpServletRequest request, HttpServletResponse response, 
			Model model) {
		String remoteIp = request.getRemoteAddr();
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /calendar/" + name);
		logger.info("--- DEFAULT PARAM [year] = ["+year+"]");
		logger.info("--- DEFAULT PARAM [month] = ["+month+"]");
		logger.info("--- INPUT PARAM : key=["+key+"], value=["+value.trim()+"]");
		logger.info("--- ACCESS IP : " + remoteIp);
		Calendar cal = Calendar.getInstance();
		int yearInt = Integer.parseInt(year);
		int monthInt = Integer.parseInt(month);
		int dayInt = cal.get(Calendar.DAY_OF_MONTH);
		String filePath = "/kbksw/swdpt/anylink/fep-manager/config/calendar."+name+"."+yearInt+".dat";
		/* 2022.05.30 관리자가 아닌자가 hcoh 캘린더를 수정할때 추가로깅 */
		if ( "hcoh".equals(name) && !"172.26.2.154".equals(remoteIp) ) {
			logger.warn("--- CAUTION : 비정상적인 ACCESS 발견(관리자가 아닌사용자의 update시도)");
		}
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 )
			sessionUserName = "K-bank";
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		model.addAttribute("sessionUserName", sessionUserName);
		logger.info("--- SAVE RESULT : " + svc.saveMap(filePath, key, value));
		model.addAttribute("yearInt", yearInt);
		model.addAttribute("monthInt", monthInt);
		model.addAttribute("dayInt", dayInt);
		model.addAttribute("name", name);
		model.addAttribute("dayTable", svc.getCalendarTable(cal, yearInt, monthInt));
		model.addAttribute("contents", svc.loadMap(filePath));
		model.addAttribute("startDay", startDay);
		logger.info("---------------------------------------");
		return "calendar/calendar2";
	}
	
	/**
	 * 카드 U2L 관련 GW IP 변경 프로세스
	 * @param ip
	 * @param model
	 * @return
	 */
	@RequestMapping("/u2lcrd")
	public String u2lcrd(
			@RequestParam(value="gwname", required=false) String gwname,
			@RequestParam(value="symbname", required=false) String symbname,
			@RequestParam(value="linename", required=false) String linename,
			@RequestParam(value="luname", required=false) String luname,
			HttpServletRequest request,
			Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /u2lcrd");
		logger.info("--- DEFAULT PARAM [gwname] = ["+gwname+"]");
		logger.info("--- DEFAULT PARAM [symbname] = ["+symbname+"]");
		logger.info("--- DEFAULT PARAM [linename] = ["+linename+"]");
		logger.info("--- DEFAULT PARAM [luname] = ["+luname+"]");
		logger.info("--- DEFAULT PARAM [request_ip] = ["+request.getRemoteAddr()+"]");
		if ( "Servlet".equals(gwname ) ) {
			logger.info("--- CHANGE CRD URL : ["+linename+"]");
			FepStaticDataInfo.crdUrl = linename;
			svc.updateLineUrlForCrdU2l(gwname, symbname, linename, luname, request.getRemoteAddr());
		} else if ( linename != null && linename.trim().length() > 0 ) {
			logger.info("--- CHAGNE CRD GW : ["+linename+"]");
			svc.updateLineIpForCrdU2l(gwname, symbname, linename, luname, request.getRemoteAddr());
		}
		model.addAttribute("requestip", request.getRemoteAddr());
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("gwlist",svc.selectLineListForCrdU2l());
		model.addAttribute("url",svc.selectUrlForCrdU2l());
		model.addAttribute("historylist",svc.selectLineListForCrdU2lHistory());
		logger.info("---------------------------------------");
		return "u2lcrd";
	}
	
	/**
	 * 카드계 거래 relay 서비스
	 * @param headerMap
	 * @param body
	 * @return
	 */
	@RequestMapping("/relayToCrdU2L")
	@ResponseBody
	public ResponseEntity<String> relayToCrdU2L(
			@RequestHeader(required=false) HttpHeaders headerMap,
			@RequestBody(required=false) String body) {
		try {
			String h = URLDecoder.decode(body.substring(0, 500), "EUC-KR");
			String b = URLDecoder.decode(body.substring(500), "EUC-KR").replaceAll(" ", "+");
			body = h + b;
		} catch ( Exception e ) {
			logger.error("--- URLDecoder ERROR : " + e, e);
		}
	
		String lastUrl = svc.selectUrlForCrdU2l();
		logger.info("---------------------------------------------");
		logger.info("--- REQUEST HEADERS : [" + headerMap + "]");
		logger.info("--- REQUEST BODY : [" + body + "]");
		logger.info("--- LAST UPDATE URL INFO : ["+lastUrl+"]");
		
		if ( FepStaticDataInfo.crdUrl == null || FepStaticDataInfo.crdUrl.trim().length() == 0 ) {
			FepStaticDataInfo.crdUrl = lastUrl;
			if ( FepStaticDataInfo.crdUrl == null ) {
				if ( "dev".equals(prop.getProfiles() ))
					FepStaticDataInfo.crdUrl = "http://172.20.128.13:7060/crd/fixedLengthforBC";
				else {
					FepStaticDataInfo.crdUrl = "http://172.20.128.12:7060/crd/fixedLengthforBC";
				}
			}
		}
		logger.info("--- FepStaticDataInfo.crdUrl INFO : ["+FepStaticDataInfo.crdUrl+"]");
		
		RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(12))
				.setReadTimeout(Duration.ofSeconds(12))
				.build();
		ResponseEntity<String> response = null;
		try {
			HttpEntity<String> entity = new HttpEntity<>(body, headerMap);
			logger.info("--- entity debug : " + entity);
			response =  restTemplate.postForEntity(FepStaticDataInfo.crdUrl, entity, String.class);
		} catch ( HttpStatusCodeException e ) {
			// 50x, 40x 와 같은 HTTP 상태코드 오류가발생하는경우도 잡아 리턴해라
			response = ResponseEntity.status(e.getRawStatusCode())
					.headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
		} finally {
			logger.info("--- RESPONSE STATUS CODE : " + response.getStatusCode());
			logger.info("--- RESPONSE HEADERS : " + response.getHeaders());
			logger.info("--- RESPONSE BODY : [" + response.getBody() + "]");
		}
		logger.info("---------------------------------------------");
		return response;
	}
}
