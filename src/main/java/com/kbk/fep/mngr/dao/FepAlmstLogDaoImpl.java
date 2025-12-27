package com.kbk.fep.mngr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kbk.fep.mngr.dao.vo.FepAlmstLogVo;
import com.kbk.fep.util.FepPropInfo;
import com.kbk.fep.util.FepStrUtil;

@Repository
public class FepAlmstLogDaoImpl implements FepAlmstLogDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FepPropInfo info;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public FepAlmstLogVo selectItem(String logId, String logPoint) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 단건 데이터 셀렉트 */");
		sql.append("\n\tSELECT PROC_DATE, PROC_MTIME, LOG_ID, INST_CODE, APPL_CODE, KIND_CODE, LOG_POINT, TX_CODE, MSG_DATA, HEADER_SIZE, TX_UID, ERR_CODE, TX_TIME, RES_FLAG, PROC_HOUR, XID, TX_STATE, SESSION_INDEX, HEAD_MAPPING_TYPE, BODY_MAPPING_TYPE   ");
		sql.append("\n\t	,(SELECT NAME FROM ALTX WHERE REP_KIND_CODE=A.KIND_CODE AND TX_CODE=A.TX_CODE AND APPL_CODE=A.APPL_CODE LIMIT 1) AS TX_NAME ");
		sql.append("\n\t	,(SELECT APPL_NAME FROM alappl WHERE APPL_CODE=A.APPL_CODE LIMIT 1) AS APPL_NAME ");
		sql.append("\n\t	,(SELECT INST_NAME FROM alinst WHERE INST_CODE=A.INST_CODE LIMIT 1 ) AS INST_NAME ");
		sql.append("\n\tFROM almstlog A");
		sql.append("\n\tWHERE 1=1 "); 
		sql.append("\n\t	AND LOG_ID='"+logId+"' ");
		sql.append("\n\t	AND LOG_POINT='"+logPoint+"' ");
		sql.append("\n\tLIMIT 1");
		sql.append("\n");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<FepAlmstLogVo>() {
			@Override
			public FepAlmstLogVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAlmstLogVo tempVo = null;
				int index = 1;
				tempVo = new FepAlmstLogVo();
				tempVo.setProcDate(rs.getString(index++));
				tempVo.setProcMtime(rs.getString(index++));
				tempVo.setLogId(rs.getString(index++));
				tempVo.setInstCode(rs.getString(index++));
				tempVo.setApplCode(rs.getString(index++));
				tempVo.setKindCode(rs.getString(index++));
				tempVo.setLogPoint(rs.getString(index++));
				tempVo.setTxCode(rs.getString(index++));
				tempVo.setMsgData(rs.getBytes(index++));
				tempVo.setHeaderSize(rs.getString(index++));
				tempVo.setTxUid(rs.getString(index++));
				tempVo.setErrCode(rs.getString(index++));
				tempVo.setTxTime(rs.getString(index++));
				tempVo.setResFlag(rs.getString(index++));
				tempVo.setProcHour(rs.getString(index++));
				tempVo.setXid(rs.getString(index++));
				tempVo.setTxState(rs.getString(index++));
				tempVo.setSessionIndex(rs.getString(index++));
				tempVo.setHeadMappingType(rs.getString(index++));
				tempVo.setBodyMappingType(rs.getString(index++));
				tempVo.setTxName(rs.getString(index++));
				tempVo.setApplName(rs.getString(index++));
				tempVo.setInstName(rs.getString(index++));
				
				/* 거래고유번호 존재하는 경우 세팅해줍니다. */
				String tempSeqNum = null;
				String tempResCode = null;
				try {
					tempSeqNum = info.getValue("TXINFO.SEQNUM." + tempVo.getInstCode() + "." + tempVo.getApplCode());
					if ( tempSeqNum != null && tempSeqNum.contains(";") ) {
						tempVo.setTrxSeqNum(tempVo.getFlatData().substring(Integer.parseInt(tempSeqNum.split(";")[0]), Integer.parseInt(tempSeqNum.split(";")[1])));
					} else if ( tempVo.getMsgDataStr().length() > 320 ){
						tempVo.setTrxSeqNum(tempVo.getMsgDataStr().substring(300,320));
					}
					
					tempResCode = info.getValue("TXINFO.RESPCD." + tempVo.getInstCode() + "." + tempVo.getApplCode());
					if ( tempResCode != null && tempResCode.contains(";") ) {
						tempVo.setTrxRespCode(tempVo.getFlatData().substring(Integer.parseInt(tempResCode.split(";")[0]), Integer.parseInt(tempResCode.split(";")[1])));
					}
				} catch (Exception e ) {
					logger.error("--- 거래고유번호 파싱하다 오류발생 [tempSeqNum]=["+tempSeqNum+"] : " + e.toString());
					logger.error("--- 그러나 계속 진행합니다.");
				}
				return tempVo;
			}
		});
	}
	
	@Override
	public List<FepAlmstLogVo> selectList(FepAlmstLogVo inputVo, int rangeMinute) throws Exception {
		
		String sql = "\n\n\tSELECT * \r" + 
				"\n\t	FROM almstlog\r" + 
				"\n\t	WHERE PROC_DATE='"+inputVo.getProcDate()+"' \r" + // '20200612'
				"\n\t	AND PROC_MTIME BETWEEN '"+inputVo.getProcMtime()+"' AND "+FepStrUtil.nextTimestamp(inputVo.getProcMtime(), rangeMinute)+" \r" +  //'140000000' , '140200000'
				"\n\t	AND INST_CODE='"+inputVo.getInstCode()+"' AND APPL_CODE=?'"+inputVo.getApplCode()+"' \r" +  // '104', '712O'
				"\n\t	ORDER BY LOG_ID, LOG_POINT\r" + 
				"\n\t	LIMIT 25 \n";
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql, new RowMapper<FepAlmstLogVo>() {
			@Override
			public FepAlmstLogVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAlmstLogVo tempVo = null;
				int index = 1;
				
				tempVo = new FepAlmstLogVo();
				tempVo.setProcDate(rs.getString(index++));
				tempVo.setProcMtime(rs.getString(index++));
				tempVo.setLogId(rs.getString(index++));
				tempVo.setInstCode(rs.getString(index++));
				tempVo.setApplCode(rs.getString(index++));
				tempVo.setKindCode(rs.getString(index++));
				tempVo.setLogPoint(rs.getString(index++));
				tempVo.setTxCode(rs.getString(index++));
				tempVo.setMsgData(rs.getBytes(index++));
				tempVo.setHeaderSize(rs.getString(index++));
				tempVo.setTxUid(rs.getString(index++));
				tempVo.setErrCode(rs.getString(index++));
				tempVo.setTxTime(rs.getString(index++));
				tempVo.setResFlag(rs.getString(index++));
				tempVo.setProcHour(rs.getString(index++));
				tempVo.setXid(rs.getString(index++));
				tempVo.setTxState(rs.getString(index++));
				tempVo.setSessionIndex(rs.getString(index++));
				tempVo.setHeadMappingType(rs.getString(index++));
				tempVo.setBodyMappingType(rs.getString(index++));
				
				return tempVo;
			}
		});
	}
	
	@Override
	public List<FepAlmstLogVo> selectList2(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 로그 조회 데이터 셀렉트 */");
		sql.append("\n\tSELECT PROC_DATE, PROC_MTIME, LOG_ID, INST_CODE, APPL_CODE, KIND_CODE, LOG_POINT, TX_CODE, MSG_DATA, HEADER_SIZE, TX_UID, ERR_CODE, TX_TIME, RES_FLAG, PROC_HOUR, XID, TX_STATE, SESSION_INDEX, HEAD_MAPPING_TYPE, BODY_MAPPING_TYPE, (SELECT NAME FROM ALTX WHERE REP_KIND_CODE=A.KIND_CODE AND TX_CODE=A.TX_CODE AND APPL_CODE=A.APPL_CODE LIMIT 1) AS TX_NAME ");
		sql.append("\n\tFROM almstlog A ");
		sql.append("\n\tWHERE 1=1 "); // '20200612'
		if ( inputVo.getLogId() !=null && inputVo.getLogId().trim().length() > 0 ) {
			if ( inputVo.getLogId().contains(";") ) {
				String logIds = "'" + inputVo.getLogId().replaceAll(";", "','") + "'";
				sql.append("\n\t	AND LOG_ID IN ("+logIds+") ");
			} else {
				sql.append("\n\t	AND LOG_ID='"+inputVo.getLogId()+"' ");
			}
		} else {
			sql.append("\n\t	AND PROC_DATE='"+inputVo.getProcDate()+ "' ");
			sql.append("\n\t	AND PROC_MTIME BETWEEN '"+inputVo.getProcMtime()+"' AND '"+FepStrUtil.nextTimestamp(inputVo.getProcMtime(), rangeMinute)+"' ");  //'140000000' , '140200000'
			if ( inputVo.getInstCode() != null && inputVo.getInstCode().length() > 0 )
				sql.append("\n\t	AND INST_CODE='"+inputVo.getInstCode()+"' "); // '104', '712O'
			if ( inputVo.getApplCode() != null  && inputVo.getApplCode().length() > 0 ) {
				if (inputVo.getApplCode().contains(";") ) {
					String applCodes = "'" + inputVo.getApplCode().replaceAll(";", "','") + "'";
					sql.append("\n\t	AND APPL_CODE IN ("+applCodes+") ");
				} else if ( inputVo.getApplCode().length() == 3 ) { 	// 059
					sql.append("\n\t	AND APPL_CODE IN ('"+inputVo.getApplCode()+"I', '"+inputVo.getApplCode()+"O', '"+inputVo.getApplCode()+"H', '"+inputVo.getApplCode()+"M', '"+inputVo.getApplCode()+"G', '"+inputVo.getApplCode()+"E', '"+inputVo.getApplCode()+"F', '"+inputVo.getApplCode()+"B') ");
				} else {
					sql.append("\n\t	AND APPL_CODE='"+inputVo.getApplCode()+"' ");
				}
			}
			if ( inputVo.getKindCode() !=null && inputVo.getKindCode().length() > 0 ) {
				sql.append("\n\t	AND KIND_CODE='"+inputVo.getKindCode()+"' "); // '104', '712O'
			}
			if ( inputVo.getTxCode() !=null && inputVo.getTxCode().length() > 0 ) {
				sql.append("\n\t	AND TX_CODE='"+inputVo.getTxCode()+"' "); // '104', '712O'
			}
			if ( inputVo.getLogPoint() !=null && inputVo.getLogPoint().length() > 0 ) {
				sql.append("\n\t	AND LOG_POINT='"+inputVo.getLogPoint()+"' "); // '104', '712O'
			}
		}
		if ( isErr )
			sql.append("\n\t	AND (ERR_CODE <> '0' OR TX_TIME > 2000) ");
		sql.append("\n\t	ORDER BY PROC_MTIME DESC, LOG_ID, LOG_POINT DESC ");
		sql.append("\n\t	LIMIT " + inputVo.getStartRowNum() + ", " + inputVo.getSelectCount());
		sql.append("\n\t");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<FepAlmstLogVo>() {
			@Override
			public FepAlmstLogVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAlmstLogVo tempVo = null;
				int index = 1;
				tempVo = new FepAlmstLogVo();
				tempVo.setProcDate(rs.getString(index++));
				tempVo.setProcMtime(rs.getString(index++));
				tempVo.setLogId(rs.getString(index++));
				tempVo.setInstCode(rs.getString(index++));
				tempVo.setApplCode(rs.getString(index++));
				tempVo.setKindCode(rs.getString(index++));
				tempVo.setLogPoint(rs.getString(index++));
				tempVo.setTxCode(rs.getString(index++));
				tempVo.setMsgData(rs.getBytes(index++));
				tempVo.setHeaderSize(rs.getString(index++));
				tempVo.setTxUid(rs.getString(index++));
				tempVo.setErrCode(rs.getString(index++));
				tempVo.setTxTime(rs.getString(index++));
				tempVo.setResFlag(rs.getString(index++));
				tempVo.setProcHour(rs.getString(index++));
				tempVo.setXid(rs.getString(index++));
				tempVo.setTxState(rs.getString(index++));
				tempVo.setSessionIndex(rs.getString(index++));
				tempVo.setHeadMappingType(rs.getString(index++));
				tempVo.setBodyMappingType(rs.getString(index++));
				tempVo.setTxName(rs.getString(index++));
				
				/* 거래고유번호 존재하는 경우 세팅해줍니다. */
				String tempSeqNum = null;
				String tempResCode = null;
				try {
					tempSeqNum = info.getValue("TXINFO.SEQNUM." + tempVo.getInstCode() + "." + tempVo.getApplCode());
					if ( tempSeqNum != null && tempSeqNum.contains(";") ) {
						tempVo.setTrxSeqNum(tempVo.getFlatData().substring(Integer.parseInt(tempSeqNum.split(";")[0]), Integer.parseInt(tempSeqNum.split(";")[1])));
					} else if ( tempVo.getMsgDataStr().length() > 320 ){
						tempVo.setTrxSeqNum(tempVo.getMsgDataStr().substring(300,320));
					}
					
					tempResCode = info.getValue("TXINFO.RESPCD." + tempVo.getInstCode() + "." + tempVo.getApplCode());
					if ( tempResCode != null && tempResCode.contains(";") ) {
						tempVo.setTrxRespCode(tempVo.getFlatData().substring(Integer.parseInt(tempResCode.split(";")[0]), Integer.parseInt(tempResCode.split(";")[1])));
						
						/* 전자금융공동망 타행수취조회거래(취급) 응답에 311이 있는경우 상대은행코드 세팅 */
						if ( "012I".equals(tempVo.getApplCode()) && "311".equals(tempVo.getTrxRespCode()) ) {
							tempVo.setTrxRespCode(tempVo.getTrxRespCode() + "("+tempVo.getFlatData().substring(73, 73+3)+")");
						} 
					}
				} catch (Exception e ) {
					logger.error("--- 거래고유번호/응답코드 파싱하다 오류발생 [tempSeqNum]=["+tempSeqNum+"] : ", e);
					logger.error("--- 그러나 계속 진행합니다.");
				}
				
				
				return tempVo;
			}
		});
	}
	
	@Override
	public int selectListCnt(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 데이터 카운트 셀렉트 */");
		sql.append("\n\tSELECT COUNT(*) AS TOT_CNT  ");
		sql.append("\n\tFROM almstlog  ");
		sql.append("\n\tWHERE 1=1  "); // '20200612'
		if ( inputVo.getLogId() !=null && inputVo.getLogId().trim().length() > 0 ) {
			if ( inputVo.getLogId().contains(";") ) {
				String logIds = "'" + inputVo.getLogId().replaceAll(";", "','") + "'";
				sql.append("\n\t	AND LOG_ID IN ("+logIds+")  ");
			} else {
				sql.append("\n\t	AND LOG_ID='"+inputVo.getLogId()+"'  ");
			}
		} else {
			sql.append("\n\t	AND PROC_DATE='"+inputVo.getProcDate()+ "'  ");
			sql.append("\n\t	AND PROC_MTIME BETWEEN '"+inputVo.getProcMtime()+"' AND '"+FepStrUtil.nextTimestamp(inputVo.getProcMtime(), rangeMinute)+"'  ");  //'140000000' , '140200000'
			if ( inputVo.getInstCode() != null && inputVo.getInstCode().trim().length() > 0 )
				sql.append("\n\t	AND INST_CODE='"+inputVo.getInstCode()+"' "); // '104', '712O'
			if ( inputVo.getApplCode() != null && inputVo.getApplCode().trim().length() > 0 ) {
				if (inputVo.getApplCode().contains(";") ) {
					String applCodes = "'" + inputVo.getApplCode().replaceAll(";", "','") + "'";
					sql.append("\n\t	AND APPL_CODE IN ("+applCodes+") ");
				} else if ( inputVo.getApplCode().length() == 3 ) { 	// 059
					sql.append("\n\t	AND APPL_CODE IN ('"+inputVo.getApplCode()+"I', '"+inputVo.getApplCode()+"O', '"+inputVo.getApplCode()+"H', '"+inputVo.getApplCode()+"M', '"+inputVo.getApplCode()+"G', '"+inputVo.getApplCode()+"E', '"+inputVo.getApplCode()+"F', '"+inputVo.getApplCode()+"B') ");
				} else {
					sql.append("\n\t	AND APPL_CODE='"+inputVo.getApplCode()+"' ");
				}
			}
			if ( inputVo.getKindCode() !=null && inputVo.getKindCode().length() > 0 ) {
				sql.append("\n\t	AND KIND_CODE='"+inputVo.getKindCode()+"' "); // '104', '712O'
			}
			if ( inputVo.getTxCode() !=null && inputVo.getTxCode().length() > 0 ) {
				sql.append("\n\t	AND TX_CODE='"+inputVo.getTxCode()+"' "); // '104', '712O'
			}
			if ( inputVo.getLogPoint() !=null && inputVo.getLogPoint().length() > 0 ) {
				sql.append("\n\t	AND LOG_POINT='"+inputVo.getLogPoint()+"' "); // '104', '712O'
			}
		}
		if ( isErr )
			sql.append("\n\t	AND (ERR_CODE <> '0' OR TX_TIME > 2000) ");
		sql.append("\n\t");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	/**
	 * guid를 통한 거래 로그조회
	 */
	@Override
	public List<FepAlmstLogVo> selectList2ForGuid(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 로그 조회 데이터 셀렉트 For GUID */");
		sql.append("\n\tSELECT PROC_DATE, PROC_MTIME, LOG_ID, INST_CODE, APPL_CODE, KIND_CODE, LOG_POINT, TX_CODE, MSG_DATA, HEADER_SIZE, TX_UID, ERR_CODE, TX_TIME, RES_FLAG, PROC_HOUR, XID, TX_STATE, SESSION_INDEX, HEAD_MAPPING_TYPE, BODY_MAPPING_TYPE, (SELECT NAME FROM ALTX WHERE REP_KIND_CODE=A.KIND_CODE AND TX_CODE=A.TX_CODE AND APPL_CODE=A.APPL_CODE LIMIT 1) AS TX_NAME ");
		sql.append("\n\tFROM almstlog A ");
		sql.append("\n\tWHERE 1=1 "); // '20200612'
		sql.append("\n\t	AND PROC_DATE='"+inputVo.getProcDate()+ "' ");
		String startMtime = inputVo.getGuid().substring(8,8+6) + "000";
		String endMtime = FepStrUtil.nextTimestamp(startMtime, 1);
		sql.append("\n\t	AND PROC_MTIME BETWEEN '"+startMtime+"' AND '"+endMtime+"' ");  //'140000000' , '140200000'
		sql.append("\n\t	AND SUBSTR(MSG_DATA, 13, "+inputVo.getGuid().length()+") ='"+inputVo.getGuid()+"' ");
		sql.append("\n\t	ORDER BY PROC_MTIME DESC, LOG_ID, LOG_POINT DESC ");
		sql.append("\n\t	LIMIT " + inputVo.getStartRowNum() + ", " + inputVo.getSelectCount());
		sql.append("\n\t");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<FepAlmstLogVo>() {
			@Override
			public FepAlmstLogVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAlmstLogVo tempVo = null;
				int index = 1;
				tempVo = new FepAlmstLogVo();
				tempVo.setProcDate(rs.getString(index++));
				tempVo.setProcMtime(rs.getString(index++));
				tempVo.setLogId(rs.getString(index++));
				tempVo.setInstCode(rs.getString(index++));
				tempVo.setApplCode(rs.getString(index++));
				tempVo.setKindCode(rs.getString(index++));
				tempVo.setLogPoint(rs.getString(index++));
				tempVo.setTxCode(rs.getString(index++));
				tempVo.setMsgData(rs.getBytes(index++));
				tempVo.setHeaderSize(rs.getString(index++));
				tempVo.setTxUid(rs.getString(index++));
				tempVo.setErrCode(rs.getString(index++));
				tempVo.setTxTime(rs.getString(index++));
				tempVo.setResFlag(rs.getString(index++));
				tempVo.setProcHour(rs.getString(index++));
				tempVo.setXid(rs.getString(index++));
				tempVo.setTxState(rs.getString(index++));
				tempVo.setSessionIndex(rs.getString(index++));
				tempVo.setHeadMappingType(rs.getString(index++));
				tempVo.setBodyMappingType(rs.getString(index++));
				tempVo.setTxName(rs.getString(index++));
				
				/* 거래고유번호 존재하는 경우 세팅해줍니다. */
				String tempSeqNum = null;
				String tempResCode = null;
				try {
					tempSeqNum = info.getValue("TXINFO.SEQNUM." + tempVo.getInstCode() + "." + tempVo.getApplCode());
					if ( tempSeqNum != null && tempSeqNum.contains(";") ) {
						tempVo.setTrxSeqNum(tempVo.getFlatData().substring(Integer.parseInt(tempSeqNum.split(";")[0]), Integer.parseInt(tempSeqNum.split(";")[1])));
					} else if ( tempVo.getMsgDataStr().length() > 320 ){
						tempVo.setTrxSeqNum(tempVo.getMsgDataStr().substring(300,320));
					}
					
					tempResCode = info.getValue("TXINFO.RESPCD." + tempVo.getInstCode() + "." + tempVo.getApplCode());
					if ( tempResCode != null && tempResCode.contains(";") ) {
						tempVo.setTrxRespCode(tempVo.getFlatData().substring(Integer.parseInt(tempResCode.split(";")[0]), Integer.parseInt(tempResCode.split(";")[1])));
						
						/* 전자금융공동망 타행수취조회거래(취급) 응답에 311이 있는경우 상대은행코드 세팅 */
						if ( "012I".equals(tempVo.getApplCode()) && "311".equals(tempVo.getTrxRespCode()) ) {
							tempVo.setTrxRespCode(tempVo.getTrxRespCode() + "("+tempVo.getFlatData().substring(73, 73+3)+")");
						} 
					}
				} catch (Exception e ) {
					logger.error("--- 거래고유번호/응답코드 파싱하다 오류발생 [tempSeqNum]=["+tempSeqNum+"] : ", e);
					logger.error("--- 그러나 계속 진행합니다.");
				}
				
				return tempVo;
			}
		});
	}
	
	@Override
	public int selectListCntForGuid(FepAlmstLogVo inputVo, int rangeMinute, boolean isErr) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 데이터 카운트 셀렉트 for GUID */");
		sql.append("\n\tSELECT COUNT(*) AS TOT_CNT  ");
		sql.append("\n\tFROM almstlog  ");
		sql.append("\n\tWHERE 1=1  "); // '20200612'
		sql.append("\n\t	AND PROC_DATE='"+inputVo.getProcDate()+ "' ");
		String startMtime = inputVo.getGuid().substring(8,8+6) + "000";
		String endMtime = FepStrUtil.nextTimestamp(startMtime, 1);
		sql.append("\n\t	AND PROC_MTIME BETWEEN '"+startMtime+"' AND '"+endMtime+"' ");  //'140000000' , '140200000'
		sql.append("\n\t	AND SUBSTR(MSG_DATA, 13, "+inputVo.getGuid().length()+") ='"+inputVo.getGuid()+"' ");
		sql.append("\n\t	ORDER BY PROC_MTIME DESC, LOG_ID, LOG_POINT DESC ");
		sql.append("\n\t	LIMIT " + inputVo.getStartRowNum() + ", " + inputVo.getSelectCount());
		sql.append("\n\t");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
}
