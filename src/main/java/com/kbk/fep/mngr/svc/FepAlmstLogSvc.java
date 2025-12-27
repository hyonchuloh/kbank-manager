package com.kbk.fep.mngr.svc;

import java.util.List;

import com.kbk.fep.mngr.dao.vo.FepAlmstLogVo;

public interface FepAlmstLogSvc {
	
	public FepAlmstLogVo selectItem(String logId, String logPoint) ;
	
	public int selectListCnt(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr);
	
	public List<FepAlmstLogVo> selectList2(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr);
	
	public List<FepAlmstLogVo> selectList(FepAlmstLogVo inputVo, int rangeMinute);

}
