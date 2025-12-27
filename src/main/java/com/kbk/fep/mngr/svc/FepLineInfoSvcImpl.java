package com.kbk.fep.mngr.svc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbk.fep.mngr.dao.FepLineInfoDao;
import com.kbk.fep.mngr.dao.vo.FepLineInfoVo;

@Service
public class FepLineInfoSvcImpl implements FepLineInfoSvc {
	
	@Autowired
	private FepLineInfoDao dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 2022.01. 10  통합테스트 증적 양식 다운로드 
	 */
	@Override
	public void getExcelTest(HttpServletResponse response, FepLineInfoVo inputVo, String searchKey, String title) {
		List<FepLineInfoVo> list = dao.selectItem2(inputVo, searchKey);
		logger.info("--- loadItem 건수 : " + list.size() + " 이중에 첫번째꺼만 적용"); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			
			if ( list.size() == 0 ) throw new IOException("조회된 데이터가 없습니다.");
			FepLineInfoVo vo = list.get(0);
			
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet(title + " 결과서");
			
			Row row = null;
			Cell cell = null;
			int rowNo = 0;

			Font fontHeader = workbook.createFont();
				fontHeader.setFontName("Malgun Gothic");
				fontHeader.setFontHeightInPoints((short) 11); 
				fontHeader.setBold(true);
			Font fontBody = workbook.createFont();
				fontBody.setFontName("Malgun Gothic");
				fontBody.setFontHeightInPoints((short) 11); 
				fontBody.setBold(false);
			Font fontTitle = workbook.createFont();
				fontTitle.setFontName("Malgun Gothic");
				fontTitle.setFontHeightInPoints((short) 14); 
				fontTitle.setBold(true);
			
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
			
			CellStyle bodyStyle = workbook.createCellStyle();
				bodyStyle.setFont(fontBody);
				bodyStyle.setWrapText(true);
				bodyStyle.setBorderTop(BorderStyle.THIN);
				bodyStyle.setBorderBottom(BorderStyle.THIN);
				bodyStyle.setBorderLeft(BorderStyle.THIN);
				bodyStyle.setBorderRight(BorderStyle.THIN);
				bodyStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_GREEN.getIndex());
				bodyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				bodyStyle.setAlignment(HorizontalAlignment.LEFT);
			
			CellStyle titleStyle = workbook.createCellStyle();
				titleStyle.setFont(fontTitle);
				titleStyle.setWrapText(false);
				titleStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				titleStyle.setAlignment(HorizontalAlignment.CENTER);
				
