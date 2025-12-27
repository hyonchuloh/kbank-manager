package com.kbk.fep.mngr.svc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbk.fep.mngr.dao.FepAlsticsDao;
import com.kbk.fep.mngr.dao.vo.FepAlsticsVo;

@Service
public class FepAlsticsSvcImpl implements FepAlsticsSvc {
	
	@Autowired
	private FepAlsticsDao dao;
	
	/**
	 * 일자별 거래량 통계 for day
	 */
	@Override
	public List<FepAlsticsVo> selectVolumDay(String start_date, String end_date, String inst_code, String appl_code) {
		return dao.selectCountForDay(start_date, end_date, inst_code, appl_code);
	}
	/**
	 * 데일리 통계 DELETE 서비스 
	 */
	@Override
	public int deleteMinStics(String proc_date) {
		return dao.deleteMinStics(proc_date);
	}
	/**
	 * 데일리 통계 UPDATE 서비스
	 */
	@Override
	public int selectForInsertDailyStics(String proc_date) {
		return dao.selectForInsertDailyStics(proc_date);
	}
	
	@Override
	public String selectAll(List<String> dates, String inst_code, String appl_code) {
		
		StringBuffer retValue = new StringBuffer();
		StringBuffer subValue = new StringBuffer();
		
		retValue.append("[");
		
		for ( int i=0; i<dates.size(); i++ ) {
			
			List<FepAlsticsVo> list = dao.selectAll(dates.get(i), inst_code, appl_code);
			
			if ( list.size() > 0 ) {
				retValue.append("[");
				for ( FepAlsticsVo vo : list ) {
					subValue.append(",['2000-01-01 "+vo.getXTime()+"',"+vo.getLog_count()+"]");
				}
				retValue.append(subValue.substring(1));
				retValue.append("]\n");
				subValue = new StringBuffer();
				
				if ( i < dates.size() )
					retValue.append(",\n");
				
			}
			
		}
		retValue.append("]");
		return retValue.toString();
	}
	
	@Override
	public Map<String, String> selectRank(String proc_date, String inst_code) {
		Map<String, String> retValue = new LinkedHashMap<String, String>();
		List<String> rankInstCodes = dao.getRankInstCode(proc_date, inst_code);
		StringBuffer lineValue = new StringBuffer();
		for ( String code : rankInstCodes) {
			List<FepAlsticsVo> list = null;
			if ( inst_code !=null && inst_code.trim().length()>0 ) {
				list = dao.selectAll(proc_date, inst_code , code);
			} else {
				list = dao.selectAll(proc_date, code , null);
			}
			if ( list.size() > 0 ) {
				for ( FepAlsticsVo vo : list ) {
					lineValue.append(",['2000-01-01 "+vo.getXTime()+"',"+vo.getLog_count()+"]");
				}
				retValue.put(code, lineValue.substring(1));
				lineValue = new StringBuffer();
			}
		}
		return retValue;
	}
	
	@Override
	public String selectInstCode(String proc_date, String inst_code) {
		return null;
	}
	
	@Override
	public String selectApplCode(String proc_date, String inst_code, String appl_code) {
		return null;
		
	}
	
	@Override
	public String selectApplName(String applCode) {
		return dao.selectApplName(applCode);
	}
	@Override
	public String selectInstName(String instCode) {
		return dao.selectInstName(instCode);
	}
	
	@Override
	public String getMaxDate() {
		return dao.getMaxDate();
	}
	
	@Override
	public String getPeakDate() {
		return dao.getPeakDate();
	}

}
