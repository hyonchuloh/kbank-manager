package com.kbk.fep.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.kbk.fep.mngr.dao.vo.FepAluserVo;
import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.svc.FepAluserSvc;


@Component
public class FepSessionUtil {
	
	@Autowired
	private FepAluserSvc aluserSvc;
	@Autowired
	private FepCommPropVo prop;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 세션정보를 가져와 없으면 주입하고 있으면 값을 리턴한다.
	 * @param request
	 * @param response
	 * @param attributeName
	 * @return
	 */
	public String getSessionInfo(HttpServletRequest request, HttpServletResponse response, String attributeName) {
		
		// 여기 서비스에서 LDAP을 다녀오든 SSO를 다녀오든 해라
		String userId = "userIdForProd";				
		if ( prop.getProfiles().startsWith("stag") ) {
			userId = "userIdForStag";
		}
		String retValue = "";
//		Cookie auth = WebUtils.getCookie(request, "AUTH"); // 쿠키정보를 가져와
		Cookie auth = null;
		FepAluserVo vo = null;
		logger.info("--- GET COOKIE [AUTH] : " + auth);
		if ( ObjectUtils.isEmpty(auth) || !auth.getValue().equalsIgnoreCase(request.getSession().getId()) ) {
			vo = aluserSvc.selectUser(userId); 
			request.getSession().setAttribute("userId", vo.getUser_id());
			request.getSession().setAttribute("userName", vo.getUser_name());
			request.getSession().setAttribute("userRole", vo.getUser_role());
			request.getSession().setAttribute("staType", vo.getSta_type());
			request.getSession().setAttribute("email", vo.getEmail());
			logger.debug("--- SET COOKIE [AUTH] : " +  vo.getUser_name() + "(" + vo.getUser_id()+ ")");
			response.addCookie(new Cookie("AUTH", request.getSession().getId()) {{
				setMaxAge(60*20); // 세션 유지시간 20분
				setPath("/");
			}});
			/* 신규 세션인 경우 리턴할 값들 */
			if ( "userName".equals(attributeName) )
				retValue = vo.getUser_name();
			else if ( "userRole".equals(attributeName) )
				retValue = Integer.toString(vo.getUser_role());
			else if ( "userId".equals(attributeName) )
				retValue = vo.getUser_id();
			else if ( "email".equals(attributeName) )
				retValue = vo.getEmail();
			else if ( "staType".equals(attributeName) )
				retValue = Integer.toString(vo.getSta_type());
		} else {
			retValue = (String) request.getSession().getAttribute(attributeName); // userName, userRole, userId, email, staType
		}
		return retValue;
	}

}
