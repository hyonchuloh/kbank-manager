package com.kbk.fep.mngr.svc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//import com.cubeone.CubeOneAPI;
import com.kbk.fep.mngr.dao.FepManagerDao;
import com.kbk.fep.mngr.dao.vo.FepAllineVo;
import com.kbk.fep.mngr.dao.vo.FepSmsInfoVo;
import com.kbk.fep.util.PropUtil;

@Service
public class FepManagerSvcImpl implements FepManagerSvc {
	
	@Autowired
	private FepManagerDao dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private RestTemplateBuilder builder;
	
	@Override
	public List<List<String>> selectObject(String sql) throws Exception {
		return dao.selectObject(sql);
	}
	
	@Override
	public int updateObject(String sql) throws Exception {
		return dao.updateObject(sql);
	}
	
	@Override
	public void selectObject_excel(HttpServletResponse response, String sql) throws Exception {
		List<List<String>> list = dao.selectObject(sql);
		logger.info("--- loadItem 건수 : " + list.size()); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		try {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("수행결과");
			
			Row row = null;
			Cell cell = null;
			int rowNo = 0;
			
			Font fontHeader = workbook.createFont();
			fontHeader.setFontName("Malgun Gothic");
			fontHeader.setBold(true);
			
			CellStyle headStyle = workbook.createCellStyle();
			headStyle.setFont(fontHeader);
			headStyle.setBorderTop(BorderStyle.THIN);
			headStyle.setBorderBottom(BorderStyle.THIN);
			headStyle.setBorderLeft(BorderStyle.THIN);
			headStyle.setBorderRight(BorderStyle.THIN);
			headStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headStyle.setAlignment(HorizontalAlignment.CENTER);
			headStyle.setWrapText(true);
			headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			
			Font fontBody = workbook.createFont();
			fontBody.setFontName("Malgun Gothic");
			fontBody.setBold(false);
			
			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setFont(fontBody);
			bodyStyle.setWrapText(true);
			bodyStyle.setBorderTop(BorderStyle.THIN);
			bodyStyle.setBorderBottom(BorderStyle.THIN);
			bodyStyle.setBorderLeft(BorderStyle.THIN);
			bodyStyle.setBorderRight(BorderStyle.THIN);
			bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			bodyStyle.setAlignment(HorizontalAlignment.CENTER);
			
			CellStyle bodyStyle2 = workbook.createCellStyle();
			bodyStyle.setFont(fontBody);
			bodyStyle.setWrapText(false);
			bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			
			CellStyle cellStyle = headStyle;
			
			for ( int i=0; i < list.size(); i++ ) {
				
				List<String> listRow = list.get(i);
				
				row = sheet.createRow(rowNo++);
				
				if ( i > 0 ) cellStyle = bodyStyle;
				
				for ( int j=0; j < listRow.size(); j++ ) {
					cell = row.createCell(j);	cell.setCellStyle(cellStyle); 	cell.setCellValue(listRow.get(j));
				}
				
			}
			
			Sheet sheet2 = workbook.createSheet("SQL");
			String [] sqllines = sql.split("\n");
			for ( int k=0; k < sqllines.length; k++) {
				row = sheet2.createRow(k);
				cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue(sqllines[k]);
			}
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("SQL수행결과_", "utf-8")+sdf.format(new Date())+".xls");
			
			workbook.write(response.getOutputStream());
			workbook.close();
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
	}
	
