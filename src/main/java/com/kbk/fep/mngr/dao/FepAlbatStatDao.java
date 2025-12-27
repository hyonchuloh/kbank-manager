package com.kbk.fep.mngr.dao;

import java.util.List;

import com.kbk.fep.mngr.dao.vo.FepAlbatStatVo;

public interface FepAlbatStatDao {
	
	public int selectListCnt(FepAlbatStatVo inputVo, int rangeMinute, boolean isErr) throws Exception;
	
	public List<FepAlbatStatVo> selectList(FepAlbatStatVo inputVo, int rangeMinute, boolean isErr) throws Exception;

}
