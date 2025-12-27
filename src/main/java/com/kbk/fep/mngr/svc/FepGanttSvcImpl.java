package com.kbk.fep.mngr.svc;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbk.fep.mngr.dao.FepGanttDao;
import com.kbk.fep.mngr.dao.vo.FepGanttVo;

@Service
public class FepGanttSvcImpl implements FepGanttSvc {
	
	@Autowired
	private FepGanttDao dao;
	private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public List<FepGanttVo> selectBizList(String startDay, String endDay) {
		return dao.selectList(startDay, endDay);
	}
	
	@Override
	public List<String> selectDayList(String startDay, String endDay) {
		List<String> retValue = new ArrayList<String>();
		try {
			long start = yyyyMMdd.parse(startDay).getTime();
			long end = yyyyMMdd.parse(endDay).getTime();
			
			for ( long i=start; i<=end; i+=(24*60*60*1000)) {
				retValue.add(yyyyMMdd.format(new Date(i)));
			}
		} catch ( Exception e ) {
			System.err.println(e);
		}
		return retValue;
	}
	
	@Override
	public FepGanttVo selectItem(int seqNo) {
		return dao.selectItem(seqNo);
	}
	
	@Override
	public int deleteItem(int seqNo) {
		return dao.deleteItem(seqNo);
	}
	
	@Override
	public int editItem(FepGanttVo vo) {
		return dao.editItem(vo);
	}
	
	@Override
	public int insertItem(FepGanttVo vo) {
		return dao.insertItem(vo);
	}
	
	public static void main(String [] args) {
		FepGanttSvcImpl svc = new FepGanttSvcImpl();
		List<String> result = svc.selectDayList("20220313", "20220201");
		for ( String temp : result ) {
			System.out.println(temp);
		}
	}

}
