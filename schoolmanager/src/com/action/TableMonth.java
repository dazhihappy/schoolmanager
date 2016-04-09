package com.action;

import java.util.ArrayList;
import java.util.Calendar;

import bean.TableInfo;
import bean.UserCount;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

public class TableMonth implements ActiveDateInterface{

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
		TableInfo tableInfo1 = new TableInfo();
		TableInfo tableInfo2 = new TableInfo();
		for( int i =0; i <12 ; i++){
			calendar1.set(year, i, 1, 0, 0, 0);
			calendar2.set(year, i+1, 1, 0, 0, 0);				
			tableInfo1.setCreateTime(calendar1.getTime());
			tableInfo2 .setCreateTime(calendar2.getTime());
			CreateAndQuery andQuery=new CreateAndQuery();//�����ϲ�
			andQuery.Add(CreateQueryFromBean.GtObj(tableInfo1));
			andQuery.Add(CreateQueryFromBean.LtObj(tableInfo2));
			BasicDBObject query=andQuery.GetResult();
			userCounts.add(new UserCount((i+1)+"��", (int)DaoImpl.GetSelectCount(TableInfo.class, query)));
		}
		return userCounts;
	}
}
