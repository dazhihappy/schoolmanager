package com.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import bean.UserCount;
import bean.UserSkimTime;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

public class ActiveHour implements ActiveDateInterface {
	/*
	 * ��ȡһ�������:yyyy-MM-dd
	 */
	public ArrayList<UserCount> getData(String time ,int page) throws Exception{
		ArrayList<UserCount> userCounts = new ArrayList<UserCount>();
		String [] times = time.split("-");
		int year = Integer.parseInt(times[0]);
		int month = Integer.parseInt(times[1])-1;
		int day = Integer.parseInt(times[2]);
		if(page==1){	//��һҳ
			System.out.println("��ȡǰ1���Ծ��");
			day =day-1;
		}else{	//��һҳ
			System.out.println("��ȡ��1���Ծ��");
			day =day+1;
		}
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		UserSkimTime userSkimTime1 = new UserSkimTime();
		UserSkimTime userSkimTime2 = new UserSkimTime();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < 24; i++) {
			calendar1.set(year, month, day, i, 0, 0);
			calendar2.set(year, month, day, i+1, 0, 0);		
			userSkimTime1.setLoginTime(calendar1.getTime());
			userSkimTime2 .setExitTime(calendar2.getTime());
			CreateAndQuery andQuery=new CreateAndQuery();//�����ϲ�
			andQuery.Add(CreateQueryFromBean.GtObj(userSkimTime1));
			andQuery.Add(CreateQueryFromBean.LtObj(userSkimTime2));
			BasicDBObject query=andQuery.GetResult();
			userCounts.add(new UserCount(i+"ʱ", (int)DaoImpl.GetSelectCount(UserSkimTime.class, query)));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayTime = sdf.format(calendar1.getTime());
		//System.out.println("todayTime:"+todayTime);
		userCounts.add(new UserCount(todayTime, 0)); //������ڴ洢����
		return userCounts;
	}
	
	public static void main(String[] args) throws Exception {
		ArrayList<UserCount> userCounts= null;
		ActiveHour activeMonth = new ActiveHour();
		userCounts = activeMonth.getData("2015-3-31", 2);
		System.out.println(userCounts.size());
		for (int i = 0; i < userCounts.size(); i++){
			System.out.println(userCounts.get(i).getName()+":"+userCounts.get(i).getCount());
		}
	}
}
