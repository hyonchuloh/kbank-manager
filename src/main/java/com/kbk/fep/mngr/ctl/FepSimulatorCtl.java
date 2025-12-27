package com.kbk.fep.mngr.ctl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.svc.FepManagerSvc;
import com.kbk.fep.mngr.svc.FepSimulatorSvc;

@Controller
@RequestMapping("/sim")
public class FepSimulatorCtl {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FepCommPropVo prop;
	@Autowired
	private FepSimulatorSvc simSvc;
	@Autowired
	private FepManagerSvc mngSvc;
	
	@GetMapping("/list")
	public String list(Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /sim/list");
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("applist", simSvc.getAppList());
		model.addAttribute("threadlist", simSvc.getThreadList());
		logger.info("---------------------------------------");
		return "sim";
	}
	
	@GetMapping("/list/{app}")
	public String list(
			@PathVariable(value="app") String app,
			Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /sim/list/" + app);
		logger.info("--- REQUEST PARAM [app]=["+app+"]");
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("app", app);
		model.addAttribute("applist", simSvc.getAppList());
		model.addAttribute("msglist", simSvc.getMsgList(app));
		model.addAttribute("threadlist", simSvc.getThreadList());
		logger.info("---------------------------------------");
		return "sim";
	}
	
	@GetMapping("/list/{app}/{msg}")
	public String list(
			@PathVariable(value="app") String app,
			@PathVariable(value="msg") String msg,
			Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /sim/list/" + app + "/" + msg);
		logger.info("--- REQUEST PARAM [app]=["+app+"]");
		logger.info("--- REQUEST PARAM [msg]=["+msg+"]");
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("app", app);
		model.addAttribute("msg", msg);
		model.addAttribute("applist", simSvc.getAppList());
		model.addAttribute("msglist", simSvc.getMsgList(app));
		model.addAttribute("msgname", simSvc.getTxName(app, msg));
		model.addAttribute("reqcols", simSvc.getReqCols(app, msg));
		model.addAttribute("rescols", simSvc.getResCols(app, msg));
		model.addAttribute("port", simSvc.getPort(app, msg));
		model.addAttribute("lengstl", simSvc.getLengStl(app, msg));
		model.addAttribute("rule", simSvc.getRule(app, msg));
		model.addAttribute("threadlist", simSvc.getThreadList());
		logger.info("---------------------------------------");
		return "sim";
	}
	
	@PostMapping("/list/{app}/{msg}/save")
	public String save(
			@PathVariable(value="app") String app,
			@PathVariable(value="msg") String msg,
			@RequestParam("msgname") String msgname, 
			@RequestParam("reqcols") String reqcols, 
			@RequestParam("rescols") String rescols, 
			@RequestParam("rule") String rule, 
			@RequestParam("port") String port,
			@RequestParam("lengstl") String lengstl,
			@RequestParam(value="iswork", required=false) String iswork,
			@RequestParam(value="isdown", required=false) String isdown,
			Model model) { 
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /sim/list/" + app + "/" + msg + "/save");
		logger.info("--- REQUEST PARAM [msgname]=["+msgname+"]");
		logger.info("--- REQUEST PARAM [reqcols]=["+reqcols+"]");
		logger.info("--- REQUEST PARAM [rescols]=["+rescols+"]");
		logger.info("--- REQUEST PARAM [rule]=["+rule+"]");
		logger.info("--- REQUEST PARAM [port]=["+port+"]");
		logger.info("--- REQUEST PARAM [lengstl]=["+lengstl+"]");
		logger.info("--- REQUEST PARAM [iswork]=["+iswork+"]");
		logger.info("--- REQUEST PARAM [isdown]=["+isdown+"]");
		simSvc.save(app, msg, msgname, reqcols, rescols, rule, port, lengstl);
		if ( "on".equals(iswork) ) {
			logger.info("--- THREAD를 시작합니다. [" +app+ "_" + msg +"]");
			simSvc.startThread(app, msg, port);
		}
		if ( "on".equals(isdown) ) {
			logger.info("--- THREAD를 종료합니다. [" +app+ "_" + msg +"]");
			simSvc.stopThread(app, msg);
		}
		logger.info("---------------------------------------");
		return "redirect:/sim/list/" + app + "/" + msg;
	}
	
	@Deprecated
	@RequestMapping("/work/{app}/{msg}")
	@ResponseBody
	public ResponseEntity<String> work(
			@PathVariable String app,
			@PathVariable String msg,
			@RequestHeader HttpHeaders headerMap,
			@RequestBody String input
			) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /sim/work/" + app + "/" + msg);
		logger.info("--- REQUEST HEADERS : [" + headerMap + "]");
		String port = simSvc.getPort(app, msg);
		String lengstl = simSvc.getLengStl(app, msg);
		logger.info("--- SIM INFO [port]=["+port+"]");
		logger.info("--- SIM INFO [lengthstl]=["+lengstl+"]");
		logger.info("--- SIM INFO [request]=["+input+"]");
		byte [] response = null;
		try {
			String temp = URLDecoder.decode(input, "utf-8");
			response = simSvc.agenyResponse(temp.substring(500).getBytes("euc-kr"), simSvc.getRule(app, msg));
			logger.info("--- SIM INFO [port]=["+port+"]");
			logger.info("--- SIM INFO [lengthstl]=["+lengstl+"]");
			logger.info("--- SIM INFO [request]=["+temp.substring(500)+"]");
			logger.info("--- SIM INFO [response]=["+new String(response)+"]");
			mngSvc.testInAsync("localhost", port, lengstl, new String(response));
		} catch (UnsupportedEncodingException e) {
			logger.error("--- ERROR", e);
		}
		// %EF%BF%BD%EF %BF%BD%EF%BF %BD%EF%BF%BD %C3%B6
		logger.info("---------------------------------------");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/*
	 * 
	 * 
	 * 	UPDATE altxparm
		SET SVC_TYPE='2', SVCNAME='OLT_163123_A_01', HOST_PGM_NAME='', HOST_TX_ID='', WEBT_POOL_NAME='', SERVER_INFO='', URL='', PARAM=''
		WHERE TX_CODE='1300' AND APPL_CODE='123O'
		
		UPDATE ALTXPARM
		SET SVC_TYPE='8', SVCNAME='', HOST_PGM_NAME='2', HOST_TX_ID='1', WEBT_POOL_NAME='utf-8', SERVER_INFO='0', URL='http://localhost:61616/sim/work/123O/0200_1300', PARAM='/'
		WHERE TX_CODE='1300' AND APPL_CODE='123O'
	 */

}
