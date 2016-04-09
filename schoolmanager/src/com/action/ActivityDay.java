package com.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import utils.AnalysisConfig;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

import bean.InActivity;
import bean.UserCount;

public class ActivityDay implements ActiveDateInterface{
	/*
	 * yyyy-MM-dd
	 */
	public ArrayList<UserCount> getData(String time, int page) throws Exception {
		int num = Integer.parseInt(AnalysisConfig.getProperty("shownum"));
		ArrayList<UserCount> userCounts = new ArrayList<UserCount>();
		String [] times = time.split("-");
		int year = Integer.parseInt(times[0]);
		int month = Integer.parseInt(times[1])-1;
		int date = Integer.parseInt(times[2]);
		if(page==1){
			date=date-num;
		}else{
			date = date+num;
		}
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();  			
		InActivity inActivity1 = new InActivity();
		InActivity inActivity2 = new InActivity();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i< num ;i++ ){
			calendar1.set(year, month, date+i, 0, 0, 0);
			calendar2.set(year, month, date+i+1, 0, 0, 0);
			inActivity1.setReleaseTime(calendar1.getTime());
			inActivity2.setReleaseTime(calendar2.getTime());
			CreateAndQuery andQuery=new CreateAndQuery();//条件合并
			andQuery.Add(CreateQueryFromBean.GtObj(inActivity1));
			andQuery.Add(CreateQueryFromBean.LtObj(inActivity2));
			BasicDBObject query=andQuery.GetResult();
			userCounts.add(new UserCount(sdf.format(calendar1.getTime()), (int)DaoImpl.GetSelectCount(InActivity.class, query)));
		}
		return userCounts;
	}
}
