package com.kbk.fep.mngr.dao;

import java.util.List;
import java.util.Map;

import com.kbk.fep.mngr.dao.vo.FepAlsticsVo;

public interface FepAlsticsDao {
	
	public List<FepAlsticsVo> selectAll(String proc_date, String inst_code, String appl_code) ;
	public List<FepAlsticsVo> selectAllByInstCode(String proc_date);
	public List<FepAlsticsVo> selectInstCode(String proc_date, String inst_code);
	public List<FepAlsticsVo> selectApplCode(String proc_date, String inst_code, String appl_code);
	
	public String selectApplName(String applCode) ;
	public String selectInstName(String instCode) ;
	
	public String getMaxDate();
	public String getPeakDate();
	public List<String> getRankInstCode(String target_date, String inst_code);
	
	/* 데일리 통계 UPDATE 서비스 */
	public int selectForInsertDailyStics(String proc_date);
	/* 데일리 통계 DELETE 서비스 */
	public int deleteMinStics(String proc_date);
	
	/* 일자별 거래 건수 조사 */
	public List<FepAlsticsVo> selectCountForDay(String start_proc_date, String end_proc_date, String inst_code, String appl_code);

}
