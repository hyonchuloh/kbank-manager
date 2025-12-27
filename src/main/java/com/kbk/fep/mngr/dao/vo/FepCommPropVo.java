package com.kbk.fep.mngr.dao.vo;

import org.springframework.stereotype.Component;

@Component
public class FepCommPropVo {
	
	private String fepLineDb;
	
	private String fepSimDb;
	
	private String profiles = "dev";
	
	private String fep_sms1;
	
	private String fep_sms2;
	
	private String fep_access_ips;
	
	private String fep_admin_user;
	
	public String getFepSimDb() {
		return fepSimDb;
	}

	public void setFepSimDb(String fepSimDb) {
		this.fepSimDb = fepSimDb;
	}

	public String getFepLineDb() {
		return fepLineDb;
	}

	public void setFepLineDb(String fepLineDb) {
		this.fepLineDb = fepLineDb;
	}

	public String getProfiles() {
		return profiles;
	}

	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

	public String getFep_sms1() {
		return fep_sms1;
	}

	public void setFep_sms1(String fep_sms1) {
		this.fep_sms1 = fep_sms1;
	}

	public String getFep_sms2() {
		return fep_sms2;
	}

	public void setFep_sms2(String fep_sms2) {
		this.fep_sms2 = fep_sms2;
	}

	public String getFep_access_ips() {
		return fep_access_ips;
	}

	public void setFep_access_ips(String fep_access_ips) {
		this.fep_access_ips = fep_access_ips;
	}

	public String getFep_admin_user() {
		return fep_admin_user;
	}

	public void setFep_admin_user(String fep_admin_user) {
		this.fep_admin_user = fep_admin_user;
	}
	
	
}
