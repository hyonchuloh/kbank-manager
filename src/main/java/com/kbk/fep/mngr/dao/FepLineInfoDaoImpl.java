package com.kbk.fep.mngr.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.dao.vo.FepLineInfoVo;
import com.kbk.fep.util.FepStrUtil;

@Repository
public class FepLineInfoDaoImpl implements FepLineInfoDao {
	
	@Autowired
	private FepCommPropVo prop;
	private String dbPath = "line.dat";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<Integer, FepLineInfoVo> resultMap;
	
	/* sqlite 를 이용한 회선대장 신규*/
	
	@Override
	public int deleteItem2(int seqNo) {
		int retValue = 0;
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = getConnection();
			prep = conn.prepareStatement("DELETE FROM LINE WHERE SEQ_NO=?");
			prep.setInt(1, seqNo);
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
	
	@Override
	public int insertItem2(FepLineInfoVo inputVo) {
		int retValue = 0;
		Connection conn = null;
		PreparedStatement prep = null;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO LINE (EXT_CD, EXT_NM, BIZ_CD, BIZ_NM, BIZ_TYPE, BIZ_CLCD, NW_LINE, NW_ROUTER, FW_VPN, DEV_CLCD, KBK_IP, KBK_NAT_IP, KBK_PORT, EXT_IP, EXT_PORT, SR_TYPE, EXT_USER, HISTORY) VALUES (?,?, ?,?,?,?, ?,?,?, ?,?,?,?,?,?,?, ?,?) ");
		try {
			conn = getConnection();
			prep = conn.prepareStatement(sql.toString());
			prep.setString(1, inputVo.getExtCd());
			prep.setString(2, inputVo.getExtNm());
			prep.setString(3, inputVo.getBizCd());
			prep.setString(4, inputVo.getBizNm());
			prep.setString(5, inputVo.getBizType());
			prep.setString(6, inputVo.getBizClcd());
			prep.setString(7, inputVo.getNwLine());
			prep.setString(8, inputVo.getNwRouter());
			prep.setString(9, inputVo.getFwVpn());
			prep.setString(10, inputVo.getDevClcd());
			prep.setString(11, inputVo.getKbkIp());
			prep.setString(12, inputVo.getKbkNatIp());
			prep.setString(13, inputVo.getKbkPort());
			prep.setString(14, inputVo.getExtIp());
			prep.setString(15, inputVo.getExtPort());
			prep.setString(16, inputVo.getSrType());
			prep.setString(17, inputVo.getExtUser());
			prep.setString(18, inputVo.getHistory());
			logger.error("--- SQL : " + sql.toString());
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
	
	@Override
	public int updateItem2(FepLineInfoVo inputVo) {
		int retValue = 0;
		Connection conn = null;
		Statement stat = null;
		StringBuffer query1 = new StringBuffer("\n\tUPDATE LINE SET");
		
		boolean isFirst = true;
		
		if ( FepStrUtil.isNotNull(inputVo.getExtCd()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tEXT_CD='"+inputVo.getExtCd()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getExtNm()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tEXT_NM='"+inputVo.getExtNm()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getBizCd()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tBIZ_CD='"+inputVo.getBizCd()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getBizNm()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tBIZ_NM='"+inputVo.getBizNm()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getBizType()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tBIZ_TYPE='"+inputVo.getBizType()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getBizClcd()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tBIZ_CLCD='"+inputVo.getBizClcd()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getNwLine()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tNW_LINE='"+inputVo.getNwLine()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getNwRouter()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tNW_ROUTER='"+inputVo.getNwRouter()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getFwVpn()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tFW_VPN='"+inputVo.getFwVpn()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getDevClcd()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tDEV_CLCD='"+inputVo.getDevClcd()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getKbkIp()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tKBK_IP='"+inputVo.getKbkIp()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getKbkNatIp()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tKBK_NAT_IP='"+inputVo.getKbkNatIp()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getKbkPort()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tKBK_PORT='"+inputVo.getKbkPort()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getExtIp()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tEXT_IP='"+inputVo.getExtIp()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getExtPort()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tEXT_PORT='"+inputVo.getExtPort()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getSrType()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tSR_TYPE='"+inputVo.getSrType()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getExtUser()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tEXT_USER='"+inputVo.getExtUser()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getHistory()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tHISTORY='"+inputVo.getHistory()+"' ");
			isFirst = false;
		}
		
		query1.append("\n\tWHERE SEQ_NO=" + inputVo.getSeqNo());
		
		try {
			conn = getConnection();
			stat = conn.createStatement();
			logger.error("--- SQL : " + query1.toString());
			retValue = stat.executeUpdate(query1.toString());
		} catch ( Exception e ) {
			logger.error("--- ERROR : ", e);
		} finally {
			try {
				if ( stat !=null ) stat.close();
				if ( conn !=null ) conn.close();
			} catch ( Exception ie ) {
				logger.error("--- ERROR : ", ie);
			}
		}
		return retValue;
	}
	
	@Override
	public List<FepLineInfoVo> selectItem2(FepLineInfoVo inputVo, String searchKey) {
		List<FepLineInfoVo> retValue = new ArrayList<FepLineInfoVo>();
		Connection conn = null;
		Statement create_stat = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer query1 = new StringBuffer("\n\tSELECT * FROM LINE WHERE 1=1 ");
		if ( FepStrUtil.isNotNull(inputVo.getExtCd()) ) 
			query1.append("\n\t\t AND EXT_CD='"+inputVo.getExtCd()+"' ");
		if ( FepStrUtil.isNotNull(inputVo.getBizCd()) ) 
			query1.append("\n\t\t AND BIZ_CD='"+inputVo.getBizCd()+"' ");
		if ( FepStrUtil.isNotNull(inputVo.getBizType()) ) 
			query1.append("\n\t\t AND BIZ_TYPE='"+inputVo.getBizType()+"' ");
		if ( FepStrUtil.isNotNull(inputVo.getBizClcd()) ) 
			query1.append("\n\t\t AND BIZ_CLCD='"+inputVo.getBizClcd()+"' ");
		if ( FepStrUtil.isNotNull(inputVo.getDevClcd()) ) 
			query1.append("\n\t\t AND DEV_CLCD='"+inputVo.getDevClcd()+"' ");
		if ( FepStrUtil.isNotNull(searchKey) ) {
			query1.append("\n\t\t AND (EXT_NM LIKE '%"+searchKey+"%'");
			query1.append("\n\t\t OR BIZ_NM LIKE '%"+searchKey+"%'");
			query1.append("\n\t\t OR NW_LINE LIKE '%"+searchKey+"%'");
			query1.append("\n\t\t OR KBK_IP LIKE '%"+searchKey+"%'");
			query1.append("\n\t\t OR KBK_NAT_IP LIKE '%"+searchKey+"%'");
			query1.append("\n\t\t OR KBK_PORT LIKE '%"+searchKey+"%'");
			query1.append("\n\t\t OR EXT_IP LIKE '%"+searchKey+"%'");
			query1.append("\n\t\t OR EXT_PORT LIKE '%"+searchKey+"%'");
			query1.append("\n\t\t OR EXT_USER LIKE '%"+searchKey+"%'");
			query1.append("\n\t\t OR HISTORY LIKE '%"+searchKey+"%' )");
		}
		
		query1.append("\n\t\t ORDER BY EXT_CD, BIZ_CD, BIZ_TYPE DESC, BIZ_CLCD, DEV_CLCD ");
		
		try {
			conn = getConnection();
			create_stat = conn.createStatement();
			create_stat.executeUpdate("CREATE TABLE IF NOT EXISTS LINE "
					+ "(SEQ_NO INTEGER PRIMARY KEY AUTOINCREMENT, EXT_CD, EXT_NM, BIZ_CD, BIZ_NM, BIZ_TYPE, BIZ_CLCD, NW_LINE, NW_ROUTER, FW_VPN, "
					+ " DEV_CLCD, KBK_IP, KBK_NAT_IP, KBK_PORT, EXT_IP, EXT_PORT, SR_TYPE, EXT_USER, HISTORY ); ");
			
			stat = conn.createStatement();
			logger.error("--- SQL : " + query1.toString());
			rs = stat.executeQuery(query1.toString());
			
			FepLineInfoVo tempVo = null;
			while ( rs.next() ) {
				tempVo = new FepLineInfoVo();
				tempVo.setSeqNo(rs.getInt("SEQ_NO"));
				tempVo.setExtCd(rs.getString("EXT_CD").trim());
				tempVo.setExtNm(rs.getString("EXT_NM").trim());
				tempVo.setBizCd(rs.getString("BIZ_CD").trim());
				tempVo.setBizNm(rs.getString("BIZ_NM").trim());
				tempVo.setBizType(rs.getString("BIZ_TYPE").trim());
				tempVo.setBizClcd(rs.getString("BIZ_CLCD").trim());
				tempVo.setNwLine(rs.getString("NW_LINE").trim());
				tempVo.setNwRouter(rs.getString("NW_ROUTER").trim());
				tempVo.setFwVpn(rs.getString("FW_VPN").trim());
				tempVo.setDevClcd(rs.getString("DEV_CLCD").trim());
				tempVo.setKbkIp(rs.getString("KBK_IP").trim());
				tempVo.setKbkNatIp(rs.getString("KBK_NAT_IP").trim());
				tempVo.setKbkPort(rs.getString("KBK_PORT").trim());
				tempVo.setExtIp(rs.getString("EXT_IP").trim());
				tempVo.setExtPort(rs.getString("EXT_PORT").trim());
				tempVo.setSrType(rs.getString("SR_TYPE").trim());
				tempVo.setExtUser(rs.getString("EXT_USER").trim());
				tempVo.setHistory(rs.getString("HISTORY").trim());
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
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection("jdbc:sqlite:/kbksw/swdpt/anylink/fep-manager/config/line.db");
	}
	
	/* sqlite 를 이용한 회선대장 신규*/
	
	public void deleteItem(int seqNo) {
		
		if ( this.resultMap == null ) {
			this.dbPath = prop.getFepLineDb();
			resultMap = loadItem();
		}
		
		Map<Integer, FepLineInfoVo> resultMap = loadItem();
		resultMap.remove(seqNo);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(dbPath));
			oos.writeObject(resultMap);
		} catch ( Exception e ) {
			logger.error("--- 에러발생 : ", e);
		} finally {
			try {
				if ( oos != null ) oos.close();
			} catch ( Exception e2) {
				logger.error("--- 에러발생 : ", e2);
			}
		}
	}
	
	public void saveItem(int seqNo, String extCd, String extNm, String bizCd, String bizNm, 
			String bizType, String bizClcd, String nwLine, String nwRouter, String fwVpn, 
			String devClcd, String kbkIp, String kbkNatIp, String kbkPort, String extIp, 
			String extPort, String srType, String extUser, String history) {
		
		if ( this.resultMap == null ) {
			this.dbPath = prop.getFepLineDb();
			resultMap = loadItem();
		}
		
		FepLineInfoVo vo = new FepLineInfoVo();
		vo.setSeqNo(seqNo);
		vo.setExtCd(extCd);
		vo.setExtNm((extNm));
		vo.setBizCd((bizCd));
		vo.setBizNm((bizNm));
		vo.setBizType((bizType));
		vo.setBizClcd((bizClcd));
		vo.setNwLine((nwLine));
		vo.setNwRouter((nwRouter));
		vo.setFwVpn((fwVpn));
		vo.setDevClcd((devClcd));
		vo.setKbkIp((kbkIp));
		vo.setKbkNatIp((kbkNatIp));
		vo.setKbkPort((kbkPort));
		vo.setExtIp((extIp));
		vo.setExtPort((extPort));
		vo.setSrType((srType));
		vo.setExtUser((extUser));
		vo.setHistory((history));
		saveItem(vo);
	}
	
	public void saveItem(FepLineInfoVo inputVo) {
		
		if ( this.resultMap == null ) {
			this.dbPath = prop.getFepLineDb();
			resultMap = loadItem();
		}
		
		if ( inputVo.getSeqNo() == -1 ) {
			int newNum = ((TreeMap<Integer, FepLineInfoVo>) resultMap ).lastKey()+1;
			inputVo.setSeqNo(newNum);
			resultMap.put(new Integer(newNum), inputVo);
		} else {
			resultMap.put(inputVo.getSeqNo(), inputVo);
		}
		
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(dbPath));
			oos.writeObject(resultMap);
			
		} catch ( Exception e ) {
			logger.error("--- 에러발생 : ", e);
		} finally {
			try {
				if ( oos != null ) oos.close();
			} catch ( Exception e2) {
				logger.error("--- 에러발생 : ", e2);
			}
		}
	}
	
	public Map<Integer, FepLineInfoVo> loadItem() {
		
		if ( this.resultMap == null ) {
			this.dbPath = prop.getFepLineDb();
		}
		
		logger.info("--- DB PATH : " + this.dbPath);
		
		Map<Integer, FepLineInfoVo> retValue = null;
		ObjectInputStream ois = null;
		
		try {
			if ( new File(dbPath).exists() ) {
				ois = new ObjectInputStream(new FileInputStream(dbPath));
				retValue = (Map<Integer, FepLineInfoVo>) ois.readObject();
			} else {
				retValue = new TreeMap<Integer, FepLineInfoVo>();
			}
			
		} catch ( Exception e ) {
			logger.error("--- 에러발생 : ", e);
		} finally {
			try {
				if ( ois != null ) ois.close();
			} catch ( Exception e2) {
				logger.error("--- 에러발생 : ", e2);
			}
		}
		return retValue;
		
	}

}
