package com.kbk.fep.mngr.dao;

import java.util.List;

import com.kbk.fep.mngr.dao.vo.FepGanttVo;

public interface FepGanttDao {
	
	public List<FepGanttVo> selectList(String startDay, String endDay);
	
	public FepGanttVo selectItem(int seqNo);
	
	public int insertItem(FepGanttVo vo);
	
	public int editItem(FepGanttVo vo);
	
	public int deleteItem(int seqNo);

}
