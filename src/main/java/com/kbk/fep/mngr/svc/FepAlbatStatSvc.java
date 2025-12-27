package com.kbk.fep.mngr.svc;

import java.util.List;

import com.kbk.fep.mngr.dao.vo.FepAlbatStatVo;

public interface FepAlbatStatSvc {
	
	public int selectListCnt(FepAlbatStatVo inputVo, int rangeMinute, boolean isErr);
	
	public List<FepAlbatStatVo> selectList(FepAlbatStatVo inputVo, int rangeMinute, boolean isErr);


}
