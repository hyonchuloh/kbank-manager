package com.kbk.fep.mngr.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbk.fep.mngr.dao.FepAluserDao;
import com.kbk.fep.mngr.dao.vo.FepAluserVo;

@Service
public class FepAluserSvcImpl implements FepAluserSvc {
	
	@Autowired
	private FepAluserDao dao;
	
	@Override
	public FepAluserVo selectUser(String userId) {
		return dao.selectUser("20160802");
	}
	@Override
	public FepAluserVo selectUser() {
		return dao.selectUser("20160802");
	}
	

}
