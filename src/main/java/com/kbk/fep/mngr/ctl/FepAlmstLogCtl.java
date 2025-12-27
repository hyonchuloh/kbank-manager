package com.kbk.fep.mngr.ctl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kbk.fep.mngr.dao.vo.FepAlmstLogVo;
import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.dao.vo.FepOmmInqRowVo;
import com.kbk.fep.mngr.dao.vo.FepOmmInqVo;
import com.kbk.fep.mngr.svc.FepAlmstLogSvc;
import com.kbk.fep.mngr.svc.FepOmmInqSvc;
import com.kbk.fep.util.FepPropInfo;
import com.kbk.fep.util.FepSessionUtil;

@Controller
@RequestMapping("/log")
public class FepAlmstLogCtl {
	
	@Autowired
	private FepCommPropVo prop;
	@Autowired
	private FepSessionUtil session;
	@Autowired
	private FepOmmInqSvc ommSvc;
	
	@Autowired
	private FepPropInfo info;
	
	@Autowired
	private FepAlmstLogSvc svc;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/detail")
	public String detail(Model model,
			@RequestParam("logId") String logId,
			@RequestParam("logPoint") String logPoint,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("---------------------------------------");
		logger.info("--- REQUEST PARAM [logId]	=["+logId+"]");
		logger.info("--- REQUEST PARAM [logPoint]	=["+logPoint+"]");
		
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		
		FepAlmstLogVo vo = svc.selectItem(logId, logPoint);
		model.addAttribute("item", vo);
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("errMsg", info.getValue("GW." + vo.getErrCode()));
		
		/* 개별부 상세로깅 조회
		Map<FepOmmInqRowVo, String> result = new HashMap<FepOmmInqRowVo, String>();
		StringBuffer getHtmlData = new StringBuffer();
		try {
			FepOmmInqVo omm = ommSvc.inquery(vo.getMsgDataStr().substring(277, 300));
			if ( "2".equals(vo.getMsgDataStr().substring(321, 322)) ) {
				result = ommSvc.getOutputParsing(omm, vo.getFlatData().getBytes());
			} else {
				result = ommSvc.getInputParsing(omm, vo.getFlatData().getBytes());
			}
			getHtmlData.append("<table style='width: 100%'>\n");
			for ( FepOmmInqRowVo tempVo : result.keySet()) {
				getHtmlData.append("<tr>\n");
				getHtmlData.append("<th>"+tempVo.getAttribute()+"<br/>("+new String(tempVo.getAttributeExplain(), "utf-8")+")</th>\n");
				getHtmlData.append("<td>"+tempVo.getDataType()+"("+tempVo.getLength()+")" + "</td>\n");
				getHtmlData.append("<td><pre style='font-family: d2coding; color: blue; white-space: pre-wrap; word-break: break-all; overflow: auto;'>["+ result.get(tempVo) + "]</pre></td>\n");
				getHtmlData.append("</tr>\n");
			}
			getHtmlData.append("</table>\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("parseData", getHtmlData);*/
		/* 개별부 상세로깅 조회*/
		
		logger.info("---------------------------------------");
		return "list/detail";
	}
	
	
	@RequestMapping("/list")
	public String list(Model model,
			@RequestParam(required=false)		String procDate,
			@RequestParam(required=false)		String procMtime,
			@RequestParam(defaultValue="5" ) 	int rangeMinute,
			@RequestParam(defaultValue="false") Boolean isErr,
			@RequestParam(defaultValue="")		String logId,
			@RequestParam(defaultValue="")		String guid,
			@RequestParam(defaultValue="")		String instCode,
			@RequestParam(defaultValue="")		String applCode,
			@RequestParam(defaultValue="")		String kindCode,
			@RequestParam(defaultValue="")		String txCode,
			@RequestParam(defaultValue="")		String logPoint,
			@RequestParam(defaultValue="1") 	int currentPage,
			@RequestParam(defaultValue="10") 	int pageBlock,
			@RequestParam(defaultValue="20")	int recordPerPage,
			@RequestParam(required=false)		String reload,
			HttpServletRequest request, HttpServletResponse response
			) {
		
		logger.info("---------------------------------------");
		logger.info("--- REQUEST PARAM [procDate]	=["+procDate+"]");
		logger.info("--- REQUEST PARAM [procMtime]	=["+procMtime+"]");
		logger.info("--- REQUEST PARAM [rangeMinute]	=["+rangeMinute+"]");
		logger.info("--- REQUEST PARAM [isErr]		=["+isErr+"]");
		logger.info("--- REQUEST PARAM [logId]		=["+logId+"]");
		logger.info("--- REQUEST PARAM [guid]		=["+guid+"]");
		logger.info("--- REQUEST PARAM [instCode]	=["+instCode+"]");
		logger.info("--- REQUEST PARAM [applCode]	=["+applCode+"]");
		logger.info("--- REQUEST PARAM [kindCode]	=["+kindCode+"]");
		logger.info("--- REQUEST PARAM [txCode]		=["+txCode+"]");
		logger.info("--- REQUEST PARAM [logPoint]		=["+logPoint+"]");
		logger.info("--- REQUEST PARAM [currentPage]	=["+currentPage+"]");
		logger.info("--- REQUEST PARAM [pageBlock]	=["+pageBlock+"]");
		logger.info("--- REQUEST PARAM [recordPerPage]	=["+recordPerPage+"]");
		
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		
		FepAlmstLogVo inputVo = new FepAlmstLogVo();
		if ( procDate == null || procDate.trim().length() == 0 ) 
			procDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		if ( procMtime == null ) 
			procMtime = new SimpleDateFormat("HHmmss").format(new Date());
		else if ( procMtime.length() > 6 ) 
			procMtime = procMtime.substring(0,6);
		if ( "prod".equals(prop.getProfiles()) && rangeMinute >= 10 ) {
			rangeMinute = 10;
		}
		
		/* 쿼리를 위한 정보를 세팅해준다. */
		inputVo.setProcDate(procDate);
		inputVo.setProcMtime(procMtime + "000");
		inputVo.setInstCode(instCode);
		inputVo.setApplCode(applCode);
		inputVo.setKindCode(kindCode);
		inputVo.setTxCode(txCode);
		inputVo.setLogPoint(logPoint);
		inputVo.setLogId(logId);
		inputVo.setGuid(guid);
		inputVo.setStartRowNum(Integer.toString((currentPage-1)*recordPerPage));
		inputVo.setSelectCount(Integer.toString(recordPerPage));
		if ( reload != null && reload.equals("on") ) {
			ResourceBundle.clearCache(Thread.currentThread().getContextClassLoader());
			model.addAttribute("reloadMsg", "RELOADED!!!!");
		}
		
		/* view 파람정보를 다시 세팅해준다. */
		model.addAttribute("procDate", procDate);
		model.addAttribute("procMtime", procMtime);
		model.addAttribute("instCode", instCode);
		model.addAttribute("applCode", applCode);
		model.addAttribute("kindCode", kindCode);
		model.addAttribute("txCode", txCode);
		model.addAttribute("logPoint", logPoint);
		model.addAttribute("logId", logId);
		model.addAttribute("guid", guid);
		model.addAttribute("rangeMinute", rangeMinute);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageBlock", pageBlock);
		model.addAttribute("recordPerPage", recordPerPage);
		model.addAttribute("env", prop.getProfiles());
		
		model.addAttribute("list", svc.selectList2(inputVo, rangeMinute, isErr));
		int totalCnt = svc.selectListCnt(inputVo, rangeMinute, isErr);
		int totalPage = totalCnt / recordPerPage;
		if ( totalCnt % recordPerPage > 0 ) 
			totalPage += 1;
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("totalPage", totalPage);
		
		logger.info("---------------------------------------");
		return "list/list";
	}

}