	@Override
	public String testOut(String url, String data) {
		
		String retValue = "";
		ResponseEntity<byte[]> response = null;
		
		try {
			
			RestTemplate rest = new RestTemplate();
			String sendData = data;
			if ( data.charAt(11) == '1' ) {
				logger.info("--- 암호화 후 데이터 : [" + sendData + "]");
				sendData = encryptPI(data);
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			HttpEntity<byte[]> request = new HttpEntity<byte[]>(sendData.getBytes(), headers);
			response = rest.postForEntity(url, request, byte[].class);
			if (response != null) {
				logger.info("--- 응답 데이터 : [" + new String(response.getBody()) + "]");
				retValue = response.getStatusCode().toString();
			} else
				logger.info("--- 응답 데이터 : [NULL]");
			
		} catch (Exception e) {
			logger.error("--- 에러발생 : ", e);
			retValue = e.toString();
		}
		
		return retValue;
	}
	
	@Override
	public ResponseEntity<String> testOutHttp(String url, String header, String body, String method, String param) {
		
		/* 기본 REQUEST 타임아웃을 지정합니다. */
		int connectionTimeout = 12000;
		int readTimeout = 12000;
		
		RestTemplate restTemplate = builder.setConnectTimeout(Duration.ofMillis(connectionTimeout))
				.setReadTimeout(Duration.ofMillis(readTimeout))
				.build();
		ResponseEntity<String> response = null;
		
		/* HEADER는 GET이든 POST는 무조건 세팅해야함 */
		HttpHeaders mapData = new HttpHeaders();
		if ( header !=null && header.trim().length() > 0 ) {
			String [] headers = header.split("\\|");
			if ( headers.length > 0 ) {
				for ( String data : headers ) {
					mapData.add(data.substring(0, data.indexOf(":")), data.substring(data.indexOf(":")+1));
				}
			}
		}
		logger.info("--- REQUEST HEADERS : " + mapData);
		
		try {
			if ( method != null && "get".equals(method) ) {
				
				if ( param != null && param.trim().length() > 0 ) {
					logger.info("--- REQUEST PARAM GET 방식으로 호출합니다.");
					String [] params = param.split("\\|");
					MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
					for ( String data : params ) {
						paramMap.add(data.substring(0, data.indexOf(":")), data.substring(data.indexOf(":")+1));
					}
					UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
							.queryParams(paramMap);
					
					logger.info("--- REQUEST URL : " + uriBuilder.toUriString());
					HttpEntity<String> reuqest = new HttpEntity<String>(mapData);
					response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, reuqest, String.class);
					
				} else {
					logger.info("--- REQUEST NO PARAM GET 방식으로 호출합니다.");
					HttpEntity<String> reuqest = new HttpEntity<String>(mapData);
					response = restTemplate.exchange(url, HttpMethod.GET, reuqest, String.class);
				}
				
			} else {
				logger.info("--- REQUEST BODY POST 방식으로 호출합니다");
				HttpEntity<String> entity = new HttpEntity<>(body, mapData);
				response = restTemplate.postForEntity(url, entity, String.class);
			}
		} catch ( HttpStatusCodeException e ) {
			response = ResponseEntity.status(e.getRawStatusCode())
					.headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
		}
		
		return response;
	}
	
	private String encryptPI(String plainText) throws Exception {
		String encryptedText = null;
		byte errbyte[] = new byte[5];
		byte[] inbyte;

		String body = plainText.substring(500);
		inbyte = body.getBytes();
//		encryptedText = CubeOneAPI.coencbytes(inbyte, inbyte.length, "AES_PI", 11, null, null, errbyte);
		encryptedText = body;

		if (errbyte[0] != 48) {
			throw new Exception("--- encryptPI Error : " + new String(errbyte));
		}

		return plainText.substring(0, 500) + encryptedText;
	}
	