			CellStyle bodyStyle2 = workbook.createCellStyle();
				bodyStyle2.setFont(fontTitle);
				bodyStyle2.setWrapText(false);
				bodyStyle2.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
				bodyStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				bodyStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
				bodyStyle2.setAlignment(HorizontalAlignment.CENTER);
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(titleStyle);	cell.setCellValue(title + " 결과서");
			cell = row.createCell(1);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(2);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("입력값");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("결과");
			
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("내용");
			cell = row.createCell(1);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			if ( "B".equals(vo.getBizType() ) ) {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue(vo.getExtNm() + ", 배치 파일 송수신 테스트");
			} else {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue(vo.getExtNm() + ", 온라인 전문 송수신 테스트");
			}
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("형상번호");
			cell = row.createCell(4);	cell.setCellStyle(bodyStyle);	cell.setCellValue(vo.getSrType());
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("인수자");
			cell = row.createCell(6);	cell.setCellStyle(bodyStyle);	cell.setCellValue("개발자B");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("테스트일자");
			cell = row.createCell(8);	cell.setCellStyle(bodyStyle);	cell.setCellValue(sdf2.format(new Date()));
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("CASE 1");
			cell = row.createCell(1);	cell.setCellStyle(bodyStyle);	cell.setCellValue("내용");
			if ( "B".equals(vo.getBizType() ) ) {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("배치 파일송신 테스트");
			} else {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("온라인 취급 테스트");
			}
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(9);	cell.setCellStyle(bodyStyle);	cell.setCellValue("업무팀 수행 데이터");
			cell = row.createCell(10);	cell.setCellStyle(bodyStyle);	cell.setCellValue("PASS");
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(1);	cell.setCellStyle(bodyStyle);	cell.setCellValue("테스트방법");
			if ( "B".equals(vo.getBizType() ) ) {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("파일 송신 테스트 수행요청");
			} else {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("취급전문 테스트 수행요청");
			}
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(1);	cell.setCellStyle(bodyStyle);	cell.setCellValue("예상결과");
			if ( "B".equals(vo.getBizType() ) ) {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("정상적으로 기관에 파일을 송신함");
			} else {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("정상적으로 기관에 전문을 송신하여 응답을 수신함");
			}
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("CASE 2");
			cell = row.createCell(1);	cell.setCellStyle(bodyStyle);	cell.setCellValue("내용");
			if ( "B".equals(vo.getBizType() ) ) {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("배치 파일수신 테스트");
			} else {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("온라인 개설 테스트");
			}
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(9);	cell.setCellStyle(bodyStyle);	cell.setCellValue("업무팀 수행 데이터");
			cell = row.createCell(10);	cell.setCellStyle(bodyStyle);	cell.setCellValue("PASS");
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(1);	cell.setCellStyle(bodyStyle);	cell.setCellValue("테스트방법");
			if ( "B".equals(vo.getBizType() ) ) {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("파일 수신 테스트 수행요청");
			} else {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("개설전문 테스트 수행요청");
			}
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(1);	cell.setCellStyle(bodyStyle);	cell.setCellValue("예상결과");
			if ( "B".equals(vo.getBizType() ) ) {
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("기관으로부터 정상적으로 파일을 수신하여 EAI 시스템 CALL 수행");
			} else { 
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle);	cell.setCellValue("정상적으로 전문유입 후 표준헤더를 조립해 계정계 대외수신서비스를 CALL하여 응답을 수신");
			}
			
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			
			
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,8)); // 단위테스트 결과서
			sheet.addMergedRegion(new CellRangeAddress(0,1,9,9)); // 입력값
			sheet.addMergedRegion(new CellRangeAddress(0,1,10,10)); // 결과
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,1)); // 내용
			sheet.addMergedRegion(new CellRangeAddress(2,4,0,0)); // CASE 1
			sheet.addMergedRegion(new CellRangeAddress(2,2,2,8)); // 온라인 취급 or 개설거래 테스트
			sheet.addMergedRegion(new CellRangeAddress(2,4,9,9)); // 업무팀 수행 데이터
			sheet.addMergedRegion(new CellRangeAddress(2,4,10,10)); // PASS
			sheet.addMergedRegion(new CellRangeAddress(3,3,2,8)); // 당행 업무팀 테스트
			sheet.addMergedRegion(new CellRangeAddress(4,4,2,8)); // 오류없이 정상 송수신
			sheet.addMergedRegion(new CellRangeAddress(5,7,0,0)); // CASE 2
			sheet.addMergedRegion(new CellRangeAddress(5,5,2,8)); // 온라인 취급 or 개설거래 테스트
			sheet.addMergedRegion(new CellRangeAddress(5,7,9,9)); // 업무팀 수행 데이터
			sheet.addMergedRegion(new CellRangeAddress(5,7,10,10)); // PASS
			sheet.addMergedRegion(new CellRangeAddress(6,6,2,8)); // 당행 업무팀 테스트
			sheet.addMergedRegion(new CellRangeAddress(7,7,2,8)); // 오류없이 정상 송수신
			
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 15000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 3500);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 3500);
			sheet.setColumnWidth(8, 4000);
			sheet.setColumnWidth(9, 8000);
			sheet.setColumnWidth(10, 4000);
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+vo.getSrType().trim() + URLEncoder.encode("_"+title+"결과서("+vo.getExtNm()+")_", "utf-8")+sdf.format(new Date())+".xls");
			
			workbook.write(response.getOutputStream());
			workbook.close();
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
		
	}
	
	/**
	 *  2022.01.10 엑셀 정책서 다운로드 
	 */ 
	@Override
	public void getExcelFireWall(HttpServletResponse response, FepLineInfoVo inputVo, String searchKey) {
		List<FepLineInfoVo> list = dao.selectItem2(inputVo, searchKey);
		logger.info("--- loadItem 건수 : " + list.size()); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String inst_name = "";
		
		try {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("방화벽정책등록신청서");
			
			Row row = null;
			Cell cell = null;
			int rowNo = 0;
			
			Font fontHeader = workbook.createFont();
			Font fontBody = workbook.createFont();
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
			bodyStyle2.setFont(fontBody);
			bodyStyle2.setWrapText(false);
			bodyStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("<신청내역>");
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("구분");
			cell = row.createCell(1);	cell.setCellStyle(headStyle);	cell.setCellValue("보안성심의대상요청번호");
			cell = row.createCell(2);	cell.setCellStyle(headStyle);	cell.setCellValue("SOURCE정보(출발지)");
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("DESTINATION(목적지)");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("프로토콜");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("포트정보\n(Out)");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("포트정보\n(In)");
			cell = row.createCell(11);	cell.setCellStyle(headStyle);	cell.setCellValue("사용기간\n(최대1년)");
			cell = row.createCell(12);	cell.setCellStyle(headStyle);	cell.setCellValue("사용용도");
			
			row = sheet.createRow(rowNo++);
			
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(1);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			
			cell = row.createCell(2);	cell.setCellStyle(headStyle);	cell.setCellValue("서버");
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("IP");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("NAT IP");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("서버");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("IP");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("NAT IP");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(11);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(12);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			
			
			sheet.addMergedRegion(new CellRangeAddress(1,1,2,4)); // SOURCE정보(출발지)
			sheet.addMergedRegion(new CellRangeAddress(1,1,5,7)); // DESTINATION(목적지)
			
			sheet.addMergedRegion(new CellRangeAddress(1,2,0,0)); // 구분
			sheet.addMergedRegion(new CellRangeAddress(1,2,1,1)); // 보안성심의대상요청번호
			sheet.addMergedRegion(new CellRangeAddress(1,2,8,8)); // 프로토콜
			sheet.addMergedRegion(new CellRangeAddress(1,2,9,9)); // 포트정보\n(Out)
			sheet.addMergedRegion(new CellRangeAddress(1,2,10,10)); // 포트정보\n(In)
			sheet.addMergedRegion(new CellRangeAddress(1,2,11,11)); // 사용기간\n(최대1년)
			sheet.addMergedRegion(new CellRangeAddress(1,2,12,12)); // 사용용도

			sheet.setColumnWidth(0, 2500);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 5600);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 5600);
			sheet.setColumnWidth(6, 5000);
			sheet.setColumnWidth(7, 5000);
			sheet.setColumnWidth(8, 1400);
			sheet.setColumnWidth(9, 2800);
			sheet.setColumnWidth(10, 2800);
			sheet.setColumnWidth(11, 1400);
			sheet.setColumnWidth(12, 8600);
			
			for ( FepLineInfoVo tempVo : list ) {
				
				/* 일몰 정책 pass */
				if ( "일몰".equals(tempVo.getSrType() ) )
					continue;
				
				/* 우리쪽에 리슨 포트가 존재하는 경우 --> 기관이 출발지 역할을 수행 */
				if ( tempVo.getKbkPort().trim().length() > 1  ) {
					
					row = sheet.createRow(rowNo++);
					inst_name = tempVo.getExtNm();
					
					if ( "P".equals(tempVo.getDevClcd()) ) {
						cell = row.createCell(0);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("운영");
					} else {
						cell = row.createCell(0);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("스테이징");
					}
					
					if ( tempVo.getSrType().length() >= 5 ) {
						cell = row.createCell(1);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getSrType());
					} else {
						cell = row.createCell(1);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("심의대상아님");
					}
					
					/* 출발지 */
					cell = row.createCell(2);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtNm());
					cell = row.createCell(3);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("-");
					cell = row.createCell(4);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n").replaceAll("\n\n", "\n"));
					/* 출발지 */
					if ( "P".equals(tempVo.getDevClcd()) ) {
						if ( "A".equals(tempVo.getBizType()) ) {
							cell = row.createCell(5);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("apiweb00\n(apiweb01~02)");
						} else {
							cell = row.createCell(5);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("fepap00\n(fepap01~02)");
						}
					} else {
						if ( "A".equals(tempVo.getBizType()) ) {
							cell = row.createCell(5);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("dapiweb01");
						} else {
							cell = row.createCell(5);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("dfepap01");
						}
					}
					cell = row.createCell(6);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n").replaceAll("\n\n", "\n"));
					cell = row.createCell(7);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkNatIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n").replaceAll("\n\n", "\n"));
					
					cell = row.createCell(8);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("TCP");
					cell = row.createCell(9);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkPort());
					cell = row.createCell(10);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("-");
					if ( "P".equals(tempVo.getDevClcd()) ) {
						cell = row.createCell(11);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("영구");
					} else {
						cell = row.createCell(11);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("1년");
					}
					
					cell = row.createCell(12);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizNm());
					
				}
				
				/* 기관쪽에 리슨포트가 있는경우 --> 우리가 출발지 역할을 수행 */
				if ( tempVo.getExtPort().trim().length() > 1  ) {
					
					row = sheet.createRow(rowNo++);
					inst_name = tempVo.getExtNm();
					
					if ( "P".equals(tempVo.getDevClcd()) ) {
						cell = row.createCell(0);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("운영");
					} else {
						cell = row.createCell(0);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("스테이징");
					}
				
					if ( tempVo.getSrType().length() >= 5 ) {
						cell = row.createCell(1);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getSrType());
					} else {
						cell = row.createCell(1);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("심의대상아님");
					}
					
					/* 출발지 */
					if ( "P".equals(tempVo.getDevClcd()) ) {
						if ( "A".equals(tempVo.getBizType()) ) {
							cell = row.createCell(2);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("apiweb00\n(apiweb01~02)");
						} else {
							cell = row.createCell(2);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("fepap00\n(fepap01~02)");
						}
					} else {
						if ( "A".equals(tempVo.getBizType()) ) {
							cell = row.createCell(2);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("dapiweb01");
						} else {
							cell = row.createCell(2);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("dfepap01");
						}
					}
					cell = row.createCell(3);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n").replaceAll("\n\n", "\n"));
					cell = row.createCell(4);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkNatIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n").replaceAll("\n\n", "\n"));
					
					/* 출발지 */
					cell = row.createCell(5);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtNm());
					cell = row.createCell(6);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("-");
					cell = row.createCell(7);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n").replaceAll("\n\n", "\n"));
					
					cell = row.createCell(8);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("TCP");
					cell = row.createCell(9);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtPort().replaceAll("&#160;", "").replaceAll("<br/>", "\n").replaceAll("\n\n", "\n"));
					cell = row.createCell(10);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("-");
					if ( "P".equals(tempVo.getDevClcd()) ) {
						cell = row.createCell(11);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("영구");
					} else {
						cell = row.createCell(11);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("1년");
					}
					
					cell = row.createCell(12);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizNm());
					
				}
				
				
			}
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("");
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("※ 보안성심의 대상(형상관리 요청번호) : 보안성심의관련 형상관리시스템에 등록한 요청 번호");
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("※ SOURCE : DESTINATION으로 접속할 PC/서버의 HOSTNAME과 IP정보");
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("※ DESTINATION : 접속할 대상서버의 HOSTNAME과 IP정보");
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("※ NAT IP : 외부 공인IP 및 기관과의 통신시 협의된 IP정보");
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("※ 프로토콜 : TCP/UDP 선택");
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("※ 사용기간 : 사용기간은 운영계 정책을 제회하고 최대 1년 신청으로 업무담당자가 1년마다 갱신신청을 해야 함(기간 만료 시 삭제)");
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("※ 사용용도 : 사용용도 기재");
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("(FEP)방화벽요청서", "utf-8")+"_"+sdf.format(new Date())+"_"+URLEncoder.encode(inst_name, "utf-8")+".xls");
			
			workbook.write(response.getOutputStream());
			workbook.close();
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
	}
	@Override
	public void getExcelL3(HttpServletResponse response, FepLineInfoVo inputVo, String searchKey) {
		List<FepLineInfoVo> list = dao.selectItem2(inputVo, searchKey);
		logger.info("--- loadItem 건수 : " + list.size()); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String inst_name = "";
		
		try {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("L3 라우팅 신청양식");
			
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
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("Source\nHostname(장비명)");
			cell = row.createCell(1);	cell.setCellStyle(headStyle);	cell.setCellValue("Source\nIP Address");
			cell = row.createCell(2);	cell.setCellStyle(headStyle);	cell.setCellValue("Destination\nHostname(장비명)");
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("Destination\nIP Address");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("라우팅적용회선");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("비고");

			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 5000);
			
	
			for ( FepLineInfoVo tempVo : list ) {
				
				row = sheet.createRow(rowNo++);
				inst_name = tempVo.getExtNm();
				
				if ( "P".equals(tempVo.getDevClcd() ) ) {
					cell = row.createCell(0);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("케이뱅크FEP\n(운영)");
					
				} else {
					cell = row.createCell(0);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("케이뱅크FEP\n(스테이징)");
				}
				cell = row.createCell(1);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkNatIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtNm());
				cell = row.createCell(3);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(4);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtNm() + " 회선");
				cell = row.createCell(5);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("서버통신 라우팅\n(양방향)");
				
			}
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("(FEP)L3_라우팅신청서_", "utf-8")+sdf.format(new Date())+"_"+URLEncoder.encode(inst_name, "utf-8")+".xls");
			
			workbook.write(response.getOutputStream());
			workbook.close();
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
		
	}
	@Override
	public void getExcelL4(HttpServletResponse response, FepLineInfoVo inputVo, String searchKey) {
		List<FepLineInfoVo> list = dao.selectItem2(inputVo, searchKey);
		logger.info("--- loadItem 건수 : " + list.size()); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String inst_name = "";
		
		try {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("네트워크 L4 신청서");
			
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
			
			Font fontTitle = workbook.createFont();
			fontTitle.setFontName("Malgun Gothic");
			fontTitle.setFontHeightInPoints((short) 14); 
			fontTitle.setBold(true);
			
			CellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setFont(fontTitle);
			titleStyle.setWrapText(false);
			titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			
			
			CellStyle bodyStyle2 = workbook.createCellStyle();
			bodyStyle2.setFont(fontBody);
			bodyStyle2.setWrapText(false);
			bodyStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(titleStyle);	cell.setCellValue("네트워크 L4 신청서 (신규/변경/해지)");
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(16);	cell.setCellStyle(bodyStyle2);	cell.setCellValue("Ver 1.0 (2018. 12. 24.)");
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("No");
			cell = row.createCell(1);	cell.setCellStyle(headStyle);	cell.setCellValue("구분");
			cell = row.createCell(2);	cell.setCellStyle(headStyle);	cell.setCellValue("Source");
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("Target");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(11);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(12);	cell.setCellStyle(headStyle);	cell.setCellValue("서비스정보");
			cell = row.createCell(13);	cell.setCellStyle(headStyle);	cell.setCellValue("신청정보");
			cell = row.createCell(14);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(15);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(16);	cell.setCellStyle(headStyle);	cell.setCellValue("비고");
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(1);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(2);	cell.setCellStyle(headStyle);	cell.setCellValue("Hostname");
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("IP");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("연동서버정보\n(hostname)");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("VIP");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("VPORT");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("RIP");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("RPORT");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("PROTOCOL");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("LOAD BALANCING\nMETHOD");
			cell = row.createCell(11);	cell.setCellStyle(headStyle);	cell.setCellValue("SESSION\nPERSISTENCE");
			cell = row.createCell(12);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			cell = row.createCell(13);	cell.setCellStyle(headStyle);	cell.setCellValue("신청일");
			cell = row.createCell(14);	cell.setCellStyle(headStyle);	cell.setCellValue("신청부서");
			cell = row.createCell(15);	cell.setCellStyle(headStyle);	cell.setCellValue("성명");
			cell = row.createCell(16);	cell.setCellStyle(headStyle);	cell.setCellValue("");
			
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,16)); // (제목) 네트워크 L4 신청서
			
			sheet.addMergedRegion(new CellRangeAddress(2,3,0,0)); // No
			sheet.addMergedRegion(new CellRangeAddress(2,3,1,1)); // 구분
			sheet.addMergedRegion(new CellRangeAddress(2,2,2,3)); // source
			sheet.addMergedRegion(new CellRangeAddress(2,2,4,11)); // target
			sheet.addMergedRegion(new CellRangeAddress(2,3,12,12)); // 서비스정보
			sheet.addMergedRegion(new CellRangeAddress(2,2,13,15)); // 신청정보
			sheet.addMergedRegion(new CellRangeAddress(2,3,16,16)); // 비고 

			sheet.setColumnWidth(0, 1000);
			sheet.setColumnWidth(1, 2000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 4000);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 3000);
			sheet.setColumnWidth(7, 4000);
			sheet.setColumnWidth(8, 3000);
			sheet.setColumnWidth(9, 3000);
			sheet.setColumnWidth(10, 5000);
			sheet.setColumnWidth(11, 4000);
			sheet.setColumnWidth(12, 6000);
			sheet.setColumnWidth(13, 3000);
			sheet.setColumnWidth(14, 4000);
			sheet.setColumnWidth(15, 3000);
			sheet.setColumnWidth(16, 6000);
			
			int index = 1;
			
			for ( FepLineInfoVo tempVo : list ) {
				
				/* 당행 리슨포트가 있는 경우에만 작성한다. && 운영인 경우에만 작성한다. */
				if ( tempVo.getKbkPort().trim().length() > 1  && "P".equals(tempVo.getDevClcd()) ) {
					
					row = sheet.createRow(rowNo++);
					inst_name = tempVo.getExtNm();
					
					cell = row.createCell(0);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(index++);
					cell = row.createCell(1);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("신규");
					cell = row.createCell(2);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("");
					cell = row.createCell(3);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("");
					cell = row.createCell(4);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("fepap01\nfepap02");
					cell = row.createCell(5);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("0.0.0.0");
					cell = row.createCell(6);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkPort().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
					cell = row.createCell(7);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("0.0.0.0\n0.0.0.0");
					cell = row.createCell(8);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkPort().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
					cell = row.createCell(9);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("tcp");
					cell = row.createCell(10);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("Round-Robin");
					cell = row.createCell(11);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("No");
					cell = row.createCell(12);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtNm() + " IN");
					cell = row.createCell(13);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
					cell = row.createCell(14);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("플랫폼기술팀");
					cell = row.createCell(15);	cell.setCellStyle(bodyStyle); 	cell.setCellValue("개발자B");
					cell = row.createCell(16);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizNm() + " 연동용");
					
				}
				
				
			}
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("(FEP)L4_SLB신청서_", "utf-8")+sdf.format(new Date())+"_"+URLEncoder.encode(inst_name, "utf-8")+".xls");
			
			workbook.write(response.getOutputStream());
			workbook.close();
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
		
	}
	
	/* sqlite 를 이용한 회선대장 신규 */
	
	@Override
	public int deleteItem2(int seqNo) {
		return dao.deleteItem2(seqNo);
	}
	
	@Override
	public int insertItem2(FepLineInfoVo inputVo) {
		return dao.insertItem2(inputVo);
	}
	
	@Override
	public List<FepLineInfoVo> selectItem2(FepLineInfoVo inputVo, String searchKey) {
		return dao.selectItem2(inputVo, searchKey);
	}
	
	@Override
	public int updateItem2(FepLineInfoVo inputVo) {
		return dao.updateItem2(inputVo);
	}
	
	/* CSV 방식으로 변경 */
	public void getExcelDown2(HttpServletResponse response) {
		List<FepLineInfoVo> list = dao.selectItem2(new FepLineInfoVo(), null);
		logger.info("--- loadItem 건수 : " + list.size());
		
		BufferedOutputStream bos = null;
		StringBuffer line = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String timestamp = sdf.format(new Date());
		
		try {
			bos = new BufferedOutputStream(response.getOutputStream());
			for ( FepLineInfoVo tempVo : list ) {
				line = new StringBuffer();
				
				line.append("\"" + tempVo.getSeqNo()  + "\",");
				line.append("\"" + tempVo.getExtCd()  + "\",");
				line.append("\"" + tempVo.getExtNm()  + "\",");
				line.append("\"" + tempVo.getBizCd()  + "\",");
				line.append("\"" + tempVo.getBizNm()  + "\",");
				line.append("\"" + tempVo.getBizType() + "\",");
				line.append("\"" + tempVo.getBizClcd() + "\",");
				line.append("\"" + tempVo.getNwLine() + "\",");
				line.append("\"" + tempVo.getDevClcd() + "\",");
				line.append("\"" + tempVo.getKbkIp()  + "\",");
				line.append("\"" + tempVo.getKbkNatIp() + "\",");
				line.append("\"" + tempVo.getKbkPort() + "\",");
				line.append("\"" + tempVo.getExtIp()  + "\",");
				line.append("\"" + tempVo.getExtPort() + "\",");
				line.append("\"" + tempVo.getSrType() + "\",");
				line.append("\"" + tempVo.getHistory() + "\",");
				line.append("\"" + tempVo.getExtUser() + "\" \n");
				
				line.toString().replaceAll("<br/>", "\n").replaceAll("&#8226;", "").replaceAll("&#160;", "");
				bos.write(line.toString().getBytes());
			}
			
			bos.flush();
			
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment;filename=FEP_LINE2_INFO_"+timestamp+".csv");
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		} finally {
			try {
				if ( bos != null ) bos.close();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void getExcelDown(HttpServletResponse response) {
		
		List<FepLineInfoVo> list = dao.selectItem2(new FepLineInfoVo(), null);
		logger.info("--- loadItem 건수 : " + list.size()); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String timestamp = sdf.format(new Date());
		
		try {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("FEP통신회선대장_" + timestamp);
			
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
			bodyStyle2.setFont(fontBody);
			bodyStyle2.setWrapText(true);
			bodyStyle2.setBorderTop(BorderStyle.THIN);
			bodyStyle2.setBorderBottom(BorderStyle.THIN);
			bodyStyle2.setBorderLeft(BorderStyle.THIN);
			bodyStyle2.setBorderRight(BorderStyle.THIN);
			bodyStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
			bodyStyle2.setAlignment(HorizontalAlignment.LEFT);
			
			Font fontTitle = workbook.createFont();
			fontTitle.setFontName("Malgun Gothic");
			fontTitle.setFontHeightInPoints((short) 14); 
			fontTitle.setBold(true);
			
			CellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setFont(fontTitle);
			titleStyle.setWrapText(false);
			titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			bodyStyle.setAlignment(HorizontalAlignment.CENTER);
			
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);	cell.setCellStyle(titleStyle);	cell.setCellValue("FEP 통신회선대장");
			row = sheet.createRow(rowNo++);
			
			row = sheet.createRow(rowNo++);
			
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("NO");
			cell = row.createCell(1);	cell.setCellStyle(headStyle);	cell.setCellValue("extCd");
			cell = row.createCell(2);	cell.setCellStyle(headStyle);	cell.setCellValue("extNm");
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("bizCd");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("bizNm");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("bizT");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("bizClcd");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("nwLine");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("devClcd");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("kbkIp");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("kbkNatIp");
			cell = row.createCell(11);	cell.setCellStyle(headStyle);	cell.setCellValue("kbkPort");
			cell = row.createCell(12);	cell.setCellStyle(headStyle);	cell.setCellValue("extIp");
			cell = row.createCell(13);	cell.setCellStyle(headStyle);	cell.setCellValue("extPort");
			cell = row.createCell(14);	cell.setCellStyle(headStyle);	cell.setCellValue("srType");
			cell = row.createCell(15);	cell.setCellStyle(headStyle);	cell.setCellValue("extUser");
			cell = row.createCell(16);	cell.setCellStyle(headStyle);	cell.setCellValue("history");
			
			sheet.setColumnWidth(0, 1000);
			sheet.setColumnWidth(1, 1000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 1000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 1000);
			sheet.setColumnWidth(6, 2000);
			sheet.setColumnWidth(7, 8000);
			sheet.setColumnWidth(8, 1000);
			sheet.setColumnWidth(9, 6000);
			sheet.setColumnWidth(10, 6000);
			sheet.setColumnWidth(11, 3000);
			sheet.setColumnWidth(12, 6000);
			sheet.setColumnWidth(13, 3000);
			sheet.setColumnWidth(14, 2000);
			sheet.setColumnWidth(15, 10000);
			sheet.setColumnWidth(16, 10000);
			
			for ( FepLineInfoVo tempVo : list ) {
				
				row = sheet.createRow(rowNo++);
				
				cell = row.createCell(0);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getSeqNo());
				cell = row.createCell(1);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtCd());
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtNm());
				cell = row.createCell(3);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizCd());
				cell = row.createCell(4);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizNm());
				cell = row.createCell(5);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizType());
				cell = row.createCell(6);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizClcd());
				cell = row.createCell(7);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getNwLine().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(8);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getDevClcd());
				cell = row.createCell(9);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(10);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkNatIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(11);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkPort().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(12);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtIp().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(13);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtPort().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(14);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getSrType().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(15);	cell.setCellStyle(bodyStyle2); 	cell.setCellValue(tempVo.getHistory().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
				cell = row.createCell(16);	cell.setCellStyle(bodyStyle2); 	cell.setCellValue(tempVo.getExtUser().replaceAll("&#160;", "").replaceAll("<br/>", "\n"));
			}
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+timestamp + URLEncoder.encode("_FEP통신회선대장", "utf-8")+".xls");
			
			workbook.write(response.getOutputStream());
			workbook.close();
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
		
	}	
	
	
	/* sqlite 를 이용한 회선대장 신규*/

	@Override
	public void deleteItem(int seqNo) {
		dao.deleteItem(seqNo);
	}

	@Override
	public void saveItem(int seqNo, String extCd, String extNm, String bizCd, String bizNm, String bizType,
			String bizClcd, String nwLine, String nwRouter, String fwVpn, String devClcd, String kbkIp, String kbkNatIp,
			String kbkPort, String extIp, String extPort, String srType, String extUser, String history) {
		dao.saveItem(seqNo, extCd, extNm, bizCd, bizNm, bizType, bizClcd, nwLine, nwRouter, fwVpn, devClcd, kbkIp, kbkNatIp, kbkPort, extIp, extPort, srType, extUser, history);
	}

	@Override
	public void saveItem(FepLineInfoVo inputVo) {
		dao.saveItem(inputVo);
	}

	@Override
	public Map<Integer, FepLineInfoVo> loadItem() {
		return dao.loadItem();
	}
	
	@Override
	public List<FepLineInfoVo> loadItemList() {
		Map<Integer, FepLineInfoVo> map = dao.loadItem();
		logger.info("--- loadItem 건수 : " + map.size()); 
		List<FepLineInfoVo> list = new ArrayList<FepLineInfoVo>();
		FepLineInfoVo tempVo = null;
		for ( Integer i : map.keySet() ) {
			tempVo = map.get(i);
			if ( tempVo.getBizNm() != null ) 	tempVo.setBizNm(tempVo.getBizNm().replaceAll("\n", "<br/>"));
			if ( tempVo.getNwLine() != null ) 	tempVo.setNwLine(tempVo.getNwLine().replaceAll("\n", "<br/>"));
			if ( tempVo.getDevClcd() != null ) 	tempVo.setDevClcd(tempVo.getDevClcd().replaceAll("\n", "<br/>"));
			if ( tempVo.getKbkIp() != null ) 	tempVo.setKbkIp(tempVo.getKbkIp().replaceAll("\n", "<br/>"));
			if ( tempVo.getKbkNatIp() != null ) 	tempVo.setKbkNatIp(tempVo.getKbkNatIp().replaceAll("\n", "<br/>"));
			if ( tempVo.getExtIp() != null ) 	tempVo.setExtIp(tempVo.getExtIp().replaceAll("\n", "<br/>"));
			if ( tempVo.getExtPort() != null ) 	tempVo.setExtPort(tempVo.getExtPort().replaceAll("\n", "<br/>"));
			if ( tempVo.getSrType() != null ) 	tempVo.setSrType(tempVo.getSrType().replaceAll("\n", "<br/>"));
			if ( tempVo.getHistory() != null ) 	tempVo.setHistory(tempVo.getHistory().replaceAll("\n", "<br/>"));
			if ( tempVo.getExtUser() != null ) 	tempVo.setExtUser(tempVo.getExtUser().replaceAll("\n", "<br/>"));
			list.add(tempVo);
		}
		return list;
	}
	
	public File toExcel() {
		return null;
	}
	/*
	public void getExcelDown(HttpServletResponse response) {
		
		Map<Integer, FepLineInfoVo> map = dao.loadItem();
		logger.info("--- loadItem 건수 : " + map.size()); 
		FepLineInfoVo tempVo = null;
		
		try {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("FEP LINE INFO");
			
			Row row = null;
			Cell cell = null;
			int rowNo = 0;
			
			Font font = workbook.createFont();
			font.setFontName("d2coding");
			
			CellStyle headStyle = workbook.createCellStyle();
			headStyle.setFont(font);
			headStyle.setBorderTop(BorderStyle.THIN);
			headStyle.setBorderBottom(BorderStyle.THIN);
			headStyle.setBorderLeft(BorderStyle.THIN);
			headStyle.setBorderRight(BorderStyle.THIN);
			headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headStyle.setAlignment(HorizontalAlignment.CENTER);
			
			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setFont(font);
			bodyStyle.setWrapText(true);
			bodyStyle.setBorderTop(BorderStyle.THIN);
			bodyStyle.setBorderBottom(BorderStyle.THIN);
			bodyStyle.setBorderLeft(BorderStyle.THIN);
			bodyStyle.setBorderRight(BorderStyle.THIN);
			
			row = sheet.createRow(rowNo++);
			
			cell = row.createCell(0);	cell.setCellStyle(headStyle);	cell.setCellValue("NO");
			cell = row.createCell(1);	cell.setCellStyle(headStyle);	cell.setCellValue("extCd");
			cell = row.createCell(2);	cell.setCellStyle(headStyle);	cell.setCellValue("extNm");
			cell = row.createCell(3);	cell.setCellStyle(headStyle);	cell.setCellValue("bizCd");
			cell = row.createCell(4);	cell.setCellStyle(headStyle);	cell.setCellValue("bizNm");
			cell = row.createCell(5);	cell.setCellStyle(headStyle);	cell.setCellValue("bizT");
			cell = row.createCell(6);	cell.setCellStyle(headStyle);	cell.setCellValue("bizClcd");
			cell = row.createCell(7);	cell.setCellStyle(headStyle);	cell.setCellValue("nwLine");
			cell = row.createCell(8);	cell.setCellStyle(headStyle);	cell.setCellValue("devClcd");
			cell = row.createCell(9);	cell.setCellStyle(headStyle);	cell.setCellValue("kbkIp");
			cell = row.createCell(10);	cell.setCellStyle(headStyle);	cell.setCellValue("kbkNatIp");
			cell = row.createCell(11);	cell.setCellStyle(headStyle);	cell.setCellValue("kbkPort");
			cell = row.createCell(12);	cell.setCellStyle(headStyle);	cell.setCellValue("extIp");
			cell = row.createCell(13);	cell.setCellStyle(headStyle);	cell.setCellValue("extPort");
			cell = row.createCell(14);	cell.setCellStyle(headStyle);	cell.setCellValue("srType");
			cell = row.createCell(15);	cell.setCellStyle(headStyle);	cell.setCellValue("extUser");
			cell = row.createCell(16);	cell.setCellStyle(headStyle);	cell.setCellValue("history");
			
			for ( Integer i : map.keySet() ) {
				
				tempVo = map.get(i);
				row = sheet.createRow(rowNo++);
				cell = row.createCell(0);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getSeqNo());
				cell = row.createCell(1);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtCd());
				cell = row.createCell(2);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtNm());
				cell = row.createCell(3);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizCd());
				cell = row.createCell(4);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizNm());
				cell = row.createCell(5);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizType());
				cell = row.createCell(6);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getBizClcd());
				cell = row.createCell(7);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getNwLine());
				cell = row.createCell(8);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getDevClcd());
				cell = row.createCell(9);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkIp());
				cell = row.createCell(10);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkNatIp());
				cell = row.createCell(11);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getKbkPort());
				cell = row.createCell(12);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtIp());
				cell = row.createCell(13);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtPort());
				cell = row.createCell(14);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getSrType());
				cell = row.createCell(15);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getHistory());
				cell = row.createCell(16);	cell.setCellStyle(bodyStyle); 	cell.setCellValue(tempVo.getExtUser());
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String timestamp = sdf.format(new Date());
			
			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment;filename="+timestamp+"_(FEP)LINE_INFO.xls");
			
			workbook.write(response.getOutputStream());
			workbook.close();
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
		
	}
	 */
}
