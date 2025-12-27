package com.kbk.fep.mngr.ctl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kbk.fep.mngr.dao.FepAluserDao;
import com.kbk.fep.mngr.dao.vo.FepAluserVo;
import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.util.FepSessionUtil;

@Controller
public class FepAuthLoginCtl {
	
	@Autowired
	private FepCommPropVo prop;
	@Autowired
	private FepSessionUtil session;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/")
	public String login(Model model) {
		return "redirect:/login";
	}
	
	@GetMapping("/errorauth")
	public String error(Model model) {
		model.addAttribute("env", prop.getProfiles());
		return "auth/error";
	}
	
	@GetMapping("/errorlogin")
	public String errorlogin(Model model,
			HttpServletRequest request,
			@RequestParam(value="history", required=false, defaultValue="/log/list") String history
			) {
		try {
			logger.info("---------------------------------------");
			logger.info("--- APP NAME : /errorlogin (GET)");
			logger.info("--- PARAM ['history'] = "+ history);
			logger.info("---------------------------------------");
			model.addAttribute("env", prop.getProfiles());
			model.addAttribute("history", history);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "auth/errorlogin";
	}
	
	@GetMapping("/login")
	public String loginGet(Model model,
			HttpServletRequest request,
			@RequestParam(value="history", required=false, defaultValue="/log/list") String history
			) {
		try {
			logger.info("---------------------------------------");
			logger.info("--- APP NAME : /login (GET)");
			logger.info("--- PARAM ['history'] = "+ history);
			logger.info("---------------------------------------");
			model.addAttribute("env", prop.getProfiles());
			model.addAttribute("history", history);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "auth/login";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String loginPost(
			@RequestParam("userId") String userId,
			@RequestParam("userPw") String userPw,
			@RequestParam(value="history", required=false) String history,
			HttpServletRequest request, 
			HttpServletResponse response) {
		try {
			logger.info("---------------------------------------");
			logger.info("--- APP NAME : /login (POST)");
			String userName = "";
			// 1. LDAP 검증 방식은 아직 검토 전
			userName = session.getSessionInfo(request, response, "userName");
			logger.info("--- SESSION ['userName'] = "+ userName);
			logger.info("---------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "<script>alert('로그인 실패!'); location.href='/errorlogin';</script>";
	
	}
	
	@GetMapping("/logout")
	public String logoutGet(Model model,
			HttpServletRequest request) {
		try {
			logger.info("---------------------------------------");
			logger.info("--- APP NAME : /logout (GET)");
			logger.info("---------------------------------------");
			model.addAttribute("env", prop.getProfiles());
			request.getSession().invalidate(); // 세션 초기화
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/login";
	}

}
