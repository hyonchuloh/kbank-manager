package com.kbk.fep.mngr.ctl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kbk.fep.mngr.dao.vo.FepOmmInqRowVo;
import com.kbk.fep.mngr.dao.vo.FepOmmInqVo;
import com.kbk.fep.mngr.svc.FepOmmInqSvc;

@Controller
@RequestMapping("/omm")
public class FepOmmInqCtl {
	
	@Autowired
	private FepOmmInqSvc svc;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/inquery")
	public String telnetTest(
			@RequestParam(value="intfId", required=true) String intfId,
			Model model
			) {
		Map<FepOmmInqRowVo, String> result = new HashMap<FepOmmInqRowVo, String>();
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : {}", "/inquery");
		logger.info("--- PARAM  : {}", intfId);
		FepOmmInqVo vo = svc.inquery("Ext09901700010010Intrfc");
		byte [] input = "".getBytes();
		StringBuffer getHtmlData = new StringBuffer();
		try {
			result = svc.getInputParsing(vo, input);
			getHtmlData.append("<table>\n");
			for ( FepOmmInqRowVo tempVo : result.keySet()) {
				getHtmlData.append("<tr>\n");
				getHtmlData.append("<td>"+tempVo.getAttribute()+"("+tempVo.getAttributeExplain()+")</td>\n");
				getHtmlData.append("<td>"+tempVo.getDataType()+"("+tempVo.getLength()+")" + "</td>\n");
				getHtmlData.append("<td><pre>["+ result.get(tempVo) + "]</pre></td>\n");
				getHtmlData.append("</tr>\n");
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		getHtmlData.append("</table>");
		model.addAttribute("parsingData", getHtmlData);
		logger.info("---------------------------------------");
		return "omm";
		
		
	}

}
