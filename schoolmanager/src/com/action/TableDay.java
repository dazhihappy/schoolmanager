package com.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import utils.AnalysisConfig;
import bean.TableInfo;
import bean.UserCount;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

public class TableDay implements ActiveDateInterface{

	public ArrayList<UserCount> getData(String time, int page) throws Exception {
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
		TableInfo tableInfo1 = new TableInfo();
		TableInfo tableInfo2 = new TableInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i< num ;i++ ){
			calendar1.set(year, month, day+i, 0, 0, 0);
			calendar2.set(year, month, day+i+1, 0, 0, 0);
			tableInfo1.setCreateTime(calendar1.getTime());
			tableInfo2.setCreateTime(calendar2.getTime());
			CreateAndQuery andQuery=new CreateAndQuery();//条件合并
			andQuery.Add(CreateQueryFromBean.GtObj(tableInfo1));
			andQuery.Add(CreateQueryFromBean.LtObj(tableInfo2));
			BasicDBObject query=andQuery.GetResult();
			userCounts.add(new UserCount(sdf.format(calendar1.getTime()), (int)DaoImpl.GetSelectCount(TableInfo.class, query)));
		}	
		return userCounts;
	}

}
