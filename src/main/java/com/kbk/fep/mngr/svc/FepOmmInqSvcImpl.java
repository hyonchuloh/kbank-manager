package com.kbk.fep.mngr.svc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbk.fep.mngr.dao.FepOmmInqDao;
import com.kbk.fep.mngr.dao.vo.FepOmmInqRowVo;
import com.kbk.fep.mngr.dao.vo.FepOmmInqVo;

@Service
public class FepOmmInqSvcImpl implements FepOmmInqSvc {
	
	@Autowired
	private FepOmmInqDao dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public FepOmmInqVo inquery(String intfId) {
		logger.info("--- intfId : {}", intfId);
		return dao.inquery(intfId);
	}
	
	@Override
	public Map<FepOmmInqRowVo, String> getInputParsing(FepOmmInqVo layoutVo, byte [] input) throws CloneNotSupportedException {
		
		int arraySize = 0;
		int arraySequence = 0;
		int arrayIndex = 1;
		
		Map<FepOmmInqRowVo, String> retValue = new LinkedHashMap<FepOmmInqRowVo, String>();

		FepOmmInqRowVo tempVo= null;
		int pointer = 0;
		byte[] tempColumn = null;

		for (int i = 0; i< layoutVo.getInputList().size()+1; i++) {
			
			// 배열종료
			if (arraySize > 0 && (i==layoutVo.getInputList().size() || layoutVo.getInputList().get(i).getDepth() == 1 )) {
				arraySize--;
				if ( arraySize > 0 ) {
					i = i - arraySequence;
					arrayIndex++;
				} else {
					arrayIndex = 1;
				}
				arraySequence = 0;
			}
			if ( i==layoutVo.getInputList().size() ) break;
			// 배열시작
			if (arraySize == 0 && layoutVo.getInputList().get(i).getDepth() > 1) {
				for ( FepOmmInqRowVo tempRow : retValue.keySet() ) {
					if ( tempRow.getAttribute().equals(layoutVo.getInputList().get(i).getSubstd())) {
						arraySize = Integer.parseInt(retValue.get(tempRow));
					}
				}
			}
			// 배열진행중
			if (arraySize > 0 && layoutVo.getInputList().get(i).getDepth() > 1) {
				arraySequence++;
			}

			tempColumn = new byte[layoutVo.getInputList().get(i).getLength()];
			System.arraycopy(input, pointer, tempColumn, 0, tempColumn.length);
			tempVo = (FepOmmInqRowVo) layoutVo.getInputList().get(i).clone();
			if ( arraySequence > 0 ) {
				tempVo.setAttribute(tempVo.getSubstd() + "/" + tempVo.getAttribute() + " ["+arrayIndex+"]");
				retValue.put(tempVo, new String(tempColumn));
			} else {
				retValue.put(tempVo, new String(tempColumn));
			}
			
			pointer += tempColumn.length;

		}

		return retValue;
	}
	
	@Override
	public Map<FepOmmInqRowVo, String> getOutputParsing(FepOmmInqVo layoutVo, byte[] input) throws CloneNotSupportedException {
		int arraySize = 0;
		int arraySequence = 0;
		int arrayIndex = 1;
		
		Map<FepOmmInqRowVo, String> retValue = new LinkedHashMap<FepOmmInqRowVo, String>();

		FepOmmInqRowVo tempVo= null;
		int pointer = 0;
		byte[] tempColumn = null;

		for (int i = 0; i< layoutVo.getOutputList().size()+1; i++) {
			
			// 배열종료
			if (arraySize > 0 && (i==layoutVo.getOutputList().size() || layoutVo.getOutputList().get(i).getDepth() == 1 )) {
				arraySize--;
				if ( arraySize > 0 ) {
					i = i - arraySequence;
					arrayIndex++;
				} else {
					arrayIndex = 1;
				}
				arraySequence = 0;
			}
			if ( i==layoutVo.getOutputList().size() ) break;
			// 배열시작
			if (arraySize == 0 && layoutVo.getOutputList().get(i).getDepth() > 1) {
				for ( FepOmmInqRowVo tempRow : retValue.keySet() ) {
					if ( tempRow.getAttribute().equals(layoutVo.getOutputList().get(i).getSubstd())) {
						arraySize = Integer.parseInt(retValue.get(tempRow));
					}
				}
			}
			// 배열진행중
			if (arraySize > 0 && layoutVo.getOutputList().get(i).getDepth() > 1) {
				arraySequence++;
			}

			tempColumn = new byte[layoutVo.getOutputList().get(i).getLength()];
			System.arraycopy(input, pointer, tempColumn, 0, tempColumn.length);
			tempVo = (FepOmmInqRowVo) layoutVo.getOutputList().get(i).clone();
			if ( arraySequence > 0 ) {
				tempVo.setAttribute(tempVo.getSubstd() + "/" + tempVo.getAttribute() + " ["+arrayIndex+"]");
				retValue.put(tempVo, new String(tempColumn));
			} else {
				retValue.put(tempVo, new String(tempColumn));
			}
			
			pointer += tempColumn.length;

		}

		return retValue;
	}


}
