package com.kbk.fep.mngr.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbk.fep.mngr.dao.FepAlbatStatDao;
import com.kbk.fep.mngr.dao.vo.FepAlbatStatVo;

@Service
public class FepAlbatStatSvcImpl implements FepAlbatStatSvc {
	
	@Autowired
	private FepAlbatStatDao dao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<FepAlbatStatVo> selectList(FepAlbatStatVo inputVo, int rangeMinute, boolean isErr) {
		List<FepAlbatStatVo> retValue = null;
		try {
			retValue = dao.selectList(inputVo, rangeMinute, isErr);
		} catch (Exception e) {
			logger.error("--- ERROR : ", e);
		}
		return retValue;
	}
	
	@Override
	public int selectListCnt(FepAlbatStatVo inputVo, int rangeMinute, boolean isErr) {
		int retValue = 0;
		try {
			retValue= dao.selectListCnt(inputVo, rangeMinute, isErr);
		} catch (Exception e) {
			logger.error("--- ERROR : ", e);
		}
		return retValue;
	}
	
}
