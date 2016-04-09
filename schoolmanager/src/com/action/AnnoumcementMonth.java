package com.action;

import java.util.ArrayList;
import java.util.Calendar;

import bean.SchoolNotice;
import bean.UserCount;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

public class AnnoumcementMonth implements ActiveDateInterface{

	public ArrayList<UserCount> getData(String time, int page) throws Exception {
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
		SchoolNotice SchoolNotice1 = new SchoolNotice();
		SchoolNotice SchoolNotice2 = new SchoolNotice();
		for( int i =0; i <12 ; i++){
			calendar1.set(year, i, 1, 0, 0, 0);
			calendar2.set(year, i+1, 1, 0, 0, 0);				
			SchoolNotice1.setReleaseTime(calendar1.getTime());
			SchoolNotice2 .setReleaseTime(calendar2.getTime());
			CreateAndQuery andQuery=new CreateAndQuery();//�����ϲ�
			andQuery.Add(CreateQueryFromBean.GtObj(SchoolNotice1));
			andQuery.Add(CreateQueryFromBean.LtObj(SchoolNotice2));
			BasicDBObject query=andQuery.GetResult();
			userCounts.add(new UserCount((i+1)+"��", (int)DaoImpl.GetSelectCount(SchoolNotice.class, query)));
		}
		return userCounts;
	}

}
