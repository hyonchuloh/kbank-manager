package com.kbk.fep.mngr.ctl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.dao.vo.FepLineInfoVo;
import com.kbk.fep.mngr.svc.FepLineInfoSvc;
import com.kbk.fep.util.FepSessionUtil;
import com.kbk.fep.util.FepStrUtil;

@Controller
@RequestMapping("/admin")
public class FepLineInfoCtl {
	
	@Autowired
	private FepLineInfoSvc svc;
	@Autowired
	private FepCommPropVo propVo;
	@Autowired
	private FepSessionUtil session;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/* 단위테스트 양식 다운받기 */
	@GetMapping("/line2-excel-test1")
	public void excel_test1(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="searchExtCd", required=false, defaultValue="") String searchExtCd,
			@RequestParam(value="searchBizCd", required=false, defaultValue="") String searchBizCd,
			@RequestParam(value="searchBizType", required=false, defaultValue="") String searchBizType,
			@RequestParam(value="searchBizClcd", required=false, defaultValue="") String searchBizClcd,
			@RequestParam(value="searchDevClcd", required=false, defaultValue="") String searchDevClcd,
			@RequestParam(value="searchKey", required=false, defaultValue="") String searchKey) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/excel-test1 (GET)");
		String remoteIp = request.getRemoteAddr();
		logger.info("--- ACCESS IP : " + remoteIp);
		logger.info("--- ACCESS LIST : " + propVo.getFep_access_ips());
		logger.info("--- PARAMETER : searchExtCd=["+searchExtCd+"]");
		logger.info("--- PARAMETER : searchBizCd=["+searchBizCd+"]");
		logger.info("--- PARAMETER : searchBizType=["+searchBizType+"]");
		logger.info("--- PARAMETER : searchBizClcd=["+searchBizClcd+"]");
		logger.info("--- PARAMETER : searchDevClcd=["+searchDevClcd+"]");
		logger.info("--- PARAMETER : searchKey=["+searchKey+"]");
		if ( !propVo.getFep_access_ips().contains(remoteIp) ) {
			logger.error("--- UNAUTHORIZED IP!! : " + remoteIp);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return ;
		}
		FepLineInfoVo vo = new FepLineInfoVo();
		vo.setExtCd(searchExtCd);			vo.setBizCd(searchBizCd);
		vo.setBizType(searchBizType);		vo.setBizClcd(searchBizClcd);
		vo.setDevClcd(searchDevClcd);
		svc.getExcelTest(response, vo, searchKey, "단위테스트");
		logger.info("---------------------------------------");
	}
	
	/* 단위테스트 양식 다운받기 */
	@GetMapping("/line2-excel-test2")
	public void excel_test2(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="searchExtCd", required=false, defaultValue="") String searchExtCd,
			@RequestParam(value="searchBizCd", required=false, defaultValue="") String searchBizCd,
			@RequestParam(value="searchBizType", required=false, defaultValue="") String searchBizType,
			@RequestParam(value="searchBizClcd", required=false, defaultValue="") String searchBizClcd,
			@RequestParam(value="searchDevClcd", required=false, defaultValue="") String searchDevClcd,
			@RequestParam(value="searchKey", required=false, defaultValue="") String searchKey) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/excel-test2 (GET)");
		String remoteIp = request.getRemoteAddr();
		logger.info("--- ACCESS IP : " + remoteIp);
		logger.info("--- ACCESS LIST : " + propVo.getFep_access_ips());
		logger.info("--- PARAMETER : searchExtCd=["+searchExtCd+"]");
		logger.info("--- PARAMETER : searchBizCd=["+searchBizCd+"]");
		logger.info("--- PARAMETER : searchBizType=["+searchBizType+"]");
		logger.info("--- PARAMETER : searchBizClcd=["+searchBizClcd+"]");
		logger.info("--- PARAMETER : searchDevClcd=["+searchDevClcd+"]");
		logger.info("--- PARAMETER : searchKey=["+searchKey+"]");
		if ( !propVo.getFep_access_ips().contains(remoteIp) ) {
			logger.error("--- UNAUTHORIZED IP!! : " + remoteIp);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return ;
		}
		FepLineInfoVo vo = new FepLineInfoVo();
		vo.setExtCd(searchExtCd);			vo.setBizCd(searchBizCd);
		vo.setBizType(searchBizType);		vo.setBizClcd(searchBizClcd);
		vo.setDevClcd(searchDevClcd);
		svc.getExcelTest(response, vo, searchKey, "통합테스트");
		logger.info("---------------------------------------");
	}
	
	/* sqlite 를 이용한 회선대장 신규*/
	
	@GetMapping("/line2-excel-firewall")
	public void excel_firewall(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="searchExtCd", required=false, defaultValue="") String searchExtCd,
			@RequestParam(value="searchBizCd", required=false, defaultValue="") String searchBizCd,
			@RequestParam(value="searchBizType", required=false, defaultValue="") String searchBizType,
			@RequestParam(value="searchBizClcd", required=false, defaultValue="") String searchBizClcd,
			@RequestParam(value="searchDevClcd", required=false, defaultValue="") String searchDevClcd,
			@RequestParam(value="searchKey", required=false, defaultValue="") String searchKey) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/excel-firewall (GET)");
		String remoteIp = request.getRemoteAddr();
		logger.info("--- ACCESS IP : " + remoteIp);
		logger.info("--- ACCESS LIST : " + propVo.getFep_access_ips());
		logger.info("--- PARAMETER : searchExtCd=["+searchExtCd+"]");
		logger.info("--- PARAMETER : searchBizCd=["+searchBizCd+"]");
		logger.info("--- PARAMETER : searchBizType=["+searchBizType+"]");
		logger.info("--- PARAMETER : searchBizClcd=["+searchBizClcd+"]");
		logger.info("--- PARAMETER : searchDevClcd=["+searchDevClcd+"]");
		logger.info("--- PARAMETER : searchKey=["+searchKey+"]");
		if ( !propVo.getFep_access_ips().contains(remoteIp) ) {
			logger.error("--- UNAUTHORIZED IP!! : " + remoteIp);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return ;
		}
		FepLineInfoVo vo = new FepLineInfoVo();
		vo.setExtCd(searchExtCd);			vo.setBizCd(searchBizCd);
		vo.setBizType(searchBizType);		vo.setBizClcd(searchBizClcd);
		vo.setDevClcd(searchDevClcd);
		svc.getExcelFireWall(response, vo, searchKey);
		logger.info("---------------------------------------");
	}
	
	@GetMapping("/line2-excel-l3")
	public void excel_l3(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="searchExtCd", required=false, defaultValue="") String searchExtCd,
			@RequestParam(value="searchBizCd", required=false, defaultValue="") String searchBizCd,
			@RequestParam(value="searchBizType", required=false, defaultValue="") String searchBizType,
			@RequestParam(value="searchBizClcd", required=false, defaultValue="") String searchBizClcd,
			@RequestParam(value="searchDevClcd", required=false, defaultValue="") String searchDevClcd,
			@RequestParam(value="searchKey", required=false, defaultValue="") String searchKey) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/excel-l4 (GET)");
		String remoteIp = request.getRemoteAddr();
		logger.info("--- ACCESS IP : " + remoteIp);
		logger.info("--- ACCESS LIST : " + propVo.getFep_access_ips());
		logger.info("--- PARAMETER : searchExtCd=["+searchExtCd+"]");
		logger.info("--- PARAMETER : searchBizCd=["+searchBizCd+"]");
		logger.info("--- PARAMETER : searchBizType=["+searchBizType+"]");
		logger.info("--- PARAMETER : searchBizClcd=["+searchBizClcd+"]");
		logger.info("--- PARAMETER : searchDevClcd=["+searchDevClcd+"]");
		logger.info("--- PARAMETER : searchKey=["+searchKey+"]");
		if ( !propVo.getFep_access_ips().contains(remoteIp) ) {
			logger.error("--- UNAUTHORIZED IP!! : " + remoteIp);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return ;
		}
		FepLineInfoVo vo = new FepLineInfoVo();
		vo.setExtCd(searchExtCd);			vo.setBizCd(searchBizCd);
		vo.setBizType(searchBizType);		vo.setBizClcd(searchBizClcd);
		vo.setDevClcd(searchDevClcd);
		svc.getExcelL3(response, vo, searchKey);
		logger.info("---------------------------------------");
	}
	
	@GetMapping("/line2-excel-l4")
	public void excel_l4(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="searchExtCd", required=false, defaultValue="") String searchExtCd,
			@RequestParam(value="searchBizCd", required=false, defaultValue="") String searchBizCd,
			@RequestParam(value="searchBizType", required=false, defaultValue="") String searchBizType,
			@RequestParam(value="searchBizClcd", required=false, defaultValue="") String searchBizClcd,
			@RequestParam(value="searchDevClcd", required=false, defaultValue="") String searchDevClcd,
			@RequestParam(value="searchKey", required=false, defaultValue="") String searchKey) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/excel-l4 (GET)");
		String remoteIp = request.getRemoteAddr();
		logger.info("--- ACCESS IP : " + remoteIp);
		logger.info("--- ACCESS LIST : " + propVo.getFep_access_ips());
		logger.info("--- PARAMETER : searchExtCd=["+searchExtCd+"]");
		logger.info("--- PARAMETER : searchBizCd=["+searchBizCd+"]");
		logger.info("--- PARAMETER : searchBizType=["+searchBizType+"]");
		logger.info("--- PARAMETER : searchBizClcd=["+searchBizClcd+"]");
		logger.info("--- PARAMETER : searchDevClcd=["+searchDevClcd+"]");
		logger.info("--- PARAMETER : searchKey=["+searchKey+"]");
		if ( !propVo.getFep_access_ips().contains(remoteIp) ) {
			logger.error("--- UNAUTHORIZED IP!! : " + remoteIp);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return ;
		}
		FepLineInfoVo vo = new FepLineInfoVo();
		vo.setExtCd(searchExtCd);			vo.setBizCd(searchBizCd);
		vo.setBizType(searchBizType);		vo.setBizClcd(searchBizClcd);
		vo.setDevClcd(searchDevClcd);
		svc.getExcelL4(response, vo, searchKey);
		logger.info("---------------------------------------");
	}
	
	@GetMapping("/line2-excel")
	public void excel2(HttpServletResponse response) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/line2-excel (GET)");
		svc.getExcelDown(response);
		logger.info("---------------------------------------");
	}
	
	@GetMapping("/line-line2")
	public String migration() {
		List<FepLineInfoVo> list = svc.loadItemList();
		for ( FepLineInfoVo vo : list ) {
			logger.info("--- INSERT : " + vo.getSeqNo());
			svc.insertItem2(vo);
		}
		return "redirect:/admin/line2";
	}
	
	@GetMapping("/line2")
	public String line2(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="searchExtCd", required=false, defaultValue="") String searchExtCd,
			@RequestParam(value="searchBizCd", required=false, defaultValue="") String searchBizCd,
			@RequestParam(value="searchBizType", required=false, defaultValue="") String searchBizType,
			@RequestParam(value="searchBizClcd", required=false, defaultValue="") String searchBizClcd,
			@RequestParam(value="searchDevClcd", required=false, defaultValue="") String searchDevClcd,
			@RequestParam(value="searchKey", required=false, defaultValue="") String searchKey
			) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/line2 (GET)");
		String remoteIp = request.getRemoteAddr();
		logger.info("--- ACCESS IP : " + remoteIp);
		logger.info("--- ACCESS LIST : " + propVo.getFep_access_ips());
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		logger.info("--- PARAMETER : searchExtCd=["+searchExtCd+"]");
		logger.info("--- PARAMETER : searchBizCd=["+searchBizCd+"]");
		logger.info("--- PARAMETER : searchBizType=["+searchBizType+"]");
		logger.info("--- PARAMETER : searchBizClcd=["+searchBizClcd+"]");
		logger.info("--- PARAMETER : searchDevClcd=["+searchDevClcd+"]");
		logger.info("--- PARAMETER : searchKey=["+searchKey+"]");
		if ( !propVo.getFep_access_ips().contains(remoteIp) ) {
			logger.error("--- UNAUTHORIZED IP!! : " + remoteIp);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return "line/line2";
		}
		FepLineInfoVo vo = new FepLineInfoVo();
		vo.setExtCd(searchExtCd);			vo.setBizCd(searchBizCd);
		vo.setBizType(searchBizType);		vo.setBizClcd(searchBizClcd);
		vo.setDevClcd(searchDevClcd);		
		model.addAttribute("list", svc.selectItem2(vo, searchKey));
		model.addAttribute("searchExtCd", searchExtCd);
		model.addAttribute("searchBizCd", searchBizCd);
		model.addAttribute("searchBizType", searchBizType);
		model.addAttribute("searchBizClcd", searchBizClcd);
		model.addAttribute("searchDevClcd", searchDevClcd);
		model.addAttribute("searchKey", searchKey);
		logger.info("---------------------------------------");
		return "line/line2";
	}
	
	@RequestMapping("/line2-delete")
	public String deleteItem2(
			@RequestParam("seqNo") int seqNo,
			@RequestParam(value="searchExtCd", required=false, defaultValue="") String searchExtCd,
			@RequestParam(value="searchBizCd", required=false, defaultValue="") String searchBizCd,
			@RequestParam(value="searchBizType", required=false, defaultValue="") String searchBizType,
			@RequestParam(value="searchBizClcd", required=false, defaultValue="") String searchBizClcd,
			@RequestParam(value="searchDevClcd", required=false, defaultValue="") String searchDevClcd,
			@RequestParam(value="searchKey", required=false, defaultValue="") String searchKey,
			HttpServletRequest request, HttpServletResponse response
			) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/line2-delete (POST)");
		logger.info("--- PARAMETER : seqNo=["+seqNo+"]");
		logger.info("--- PARAMETER : searchKey=["+searchKey+"]");
		logger.info("--- DELETE RESULT AFFECT=[" + svc.deleteItem2(seqNo) +"]");
		
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		
		logger.info("---------------------------------------");
		return "redirect:/admin/line2?searchExtCd="+searchExtCd+ "&searchBizCd="+searchBizCd+"&searchBizType="+searchBizType+"&searchBizClcd="+searchBizClcd+"&searchDevClcd="+searchDevClcd+"&searchKey=" + FepStrUtil.encoding(searchKey);
	}
	
	@RequestMapping("/line2-update")
	public String updateItem2(
			@RequestParam("seqNo") int seqNo,
			@RequestParam("extCd") String extCd,
			@RequestParam("extNm") String extNm,
			@RequestParam("bizCd") String bizCd,
			@RequestParam("bizNm") String bizNm,
			@RequestParam("bizType") String bizType,
			@RequestParam("bizClcd") String bizClcd,
			@RequestParam("nwLine") String nwLine,
			@RequestParam("nwRouter") String nwRouter,
			@RequestParam("fwVpn") String fwVpn,
			@RequestParam("devClcd") String devClcd,
			@RequestParam("kbkIp") String kbkIp,
			@RequestParam("kbkNatIp") String kbkNatIp,
			@RequestParam("kbkPort") String kbkPort,
			@RequestParam("extIp") String extIp,
			@RequestParam("extPort") String extPort,
			@RequestParam("srType") String srType,
			@RequestParam("extUser") String extUser,
			@RequestParam("history") String history,
			@RequestParam(value="searchExtCd", required=false, defaultValue="") String searchExtCd,
			@RequestParam(value="searchBizCd", required=false, defaultValue="") String searchBizCd,
			@RequestParam(value="searchBizType", required=false, defaultValue="") String searchBizType,
			@RequestParam(value="searchBizClcd", required=false, defaultValue="") String searchBizClcd,
			@RequestParam(value="searchDevClcd", required=false, defaultValue="") String searchDevClcd,
			@RequestParam(value="searchKey", required=false, defaultValue="") String searchKey,
			HttpServletRequest request, HttpServletResponse response
			) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/line2-update (POST)");
		logger.info("--- PARAMETER : seqNo=["+seqNo+"]");
		logger.info("--- PARAMETER : extCd=["+extCd+"]");
		logger.info("--- PARAMETER : extNm=["+extNm+"]");
		logger.info("--- PARAMETER : bizCd=["+bizCd+"]");
		logger.info("--- PARAMETER : bizNm=["+bizNm+"]");
		logger.info("--- PARAMETER : bizType=["+bizType+"]");
		logger.info("--- PARAMETER : bizClcd=["+bizClcd+"]");
		logger.info("--- PARAMETER : nwLine=["+nwLine+"]");
		logger.info("--- PARAMETER : nwRouter=["+nwRouter+"]");
		logger.info("--- PARAMETER : fwVpn=["+fwVpn+"]");
		logger.info("--- PARAMETER : devClcd=["+devClcd+"]");
		logger.info("--- PARAMETER : kbkIp=["+kbkIp+"]");
		logger.info("--- PARAMETER : kbkNatIp=["+kbkNatIp+"]");
		logger.info("--- PARAMETER : kbkPort=["+kbkPort+"]");
		logger.info("--- PARAMETER : extIp=["+extIp+"]");
		logger.info("--- PARAMETER : extPort=["+extPort+"]");
		logger.info("--- PARAMETER : srType=["+srType+"]");
		logger.info("--- PARAMETER : extUser=["+extUser+"]");
		logger.info("--- PARAMETER : history=["+history+"]");
		logger.info("--- PARAMETER : searchExtCd=["+searchExtCd+"]");
		logger.info("--- PARAMETER : searchBizCd=["+searchBizCd+"]");
		logger.info("--- PARAMETER : searchBizType=["+searchBizType+"]");
		logger.info("--- PARAMETER : searchBizClcd=["+searchBizClcd+"]");
		logger.info("--- PARAMETER : searchDevClcd=["+searchDevClcd+"]");
		logger.info("--- PARAMETER : searchKey=["+searchKey+"]");
		
		FepLineInfoVo vo = new FepLineInfoVo();
		vo.setSeqNo(seqNo);
		vo.setExtCd(extCd);			vo.setExtNm(extNm);			vo.setBizCd(bizCd);
		vo.setBizNm(bizNm);			vo.setBizType(bizType);		vo.setBizClcd(bizClcd);
		vo.setNwLine(nwLine);		vo.setNwRouter(nwRouter);	vo.setFwVpn(fwVpn);
		vo.setDevClcd(devClcd);		vo.setKbkIp(kbkIp);			vo.setKbkNatIp(kbkNatIp);			vo.setKbkPort(kbkPort);
		vo.setExtIp(extIp);			vo.setExtPort(extPort);		vo.setSrType(srType);
		vo.setExtUser(extUser);		vo.setHistory(history);
		
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		
		logger.info("--- UPDATE RESULT AFFECT=[" + 	svc.updateItem2(vo) +"]");
		return "redirect:/admin/line2?searchExtCd="+searchExtCd+ "&searchBizCd="+searchBizCd+"&searchBizType="+searchBizType+"&searchBizClcd="+searchBizClcd+"&searchDevClcd="+searchDevClcd+"&searchKey=" + FepStrUtil.encoding(searchKey);
	}
	
	@RequestMapping("/line2-insert")
	public String insertItem2(
			@RequestParam("extCd") String extCd,
			@RequestParam("extNm") String extNm,
			@RequestParam("bizCd") String bizCd,
			@RequestParam("bizNm") String bizNm,
			@RequestParam("bizType") String bizType,
			@RequestParam("bizClcd") String bizClcd,
			@RequestParam("nwLine") String nwLine,
			@RequestParam("nwRouter") String nwRouter,
			@RequestParam("fwVpn") String fwVpn,
			@RequestParam("devClcd") String devClcd,
			@RequestParam("kbkIp") String kbkIp,
			@RequestParam("kbkNatIp") String kbkNatIp,
			@RequestParam("kbkPort") String kbkPort,
			@RequestParam("extIp") String extIp,
			@RequestParam("extPort") String extPort,
			@RequestParam("srType") String srType,
			@RequestParam("extUser") String extUser,
			@RequestParam("history") String history,
			@RequestParam(value="searchExtCd", required=false, defaultValue="") String searchExtCd,
			@RequestParam(value="searchBizCd", required=false, defaultValue="") String searchBizCd,
			@RequestParam(value="searchBizType", required=false, defaultValue="") String searchBizType,
			@RequestParam(value="searchBizClcd", required=false, defaultValue="") String searchBizClcd,
			@RequestParam(value="searchDevClcd", required=false, defaultValue="") String searchDevClcd,
			@RequestParam(value="searchKey", required=false, defaultValue="") String searchKey,
			HttpServletRequest request, HttpServletResponse response
			) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/line2-insert (POST)");
		logger.info("--- PARAMETER : extCd=["+extCd+"]");
		logger.info("--- PARAMETER : extNm=["+extNm+"]");
		logger.info("--- PARAMETER : bizCd=["+bizCd+"]");
		logger.info("--- PARAMETER : bizNm=["+bizNm+"]");
		logger.info("--- PARAMETER : bizType=["+bizType+"]");
		logger.info("--- PARAMETER : bizClcd=["+bizClcd+"]");
		logger.info("--- PARAMETER : nwLine=["+nwLine+"]");
		logger.info("--- PARAMETER : nwRouter=["+nwRouter+"]");
		logger.info("--- PARAMETER : fwVpn=["+fwVpn+"]");
		logger.info("--- PARAMETER : devClcd=["+devClcd+"]");
		logger.info("--- PARAMETER : kbkIp=["+kbkIp+"]");
		logger.info("--- PARAMETER : kbkNatIp=["+kbkNatIp+"]");
		logger.info("--- PARAMETER : kbkPort=["+kbkPort+"]");
		logger.info("--- PARAMETER : extIp=["+extIp+"]");
		logger.info("--- PARAMETER : extPort=["+extPort+"]");
		logger.info("--- PARAMETER : srType=["+srType+"]");
		logger.info("--- PARAMETER : extUser=["+extUser+"]");
		logger.info("--- PARAMETER : history=["+history+"]");
		logger.info("--- PARAMETER : searchKey=["+searchKey+"]");
		
		FepLineInfoVo vo = new FepLineInfoVo();
		vo.setExtCd(extCd);			vo.setExtNm(extNm);			vo.setBizCd(bizCd);
		vo.setBizNm(bizNm);			vo.setBizType(bizType);		vo.setBizClcd(bizClcd);
		vo.setNwLine(nwLine);		vo.setNwRouter(nwRouter);	vo.setFwVpn(fwVpn);
		vo.setDevClcd(devClcd);		vo.setKbkIp(kbkIp);			vo.setKbkNatIp(kbkNatIp);		vo.setKbkPort(kbkPort);
		vo.setExtIp(extIp);			vo.setExtPort(extPort);		vo.setSrType(srType);
		vo.setExtUser(extUser);		vo.setHistory(history);
		
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		
		logger.info("--- INSERT RESULT AFFECT=[" + 	svc.insertItem2(vo) +"]");
		return "redirect:/admin/line2?searchExtCd="+searchExtCd+ "&searchBizCd="+searchBizCd+"&searchBizType="+searchBizType+"&searchBizClcd="+searchBizClcd+"&searchDevClcd="+searchDevClcd+"&searchKey=" + FepStrUtil.encoding(searchKey);
	}
	
	
	/* sqlite 를 이용한 회선대장 신규*/
	@GetMapping("/line-excel")
	public void excel(HttpServletResponse response) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/line-excel (GET)");
		svc.getExcelDown2(response);
		logger.info("---------------------------------------");
	}
	
	@Deprecated
	@GetMapping("/line")
	public String line(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/line (GET)");
		String remoteIp = request.getRemoteAddr();
		logger.info("--- ACCESS IP : " + remoteIp);
		logger.info("--- ACCESS LIST : " + propVo.getFep_access_ips());
		if ( !propVo.getFep_access_ips().contains(remoteIp) ) {
			logger.error("--- UNAUTHORIZED IP!! : " + remoteIp);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return "line";
		}
		model.addAttribute("devFilter", "checked");
		model.addAttribute("stgFilter", "checked");
		model.addAttribute("prdFilter", "checked");
		model.addAttribute("innFilter", "checked");
		model.addAttribute("list", svc.loadItemList());
		logger.info("---------------------------------------");
		return "line/line";
	}
	
	@Deprecated
	@PostMapping("/line")
	public String linePost(Model model,
			@RequestParam("seqNo") String seqNo,
			@RequestParam("extCd") String extCd,
			@RequestParam("extNm") String extNm,
			@RequestParam("bizCd") String bizCd,
			@RequestParam("bizNm") String bizNm,
			@RequestParam("bizType") String bizType,
			@RequestParam("bizClcd") String bizClcd,
			@RequestParam("nwLine") String nwLine,
			@RequestParam("nwRouter") String nwRouter,
			@RequestParam("fwVpn") String fwVpn,
			@RequestParam("devClcd") String devClcd,
			@RequestParam("kbkIp") String kbkIp,
			@RequestParam("kbkNatIp") String kbkNatIp,
			@RequestParam("kbkPort") String kbkPort,
			@RequestParam("extIp") String extIp,
			@RequestParam("extPort") String extPort,
			@RequestParam("srType") String srType,
			@RequestParam("extUser") String extUser,
			@RequestParam("history") String history,
			@RequestParam("deleteYn") String deleteYn,
			@RequestParam("key") String key,
			@RequestParam("devFilter") String devFilter,
			@RequestParam("stgFilter") String stgFilter,
			@RequestParam("prdFilter") String prdFilter,
			@RequestParam("innFilter") String innFilter
			) {
		/*
		extCd = FepStrUtil.toKor(extCd);
		extCd = FepStrUtil.toKor(extCd);
		extNm = FepStrUtil.toKor(extNm);
		bizCd = FepStrUtil.toKor(bizCd);
		bizNm = FepStrUtil.toKor(bizNm);
		bizType = FepStrUtil.toKor(bizType);
		bizClcd = FepStrUtil.toKor(bizClcd);
		nwLine = FepStrUtil.toKor(nwLine);
		nwRouter = FepStrUtil.toKor(nwRouter);
		fwVpn = FepStrUtil.toKor(fwVpn);
		devClcd = FepStrUtil.toKor(devClcd);
		kbkIp = FepStrUtil.toKor(kbkIp);
		kbkNatIp = FepStrUtil.toKor(kbkNatIp);
		kbkPort = FepStrUtil.toKor(kbkPort);
		extIp = FepStrUtil.toKor(extIp);
		extPort = FepStrUtil.toKor(extPort);
		srType = FepStrUtil.toKor(srType);
		extUser = FepStrUtil.toKor(extUser);
		history = FepStrUtil.toKor(history);
		*/
		logger.info("---------------------------------------");
		logger.info("--- URL : /admin/line (POST)");
		logger.info("--- PARAMETER : seqNo=["+seqNo+"]");
		logger.info("--- PARAMETER : extCd=["+extCd+"]");
		logger.info("--- PARAMETER : extNm=["+extNm+"]");
		logger.info("--- PARAMETER : bizCd=["+bizCd+"]");
		logger.info("--- PARAMETER : bizNm=["+bizNm+"]");
		logger.info("--- PARAMETER : bizType=["+bizType+"]");
		logger.info("--- PARAMETER : bizClcd=["+bizClcd+"]");
		logger.info("--- PARAMETER : nwLine=["+nwLine+"]");
		logger.info("--- PARAMETER : nwRouter=["+nwRouter+"]");
		logger.info("--- PARAMETER : fwVpn=["+fwVpn+"]");
		logger.info("--- PARAMETER : devClcd=["+devClcd+"]");
		logger.info("--- PARAMETER : kbkIp=["+kbkIp+"]");
		logger.info("--- PARAMETER : kbkNatIp=["+kbkNatIp+"]");
		logger.info("--- PARAMETER : kbkPort=["+kbkPort+"]");
		logger.info("--- PARAMETER : extIp=["+extIp+"]");
		logger.info("--- PARAMETER : extPort=["+extPort+"]");
		logger.info("--- PARAMETER : srType=["+srType+"]");
		logger.info("--- PARAMETER : extUser=["+extUser+"]");
		logger.info("--- PARAMETER : history=["+history+"]");
		logger.info("--- PARAMETER : deleteYn=["+deleteYn+"]");
		logger.info("--- PARAMETER : key=["+key+"]");
		logger.info("--- PARAMETER : devFilter=["+devFilter+"]");
		logger.info("--- PARAMETER : stgFilter=["+stgFilter+"]");
		logger.info("--- PARAMETER : prdFilter=["+prdFilter+"]");
		logger.info("--- PARAMETER : innFilter=["+innFilter+"]");
		
		
		if ( deleteYn!=null && "Y".equals(deleteYn) ) {
			svc.deleteItem(Integer.parseInt(seqNo));
		} else if ( seqNo != null ) {
			svc.saveItem(Integer.parseInt(seqNo), extCd, extNm, bizCd, bizNm, bizType, 
					bizClcd, nwLine, nwRouter, fwVpn, devClcd, kbkIp, kbkNatIp, 
					kbkPort, extIp, extPort, srType, extUser, history);
		}
		model.addAttribute("list", svc.loadItemList());
		if ( devFilter != null && devFilter.equals("on") )
			model.addAttribute("devFilter", "checked");
		if ( stgFilter != null && stgFilter.equals("on") )
			model.addAttribute("stgFilter", "checked");
		if ( prdFilter != null && prdFilter.equals("on") )
			model.addAttribute("prdFilter", "checked");
		if ( innFilter != null && innFilter.equals("on") )
			model.addAttribute("innFilter", "checked");
		model.addAttribute("key", key);
		logger.info("---------------------------------------");
		return "line/line";
	}
	
	public String toKor(String input) {
		if ( input == null ) return null;
		try {
			return new String(input.getBytes("8859_1"), "EUC-KR");
		} catch (UnsupportedEncodingException e) {
			return input;
		}
	}

}
