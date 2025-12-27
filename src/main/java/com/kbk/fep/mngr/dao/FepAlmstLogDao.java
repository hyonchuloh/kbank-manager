package com.kbk.fep.mngr.dao;

import java.util.List;

import com.kbk.fep.mngr.dao.vo.FepAlmstLogVo;

public interface FepAlmstLogDao {
	
	public FepAlmstLogVo selectItem(String logId, String logPoint) throws Exception;
	
	public int selectListCnt(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr) throws Exception ;
	
	public List<FepAlmstLogVo> selectList2(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr) throws Exception;
	
	public List<FepAlmstLogVo> selectList(FepAlmstLogVo inputVo, int rangeMinute) throws Exception ;
	
	public List<FepAlmstLogVo> selectList2ForGuid(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr) throws Exception;
	
	public int selectListCntForGuid(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr) throws Exception ;
	
}
