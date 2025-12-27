package com.kbk.fep.mngr.svc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FepSimulatorThread extends Thread {

	public boolean isContinue = true;
	
	@Autowired
	private FepSimulatorSvc svc;
	
	private Logger logger  = LoggerFactory.getLogger(this.getClass());
	private final String lengthStyle = "0000";
	private String rule;
	private int portNumber = 9999;
	
	public FepSimulatorThread(String rule) {
		this.rule = rule;
		logger.info("--- SIMULATOR THREAD CREATED!");
	}
	
	public FepSimulatorThread(String rule, int portNumber) {
		this.rule = rule;
		this.portNumber = portNumber;
		logger.info("--- SIMULATOR THREAD CREATED! & PORT=["+portNumber+"]");
	}
	
	/**
	 * 스레드 정상종료 프로세스 
	 */
	public void destroy() {
		this.isContinue = false;
		Socket socket = new Socket();
		OutputStream os = null;
		try {
			socket.connect(new InetSocketAddress("0.0.0.0", this.portNumber), 500);
			os = socket.getOutputStream();
			os.write("aaaa".getBytes());
			os.flush();
		} catch ( IOException ioe ) {
			try {
				if ( os != null ) os.close();
				if ( socket != null ) socket.close();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		DecimalFormat df = new DecimalFormat(lengthStyle);
		
		ServerSocket server = null;
		Socket socket = null;
		
		InputStream is = null;
		OutputStream os = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		int readLength =0;
		int bodyLength = 0;
		String tempStr = "";
		
		try {
			server = new ServerSocket(this.portNumber);
			
			while ( true ) {
				
				if ( this.isContinue == false ) {
					logger.info("--- SIMULATOR THREAD 종료 시그널...");
					break;
				}
				
				try {
					socket = server.accept();
					is = socket.getInputStream();
					os = socket.getOutputStream();
					
					dis = new DataInputStream(dis);
					dos = new DataOutputStream(dos);
					
					byte [] lengthBytes = new byte[lengthStyle.length()];
					readLength = dis.read(lengthBytes);
					tempStr = new String(lengthBytes);
					bodyLength = df.parse(tempStr).intValue();
					logger.info("--- READ LENGTH [" + readLength + "]=["+ new String(lengthBytes) + "]");
					
					byte [] bodyBytes = new byte[bodyLength];
					readLength = dis.read(bodyBytes);
					
					byte [] responseBytes = svc.agenyResponse(bodyBytes, this.rule);
					tempStr = df.format(responseBytes.length);
					dos.write(tempStr.getBytes());
					dos.write(responseBytes);
					dos.flush();
				} catch ( IOException ioe ) {
					logger.error("--- ERROR : " + ioe, ioe);
				} finally {
					try {
						if ( dos != null ) dos.close();
						if ( dis != null ) dis.close();
						if ( os != null ) os.close();
						if ( is != null ) is.close();
						if ( socket != null ) socket.close();
						Thread.sleep(20*1000);
					} catch ( Exception e ) {
						e.printStackTrace();
					}
				}
			}
		} catch ( Exception e ) {
			logger.error("--- ERROR (process down) : " + e, e);
		} finally {
			try {
				if ( socket != null ) socket.close();
				if ( server != null ) server.close();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
}
