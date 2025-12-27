package com.kbk.fep.mngr.dao;

import java.util.List;

import com.kbk.fep.mngr.dao.vo.FepAllineVo;
import com.kbk.fep.mngr.dao.vo.FepSmsInfoVo;

public interface FepManagerDao {
	
	public List<List<String>> selectObject(String sql) throws Exception;
	
	public int updateObject(String sql) throws Exception;
	
	public List<FepSmsInfoVo> getList(String filePath) ;
	
	public String selectLineIpFor089CRD();
	
	public List<FepAllineVo> selectLineListForCrdU2l();
	
	public int updateLineIpForCrdU2l(FepAllineVo vo);
	
	public List<FepAllineVo> selectLineListHistory();
	
	public int insertCrdU2lLog(FepAllineVo vo);
	
	public String tmdownGw(String gwname);
	public String tmbootGw(String gwname);
	
	public String selectUrlForCrdU2l();
	
}
