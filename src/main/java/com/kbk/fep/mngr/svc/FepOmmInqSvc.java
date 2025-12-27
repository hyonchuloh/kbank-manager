package com.kbk.fep.mngr.svc;

import java.util.Map;

import com.kbk.fep.mngr.dao.vo.FepOmmInqRowVo;
import com.kbk.fep.mngr.dao.vo.FepOmmInqVo;

public interface FepOmmInqSvc {
	
	public FepOmmInqVo inquery(String intfId);
	
	public Map<FepOmmInqRowVo, String> getInputParsing(FepOmmInqVo layoutVo, byte [] input) throws CloneNotSupportedException ;
	
	public Map<FepOmmInqRowVo, String> getOutputParsing(FepOmmInqVo layoutVo, byte [] input) throws CloneNotSupportedException ;

}
