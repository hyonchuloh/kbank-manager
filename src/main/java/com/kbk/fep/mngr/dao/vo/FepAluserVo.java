package com.kbk.fep.mngr.dao.vo;

public class FepAluserVo {
	
	/* SELECT USER_ID, STA_TYPE, USER_ROLE, USER_NAME, EMAIL FROM ALUSER */ 
	private String user_id;
	private int sta_type;
	private int user_role;
	private String user_name;
	private String email;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getSta_type() {
		return sta_type;
	}
	public void setSta_type(int sta_type) {
		this.sta_type = sta_type;
	}
	public int getUser_role() {
		return user_role;
	}
	public void setUser_role(int user_role) {
		this.user_role = user_role;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
