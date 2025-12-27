package com.kbk.fep.mngr.ctl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.util.FepSessionUtil;

@Controller
@RequestMapping("/biz")
public class FepBusinessCtl {

	@Autowired
	private FepCommPropVo prop;
	@Autowired
	private FepSessionUtil session;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/timeout")
	@ResponseBody
	public String timeout(
			@RequestBody(required=false) String body
			) {
		try {
			logger.info("---------------------------------------");
			logger.info("--- APP NAME : /biz/timeout");
			logger.info("--- REQUEST BODY : " + body);
			logger.info("--- WAITING...");
			Thread.sleep(65*1000);
			logger.info("---------------------------------------");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "result";
	}
	
	@GetMapping(value="/file/{instCode}")
	public String fileGet(
			@PathVariable("instCode") String instCode,
			Model model) {
		model.addAttribute("env", prop.getProfiles());
		return "file";
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("files") MultipartFile file) throws IllegalStateException, IOException {
		logger.info("---------------------------------------");
		logger.info("--- URL : /biz/upload (POST)");
		logger.info("--- INPUT : " + file.getName());
		String absolutePath = "/tmp/" + file.getOriginalFilename();
		File dest = new File(absolutePath);
		file.transferTo(dest);
		logger.info("--- RESULT : " + dest.length());
		logger.info("---------------------------------------");
		return "<script>alert('UPLOAD COMPLETE!'); window.history.back();</script>";	
	}
	
}
