package com.kbk.fep.sim.svc;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FepSim099012RecverThread extends Thread {
	
	private int port = 57504;
	private String logPrifix = "--- [RECVER] : ";
	private DecimalFormat df = new DecimalFormat("0000");
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run() {
		
		ServerSocket server =null;
		Socket socket = null;
		DataInputStream dis = null;
		InputStream is = null;
		
		while ( true ) {
			
			try {
				server = new ServerSocket(this.port);
				logger.info(logPrifix + "LISTENING PORT ["+this.port+"]...");
				socket = server.accept();
				
				is = socket.getInputStream();
				dis = new DataInputStream(is);
				
				while ( true ) {
					
					byte [] length = new byte[4];
					dis.read(length);
					
					String lengthStr = new String(length);
					int lengthInt = df.parse(lengthStr).intValue();
					byte [] msg = new byte[lengthInt];
					int readBytes = dis.read(msg);
					
					logger.info(logPrifix + "["+lengthStr+"]["+readBytes+"]["+new String(msg)+"]");
					
				}
			} catch (Exception e) {
				logger.error(logPrifix + e.toString(), e);
			} finally {
				try {
					logger.info(logPrifix + "종료절차");
					if ( is != null ) is.close();
					if ( dis != null ) dis.close();
					if ( socket != null ) socket.close();
					if ( server != null ) server.close();
				} catch (Exception e) {
					logger.error(logPrifix + e.toString(), e);
				}
			}
		}
	}
}
