package com.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.apache.struts2.ServletActionContext;
import org.bson.Document;

import staticData.StaticString;
import bean.School;
import bean.StudentInfo;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class OpenSchoolAction {
	
	final private int dayScale=6;//ÿ����ʾ����-1
	final private int monthScale=5;//ÿ��12�� 
	private String lastDay;
	private  String firstDay;
	private String lastMonth;
	private  String firstMonth;
	
	public String schoolByDay() throws Exception{
		
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		
		School school1=new School();
		School school2=new School();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.School_Name, 1);
		for(int i=0;i<=dayScale;i++){
			String schoolState="";
			if(i==0){
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());	
			}
			else{
				
				//��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(school1));
			andQuery.Add(CreateQueryFromBean.LteObj(school2));
			num=DaoImpl.GetSelectCount(School.class, andQuery.GetResult());//ÿ�����ӵ���Ŀ
			increaseMap.put(sdf.format(today0.getTime()), num);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(School.class, andQuery.GetResult(), projection);
			while(mc.hasNext()){
				
				schoolState+=mc.next().get(StaticString.School_Name)+"��";
			}
			schoolMap.put(sdf.format(today0.getTime()), schoolState);
		}
		
//		Iterator<Entry<String, String>> iter = schoolMap.entrySet().iterator();
//		while (iter.hasNext()) {
//		    Map.Entry entry = (Map.Entry) iter.next();
//		    Object key = entry.getKey();
//		    Object val = entry.getValue();
//		    System.out.println(""+key+" "+val);
//		}
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}
	//������һҳ
	public String schoolByDayPre() throws Exception{
		
		String[] tempDay=firstDay.split("-");
		int year=Integer.parseInt(tempDay[0]);
		int month=Integer.parseInt(tempDay[1]);
		int day=Integer.parseInt(tempDay[2]);
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.YEAR, year);
		today0.set(Calendar.MONTH, month-1);//�´�0��ʼ
		today0.set(Calendar.DATE,day-1);//��ǰһ��
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		
		School school1=new School();
		School school2=new School();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.School_Name, 1);
		for(int i=0;i<=dayScale;i++){
			String schoolState="";
			if(i==0){
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());	
			}
			else{
				
				//��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(school1));
			andQuery.Add(CreateQueryFromBean.LteObj(school2));
			num=DaoImpl.GetSelectCount(School.class, andQuery.GetResult());//ÿ�����ӵ���Ŀ
			increaseMap.put(sdf.format(today0.getTime()), num);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(School.class, andQuery.GetResult(), projection);
			while(mc.hasNext()){
				
				schoolState+=mc.next().get(StaticString.School_Name)+"��";
			}
			schoolMap.put(sdf.format(today0.getTime()), schoolState);
		}
		
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
		
	}

	//������һҳ
	public String schoolByDayNext() throws Exception{
		
		String[] tempDay=lastDay.split("-");
		int year=Integer.parseInt(tempDay[0]);
		int month=Integer.parseInt(tempDay[1]);
		int day=Integer.parseInt(tempDay[2]);
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.YEAR, year);
		today0.set(Calendar.MONTH, month-1);//�´�0��ʼ
		today0.set(Calendar.DATE,day+1+dayScale);//�õ���ҳ���һ��
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		
		School school1=new School();
		School school2=new School();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.School_Name, 1);
		for(int i=0;i<=dayScale;i++){
			String schoolState="";
			if(i==0){
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());	
			}
			else{
				
				//��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(school1));
			andQuery.Add(CreateQueryFromBean.LteObj(school2));
			num=DaoImpl.GetSelectCount(School.class, andQuery.GetResult());//ÿ�����ӵ���Ŀ
			increaseMap.put(sdf.format(today0.getTime()), num);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(School.class, andQuery.GetResult(), projection);
			while(mc.hasNext()){
				
				schoolState+=mc.next().get(StaticString.School_Name)+"��";
			}
			schoolMap.put(sdf.format(today0.getTime()), schoolState);
		}
				
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}
	
	//���²�ѯ������
	public String schoolByMonth()throws Exception{
		
		Calendar today0=Calendar.getInstance();//�µ�һ��
		today0.set(Calendar.DAY_OF_MONTH, today0.getActualMinimum(today0.DAY_OF_MONTH));
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		
		Calendar today23=Calendar.getInstance();//�����һ��
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH),today0.getActualMaximum(today0.DAY_OF_MONTH));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		
		School school1=new School();
		School school2=new School();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.School_Name, 1);
		for(int i=0;i<=monthScale;i++){
			String schoolState="";
			if(i==0){
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());	
			}
			else{
				
				//add��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.MONTH, -1);
				today0.set(Calendar.DAY_OF_MONTH, 1);//ÿ�µ�һ��
				today23.add(Calendar.MONTH, -1);
				today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));//ÿ�����һ��
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(school1));
			andQuery.Add(CreateQueryFromBean.LteObj(school2));
			num=DaoImpl.GetSelectCount(School.class, andQuery.GetResult());//ÿ�����ӵ���Ŀ
			increaseMap.put(sdf.format(today0.getTime()), num);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(School.class, andQuery.GetResult(), projection);
			while(mc.hasNext()){
				
				schoolState+=mc.next().get(StaticString.School_Name)+"��";
			}
			schoolMap.put(sdf.format(today0.getTime()), schoolState);
		}
				
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}
	//���²�ѯ��һҳ
	public String schoolByMonthPre() throws Exception{
		
		String[] tempDay=firstMonth.split("-");
		int year=Integer.parseInt(tempDay[0]);
		int month=Integer.parseInt(tempDay[1]);
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.YEAR, year);
		today0.set(Calendar.MONTH, month-1);//�´�0��ʼ
		today0.set(Calendar.DAY_OF_MONTH, 1);//��һ��
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		today0.add(Calendar.MONTH, -1);//ǰһ��
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.getActualMaximum(today0.DAY_OF_MONTH));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		
		School school1=new School();
		School school2=new School();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.School_Name, 1);
		for(int i=0;i<=monthScale;i++){
			String schoolState="";
			if(i==0){
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());	
			}
			else{
				
				//add��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.MONTH, -1);
				today0.set(Calendar.DAY_OF_MONTH, 1);//ÿ�µ�һ��
				today23.add(Calendar.MONTH, -1);
				today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));//ÿ�����һ��
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(school1));
			andQuery.Add(CreateQueryFromBean.LteObj(school2));
			num=DaoImpl.GetSelectCount(School.class, andQuery.GetResult());//ÿ�����ӵ���Ŀ
			increaseMap.put(sdf.format(today0.getTime()), num);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(School.class, andQuery.GetResult(), projection);
			while(mc.hasNext()){
				
				schoolState+=mc.next().get(StaticString.School_Name)+"��";
			}
			schoolMap.put(sdf.format(today0.getTime()), schoolState);
		}
		Iterator<Entry<String, String>> iter = schoolMap.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    Object key = entry.getKey();
		    Object val = entry.getValue();
		    System.out.println(""+key+" "+val);
		}
	
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}
	
	//���²�ѯ��һҳ
	public String schoolByMonthNext() throws Exception{
		
		String[] tempDay=lastMonth.split("-");
		int year=Integer.parseInt(tempDay[0]);
		int month=Integer.parseInt(tempDay[1]);
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.YEAR, year);
		today0.set(Calendar.MONTH, month-1);//�´�0��ʼ
		today0.set(Calendar.DAY_OF_MONTH, 1);//��һ��
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		today0.add(Calendar.MONTH, monthScale);//��һҳ���һ��
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.getActualMaximum(today0.DAY_OF_MONTH));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		
		School school1=new School();
		School school2=new School();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.School_Name, 1);
		for(int i=0;i<=monthScale;i++){
			String schoolState="";
			if(i==0){
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());	
			}
			else{
				
				//add��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.MONTH, -1);
				today0.set(Calendar.DAY_OF_MONTH, 1);//ÿ�µ�һ��
				today23.add(Calendar.MONTH, -1);
				today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));//ÿ�����һ��
				school1.setOpenTime(today0.getTime());
				school2.setOpenTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(school1));
			andQuery.Add(CreateQueryFromBean.LteObj(school2));
			num=DaoImpl.GetSelectCount(School.class, andQuery.GetResult());//ÿ�����ӵ���Ŀ
			increaseMap.put(sdf.format(today0.getTime()), num);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(School.class, andQuery.GetResult(), projection);
			while(mc.hasNext()){
				
				schoolState+=mc.next().get(StaticString.School_Name)+"��";
			}
			schoolMap.put(sdf.format(today0.getTime()), schoolState);
		}
				
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}
	public String getLastDay() {
		return lastDay;
	}

	public String getFirstDay() {
		return firstDay;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay;
	}

	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
	}

	public String getLastMonth() {
		return lastMonth;
	}

	public String getFirstMonth() {
		return firstMonth;
	}

	public void setLastMonth(String lastMonth) {
		this.lastMonth = lastMonth;
	}

	public void setFirstMonth(String firstMonth) {
		this.firstMonth = firstMonth;
	}
	
	
}
