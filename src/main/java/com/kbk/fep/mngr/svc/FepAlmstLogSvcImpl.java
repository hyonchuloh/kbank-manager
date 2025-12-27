package com.kbk.fep.mngr.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbk.fep.mngr.dao.FepAlmstLogDao;
import com.kbk.fep.mngr.dao.vo.FepAlmstLogVo;

@Service 
public class FepAlmstLogSvcImpl implements FepAlmstLogSvc {
	
	@Autowired
	private FepAlmstLogDao dao;
	
	@Override
	public FepAlmstLogVo selectItem(String logId, String logPoint) {
		try {
			return dao.selectItem(logId, logPoint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<FepAlmstLogVo> selectList(FepAlmstLogVo inputVo, int rangeMinute) {
		try {
			return dao.selectList(inputVo, rangeMinute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<FepAlmstLogVo> selectList2(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr)  {
		try {
			if ( inputVo.getGuid().length() >= 30 ) {
				return dao.selectList2ForGuid(inputVo, rangeMinute, isErr);
			} else {
				return dao.selectList2(inputVo, rangeMinute, isErr);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int selectListCnt(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr) {
		try {
			if ( inputVo.getGuid().length() >= 30 ) {
				return dao.selectListCntForGuid(inputVo, rangeMinute, isErr);
			} else {
				return dao.selectListCnt(inputVo, rangeMinute, isErr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
