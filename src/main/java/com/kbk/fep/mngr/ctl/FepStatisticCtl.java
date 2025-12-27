package com.kbk.fep.mngr.ctl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kbk.fep.mngr.dao.vo.FepAlsticsVo;
import com.kbk.fep.mngr.dao.vo.FepCommPropVo;
import com.kbk.fep.mngr.svc.FepAlsticsSvc;
import com.kbk.fep.util.FepStaticDataInfo;

@Controller
@RequestMapping("/stics")
public class FepStatisticCtl {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FepCommPropVo prop;
	@Autowired
	private FepAlsticsSvc sticsSvc;
	
	@RequestMapping("/deletestatics")
	@ResponseBody
	public String deletestatics(
			@RequestParam(value="target_date", required=true) String target_date
			) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /stics/deletestatics");
		logger.info("--- REQUEST PARAM [target_date]=["+target_date+"]");
		
		long currentTime =  new Date().getTime();
		
		if ( FepStaticDataInfo.history_date == null || FepStaticDataInfo.updateMaxTime + 10*60*60*1000 < currentTime ) {
			logger.info("--- HISTORY DATE IS NULL -- MAX DATE QUERY START!");
			FepStaticDataInfo.history_date = sticsSvc.getMaxDate();
			FepStaticDataInfo.updateMaxTime = currentTime;
		}
		if ( FepStaticDataInfo.peak_date == null || FepStaticDataInfo.updatePeakTime + 10*60*60*1000 < currentTime ) {
			logger.info("--- PEAK DATE IS NULL -- PEAK DATE QUERY START!");
			FepStaticDataInfo.peak_date = sticsSvc.getPeakDate();
			FepStaticDataInfo.updatePeakTime = currentTime;
		}
		
		int result = 0;
		/* 지우지말아야할 날짜가 도착한경우 비교하여 패스한다. */
		logger.info("--- HISTORY DATE=["+FepStaticDataInfo.history_date+"]");
		logger.info("--- PEAK DATE=["+FepStaticDataInfo.peak_date+"]");
		if ( !target_date.equals(FepStaticDataInfo.history_date) && !target_date.equals(FepStaticDataInfo.peak_date) )  {
			result = sticsSvc.deleteMinStics(target_date);
		} else {
			logger.info("--- 지우지 말아야할 날짜입니다. : " + target_date);
		}
			
