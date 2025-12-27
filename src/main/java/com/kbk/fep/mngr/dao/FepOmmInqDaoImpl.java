package com.kbk.fep.mngr.dao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.kbk.fep.mngr.dao.vo.FepOmmInqVo;

@Repository
public class FepOmmInqDaoImpl implements FepOmmInqDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private RestTemplateBuilder builder;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("mmss");
	private DecimalFormat rand = new DecimalFormat("0000");

	@Override
	public FepOmmInqVo inquery(String intfId) {
		
		/* 기본 REQUEST 타임아웃을 지정합니다. */
		int connectionTimeout = 12000;
		int readTimeout = 12000;
		
		RestTemplate restTemplate = builder.setConnectTimeout(Duration.ofMillis(connectionTimeout))
				.setReadTimeout(Duration.ofMillis(readTimeout))
				.build();
		
		FepOmmInqVo retValue = null;
		
		/* 헤더 세팅 */
		HttpHeaders mapData = new HttpHeaders();
		mapData.add(HttpHeaders.ACCEPT_CHARSET, "utf-8");
		mapData.add(HttpHeaders.CONTENT_TYPE, "application/json; charset-utf-8");
		logger.info("--- REQUEST HEADERS : {}", mapData);
		
		/* 글로벌ID 셋업 */
		String guid1 = sdf.format(new Date()); 			// 전문 작성일시
		String guid3 = sdf2.format(new Date())+rand.format(Math.random()*999);			// 전문일련번호 (8) = 분(2)초(2) + 랜덤(4)
		
		/* 바디 세팅 */
		String body = "000005231040"+guid1+"FEPA01A1"+guid3+"01                                0         0              ko                   "
				+ "SKBXP01001038                                TFEP                                                                              "
				+ "089000  0200      000000    "
				+ intfId + "00096659            3                                                                                                                                                                                   "
				+ intfId;
		
		try {
			restTemplate = new RestTemplate();
			HttpEntity<String> entity = new HttpEntity<>(body, mapData);
			ResponseEntity<byte[]> response = restTemplate.postForEntity("http://0.0.0.0/corbiz/channel", entity, byte[].class);
			byte [] resBody = response.getBody();
			logger.info("--- RESPONSE CODE : {}", response.getStatusCodeValue());
			logger.info("--- RESPONSE HEADERS : {}", response.getHeaders());
			logger.info("--- RESPONSE BODY : {}", new String(resBody));
			
			logger.info("--- RESPONSE BODY DUMP : {}", getDumpHexaString(resBody));
			
			retValue = new FepOmmInqVo(resBody);
			
		} catch ( HttpStatusCodeException e ) {
			e.printStackTrace();
		}
		
		return retValue;
	}
	
	/*HexaString 만들기*/
	private String getDumpHexaString(byte [] input) {
		StringBuffer hexStr = new StringBuffer();
		
		byte [] tempBytes = new byte[16];
		for (int i=0; i<input.length; i++ ) {
			hexStr.append(String.format("%02x ", input[i]&0xff).toUpperCase());
			if ( i == input.length-1) {
				for ( int j=0; j < (16 - (input.length % 16)); j++) {
					hexStr.append("   ");
				}
				tempBytes = new byte[input.length % 16];
				System.arraycopy(input, input.length/16, tempBytes, 0, tempBytes.length);
				hexStr.append(" |  " + new String(tempBytes) + "\n");
			}
			if ( i % 16 == 15 ) {
				System.arraycopy(input, (i/16)*16, tempBytes, 0, tempBytes.length);
				hexStr.append(" |  " + new String(tempBytes) + "\n");
			}
		}
		return hexStr.toString();
	}
}
