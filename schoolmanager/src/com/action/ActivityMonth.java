package com.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

import bean.InActivity;
import bean.UserCount;

public class ActivityMonth implements ActiveDateInterface{
	public static final int NUM =12; 

	public ArrayList<UserCount> getData(String time, int page) throws Exception {
		ArrayList<UserCount> userCounts = new ArrayList<UserCount>();
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int year = Integer.parseInt(time);
		if(page==1){
			year--;
		}else{
			year++;
		}
		InActivity inActivity1 = new InActivity();
		InActivity inActivity2 = new InActivity();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		for(int i = 0; i< NUM ;i++ ){
			calendar1.set(year, i, 1, 0, 0, 0);
			calendar2.set(year, i+1, 1, 0, 0, 0);
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
