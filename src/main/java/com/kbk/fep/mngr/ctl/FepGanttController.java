package com.kbk.fep.mngr.ctl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.dao.vo.FepGanttVo;
import com.kbk.fep.mngr.svc.FepGanttSvc;
import com.kbk.fep.util.FepSessionUtil;

@Controller
public class FepGanttController {
	
	@Autowired
	private FepCommPropVo prop;
	@Autowired
	private FepSessionUtil session;
	
	@Autowired
	private FepGanttSvc svc;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/manager/gantt")
	public String gantt(
			@RequestParam(value="sel_start_date", required=false) String sel_start_date,
			@RequestParam(value="sel_end_date", required=false) String sel_end_date,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("---------------------------------------");
		logger.info("--- REQUEST PARAM [sel_start_date]		=["+sel_start_date+"]");
		logger.info("--- REQUEST PARAM [sel_end_date]		=["+sel_end_date+"]");
		
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		model.addAttribute("sessionUserName", sessionUserName + "("+session.getSessionInfo(request, response, "userId")+")");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		/* 입력값이 널이라면 오늘 기준으로 과거15일 미래 15일분 조회할것*/
		if ( sel_start_date == null || sel_end_date == null ) {
			
			long currentTime = new Date().getTime();
			sel_start_date = sdf.format(new Date(currentTime-35L*24L*60L*60L*1000L));
			sel_end_date = sdf.format(new Date(currentTime+35L*24L*60L*60L*1000L));
		}
		
		List<FepGanttVo> bizList = svc.selectBizList(sel_start_date, sel_end_date);
		List<String> dayList = svc.selectDayList(sel_start_date, sel_end_date);
		
		model.addAttribute("sel_start_date", sel_start_date);
		model.addAttribute("sel_end_date", sel_end_date);
		model.addAttribute("biz_list", bizList);
		model.addAttribute("day_count", dayList.size());
		model.addAttribute("day_list", dayList);
		model.addAttribute("current_date", sdf.format(new Date()));
		model.addAttribute("env", prop.getProfiles());
		logger.info("---------------------------------------");
		
		return "graph/gantt";
	}
	
	@RequestMapping("/manager/gantt/detail")
	public String ganttPop(
			@RequestParam(value="seqNo", required=false) Integer seqNo,
			Model model) {
		if ( seqNo == null ) {	/* 신규입력*/
			model.addAttribute("item", new FepGanttVo());
		} else {				/* 수정입력 */
			model.addAttribute("item", svc.selectItem(seqNo));
		}
		return "graph/ganttPop";
	}
	
	@RequestMapping("/manager/gantt/delete")
	@ResponseBody
	public String ganttDelete(@RequestParam("seqNo") int seqNo,
			HttpServletRequest request, HttpServletResponse response) {
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		int result = svc.deleteItem(seqNo);
		return "<script>alert('삭제결과="+result+"');window.opener.location.reload(); window.close();</script>";
	}
	
	@RequestMapping("/manager/gantt/edit")
	@ResponseBody
	public String ganttDelete(
			@RequestParam("seqNo") int seqNo,
			@RequestParam("prjName") String prjName,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("developer") String developer,
			@RequestParam("prjMemo") String prjMemo,
			@RequestParam("check1") String check1,
			@RequestParam("check2") String check2,
			@RequestParam("check3") String check3,
			@RequestParam("check4") String check4,
			@RequestParam("check5") String check5,
			@RequestParam("check6") String check6,
			@RequestParam("check7") String check7,
			@RequestParam("check8") String check8,
			@RequestParam("check9") String check9,
			@RequestParam("check10") String check10,
			HttpServletRequest request, HttpServletResponse response
			) {
		FepGanttVo vo = new FepGanttVo();
		vo.setSeqNo(seqNo);
		vo.setPrjName(prjName);
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setDeveloper(developer);
		vo.setPrjMemo(prjMemo);
		vo.setCheck1(check1);
		vo.setCheck2(check2);
		vo.setCheck3(check3);
		vo.setCheck4(check4);
		vo.setCheck5(check5);
		vo.setCheck6(check6);
		vo.setCheck7(check7);
		vo.setCheck8(check8);
		vo.setCheck9(check9);
		vo.setCheck10(check10);
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		int result = svc.editItem(vo);
		if ( result > 0 )
			return "<script>window.opener.location.reload(); window.close();</script>";
		else
			return "<script>alert('실패');</script>";
	}
	
	@RequestMapping("/manager/gantt/insert")
	@ResponseBody
	public String ganttInsert(
			@RequestParam("prjName") String prjName,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("developer") String developer,
			@RequestParam("prjMemo") String prjMemo,
			@RequestParam("check1") String check1,
			@RequestParam("check2") String check2,
			@RequestParam("check3") String check3,
			@RequestParam("check4") String check4,
			@RequestParam("check5") String check5,
			@RequestParam("check6") String check6,
			@RequestParam("check7") String check7,
			@RequestParam("check8") String check8,
			@RequestParam("check9") String check9,
			@RequestParam("check10") String check10,
			HttpServletRequest request, HttpServletResponse response
			) {
		FepGanttVo vo = new FepGanttVo();
		vo.setPrjName(prjName);
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setDeveloper(developer);
		vo.setPrjMemo(prjMemo);
		vo.setCheck1(check1);
		vo.setCheck2(check2);
		vo.setCheck3(check3);
		vo.setCheck4(check4);
		vo.setCheck5(check5);
		vo.setCheck6(check6);
		vo.setCheck7(check7);
		vo.setCheck8(check8);
		vo.setCheck9(check9);
		vo.setCheck10(check10);
		/* 2022.06.22 세션검증*/
		String sessionUserName = session.getSessionInfo(request, response, "userName");
		logger.info("--- ACCESS USERNAME : " + sessionUserName);
		if ( sessionUserName == null || sessionUserName.trim().length() == 0 ) {
			return "redirect:/login";
		}
		int result = svc.insertItem(vo);
		if ( result > 0 ) {
			return "<script>window.opener.location.reload(); window.close();</script>";
		} else {
			return "<script>alert('실패');</script>";
		}
		
	}
}
