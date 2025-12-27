package com.kbk.fep.mngr.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kbk.fep.mngr.dao.vo.FepAllineVo;
import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.dao.vo.FepSmsInfoVo;

@Repository
public class FepManagerDaoImpl implements FepManagerDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FepCommPropVo prop;
	private List<String> headerRecord;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<List<String>> selectObject(String sql) throws Exception {
		logger.info("--- INPUT SQL : \n\n" + sql);
		headerRecord = null;
		
		List<List<String>> retValue = jdbcTemplate.query(sql, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<String> datas = new LinkedList<String>();
				StringBuffer rowLog = new StringBuffer("--- ["+rowNum+"]");
				/* 헤더 메타정보가 없는 경우 세팅 한다. */
				if ( headerRecord == null ) {
					headerRecord = new ArrayList<String>();
					for ( int i=1; i<=rs.getMetaData().getColumnCount(); i++) {
						headerRecord.add(rs.getMetaData().getColumnName(i));
					}
				}
				for ( int i=1; i<=rs.getMetaData().getColumnCount(); i++) {
					if ( rs.getMetaData().getColumnType(i) == Types.INTEGER) {
						datas.add(new Integer(rs.getInt(i)).toString());
						rowLog.append(new Integer(rs.getInt(i)).toString());
					} else {
						datas.add(rs.getString(i));
						rowLog.append(rs.getString(i));
					}
				}
				rowLog.append("\n");
				return datas;
			}
		});
		retValue.add(0, headerRecord);
		
		return retValue;
	}
	
	@Override
	public int updateObject(String sql) throws Exception {
		return jdbcTemplate.update(sql);
	}
	
	@Override
	public List<FepSmsInfoVo> getList(String filePath) {
		
		FileReader fr = null;
		BufferedReader br = null;
		List<FepSmsInfoVo> retValue = null;
		
		try {
			
			fr = new FileReader (filePath);
			br = new BufferedReader(fr);
			
			List<String> smsList = new ArrayList<String>();
			List<String> codeList = new ArrayList<String>();
			
			String tempLine = "";
			while ( ( tempLine = br.readLine() ) != null ) {
				if ( tempLine.startsWith("SMS") ) {
					smsList.add(tempLine);
				} else if ( tempLine.startsWith("[") ) {
					continue;
				} else {
					codeList.add(tempLine);
				}
			}
			
			retValue = new ArrayList<FepSmsInfoVo>();
			FepSmsInfoVo vo = null;
			String [] items = null;
			for ( String code : codeList ) {
				vo = new FepSmsInfoVo();
				items = code.split("=");
				if ( items.length < 5 ) {
					logger.error("--- CODE리스트에 =개수 부족 ["+code+"]");
					continue;
				}
				vo.setId(items[0]);
				vo.setInstName(items[1]);
				vo.setApplName(items[2]);
				vo.setStartTime(items[3]);
				vo.setEndTime(items[4]);
				if ( items.length == 6 ) {
					for ( String sms: smsList ) {
						if ( sms.startsWith("SMS=" + items[5]) ) {
							vo.setTarget(sms.substring(sms.lastIndexOf("=")+1));
							break;
						}
					}
				} else {
					vo.setHolydayYn(items[5]);
					for ( String sms: smsList ) {
						if ( sms.startsWith("SMS=" + items[6]) ) {
							vo.setTarget(sms.substring(sms.lastIndexOf("=")+1));
							break;
						}
					}
				}
				retValue.add(vo);
			}
			
		} catch  ( Exception e ) {
			logger.error("--- 에러발생 : " + e.toString());
			e.printStackTrace();
		} finally {
			try {
				if ( br != null ) br.close();
				if ( fr != null ) fr.close();
			} catch ( Exception e ) {}
		}
		return retValue;
	}
	
	/**
	 * olt_089CRD_i_01 의 IP 정보를 가져오는 쿼리
	 */
	@Override
	public String selectLineIpFor089CRD() {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 현재 olt_089CRD_i_01 의 IP 정보를 가져오는 쿼리 */ ");
		sql.append("\n\tSELECT LINE_NAME FROM ALLINE ");
		sql.append("\n\tWHERE GWNAME IN ('olt_089CRD_i_01') AND STA_TYPE='1' " );
		sql.append("\n\tLIMIT 1");
		logger.info("--- SQL : " + sql.toString());
		return jdbcTemplate.queryForObject(sql.toString(), String.class);
	}
	
	@Override
	public List<FepAllineVo> selectLineListForCrdU2l() {
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 카드계 U2L 관련 대외기관 통신정보 현황  */");
		sql.append("\n\tSELECT GWNAME, SYMBNAME, LINE_NAME, LU_NAME, STA_TYPE ");
		sql.append("\n\tFROM ALLINE");
		sql.append("\n\tWHERE (GWNAME LIKE 'olt_1047%' OR GWNAME LIKE 'olt_104109%' OR GWNAME='olt_089CRD_i_01') AND LINE_NAME <> '' ");
		sql.append("\n\tAND STA_TYPE <> '9'");
		sql.append("\n\tORDER BY LINE_NAME, LU_NAME");
		sql.append("\n");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<FepAllineVo>() {
			@Override
			public FepAllineVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAllineVo tempVo = null;
				int index = 1;
				tempVo = new FepAllineVo();
				tempVo.setGwname(rs.getString(index++));
				tempVo.setSymbname(rs.getString(index++));
				tempVo.setLinename(rs.getString(index++));
				tempVo.setLuname(rs.getString(index++));
				tempVo.setStatype(rs.getString(index++));
				return tempVo;
			}
		});
	}
	
	@Override
	public int updateLineIpForCrdU2l(FepAllineVo vo) {
		StringBuffer query1 = new StringBuffer("\n\n\t /* updateLineIpForCrdU2l 의 IP 정보를 변경합니다. */");
		query1.append("\n\tUPDATE ALLINE SET LINE_NAME='"+vo.getLinename()+"' WHERE GWNAME='"+vo.getGwname()+"' AND SYMBNAME='"+vo.getSymbname()+"' AND LU_NAME='"+vo.getLuname()+"' ");
		logger.info("--- SQL : " + query1.toString());
		return jdbcTemplate.update(query1.toString());
	}
	
	@Override
	public List<FepAllineVo> selectLineListHistory() {
		List<FepAllineVo> retValue = new ArrayList<FepAllineVo>();
		Connection conn = null;
		Statement create_stat = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer query1 = new StringBuffer("\n\n\t/* 전환 히스토리 정보 SELECT */");
		query1.append("\n\tSELECT * FROM CRDU2L_HISTORY WHERE 1=1 ");
		query1.append("\n\tORDER BY UPDATE_TIME desc");
		
		try {
			conn = getConnection();
			create_stat = conn.createStatement();
			create_stat.executeUpdate("CREATE TABLE IF NOT EXISTS CRDU2L_HISTORY "
					+ "(GWNAME, SYMBNAME, LINE_NAME, LU_NAME, REQ_IP, UPDATE_TIME); ");
			
			stat = conn.createStatement();
			logger.info("--- SQL : " + query1.toString());
			rs = stat.executeQuery(query1.toString());
			
			FepAllineVo tempVo = null;
			while ( rs.next() ) {
				tempVo = new FepAllineVo();
				tempVo.setGwname(rs.getString("GWNAME"));
				tempVo.setSymbname(rs.getString("SYMBNAME"));
				tempVo.setLinename(rs.getString("LINE_NAME"));
				tempVo.setLuname(rs.getString("LU_NAME"));
				tempVo.setIp(rs.getString("REQ_IP"));
				tempVo.setUpdateTime(rs.getString("UPDATE_TIME"));
				retValue.add(tempVo);
			}
			
		} catch ( Exception e ) {
			logger.error("--- ERROR : ", e);
		} finally {
			try {
				if ( rs != null ) rs.close();
				if ( stat !=null ) stat.close();
				if ( conn !=null ) conn.close();
			} catch ( Exception ie ) {
				logger.error("--- ERROR : ", ie);
			}
		}
		return retValue;
	}
	
	@Override
	public int insertCrdU2lLog(FepAllineVo vo) {
		int retValue = 0;
		Connection conn = null;
		PreparedStatement prep = null;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO CRDU2L_HISTORY (GWNAME, SYMBNAME, LINE_NAME, LU_NAME, REQ_IP, UPDATE_TIME) VALUES (?,?,?,?,?,?) ");
		try {
			conn = getConnection();
			prep = conn.prepareStatement(sql.toString());
			prep.setString(1, vo.getGwname());
			prep.setString(2, vo.getSymbname());
			prep.setString(3, vo.getLinename());
			prep.setString(4, vo.getLuname());
			prep.setString(5, vo.getIp());
			prep.setString(6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			logger.info("--- SQL : " + sql.toString());
			retValue = prep.executeUpdate();
		} catch ( Exception e ) {
			logger.error("--- ERROR : ", e);
		} finally {
			try {
				if ( prep !=null ) prep.close();
				if ( conn !=null ) conn.close();
			} catch ( Exception ie ) {
				logger.error("--- ERROR : ", ie);
			}
		}
		return retValue;
	}
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:/kbksw/swdpt/anylink/fep-manager/config/crdu2l_history.db";
		if ( "dev".equals(prop.getProfiles())) {
			url = "jdbc:sqlite:/kbksw/swdpt/anylinkb/fep-manager/config/crdu2l_history.db";
		}
		return DriverManager.getConnection(url);
	}
	
	@Override
	public String tmbootGw(String gwname) {
		logger.info("--- tmbootGw GWNAME : " + gwname);
		String [] cmd = {"/bin/sh",  "-c", "tmboot -S " + gwname + " -n dfepap01"};
		Process process = null;
		BufferedReader reader = null;
		StringBuilder output = new StringBuilder();
		try {
			process = Runtime.getRuntime().exec(cmd);
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			while ( ( line = reader.readLine() ) != null ) {
				output.append(line + "\n");
			}
			logger.info("--- tmbootGw RESULT : " + output.toString());
		} catch (IOException e) {
			logger.error("--- tmbootGw ERROR : " + e.toString(), e);
		} finally {
			try {
				if ( reader != null ) reader.close();
			} catch ( Exception e ) {}
		}
		return output.toString();
	}
	
	@Override
	public String tmdownGw(String gwname) {
		logger.info("--- tmdownGw GWNAME : " + gwname);
		String [] cmd = {"/bin/sh",  "-c", "tmdown -S " + gwname + " -n dfepap01"};
		Process process = null;
		BufferedReader reader = null;
		StringBuilder output = new StringBuilder();
		try {
			process = Runtime.getRuntime().exec(cmd);
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			while ( ( line = reader.readLine() ) != null ) {
				output.append(line + "\n");
			}
			logger.info("--- tmdownGw RESULT : " + output.toString());
		} catch (IOException e) {
			logger.error("--- tmbootGw ERROR : " + e.toString(), e);
		} finally {
			try {
				if ( reader != null ) reader.close();
			} catch ( Exception e ) {}
		}
		return output.toString();
	}
	
	@Override
	public String selectUrlForCrdU2l() {
		String retValue = "";
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer query1 = new StringBuffer("\n\n\t/* 최종URL HISTORY SELECT */");
		query1.append("\n\tSELECT LINE_NAME FROM CRDU2L_HISTORY WHERE GWNAME='Servlet' ");
		query1.append("\n\tORDER BY UPDATE_TIME desc LIMIT 1 ");
		
		try {
			conn = getConnection();
			stat = conn.createStatement();
			logger.info("--- SQL : " + query1.toString());
			rs = stat.executeQuery(query1.toString());
			retValue = rs.getString(1);
			
		} catch ( Exception e ) {
			logger.error("--- ERROR : ", e);
		} finally {
			try {
				if ( rs != null ) rs.close();
				if ( stat !=null ) stat.close();
				if ( conn !=null ) conn.close();
			} catch ( Exception ie ) {
				logger.error("--- ERROR : ", ie);
			}
		}
		return retValue;
	}
}
