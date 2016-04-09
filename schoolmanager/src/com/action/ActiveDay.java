package com.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import utils.AnalysisConfig;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

import bean.UserCount;
import bean.UserSkimTime;

public class ActiveDay implements ActiveDateInterface{
	/*
	 *获取前几天的数据:yyyy-MM-dd 
	 *传入的time是坐标轴最左边的年月日
	 */
	public ArrayList<UserCount> getData(String time ,int page) throws Exception{
		int num = Integer.parseInt(AnalysisConfig.getProperty("shownum")) ;
		ArrayList<UserCount> userCounts = new ArrayList<UserCount>();
		String [] times = time.split("-");
		int year = Integer.parseInt(times[0]);
		int month = Integer.parseInt(times[1])-1;
		int day = Integer.parseInt(times[2]);
		if(page==1){	//上一页
			System.out.println("获取前几天活跃度");
			day=day-num;
		}else{	//下一页
			System.out.println("获取后几天活跃度");
			day = day +num;
		}
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		UserSkimTime userSkimTime1 = new UserSkimTime();
		UserSkimTime userSkimTime2 = new UserSkimTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < num; i++) {
			calendar1.set(year, month, day+i, 0, 0, 0);
			calendar2.set(year, month, day+i+1, 0, 0, 0);
			userSkimTime1.setLoginTime(calendar1.getTime());
			userSkimTime2 .setExitTime(calendar2.getTime());
			CreateAndQuery andQuery=new CreateAndQuery();//条件合并
			andQuery.Add(CreateQueryFromBean.GtObj(userSkimTime1));
			andQuery.Add(CreateQueryFromBean.LtObj(userSkimTime2));
			BasicDBObject query=andQuery.GetResult();
			userCounts.add(new UserCount(sdf.format(calendar1.getTime()), (int)DaoImpl.GetSelectCount(UserSkimTime.class, query)));
		}		
		return userCounts;
	}
	
	public static void main(String[] args) throws Exception {
		ArrayList<UserCount> userCounts= null;
		ActiveDay activeMonth = new ActiveDay();
		userCounts = activeMonth.getData("2015-3-1", 1);
		System.out.println(userCounts.size());
		for (int i = 0; i < userCounts.size(); i++){
			System.out.println(userCounts.get(i).getName()+":"+userCounts.get(i).getCount());
		}
	}
}
