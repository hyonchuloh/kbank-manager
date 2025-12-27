package com.kbk.fep.mngr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.dao.vo.FepGanttVo;
import com.kbk.fep.util.FepStrUtil;
@Repository
public class FepGanttDaoImpl implements FepGanttDao {
	
	@Autowired
	private FepCommPropVo prop;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 조회
	 */
	@Override
	public List<FepGanttVo> selectList(String startDay, String endDay) {
		List<FepGanttVo> retValue = new ArrayList<FepGanttVo>();
		Connection conn = null;
		Statement create_stat = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer query1 = new StringBuffer("\n\n\t/* 간트차트 리스트 조회 */");
		query1.append("\n\tSELECT * FROM GANTT WHERE 1=1 ");
		query1.append("\n\tORDER BY END_DATE, START_DATE " );
		try {
			conn = getConnection();
			create_stat = conn.createStatement();
			create_stat.executeUpdate("CREATE TABLE IF NOT EXISTS GANTT "
					+ "(SEQ_NO INTEGER PRIMARY KEY AUTOINCREMENT, PRJ_NAME, START_DATE, END_DATE, DEVELOPER, PRJ_MEMO, "
					+ "CHECK1, CHECK2, CHECK3, CHECK4, CHECK5, CHECK6, CHECK7, CHECK8, CHECK9, CHECK10, "
					+ "LASTUPDATE); ");
			
			stat = conn.createStatement();
			logger.error("--- SQL : " + query1.toString());
			rs = stat.executeQuery(query1.toString());
			
			FepGanttVo tempVo = null;
			while ( rs.next() ) {
				tempVo = new FepGanttVo();
				tempVo.setSeqNo(rs.getInt("SEQ_NO"));
				tempVo.setPrjName(rs.getString("PRJ_NAME"));
				tempVo.setStartDate(rs.getString("START_DATE"));
				tempVo.setEndDate(rs.getString("END_DATE"));
				tempVo.setDeveloper(rs.getString("DEVELOPER"));
				tempVo.setPrjMemo(rs.getString("PRJ_MEMO"));
				tempVo.setCheck1(rs.getString("CHECK1"));
				tempVo.setCheck2(rs.getString("CHECK2"));
				tempVo.setCheck3(rs.getString("CHECK3"));
				tempVo.setCheck4(rs.getString("CHECK4"));
				tempVo.setCheck5(rs.getString("CHECK5"));
				tempVo.setCheck6(rs.getString("CHECK6"));
				tempVo.setCheck7(rs.getString("CHECK7"));
				tempVo.setCheck8(rs.getString("CHECK8"));
				tempVo.setCheck9(rs.getString("CHECK9"));
				tempVo.setCheck10(rs.getString("CHECK10"));
				tempVo.setLastupdate(rs.getString("LASTUPDATE"));
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
	public FepGanttVo selectItem(int seqNo) {
		FepGanttVo retValue = new FepGanttVo();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer query1 = new StringBuffer("\n\n\t/* */");
		query1.append("\n\tSELECT * FROM GANTT WHERE 1=1");
		query1.append("\n\t\tAND SEQ_NO=" + seqNo);
		try {
			conn = getConnection();
			stat = conn.createStatement();
			logger.error("--- SQL : " + query1.toString());
			rs = stat.executeQuery(query1.toString());
			
			if ( rs.next() ) {
				retValue.setSeqNo(rs.getInt("SEQ_NO"));
				retValue.setPrjName(rs.getString("PRJ_NAME"));
				retValue.setStartDate(rs.getString("START_DATE"));
				retValue.setEndDate(rs.getString("END_DATE"));
				retValue.setDeveloper(rs.getString("DEVELOPER"));
				retValue.setPrjMemo(rs.getString("PRJ_MEMO"));
				retValue.setCheck1(rs.getString("CHECK1"));
				retValue.setCheck2(rs.getString("CHECK2"));
				retValue.setCheck3(rs.getString("CHECK3"));
				retValue.setCheck4(rs.getString("CHECK4"));
				retValue.setCheck5(rs.getString("CHECK5"));
				retValue.setCheck6(rs.getString("CHECK6"));
				retValue.setCheck7(rs.getString("CHECK7"));
				retValue.setCheck8(rs.getString("CHECK8"));
				retValue.setCheck9(rs.getString("CHECK9"));
				retValue.setCheck10(rs.getString("CHECK10"));
				retValue.setLastupdate(rs.getString("LASTUPDATE"));
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

	/* insert */
	@Override
	public int insertItem(FepGanttVo inputVo) {
		int retValue = 0;
		Connection conn = null;
		PreparedStatement prep = null;
		StringBuffer sql = new StringBuffer("\n\n\t\t/* 신규 프로젝트 INSERT */");
		sql.append("\n\t\tINSERT INTO GANTT ");
		sql.append("\n\t\t(PRJ_NAME, START_DATE, END_DATE, DEVELOPER, PRJ_MEMO, ");
		sql.append("\n\t\tCHECK1, CHECK2, CHECK3, CHECK4, CHECK5, CHECK6, CHECK7, CHECK8, CHECK9, CHECK10, ");
		sql.append("\n\t\tLASTUPDATE) ");
		sql.append("\n\t\tVALUES (?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?) ");
		
		try {
			conn = getConnection();
			int index = 1;
			prep = conn.prepareStatement(sql.toString());
			prep.setString(index++, inputVo.getPrjName());
			prep.setString(index++, inputVo.getStartDate());
			prep.setString(index++, inputVo.getEndDate());
			prep.setString(index++, inputVo.getDeveloper());
			prep.setString(index++, inputVo.getPrjMemo().replaceAll("\n","<br/>"));
			prep.setString(index++, inputVo.getCheck1());
			prep.setString(index++, inputVo.getCheck2());
			prep.setString(index++, inputVo.getCheck3());
			prep.setString(index++, inputVo.getCheck4());
			prep.setString(index++, inputVo.getCheck5());
			prep.setString(index++, inputVo.getCheck6());
			prep.setString(index++, inputVo.getCheck7());
			prep.setString(index++, inputVo.getCheck8());
			prep.setString(index++, inputVo.getCheck9());
			prep.setString(index++, inputVo.getCheck10());
			prep.setString(index++, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
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
	public int editItem(FepGanttVo inputVo) {
		int retValue = 0;
		Connection conn = null;
		Statement stat = null;
		StringBuffer query1 = new StringBuffer("\n\n\t\tUPDATE GANTT SET");
		
		boolean isFirst = true;
		
		if ( FepStrUtil.isNotNull(inputVo.getPrjName()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tPRJ_NAME='"+inputVo.getPrjName()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getStartDate()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tSTART_DATE='"+inputVo.getStartDate()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getEndDate()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tEND_DATE='"+inputVo.getEndDate()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getDeveloper()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tDEVELOPER='"+inputVo.getDeveloper()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getPrjMemo()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tPRJ_MEMO='"+inputVo.getPrjMemo().replaceAll("\n","<br/>")+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck1()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK1='"+inputVo.getCheck1()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck2()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK2='"+inputVo.getCheck2()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck3()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK3='"+inputVo.getCheck3()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck4()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK4='"+inputVo.getCheck4()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck5()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK5='"+inputVo.getCheck5()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck6()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK6='"+inputVo.getCheck6()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck7()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK7='"+inputVo.getCheck7()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck8()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK8='"+inputVo.getCheck8()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck9()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK9='"+inputVo.getCheck9()+"' ");
			isFirst = false;
		}
		if ( FepStrUtil.isNotNull(inputVo.getCheck10()) ) {
			if ( isFirst == false ) query1.append(",");
			query1.append("\n\t\tCHECK10='"+inputVo.getCheck10()+"' ");
			isFirst = false;
		}
		if ( isFirst == false ) query1.append(",");
		query1.append("\n\t\tLASTUPDATE='"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"' ");
		isFirst = false;
		
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
	public int deleteItem(int seqNo) {
		int retValue = 0;
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = getConnection();
			prep = conn.prepareStatement("DELETE FROM GANTT WHERE SEQ_NO=?");
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
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection("jdbc:sqlite:/kbksw/swdpt/anylink/fep-manager/config/gantt.db");
	}

}
