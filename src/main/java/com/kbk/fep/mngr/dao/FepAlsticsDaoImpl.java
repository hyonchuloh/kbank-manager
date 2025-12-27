package com.kbk.fep.mngr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kbk.fep.mngr.dao.vo.FepAlsticsVo;

@Repository
public class FepAlsticsDaoImpl implements FepAlsticsDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** 
	 * 일자별 거래 건수 서비스 
	 */
	@Override
	public List<FepAlsticsVo> selectCountForDay(String start_proc_date, String end_proc_date, String inst_code,
			String appl_code) {
		StringBuffer sql = new StringBuffer("\n\n\t /* 일자별데이터 건수를 조사합니다. */");
		sql.append("\n\tSELECT PROC_DATE, SUM(LOG_COUNT)  ");
		sql.append("\n\tFROM ALSTICS ");
		sql.append("\n\tWHERE PROC_DATE BETWEEN '"+start_proc_date+"' AND '"+end_proc_date+"' ");
		sql.append("\n\tAND REP_KIND_CODE='' ");
		if ( inst_code != null && inst_code.trim().length() > 0 ) {
			sql.append("\n\tAND INST_CODE='"+inst_code+"' ");
		}
		if ( appl_code != null && appl_code.trim().length() > 0 ) {
			sql.append("\n\tAND APPL_CODE='"+appl_code+"' ");
		}
		sql.append("\n\tGROUP BY PROC_DATE ");
		sql.append("\n\tORDER BY 1 ");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<FepAlsticsVo>() {
			@Override
			public FepAlsticsVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				FepAlsticsVo tempVo = new FepAlsticsVo();
				int index = 1;
				tempVo.setProc_date(rs.getString(index++));
				tempVo.setProc_hour("");
				tempVo.setInst_code("");
				tempVo.setAppl_code("");
				tempVo.setRep_kind_code("");
				tempVo.setTx_code("");
				tempVo.setLog_count(rs.getInt(index++));
				tempVo.setErr_code(0);
				tempVo.setTx_time(0);
				return tempVo;
			}
		});
	}
	
	/**
	 * 분당 데이터 DELETE 서비스
	 */
	@Override
	public int deleteMinStics(String proc_date) {
		StringBuffer query1 = new StringBuffer("\n\n\t /* 분당데이터를 삭제합니다. */");
		query1.append("\n\tDELETE FROM ALSTICS WHERE PROC_DATE='"+proc_date+"' AND  REP_KIND_CODE <> '' ");
		logger.info("--- SQL : " + query1.toString());
		return jdbcTemplate.update(query1.toString());
	}
	
	/**
	 * 데일리 통계 UPDATE 서비스
	 */
	@Override
	public int selectForInsertDailyStics(String proc_date) {
		
		StringBuffer query1 = new StringBuffer("\n\n\t /* 일자별 통계건수를 산출하여 재입력합니다. */");
		query1.append("\n\tINSERT INTO ALSTICS (PROC_DATE, PROC_HOUR, INST_CODE, APPL_CODE, REP_KIND_CODE, TX_CODE, LOG_COUNT,ERR_CODE,TX_TIME )  ");
		query1.append("\n\tSELECT PROC_DATE, '', INST_CODE, APPL_CODE, '', '',  SUM(LOG_COUNT), 0,0 ");
		query1.append("\n\tFROM ALSTICS ");
		query1.append("\n\tWHERE PROC_DATE='"+proc_date+"' ");
		query1.append("\n\tGROUP BY PROC_DATE, INST_CODE, APPL_CODE ");
		logger.info("--- SQL : " + query1.toString());
		
		return jdbcTemplate.update(query1.toString());
	}
	
	@Override
	public List<FepAlsticsVo> selectAll(String proc_date, String inst_code, String appl_code) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 기간별 전체거래 통계 셀렉트 */");
		sql.append("\n\tSELECT PROC_DATE, REP_KIND_CODE, SUM(LOG_COUNT) ");
		sql.append("\n\tFROM alstics ");
		sql.append("\n\tWHERE PROC_DATE='"+proc_date+"' AND REP_KIND_CODE<> '' ");
		if ( inst_code !=null && inst_code.trim().length() > 0 ) {
			sql.append("\n\t	AND INST_CODE='"+inst_code+"' ");
		}
		if ( appl_code !=null && appl_code.trim().length() > 0 ) {
			sql.append("\n\t	AND APPL_CODE='"+appl_code+"' ");
		}
		sql.append("\n\tGROUP BY PROC_DATE, REP_KIND_CODE \n");
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<FepAlsticsVo>() {
			@Override
			public FepAlsticsVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAlsticsVo tempVo = null;
				int index = 1;
				tempVo = new FepAlsticsVo();
				tempVo.setProc_date(rs.getString(index++));
				tempVo.setProc_hour("");
				tempVo.setInst_code("");
				tempVo.setAppl_code("");
				tempVo.setRep_kind_code(rs.getString(index++));
				tempVo.setTx_code("");
				tempVo.setLog_count(rs.getInt(index++));
				tempVo.setErr_code(0);
				tempVo.setTx_time(0);
				return tempVo;
			}
		});
	}
	
	@Override
	public List<FepAlsticsVo> selectAllByInstCode(String proc_date) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 기관별 통계 셀렉트 */");
		sql.append("\n\tSELECT PROC_DATE, INST_CODE, REP_KIND_CODE, SUM(LOG_COUNT) ");
		sql.append("\n\tFROM alstics ");
		sql.append("\n\tWHERE PROC_DATE='"+proc_date+"' AND REP_KIND_CODE <> '' ");
		sql.append("\n\tGROUP BY PROC_DATE, INST_CODE, REP_KIND_CODE ");
		sql.append("\n\tORDER BY INST_CODE, REP_KIND_CODE \n");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<FepAlsticsVo>() {
			@Override
			public FepAlsticsVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAlsticsVo tempVo = null;
				int index = 1;
				tempVo = new FepAlsticsVo();
				tempVo.setProc_date(rs.getString(index++));
				tempVo.setProc_hour("");
				tempVo.setInst_code(rs.getString(index++));
				tempVo.setAppl_code("");
				tempVo.setRep_kind_code(rs.getString(index++));
				tempVo.setTx_code("");
				tempVo.setLog_count(rs.getInt(index++));
				tempVo.setErr_code(0);
				tempVo.setTx_time(0);
				return tempVo;
			}
		});
	}

	@Override
	public List<FepAlsticsVo> selectInstCode(String proc_date, String inst_code) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 업무별 통계 셀렉트 */");
		sql.append("\n\tSELECT PROC_DATE, REP_KIND_CODE, SUM(LOG_COUNT) ");
		sql.append("\n\tFROM alstics ");
		sql.append("\n\tWHERE PROC_DATE='"+proc_date+"' AND INST_CODE='"+inst_code+"' AND REP_KIND_CODE <> '' ");
		sql.append("\n\tGROUP BY PROC_DATE, REP_KIND_CODE \n");
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<FepAlsticsVo>() {
			@Override
			public FepAlsticsVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAlsticsVo tempVo = null;
				int index = 1;
				tempVo = new FepAlsticsVo();
				tempVo.setProc_date(rs.getString(index++));
				tempVo.setProc_hour("");
				tempVo.setInst_code("");
				tempVo.setAppl_code("");
				tempVo.setRep_kind_code(rs.getString(index++));
				tempVo.setTx_code("");
				tempVo.setLog_count(rs.getInt(index++));
				tempVo.setErr_code(0);
				tempVo.setTx_time(0);
				return tempVo;
			}
		});
	}

	@Override
	public List<FepAlsticsVo> selectApplCode(String proc_date, String inst_code, String appl_code) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 업무별 통계 셀렉트 */");
		sql.append("\n\tSELECT PROC_DATE, REP_KIND_CODE, SUM(LOG_COUNT) ");
		sql.append("\n\tFROM alstics ");
		sql.append("\n\tWHERE PROC_DATE='"+proc_date+"' AND INST_CODE='"+inst_code+"' AND APPL_CODE='"+appl_code+"' AND REP_KIND_CODE <> '' ");
		sql.append("\n\tGROUP BY PROC_DATE, REP_KIND_CODE \n");
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<FepAlsticsVo>() {
			@Override
			public FepAlsticsVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAlsticsVo tempVo = null;
				int index = 1;
				tempVo = new FepAlsticsVo();
				tempVo.setProc_date(rs.getString(index++));
				tempVo.setProc_hour("");
				tempVo.setInst_code("");
				tempVo.setAppl_code("");
				tempVo.setRep_kind_code(rs.getString(index++));
				tempVo.setTx_code("");
				tempVo.setLog_count(rs.getInt(index++));
				tempVo.setErr_code(0);
				tempVo.setTx_time(0);
				return tempVo;
			}
		});
	}
	
	@Override
	public String selectApplName(String applCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 업무이름 조회 */");
		sql.append("\n\t SELECT APPL_NAME FROM ALAPPL WHERE APPL_CODE='"+applCode+"' \n");
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("APPL_NAME");
			}
		});
	}
	
	@Override
	public String selectInstName(String instCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 기관이름 조회 */");
		sql.append("\n\t SELECT INST_NAME FROM ALINST WHERE INST_CODE='"+instCode+"' \n");
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("INST_NAME");
			}
		});
	}
	
	@Override
	public String getMaxDate() {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 역대 거래 날짜 조회 */");
		sql.append("\n\t SELECT PROC_DATE, SUM(LOG_COUNT) AS CNT FROM ALSTICS WHERE REP_KIND_CODE <> '' GROUP BY PROC_DATE ORDER BY CNT DESC LIMIT 1 \n");
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("PROC_DATE");
			}
		});
	}
	
	@Override
	public String getPeakDate() {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 역대 피크 날짜 조회 */");
		sql.append("\n\t SELECT PROC_DATE, REP_KIND_CODE, SUM(LOG_COUNT) AS CNT FROM ALSTICS WHERE REP_KIND_CODE <> '' GROUP BY PROC_DATE, REP_KIND_CODE ORDER BY CNT DESC LIMIT 1 \n");
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("PROC_DATE");
			}
		});
	}
	
	@Override
	public List<String> getRankInstCode(String target_date, String inst_code) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 특정 날짜에 대한 기관(업무) 랭킹정보 */");
		if ( inst_code !=null && inst_code.trim().length() > 0 ) {
			sql.append("\n\tSELECT APPL_CODE, SUM(LOG_COUNT) AS CNT ");
			sql.append("\n\tFROM alstics ");
			sql.append("\n\tWHERE PROC_DATE='"+target_date+"' ");
			sql.append("\n\t	  AND INST_CODE='"+inst_code+"' ");
			sql.append("\n\t	  AND REP_KIND_CODE <> '' ");
			sql.append("\n\tGROUP BY APPL_CODE ");
			sql.append("\n\tORDER BY CNT DESC ");
			sql.append("\n\tLIMIT 12 \n");
		} else {
			sql.append("\n\tSELECT INST_CODE, SUM(LOG_COUNT) AS CNT ");
			sql.append("\n\tFROM alstics ");
			sql.append("\n\tWHERE PROC_DATE='"+target_date+"' ");
			sql.append("\n\t	  AND REP_KIND_CODE <> '' ");
			sql.append("\n\tGROUP BY INST_CODE ");
			sql.append("\n\tORDER BY CNT DESC ");
			sql.append("\n\tLIMIT 12 \n");
		}
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
	}
	
}
