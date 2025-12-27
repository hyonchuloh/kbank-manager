package com.kbk.fep.mngr.ctl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kbk.fep.mngr.dao.vo.FepAlbatStatVo;
import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.svc.FepAlbatStatSvc;
import com.kbk.fep.util.FepSessionUtil;

@Controller 
@RequestMapping("/log")
public class FepAlbatStatCtl {
	
	@Autowired
	private FepCommPropVo prop;
	@Autowired
	private FepSessionUtil session;
	
	@Autowired
	private FepAlbatStatSvc svc;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/listbat")
	public String listbat(Model model,
			@RequestParam(required=false)		String strtDate,
			@RequestParam(required=false)		String strtTime,
			@RequestParam(defaultValue="30" ) 	int rangeMinute,
			@RequestParam(defaultValue="") 		String instCode,
			@RequestParam(defaultValue="")		String applCode,
			@RequestParam(defaultValue="on")	String srFlagS,
			@RequestParam(defaultValue="on")	String srFlagR,
			@RequestParam(defaultValue="1") 	int currentPage,
			@RequestParam(defaultValue="20") 	int pageBlock,
			@RequestParam(defaultValue="20")	int recordPerPage,
			@RequestParam(defaultValue="false")	Boolean isErr,
			HttpServletRequest request, HttpServletResponse response
			) {
		
		logger.info("---------------------------------------");
		logger.info("--- REQUEST PARAM [strtDate]		=["+strtDate+"]");
		logger.info("--- REQUEST PARAM [strtTime]		=["+strtTime+"]");
		logger.info("--- REQUEST PARAM [rangeMinute]	=["+rangeMinute+"]");
		logger.info("--- REQUEST PARAM [isErr]		=["+isErr+"]");
		logger.info("--- REQUEST PARAM [instCode]		=["+instCode+"]");
		logger.info("--- REQUEST PARAM [applCode]		=["+applCode+"]");
		logger.info("--- REQUEST PARAM [srFlagS]		=["+srFlagS+"]");
		logger.info("--- REQUEST PARAM [srFlagR]		=["+srFlagR+"]");
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
		
		FepAlbatStatVo inputVo = new FepAlbatStatVo();
		if ( strtDate == null ) 
			strtDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		if ( strtTime == null ) 
			strtTime = new SimpleDateFormat("HHmmss").format(new Date());
		else if ( strtTime.length() > 6 ) 
			strtTime = strtTime.substring(0,6);
		
		/* 쿼리를 위한 정보를 세팅해준다. */
		inputVo.setStrtDate(strtDate);
		inputVo.setStrtTime(strtTime+"000");
		inputVo.setInstCode(instCode);
		inputVo.setApplCode(applCode);
		
		String checkedSrFlagS = "", checkedSrFlagR = "", srFlag = "", checkedIsErr = "";
		if ( srFlagS.trim().length() > 0 ) {
			checkedSrFlagS = "checked";
			srFlag += ";1";
		}
		if ( srFlagR.trim().length() > 0 ) {
			checkedSrFlagR = "checked";
			srFlag += ";2";
		}
		if ( isErr == true ) {
			checkedIsErr = "checked";
		}
		inputVo.setSrFlag(srFlag.substring(1));
		inputVo.setStartRowNum(Integer.toString((currentPage-1)*recordPerPage));
		inputVo.setSelectCount(Integer.toString(recordPerPage));
		
		/* view 파람정보를 다시 세팅해준다. */
		model.addAttribute("strtDate", strtDate);
		model.addAttribute("strtTime", strtTime);
		model.addAttribute("rangeMinute", rangeMinute);
		model.addAttribute("isErr", isErr);
		model.addAttribute("instCode", instCode);
		model.addAttribute("applCode", applCode);
		model.addAttribute("checkedSrFlagS", checkedSrFlagS);
		model.addAttribute("checkedSrFlagR", checkedSrFlagR);
		model.addAttribute("checkedIsErr", checkedIsErr);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageBlock", pageBlock);
		model.addAttribute("recordPerPage", recordPerPage);
		model.addAttribute("env", prop.getProfiles());
		
		model.addAttribute("list", svc.selectList(inputVo, rangeMinute, isErr));
		int totalCnt = svc.selectListCnt(inputVo, rangeMinute, isErr);
		int totalPage = totalCnt / recordPerPage;
		if ( totalCnt % recordPerPage > 0 ) 
			totalPage += 1;
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("totalPage", totalPage);
		
		logger.info("---------------------------------------");
		return "list/listbat";
	}

}
