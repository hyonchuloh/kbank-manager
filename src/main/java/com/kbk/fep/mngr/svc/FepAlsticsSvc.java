package com.kbk.fep.mngr.svc;

import java.util.List;
import java.util.Map;

import com.kbk.fep.mngr.dao.vo.FepAlsticsVo;

public interface FepAlsticsSvc {
	
	public String selectAll(List<String> dates, String inst_code, String appl_code) ;
	public Map<String, String> selectRank(String proc_date, String inst_code);
	public String selectInstCode(String proc_date, String inst_code);
	public String selectApplCode(String proc_date, String inst_code, String appl_code);
	
	public String selectApplName(String applCode) ;
	public String selectInstName(String instCode) ;
	
	public String getMaxDate();
	public String getPeakDate();
	
	/**
	 * 데일리 통계 UPDATE 서비스
	 */
	public int selectForInsertDailyStics(String proc_date) ;
	/**
	 *  데일리 통계 DELETE 서비스 
	 */
	public int deleteMinStics(String proc_date);
	
	/**
	 * 일자별 거래량 볼륨 for day
	 */
	public List<FepAlsticsVo> selectVolumDay(String start_date, String end_date, String inst_code, String appl_code);
	

}