		logger.info("--- DELETE RESULT : " + result);
		logger.info("---------------------------------------");
		return target_date;
	}
	
	@RequestMapping("/dailystatics")
	@ResponseBody
	public String dailystatics(
			@RequestParam(value="target_date", required=true) String target_date
			) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /stics/dailystatics");
		logger.info("--- REQUEST PARAM [target_date]=["+target_date+"]");
		int result = sticsSvc.selectForInsertDailyStics(target_date);
		logger.info("--- INSRET RESULT : " + result);
		logger.info("---------------------------------------");
		return target_date;
	}
	
	@RequestMapping("/rank")
	public String sticsRank(
			@RequestParam(value="target_date", required=false) String target_date,
			@RequestParam(value="inst_code", required=false) String inst_code,
			@RequestParam(value="yaxis_max", required=false) String yaxis_max,
			@RequestParam(value="chart_height", required=false) String chart_height,
			Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /stics/volume");
		logger.info("--- REQUEST PARAM [target_date]=["+target_date+"]");
		logger.info("--- REQUEST PARAM [inst_code]=["+inst_code+"]");
		logger.info("--- REQUEST PARAM [yaxis_max]=["+yaxis_max+"]");
		logger.info("--- REQUEST PARAM [chart_height]=["+chart_height+"]");
		
		model.addAttribute("target_date", target_date);
		if ( target_date == null || target_date.trim().length() == 0 ) 
			target_date = new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()));
		
		model.addAttribute("yaxis_max", yaxis_max);
		if ( chart_height == null || chart_height.trim().length() == 0 ) chart_height = "300";
		model.addAttribute("chart_height", chart_height);
		model.addAttribute("inst_code", inst_code);
		if ( yaxis_max != null && yaxis_max.trim().length() > 0 )
			model.addAttribute("yaxis_str", ", max: " + yaxis_max);
		if ( inst_code !=null && inst_code.trim().length() > 0 )
			model.addAttribute("title", "'"+target_date+" "+sticsSvc.selectInstName(inst_code)+" 업무별 FEP TOP 거래량'");
		else
			model.addAttribute("title", "'"+target_date+" 기관별 FEP TOP 12 거래량'");
		
		Map<String, String> data = sticsSvc.selectRank(target_date, inst_code);
		StringBuffer chart_data = new StringBuffer("[");
		StringBuffer series_data = new StringBuffer();
		int index=0;
		for ( String key : data.keySet() ) {
			if ( inst_code !=null && inst_code.trim().length() > 0 )
				series_data.append("{ label:'"+sticsSvc.selectApplName(key)+"', markerOptions: { size: 3 }, shadow:false } ");
			else
				series_data.append("{ label:'"+sticsSvc.selectInstName(key)+"', markerOptions: { size: 3 }, shadow:false } ");
			chart_data.append("[ "+data.get(key)+" ] ");
			if ( index++ < data.size() ) {
				series_data.append(", \n");
				chart_data.append(", \n");
			}
		}
		chart_data.append("]");
		model.addAttribute("chart_data", chart_data.toString());
		model.addAttribute("series_data", series_data.toString());
		model.addAttribute("env", prop.getProfiles());
		logger.info("---------------------------------------");
		return "graph/rank";
	}
	
	@RequestMapping("/volume-day")
	public String sticsVolumeDay(
			@RequestParam(value="start_date", required=false) String start_date,
			@RequestParam(value="end_date", required=false) String end_date,
			@RequestParam(value="inst_code", required=false) String inst_code,
			@RequestParam(value="appl_code", required=false) String appl_code,
			@RequestParam(value="yaxis_max", required=false) String yaxis_max,
			@RequestParam(value="chart_height", required=false) String chart_height,
			Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /stics/volume-day");
		logger.info("--- REQUEST PARAM [start_date]=["+start_date+"]");
		logger.info("--- REQUEST PARAM [end_date]=["+end_date+"]");
		logger.info("--- REQUEST PARAM [inst_code]=["+inst_code+"]");
		logger.info("--- REQUEST PARAM [appl_code]=["+appl_code+"]");
		logger.info("--- REQUEST PARAM [yaxis_max]=["+yaxis_max+"]");
		logger.info("--- REQUEST PARAM [chart_height]=["+chart_height+"]");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		if ( end_date == null || end_date.trim().length() == 0 ) {
			end_date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		}

		if ( start_date == null || start_date.trim().length() == 0 ) {
			cal.add(Calendar.MONTH, -1);
			start_date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		}
		
		model.addAttribute("start_date", start_date);
		model.addAttribute("end_date", end_date);
		model.addAttribute("inst_code", inst_code);
		model.addAttribute("appl_code", appl_code);
		List<FepAlsticsVo> result = sticsSvc.selectVolumDay(start_date, end_date, inst_code, appl_code);
		logger.info("--- select count : " + result.size());
		StringBuffer chart_data = new StringBuffer();
		StringBuffer ticks_data = new StringBuffer();
		for ( FepAlsticsVo vo : result ) {
			ticks_data.append("," + vo.getProc_date());
			chart_data.append("," + vo.getLog_count());
		}
		if ( chart_data.toString().trim().length() > 1 ) {
			model.addAttribute("chart_data", "["+chart_data.toString().substring(1)+"]");
			model.addAttribute("ticks_data", "["+ticks_data.toString().substring(1)+"]");
		} else {
			model.addAttribute("chart_data", "[]");
			model.addAttribute("ticks_data", "[]");
		}
		
		model.addAttribute("env", prop.getProfiles());
		if ( chart_height == null || chart_height.trim().length() == 0 ) chart_height = "300";
		model.addAttribute("chart_height", chart_height);
		model.addAttribute("yaxis_max", yaxis_max);
		if ( yaxis_max != null && yaxis_max.trim().length() > 0 )
			model.addAttribute("yaxis_str", ", max: " + yaxis_max);
		if ( inst_code != null && inst_code.trim().length() > 0 ) {
			if ( appl_code != null && appl_code.trim().length() > 0 ) {
				model.addAttribute("title", "'" + sticsSvc.selectInstName(inst_code) + ", "+ sticsSvc.selectApplName(appl_code)+" 일별 거래량'");
			} else {
				model.addAttribute("title", "'" + sticsSvc.selectInstName(inst_code) + " 일별 거래량'");
			}
		} else {
			model.addAttribute("title", "'전체 일별 거래량'");
		}
		logger.info("---------------------------------------");
		return "graph/volume-day";
	}
	
	/**
	 * 
	 * @param start_date
	 * @param end_date
	 * @param inst_code
	 * @param appl_code
	 * @param model
	 * @return
	 */
	@RequestMapping("/volume")
	public String sticsAll(
			@RequestParam(value="target_date", required=false) String target_date,
			@RequestParam(value="history_date", required=false) String history_date,
			@RequestParam(value="inst_code", required=false) String inst_code,
			@RequestParam(value="appl_code", required=false) String appl_code,
			@RequestParam(value="yaxis_max", required=false) String yaxis_max,
			@RequestParam(value="chart_height", required=false) String chart_height,
			Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /stics/volume");
		logger.info("--- REQUEST PARAM [target_date]=["+target_date+"]");
		logger.info("--- REQUEST PARAM [history_date]=["+history_date+"]");
		logger.info("--- REQUEST PARAM [inst_code]=["+inst_code+"]");
		logger.info("--- REQUEST PARAM [appl_code]=["+appl_code+"]");
		logger.info("--- REQUEST PARAM [yaxis_max]=["+yaxis_max+"]");
		logger.info("--- REQUEST PARAM [chart_height]=["+chart_height+"]");
		
		model.addAttribute("target_date", target_date);
		if ( target_date == null || target_date.trim().length() == 0) 
			target_date = new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()));
		if ( FepStaticDataInfo.history_date == null ) {
			logger.info("--- HISTORY DATE IS NULL -- MAXDATE QUERY START!");
			FepStaticDataInfo.history_date = sticsSvc.getMaxDate();
		} 
		if ( history_date == null || history_date.trim().length() == 0 )
			history_date = FepStaticDataInfo.history_date;
		
		model.addAttribute("history_date", history_date);
		model.addAttribute("inst_code", inst_code);
		model.addAttribute("appl_code", appl_code);
		List<String> dateRange = new ArrayList<String>();
		dateRange.add(history_date); dateRange.add(target_date);
		model.addAttribute("chart_data", sticsSvc.selectAll(dateRange, inst_code, appl_code));
		model.addAttribute("series_data", lebelCreator(dateRange));
		model.addAttribute("env", prop.getProfiles());
		model.addAttribute("yaxis_max", yaxis_max);
		if ( chart_height == null || chart_height.trim().length() == 0 ) chart_height = "300";
		model.addAttribute("chart_height", chart_height);
		if ( yaxis_max != null && yaxis_max.trim().length() > 0 )
			model.addAttribute("yaxis_str", ", max: " + yaxis_max);
		if ( inst_code != null && inst_code.trim().length() > 0 ) {
			if ( appl_code != null && appl_code.trim().length() > 0 ) {
				model.addAttribute("title", "'" + sticsSvc.selectInstName(inst_code) + ", "+ sticsSvc.selectApplName(appl_code)+" 1분당거래량'");
			} else {
				model.addAttribute("title", "'" + sticsSvc.selectInstName(inst_code) + " 1분당거래량'");
			}
		} else {
			model.addAttribute("title", "'전체 1분당거래량'");
		}
			
		logger.info("---------------------------------------");
		return "graph/graph";
	}
	
	private String lebelCreator(List<String> dates) {
		StringBuffer retValue = new StringBuffer();
		for ( int i=0; i<dates.size(); i++ ) {
			if ( i == dates.size()-1 ) {
				retValue.append(",{ label:'오늘("+dates.get(i)+")', markerOptions: { size: 3 }, shadow:false, fill:true, fillAndStroke:true, fillAlpha:0.6 } \n");
			} else {
				retValue.append(",{ label:'역대("+dates.get(i)+")', markerOptions: { size: 3 }, shadow:false } \n");
			}
		}
		return retValue.substring(1);
	}
	
	private String lebelCreator2(List<String> dates) {
		StringBuffer retValue = new StringBuffer();
		for ( int i=0; i<dates.size(); i++ ) {
			if ( i == 0 ) {
				retValue.append(",{ label:'역대("+dates.get(i)+")', markerOptions: { size: 3 }, shadow:false} \n");
			} else if ( i== 1 ) {
				retValue.append(",{ label:'피크("+dates.get(i)+")', markerOptions: { size: 3 }, shadow:false } \n");
			} else {
				retValue.append(",{ label:'오늘("+dates.get(i)+")', markerOptions: { size: 3 }, shadow:false, fill:true, fillAndStroke:true, fillAlpha:0.6  } \n");
			}
		}
		return retValue.substring(1);
	}
	/**
	 * 
	 * @param start_date
	 * @param end_date
	 * @param inst_code
	 * @param appl_code
	 * @param model
	 * @return
	 */
	@RequestMapping("/peak")
	public String sticsPeak(
			@RequestParam(value="target_date", required=false) String target_date,
			@RequestParam(value="history_date", required=false) String history_date,
			@RequestParam(value="peak_date", required=false) String peak_date,
			@RequestParam(value="inst_code", required=false) String inst_code,
			@RequestParam(value="appl_code", required=false) String appl_code,
			@RequestParam(value="yaxis_max", required=false) String yaxis_max,
			@RequestParam(value="chart_height", required=false) String chart_height,
			Model model) {
		logger.info("---------------------------------------");
		logger.info("--- APP NAME : /stics/volume");
		logger.info("--- REQUEST PARAM [target_date]=["+target_date+"]");
		logger.info("--- REQUEST PARAM [history_date]=["+history_date+"]");
		logger.info("--- REQUEST PARAM [peak_date]=["+peak_date+"]");
		logger.info("--- REQUEST PARAM [inst_code]=["+inst_code+"]");
		logger.info("--- REQUEST PARAM [appl_code]=["+appl_code+"]");
		logger.info("--- REQUEST PARAM [yaxis_max]=["+yaxis_max+"]");
		logger.info("--- REQUEST PARAM [chart_height]=["+chart_height+"]");
		
		model.addAttribute("target_date", target_date);
		long currentTime =  new Date().getTime();
		if ( target_date == null || target_date.trim().length() == 0 ) target_date = new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()));
		if ( FepStaticDataInfo.history_date == null || FepStaticDataInfo.updateMaxTime + 10*60*60*1000 < currentTime ) {
			logger.info("--- HISTORY DATE IS NULL -- MAX DATE QUERY START!");
			FepStaticDataInfo.history_date = sticsSvc.getMaxDate();
			FepStaticDataInfo.updateMaxTime = currentTime;
		}
		if ( history_date == null ) 
			history_date = FepStaticDataInfo.history_date;
		if ( FepStaticDataInfo.history_date == null || FepStaticDataInfo.updatePeakTime + 10*60*60*1000 < currentTime ) {
			logger.info("--- PEAK DATE IS NULL -- PEAK DATE QUERY START!");
			FepStaticDataInfo.peak_date = sticsSvc.getPeakDate();
			FepStaticDataInfo.updatePeakTime = currentTime;
		}
		if ( peak_date == null )
			peak_date = FepStaticDataInfo.peak_date;
					
		model.addAttribute("history_date", history_date);
		model.addAttribute("peak_date", peak_date);
		model.addAttribute("inst_code", inst_code);
		model.addAttribute("appl_code", appl_code);
		model.addAttribute("yaxis_max", yaxis_max);
		if ( chart_height == null ) chart_height = "300";
		model.addAttribute("chart_height", chart_height);
		model.addAttribute("env", prop.getProfiles());
		List<String> dateRange = new ArrayList<String>();
		dateRange.add(history_date); 	//1
		dateRange.add(peak_date);		//2
		dateRange.add(target_date);		//3 순서임
		
		try {
			model.addAttribute("chart_data", sticsSvc.selectAll(dateRange, inst_code, appl_code));
			model.addAttribute("series_data", lebelCreator2(dateRange)); // 라벨도 순서지켜
			if ( yaxis_max != null && yaxis_max.trim().length() > 0 )
				model.addAttribute("yaxis_str", ", max: " + yaxis_max);
			if ( inst_code != null && inst_code.trim().length() > 0 ) {
				if ( appl_code != null && appl_code.trim().length() > 0 ) {
					model.addAttribute("title", "'" + sticsSvc.selectInstName(inst_code) + ", "+ sticsSvc.selectApplName(appl_code)+" 1분당거래량'");
				} else {
					model.addAttribute("title", "'" + sticsSvc.selectInstName(inst_code) + " 1분당거래량'");
				}
			} else {
				model.addAttribute("title", "'전체 1분당거래량'");
			}
			
		} catch ( Exception e ) {
			model.addAttribute("chart_data", "");
			model.addAttribute("series_data", ""); // 라벨도 순서지켜
			model.addAttribute("title", "'오류입니다. 리프레시하세요'");
		} 
			
		logger.info("---------------------------------------");
		return "graph/peak";
	}
	
	@Deprecated
	private List<String> listDates(String start_date, String end_date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		List<String> retValue = new ArrayList<String>();
		retValue.add(start_date);
		long startDate;
		long endDate;
		try {
			startDate = sdf.parse(start_date).getTime();
			endDate = sdf.parse(end_date).getTime()+10;
			long nextTime = startDate;
			for ( int i=0; i<7; i++ ) {
				nextTime += 24*60*60*1000;
				if ( nextTime <= endDate ) {
					retValue.add(sdf.format(new Date(nextTime)));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retValue;
	}
	
	@Deprecated
	private String dayOfWeek(String yyyyMMdd) {
		Calendar cal = Calendar.getInstance();
		Date d;
		String retValue = "";
		try {
			d = new SimpleDateFormat("yyyyMMdd").parse(yyyyMMdd);
			cal.setTime(d);
			switch ( cal.get(Calendar.DAY_OF_WEEK) ) {
			case 1:
				retValue="일"; break;
			case 2:
				retValue="월"; break;
			case 3:
				retValue="화"; break;
			case 4:
				retValue="수"; break;
			case 5:
				retValue="목"; break;
			case 6:
				retValue="금"; break;
			case 7:
				retValue="토"; break;
			}
		} catch (ParseException e) {
			logger.error("--- ERROR ["+yyyyMMdd+"] ", e);
		}
		return retValue;
	}


}
