package com.kbk.fep.sim.svc;

import java.net.URLDecoder;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

//import com.cubeone.CubeOneAPI;

@Controller
public class FepSim099012CoreCtl {
	
	@Autowired 
	private RestTemplateBuilder restTemplateBuilder;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/a099012")
	@ResponseBody
	ResponseEntity<String> relay099012(
			@RequestHeader(required=false) HttpHeaders headerMap,
			@RequestBody(required=false) String body) {

		try {
			String h = URLDecoder.decode(body.substring(0, 500), "EUC-KR");
			String b = URLDecoder.decode(body.substring(500), "EUC-KR").replaceAll(" ", "+");
//			byte [] decbyte = CubeOneAPI.codecbyte(b, "AES_PI", 11, null, null, errbyte);
			byte [] decbyte = b.getBytes();
			body = h + new String(decbyte);
		} catch ( Exception e ) {
			logger.error("--- URLDecoder ERROR : " + e, e);
		}
		
		logger.info("---------------------------------------------");
		logger.info("--- REQUEST HEADERS : [" + headerMap + "]");
		logger.info("--- REQUEST BODY : [" + body + "]");
		
		RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofMillis(12000))
				.setReadTimeout(Duration.ofMillis(12000))
				.build();
		ResponseEntity<String> response = null;
		body = body.replaceAll("0200", "0210");
		body = encryptPI(body);
		logger.info("--- RESPONSE BODY : [" + body + "]");
		try {
			HttpEntity<byte []> entity = new HttpEntity<byte []>(body.getBytes(), headerMap);
			response =  restTemplate.postForEntity("http://0.0.0.0/a099012", entity, String.class);
		} catch ( HttpStatusCodeException e ) {
			// 50x, 40x 와 같은 HTTP 상태코드 오류가발생하는경우도 잡아 리턴해라
			response = ResponseEntity.status(e.getRawStatusCode())
					.headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
		} finally {
			if ( response != null ) {
				logger.info("--- RESPONSE STATUS CODE : " + response.getStatusCode());
				logger.info("--- RESPONSE HEADERS : " + response.getHeaders());
				logger.info("--- RESPONSE BODY : [" + response.getBody() + "]");
			} else {
				logger.info("--- RESPONSE IS NULL ");
			}
		}
		logger.info("---------------------------------------------");
		
		return ResponseEntity.status(HttpStatus.OK)
					.headers(response.getHeaders())
					.body(body);
		
	}
	
	private String encryptPI(String plainText) {
		String encryptedText = null;
		byte errbyte[] = new byte[5];
		byte[] inbyte;

		String body = plainText.substring(500);
		inbyte = body.getBytes();
//		encryptedText = CubeOneAPI.coencbytes(inbyte, inbyte.length, "AES_PI", 11, null, null, errbyte);
		encryptedText = new String(inbyte);

		if (errbyte[0] != 48) {
			logger.error("--- encryptPI Error : " + new String(errbyte));
		}

		return plainText.substring(0, 500) + encryptedText;
	}
}
