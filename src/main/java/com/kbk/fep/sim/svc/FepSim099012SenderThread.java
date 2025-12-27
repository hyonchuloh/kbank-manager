package com.kbk.fep.sim.svc;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FepSim099012SenderThread extends Thread {
	
	private String ip = "0.0.0.0";
	private int port = 59002;
	private String logPrifix = "--- [SENDER] : ";
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run() {
		
		Socket socket = null;
		DataOutputStream dos = null;
		OutputStream os = null;
		
		StringBuffer msg = new StringBuffer();
		
		while (true ) {
			try {
				logger.info(logPrifix + "연결 CONNECT TO ["+ip+"]["+port+"]...");
				socket = new Socket(ip, port);
				os = socket.getOutputStream();
				dos = new DataOutputStream(os);
				
				while ( true ) {
					msg = new StringBuffer();
					msg.append("0403HDR"); // length
					msg.append("ELB");
					msg.append("0200");
					msg.append("300000");
					msg.append("2"); // 송수신FLAG
					msg.append("000"); // STATUS
					msg.append("   "); // 응답코드
					msg.append(dateFormat.format(new Date())); // YYYYMMDD
					msg.append(timeFormat.format(new Date())); // 시간
					String num = new DecimalFormat("00000000").format(Math.random()*9999999);
					msg.append(num); // 전문추적번호8
					msg.append(dateFormat.format(new Date())); // 거래발생일
					
					msg.append("01100" + num); // 거래고유번호
					msg.append("011"); // 취급기관 대표코드
					msg.append("0110013"); // 취급기관 점별코드
					msg.append("089"); // 개설기관 대표코드
					msg.append("0000000"); // 개설기관 점별코드
					
					msg.append("100194097782    "); // 수취계좌번호
					msg.append("00000000000001"); // 거래금액
					msg.append("00"); // 수취지역코드
					msg.append("수취인조회　　　　　"); // 출금인
					msg.append("　　　　　　　　　　"); // 수취인
					msg.append("06"); // 매체구분코드
					msg.append("00"); // 자금성격
					msg.append("1                               "); // 의뢰인정보
					msg.append("99999999999     "); // 출금계좌번호
					msg.append("85747     "); // 예비정보1
					msg.append("1                           "); // 예비정보2
					msg.append("　　　　　　　　　　"); // 송금인 실명
					msg.append("00"); // 금융사기 의심정보
					msg.append("                                                                                                                                     "); // FILLER
					
					logger.info(logPrifix + "[" + msg.toString() + "]");
					
					dos.write(msg.toString().getBytes());
					dos.flush();
					Thread.sleep(5000);
				}
			} catch (Exception e) {
				logger.error(logPrifix + e.toString(), e);
			} finally {
				try {
					logger.info(logPrifix + "종료절차");
					if ( os != null ) os.close();
					if ( dos != null ) dos.close();
					if ( socket != null ) socket.close();
				} catch (Exception e) {
					logger.error(logPrifix + e.toString(), e);
				}
			}
		}
	}

}
