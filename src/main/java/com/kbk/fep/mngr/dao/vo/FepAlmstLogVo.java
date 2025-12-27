package com.kbk.fep.mngr.dao.vo;

//import com.cubeone.CubeOneAPI;

public class FepAlmstLogVo {
	
	private String procDate;
	private String procMtime;
	private String logId;
	private String instCode;
	private String applCode;
	private String kindCode;
	private String logPoint;
	private String txCode;
	private byte [] msgData;
	private String msgDataStr;
	private String headerSize;
	private String txUid;
	private String errCode;
	private String txTime;
	private String resFlag;
	private String procHour;
	private String xid;
	private String txState;
	private String sessionIndex;
	private String headMappingType;
	private String bodyMappingType;
	
	/* ����¡ ó��*/
	private String startRowNum;
	private String selectCount;
	
	/* �ŷ��� */
	private String txName;
	
	/* 고유번호 */
	private String flatData;
	private int flatDataLength;
	private String trxSeqNum;
	private String utf8Data;
	
	/* 기관코드 업무코드 한글화 */
	private String instName;
	private String applName;
	private String trxRespCode;
	
	/* hex */
	private String hexaData;
	
	/* GUID */
	private String guid;
	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTxName() {
		return txName;
	}

	public void setTxName(String txName) {
		this.txName = txName;
	}

	public String getStartRowNum() {
		return startRowNum;
	}

	public void setStartRowNum(String startRowNum) {
		this.startRowNum = startRowNum;
	}

	public String getSelectCount() {
		return selectCount;
	}

	public void setSelectCount(String selectCount) {
		this.selectCount = selectCount;
	}

	public FepAlmstLogVo() {
	}

	public String getProcDate() {
		return procDate;
	}

	public void setProcDate(String procDate) {
		this.procDate = procDate;
	}

	public String getProcMtime() {
		return procMtime;
	}

	public void setProcMtime(String procMtime) {
		this.procMtime = procMtime;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getApplCode() {
		return applCode;
	}

	public void setApplCode(String applCode) {
		this.applCode = applCode;
	}

	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	public String getLogPoint() {
		return logPoint;
	}

	public void setLogPoint(String logPoint) {
		this.logPoint = logPoint;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public byte [] getMsgData() {
		return msgData;	
	}

	public void setMsgData(byte [] msgData) {
		this.msgData = msgData;
		this.msgDataStr = new String(msgData);
		StringBuffer hexStr = new StringBuffer();
		try {
			byte [] decbyte = new String(msgData).substring(500).getBytes();
			if( decbyte != null && decbyte.length > 50) {
				this.flatData = new String(decbyte);
				if ( this.flatData.startsWith("{") ) {
					this.flatData = new String(decbyte, "UTF-8");
				}
				this.utf8Data = new String(decbyte, "UTF-8");
			}
			
			/*HexaString 만들기*/
			byte [] tempBytes = new byte[16];
			for (int i=0; i<decbyte.length; i++ ) {
				hexStr.append(String.format("%02x ", decbyte[i]&0xff).toUpperCase());
				if ( i == decbyte.length-1) {
					for ( int j=0; j < (16 - (decbyte.length % 16)); j++) {
						hexStr.append("   ");
					}
					tempBytes = new byte[decbyte.length % 16];
					System.arraycopy(decbyte, decbyte.length/16, tempBytes, 0, tempBytes.length);
					hexStr.append(" |  " + new String(tempBytes) + "\n");
				}
				if ( i % 16 == 15 ) {
					System.arraycopy(decbyte, (i/16)*16, tempBytes, 0, tempBytes.length);
					hexStr.append(" |  " + new String(tempBytes) + "\n");
				}
			}
			/**/
//			for (final byte b : decbyte ) {
//				hexStr.append(String.format("%02x ", b&0xff));
//			}
			this.hexaData = hexStr.toString();
			this.flatDataLength = decbyte.length;
		} catch ( Exception e ) {
			System.err.println(e);
			System.err.println("--- 그러나 계속 진행합니다.");
		}
	}

	public String getHeaderSize() {
		return headerSize;
	}

	public void setHeaderSize(String headerSize) {
		this.headerSize = headerSize;
	}

	public String getTxUid() {
		return txUid;
	}

	public void setTxUid(String txUid) {
		this.txUid = txUid;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getTxTime() {
		return txTime;
	}

	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}

	public String getResFlag() {
		return resFlag;
	}

	public void setResFlag(String resFlag) {
		this.resFlag = resFlag;
	}

	public String getProcHour() {
		return procHour;
	}

	public void setProcHour(String procHour) {
		this.procHour = procHour;
	}

	public String getXid() {
		return xid;
	}

	public void setXid(String xid) {
		this.xid = xid;
	}

	public String getTxState() {
		return txState;
	}

	public void setTxState(String txState) {
		this.txState = txState;
	}

	public String getSessionIndex() {
		return sessionIndex;
	}

	public void setSessionIndex(String sessionIndex) {
		this.sessionIndex = sessionIndex;
	}

	public String getHeadMappingType() {
		return headMappingType;
	}

	public void setHeadMappingType(String headMappingType) {
		this.headMappingType = headMappingType;
	}

	public String getBodyMappingType() {
		return bodyMappingType;
	}

	public void setBodyMappingType(String bodyMappingType) {
		this.bodyMappingType = bodyMappingType;
	}

	public String getTrxSeqNum() {
		return trxSeqNum;
	}
	
	public String getFlatData() {
		return flatData;
	}

	public void setFlatData(String flatData) {
		this.flatData = flatData;
	}

	public void setTrxSeqNum(String trxSeqNum) {
		this.trxSeqNum = trxSeqNum;
	}

	public String getMsgDataStr() {
		return msgDataStr;
	}

	public void setMsgDataStr(String msgDataStr) {
		this.msgDataStr = msgDataStr;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getApplName() {
		return applName;
	}

	public void setApplName(String applName) {
		this.applName = applName;
	}

	public String getHexaData() {
		return hexaData;
	}

	public void setHexaData(String hexaData) {
		this.hexaData = hexaData;
	}

	public String getTrxRespCode() {
		return trxRespCode;
	}

	public void setTrxRespCode(String trxRespCode) {
		this.trxRespCode = trxRespCode;
	}

	public int getFlatDataLength() {
		return flatDataLength;
	}

	public void setFlatDataLength(int flatDataLength) {
		this.flatDataLength = flatDataLength;
	}

	public String getUtf8Data() {
		return utf8Data;
	}

	public void setUtf8Data(String utf8Data) {
		this.utf8Data = utf8Data;
	}

}
