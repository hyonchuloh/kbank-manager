package com.kbk.fep.mngr.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kbk.fep.mngr.svc.FepApiSvc;

/**
 * 웹서비스가 아닌 API 서비를 위하여 
 * 아래 컨트롤러는 RestController로 선언합니다.
 * @author ohhyonchul
 *
 */
@RestController
public class FepApiCtl {
	
	@Autowired
	private FepApiSvc apiSvc;
	
	@GetMapping("/api/restart-gw")
	public RestartVo restartGw(
			@RequestParam("gwName") String gwName
			) {
		RestartVo retValue = new RestartVo(gwName, false);
		retValue.setResult(apiSvc.restartGw(gwName));
		return retValue;
	}

}

class RestartVo {
	
	private String gwName;
	private boolean result;
	
	public RestartVo(String gwName, boolean result) {
		this.gwName = gwName;
		this.result = result;
	}
	public String getGwName() {
		return gwName;
	}
	public void setGwName(String gwName) {
		this.gwName = gwName;
	}
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
}
