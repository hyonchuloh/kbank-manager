package com.kbk.fep.mngr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kbk.fep.mngr.dao.vo.FepAluserVo;

@Repository
public class FepAluserDaoImpl implements FepAluserDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public FepAluserVo selectUser(String userId) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("\n\n\t/* 단건 데이터 셀렉트 */");
		sql.append("\n\tSELECT USER_ID, STA_TYPE, USER_ROLE, USER_NAME, EMAIL ");
		sql.append("\n\tFROM ALUSER");
		sql.append("\n\tWHERE 1=1 "); 
		sql.append("\n\t	AND USER_ID='"+userId+"' ");
		sql.append("\n\tLIMIT 1");
		sql.append("\n");
		
		logger.info("--- SQL : " + sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<FepAluserVo>() {
			@Override
			public FepAluserVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				FepAluserVo tempVo = null;
				int index = 1;
				tempVo = new FepAluserVo();
				tempVo.setUser_id(rs.getString(index++));
				tempVo.setSta_type(rs.getInt(index++));
				tempVo.setUser_role(rs.getInt(index++));
				tempVo.setUser_name(rs.getString(index++));
				tempVo.setEmail(rs.getString(index++));
				
				return tempVo;
			}
		});
	}

}
