package com.kbk.fep.mngr.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kbk.fep.mngr.dao.vo.FepAlbatStatVo;
import com.kbk.fep.util.FepStrUtil;


/**
 * INDEX1 = INST_CODE+APPL_CODE+SR_FLAG+STRT_DATE+STRT_TIME
 * INDEX2 = APPL_CODE
 * @author 20160521
 *
 */
@Repository
public class FepAlbatStatDaoImpl implements FepAlbatStatDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, String> eaiIntfIds;
	private long updateTime;
	
	@Override
	public int selectListCnt(FepAlbatStatVo inputVo, int rangeMinute, boolean isErr) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 조회 건수를 SELECT */ ");
		sql.append("\n\tSELECT COUNT(*) AS TOT_CNT ");
		sql.append("\n\tFROM albatstat " );
		sql.append("\n\tWHERE 1=1  ");
		sql.append("\n\t	AND STRT_DATE='"+ inputVo.getStrtDate() +"' ");
		sql.append("\n\t	AND STRT_TIME BETWEEN '"+ inputVo.getStrtTime() +"' AND '" + FepStrUtil.nextTimestamp(inputVo.getStrtTime(), rangeMinute)+"' ");
		if ( inputVo.getInstCode() != null && inputVo.getInstCode().trim().length() > 0 ) 
			sql.append("\n\t	AND INST_CODE='"+inputVo.getInstCode()+"' ");
		if ( inputVo.getApplCode() != null && inputVo.getApplCode().trim().length() > 0 ) {
			if ( inputVo.getApplCode().contains(";") ) {
				String applCodes = "'"+inputVo.getApplCode().replaceAll(";", "','")+"'";
				sql.append("\n\t	AND APPL_CODE IN (" + applCodes + ") ");
			} else if ( inputVo.getApplCode().length() == 3 ) { 	// 059
				sql.append("\n\t	AND APPL_CODE IN ('"+inputVo.getApplCode()+"R', '"+inputVo.getApplCode()+"S', '"+inputVo.getApplCode()+"H', '"+inputVo.getApplCode()+"M', '"+inputVo.getApplCode()+"G', '"+inputVo.getApplCode()+"E', '"+inputVo.getApplCode()+"F', '"+inputVo.getApplCode()+"B') ");
			} else {
				sql.append("\n\t	AND APPL_CODE='"+inputVo.getApplCode()+"' ");
			}
		}
		if ( inputVo.getSrFlag() != null && inputVo.getSrFlag().trim().length() > 0 ) {
			if ( inputVo.getSrFlag().contains(";") ) {
				String srFlags =  "'"+inputVo.getSrFlag().replaceAll(";", "','")+"'";
				sql.append("\n\t	AND SR_FLAG IN ("+srFlags+") ");
			} else {
				sql.append("\n\t	AND SR_FLAG='"+inputVo.getSrFlag()+"' ");
			}
		}
		if ( isErr )
			sql.append("\n\t	AND EXT_PROC_FLAG <> 'X'  ");
		sql.append("\n\t");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	@Override
	public List<FepAlbatStatVo> selectList(FepAlbatStatVo inputVo, int rangeMinute, boolean isErr) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 실제 로그데이터 SELECT */ ");
		sql.append("\n\tSELECT INST_CODE, APPL_CODE, FILE_CODE, SR_FLAG, STRT_DATE, STRT_TIME, EXT_PROC_FLAG, END_DATE, END_TIME, EXT_FIN_NO, FAIL_CODE, STRT_SEND_TYPE, FILE_NAME, TOT_FILE_NO, RES_CODE, FILLER1, FILLER2 ");
		sql.append("\n\tFROM albatstat A " );
		sql.append("\n\tWHERE 1=1  ");
		sql.append("\n\t	AND STRT_DATE='"+ inputVo.getStrtDate() +"' ");
		sql.append("\n\t	AND STRT_TIME BETWEEN '"+ inputVo.getStrtTime() +"' AND '" + FepStrUtil.nextTimestamp(inputVo.getStrtTime(), rangeMinute)+"' ");
		if ( inputVo.getInstCode() != null && inputVo.getInstCode().trim().length() > 0 ) 
			sql.append("\n\t	AND INST_CODE='"+inputVo.getInstCode()+"' ");
		if ( inputVo.getApplCode() != null && inputVo.getApplCode().trim().length() > 0 ) {
			if ( inputVo.getApplCode().contains(";") ) {
				String applCodes = "'"+inputVo.getApplCode().replaceAll(";", "','")+"'";
				sql.append("\n\t	AND APPL_CODE IN (" + applCodes + ") ");
			} else if ( inputVo.getApplCode().length() == 3 ) { 	// 059
				sql.append("\n\t	AND APPL_CODE IN ('"+inputVo.getApplCode()+"R', '"+inputVo.getApplCode()+"S', '"+inputVo.getApplCode()+"H', '"+inputVo.getApplCode()+"M', '"+inputVo.getApplCode()+"G', '"+inputVo.getApplCode()+"E', '"+inputVo.getApplCode()+"F', '"+inputVo.getApplCode()+"B') ");
			} else {
				sql.append("\n\t	AND APPL_CODE='"+inputVo.getApplCode()+"' ");
			}
		}
		if ( inputVo.getSrFlag() != null && inputVo.getSrFlag().trim().length() > 0 ) {
			if ( inputVo.getSrFlag().contains(";") ) {
				String srFlags =  "'"+inputVo.getSrFlag().replaceAll(";", "','")+"'";
				sql.append("\n\t	AND SR_FLAG IN ("+srFlags+") ");
			} else {
				sql.append("\n\t	AND SR_FLAG='"+inputVo.getSrFlag()+"' ");
			}
		}
		if ( isErr )
			sql.append("\n\t	AND EXT_PROC_FLAG <> 'X'  ");
		
		sql.append("\n\tORDER BY STRT_TIME DESC ");
		sql.append("\n\tLIMIT " + inputVo.getStartRowNum() + ", " + inputVo.getSelectCount());
		sql.append("\n\t");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<FepAlbatStatVo>() {
			@Override
			public FepAlbatStatVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAlbatStatVo tempVo = new FepAlbatStatVo();
				int index = 1;
				
				tempVo.setInstCode(rs.getString(index++));
				tempVo.setApplCode(rs.getString(index++));
				tempVo.setFileCode(rs.getString(index++));
				tempVo.setSrFlag(rs.getString(index++));
				tempVo.setStrtDate(rs.getString(index++));
				tempVo.setStrtTime(rs.getString(index++));
				tempVo.setExtProcFlag(rs.getString(index++));
				tempVo.setEndDate(rs.getString(index++));
				tempVo.setEndTime(rs.getString(index++));
				tempVo.setExtFinNo(rs.getLong(index++));
				tempVo.setFailCode(rs.getInt(index++));
				tempVo.setStrtSendType(rs.getInt(index++));
				tempVo.setFileName(rs.getString(index++));
				tempVo.setTotFileNo(rs.getLong(index++));
				tempVo.setResCode(rs.getString(index++));
				tempVo.setFiller1(rs.getInt(index++));
				tempVo.setFiller2(rs.getString(index++));
				
				tempVo.setElapsTime(FepStrUtil.elapseTime(tempVo.getStrtDate()+tempVo.getStrtTime(), tempVo.getStrtDate()+tempVo.getEndTime()));
				
				/* EAI 인터페이스 ID를 찾아 set하는 여정 */
				long currentTime = new Date().getTime();	
				if ( eaiIntfIds == null || updateTime + (24*60*60*1000) < currentTime) {	// null 이거나 update한지 하루가 지났으면 update
					eaiIntfIds = readIds();
					updateTime = currentTime;
				}
				String eaiIntfId = eaiIntfIds.get(tempVo.getFileCode());	// 파일코드로 바로 찾을 수 있다면 셋하고
				if ( eaiIntfId == null ) {									// 바로 못찾는 경우는 map 을 풀써치해서 파일명이라도 일치하는걸 가져와
					for ( String fileName : eaiIntfIds.keySet() ) {
						if ( tempVo.getFileName().contains(fileName) ) {
							eaiIntfId = eaiIntfIds.get(fileName);
							break;
						}
					}
				}
				tempVo.setEaiIntfId(eaiIntfId);								// 셋 완료
				
				return tempVo;
			}
		});
	}
	
	
	/**
	 * 실물 인터페이스 ID를 읽어옵니다.
	 * @return
	 */
	private Map<String, String> readIds() {
		Map<String, String> retValue = new HashMap<String, String>();
		FileReader fr = null;
		BufferedReader br = null;
		String line = "";
		try {
			fr = new FileReader("/kbkdat/chs/eai/fep/rcv/interfaceid/interfaceid.ctl");
			br = new BufferedReader(fr);
			
			line = "";
			while ( ( line = br.readLine() ) != null ) {
				String [] values = line.split("=");
				retValue.put(values[0], values[1]);
			}
		} catch ( ArrayIndexOutOfBoundsException aioe ) {
			logger.error("--- ERROR " + line, aioe);
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		} finally {
			try {
				if ( br != null ) br.close();
				if ( fr != null ) fr.close();
			} catch ( Exception e ) {}
		}
		return retValue;
	}
}
