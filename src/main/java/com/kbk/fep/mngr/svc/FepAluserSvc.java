package com.kbk.fep.mngr.svc;

import com.kbk.fep.mngr.dao.vo.FepAluserVo;

public interface FepAluserSvc {
	
	public FepAluserVo selectUser();
	
	public FepAluserVo selectUser(String userId);

}
