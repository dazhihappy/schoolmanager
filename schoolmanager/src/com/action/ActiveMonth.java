package com.action;

import java.util.ArrayList;
import java.util.Calendar;

import bean.UserCount;
import bean.UserSkimTime;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

public class ActiveMonth implements ActiveDateInterface{
	/*
	 * ��ȡһ�������:yyyy
	 */
	public ArrayList<UserCount> getData(String time ,int page) throws Exception {
		ArrayList<UserCount> userCounts = new ArrayList<UserCount>();
		int year = 0;//��ǰ���
		if(page==1){ //��һҳ
			System.out.println("��ȡ��һ���Ծ��");
			year = Integer.parseInt(time)-1;
		}else{	//��һҳ
			System.out.println("��ȡ��һ���Ծ��");
			year = Integer.parseInt(time)+1;
		}
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		UserSkimTime userSkimTime1 = new UserSkimTime();
		UserSkimTime userSkimTime2 = new UserSkimTime();
		for( int i =0; i <12 ; i++){
			calendar1.set(year, i, 1, 0, 0, 0);
			calendar2.set(year, i+1, 1, 0, 0, 0);				
			userSkimTime1.setLoginTime(calendar1.getTime());
			userSkimTime2 .setExitTime(calendar2.getTime());
			CreateAndQuery andQuery=new CreateAndQuery();//�����ϲ�
			andQuery.Add(CreateQueryFromBean.GtObj(userSkimTime1));
			andQuery.Add(CreateQueryFromBean.LtObj(userSkimTime2));
			BasicDBObject query=andQuery.GetResult();
			userCounts.add(new UserCount((i+1)+"��", (int)DaoImpl.GetSelectCount(UserSkimTime.class, query)));
		}
		return userCounts;
	}
	
	public static void main(String[] args) throws Exception {
		ArrayList<UserCount> userCounts= null;
		ActiveMonth activeMonth = new ActiveMonth();
		userCounts = activeMonth.getData("2016", 1);
		System.out.println(userCounts.size());
		for (int i = 0; i < userCounts.size(); i++) {
			System.out.println(userCounts.get(i).getName()+":"+userCounts.get(i).getCount());
		}
	}

}
