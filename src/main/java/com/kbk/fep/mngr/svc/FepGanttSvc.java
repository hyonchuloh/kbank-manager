package com.kbk.fep.mngr.svc;

import java.util.List;

import com.kbk.fep.mngr.dao.vo.FepGanttVo;

public interface FepGanttSvc {
	
	public List<FepGanttVo> selectBizList(String startDay, String endDay);
	
	public FepGanttVo selectItem(int seqNo);
	
	public int editItem(FepGanttVo vo);
	
	public int deleteItem(int seqNo);
	
	public int insertItem(FepGanttVo vo);
	
	public List<String> selectDayList(String startDay, String endDay) ;

}