	@Override
	public String testIn(String Ip, String port, String lengthStyle, String inputData) {
		
		DecimalFormat df = new DecimalFormat(lengthStyle);
		String outputData = null;
		
		Socket client = null;
		OutputStream os = null;
		InputStream is = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		try {
			
			/* 연결 */
			client = new Socket(Ip, Integer.parseInt(port));
			client.setSoTimeout(5*1000);
			os = client.getOutputStream();
			dos = new DataOutputStream(os);
			is = client.getInputStream();
			dis = new DataInputStream(is);

			/* 요청 Write */
			String lengthStr = df.format(inputData.getBytes().length);
			logger.info("--- outputData : ["+lengthStr + "][" + inputData+"]");
			dos.write((lengthStr + inputData).getBytes("MS949"));
			dos.flush();

			/* 응답 Read */
			byte [] lengthBytes = new byte[lengthStyle.getBytes().length];
			dis.read(lengthBytes);
			int readLengthInt = df.parse(new String(lengthBytes)).intValue();
			byte [] data = new byte[readLengthInt];
			int readBodyBytes = dis.read(data);
			logger.info("--- readBodyBytes : ["+readBodyBytes+"]");
			outputData = new String(data);
			logger.info("--- outputData : ["+outputData+"]");
			
		} catch ( Exception e ) {
			outputData = e.getMessage();
		} finally {
			try {
				if ( dos !=null ) dos.close();
				if ( dis !=null ) dis.close();
				if ( os !=null ) os.close();
				if ( is !=null ) is.close();
				if ( client !=null ) client.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return outputData;
	}
	
	@Override
	public String testInForUtf8(String Ip, String port, String lengthStyle, String inputData) {
		
		DecimalFormat df = new DecimalFormat(lengthStyle);
		String outputData = null;
		
		Socket client = null;
		OutputStream os = null;
		InputStream is = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		try {
			
			/* 연결 */
			client = new Socket(Ip, Integer.parseInt(port));
			client.setSoTimeout(5*1000);
			os = client.getOutputStream();
			dos = new DataOutputStream(os);
			is = client.getInputStream();
			dis = new DataInputStream(is);

			/* 요청 Write */
			String lengthStr = df.format(inputData.getBytes("utf-8").length);
			logger.info("--- outputData : ["+lengthStr + "][UTF-8][" + inputData+"]");
			dos.write((lengthStr + inputData).getBytes("utf-8"));
			dos.flush();

			/* 응답 Read */
			byte [] lengthBytes = new byte[lengthStyle.getBytes().length];
			dis.read(lengthBytes);
			int readLengthInt = df.parse(new String(lengthBytes)).intValue();
			byte [] data = new byte[readLengthInt];
			int readBodyBytes = dis.read(data);
			logger.info("--- readBodyBytes : ["+readBodyBytes+"]");
			outputData = new String(data);
			logger.info("--- outputData : ["+outputData+"]");
			
		} catch ( Exception e ) {
			outputData = e.getMessage();
		} finally {
			try {
				if ( dos !=null ) dos.close();
				if ( dis !=null ) dis.close();
				if ( os !=null ) os.close();
				if ( is !=null ) is.close();
				if ( client !=null ) client.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return outputData;
	}
	
	@Override
	public String testInAsync(String Ip, String port, String lengthStyle, String inputData) {
		
		DecimalFormat df = new DecimalFormat(lengthStyle);
		String outputData = null;
		
		Socket client = null;
		OutputStream os = null;
		DataOutputStream dos = null;
		
		try {
			
			/* 연결 */
			client = new Socket(Ip, Integer.parseInt(port));
			client.setSoTimeout(10*1000);
			os = client.getOutputStream();
			dos = new DataOutputStream(os);

			/* 요청 Write */
			String lengthStr = df.format(inputData.getBytes().length);
			if ("NULL".equals(lengthStyle))
				lengthStr = "";
			logger.info("--- outputData : ["+lengthStr + "][" + inputData+"]");
			dos.write((lengthStr + inputData).getBytes());
			dos.flush();

		} catch ( Exception e ) {
			outputData = e.getMessage();
		} finally {
			try {
				if ( dos !=null ) dos.close();
				if ( os !=null ) os.close();
				if ( client !=null ) client.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return outputData;
	}
	
	
	@Override
	public List<FepSmsInfoVo> getSmsList(String filePath) {
		List<FepSmsInfoVo> list = dao.getList(filePath);
		for ( FepSmsInfoVo vo : list ) {
			String target = vo.getTarget();
			if ( target != null ) {
				String [] phones = target.split(",");
				for ( String phone : phones ) {
					String name = PropUtil.getPropertiesValue("phone", phone);
					if ( name != null ) {
						vo.setTarget(target.replaceAll(phone, phone + "("+name+")"));
					}
				}
			}
		}
		return list;
	}
	
	
	
	
	@Override
	public int [][] getCalendarTable(Calendar calendar, int year, int month) {
		calendar.set(year, month-1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
		int retValue[][] = new int[7][7];
		int daycount = 1;
		for ( int i=0; i<7; i++) {
			for ( int j=0; j<7; j++) {
				if ( firstDay -1 > 0 ) {
					retValue[i][j] = 0;
					firstDay--;
					continue;
				} else if ( daycount > lastDay ) {
					retValue[i][j] = -1;
				} else {
					retValue[i][j] = daycount;
				}
				daycount++;
			}
		}
		return retValue;
	}
	
	@Override
	public Map<String, String> loadMap(String filePath) {
		Map<String, String> map = new HashMap<String, String>();
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(filePath));
			map = (Map<String, String>) ois.readObject();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( ois != null ) ois.close();
			} catch ( Exception e ) {
				//
			}
		}
		return map;
	}
	
	/**
	 * key = CAL.${yearInt}.${monthInt}.${dayInt}
	 * value = td.innerHTML
	 */
	@Override
	public int saveMap(String filePath, String key, String value) {
		Map<String, String> map = loadMap(filePath);
		logger.info("--- CURRENT MAP ["+key+"]=["+map.get(key)+"]");
		if ( value != null && value.trim().length() > 0 ) {
			map.put(key, value.trim());
			logger.info("--- SAVE MAP ["+key+"]=["+value.trim()+"]");
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filePath));
			oos.writeObject(map);
			logger.info("--- SAVE SUCCESS!");
		} catch ( Exception e ) {
			logger.error("--- SAVE FAILURE!", e);
			return 0;
		} finally {
			try {
				if ( oos !=null ) oos.close();
			} catch ( Exception e ) {
				logger.error("--- SAVE FAILURE!", e);
				return 0;
			}
		}
		return 1;
	}

	
	@Override
	public String ftpView(String pathView) throws Exception {
		
		if ( pathView.endsWith("enc") ) {
			String outfile =  "/tmp/temp_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			decryptPI(pathView, outfile);
			pathView = outfile;
		}
		StringBuffer retValue = new StringBuffer();
		if ( pathView.length() > 0 ) {
			FileReader fr = null;
			BufferedReader br = null;
			try {
				fr = new FileReader(pathView);
				br = new BufferedReader(fr);
				
				String temp = "";
				while ( ( temp = br.readLine() ) != null ) {
					retValue.append(temp + "\n");
				}
			} catch ( IOException ioe ) {
				logger.error("--- ERROR : " + ioe.toString(), ioe);
				throw ioe;
			} finally {
				try {
					if ( br != null ) br.close();
					if ( fr != null ) fr.close();
				} catch ( Exception e ) {}
			}
		}
		return retValue.toString();
	}
	
	/* 파일 복호화 */
	public void decryptPI(String infile, String outfile) throws Exception {
//		CubeOneAPI.codecfile(infile,outfile,"KBANK","AES_PI");
	}
	
	
	@Override
	public String selectLineIpFor089CRD() {
		return dao.selectLineIpFor089CRD();
	}
	
	public List<FepAllineVo> selectLineListForCrdU2l() {
		return dao.selectLineListForCrdU2l();
	}
	
	@Override
	public List<FepAllineVo> selectLineListForCrdU2lHistory() {
		return dao.selectLineListHistory();
	}
	
	@Override
	public String selectUrlForCrdU2l() {
		return dao.selectUrlForCrdU2l();
	}
	
	public int updateLineUrlForCrdU2l(String gwname, String symbname, String linename, String luname, String requestIp) {
		FepAllineVo vo = new FepAllineVo();
		vo.setGwname(gwname);
		vo.setSymbname(symbname);
		vo.setLinename(linename);
		vo.setLuname(luname);
		vo.setIp(requestIp);
		return dao.insertCrdU2lLog(vo);
	}
	
	@Override
	public int updateLineIpForCrdU2l(String gwname, String symbname, String linename, String luname, String requestIp) {
		FepAllineVo vo = new FepAllineVo();
		vo.setGwname(gwname);
		vo.setSymbname(symbname);
		vo.setLinename(linename);
		vo.setLuname(luname);
		vo.setIp(requestIp);
		
		int retValue = dao.updateLineIpForCrdU2l(vo);
		retValue += dao.insertCrdU2lLog(vo);
		dao.tmdownGw(vo.getGwname());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dao.tmbootGw(vo.getGwname());
		return retValue;
	}
}
