package com.kbk.fep.mngr.dao.vo;

public class FepAlsticsVo {
	
	private String proc_date;
	private String proc_hour;
	private String inst_code;
	private String appl_code;
	private String rep_kind_code;
	private String tx_code;
	private int log_count;
	private int err_code;
	private int tx_time;
	
	public FepAlsticsVo() {
	}

	public String getProc_date() {
		return proc_date;
	}

	public void setProc_date(String proc_date) {
		this.proc_date = proc_date;
	}

	public String getProc_hour() {
		return proc_hour;
	}

	public void setProc_hour(String proc_hour) {
		this.proc_hour = proc_hour;
	}

	public String getInst_code() {
		return inst_code;
	}

	public void setInst_code(String inst_code) {
		this.inst_code = inst_code;
	}

	public String getAppl_code() {
		return appl_code;
	}

	public void setAppl_code(String appl_code) {
		this.appl_code = appl_code;
	}

	public String getRep_kind_code() {
		return rep_kind_code;
	}
	
	public String getXTime() {
		return this.rep_kind_code.substring(0,2) + ":" + this.rep_kind_code.substring(2);
	}

	public void setRep_kind_code(String rep_kind_code) {
		this.rep_kind_code = rep_kind_code;
	}

	public String getTx_code() {
		return tx_code;
	}

	public void setTx_code(String tx_code) {
		this.tx_code = tx_code;
	}

	public int getLog_count() {
		return log_count;
	}

	public void setLog_count(int log_count) {
		this.log_count = log_count;
	}

	public int getErr_code() {
		return err_code;
	}

	public void setErr_code(int err_code) {
		this.err_code = err_code;
	}

	public int getTx_time() {
		return tx_time;
	}

	public void setTx_time(int tx_time) {
		this.tx_time = tx_time;
	}
	
	

}
