package com.action;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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

public class EnrollStudentAction {
	
	final private int dayScale=6;//ÿ����ʾ����
	final private int monthScale=5;//ÿ������
	private String lastDay;
	private  String firstDay;
	private String lastMonth;
	private  String firstMonth;
	
	public static void main(String[] args) {
/*		Date date=new Date();//yyyy-MM-dd HH:mm:ss
		String today=new SimpleDateFormat("yyyy-MM-dd").format(date);
		System.out.println(date);
		System.out.println(today);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		String today0=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		System.out.println(today0);
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		String today23=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		System.out.println(today23);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-30);//��������������һ��.����������,������ǰ�ƶ�
		date=calendar.getTime(); //���ʱ���������������һ��Ľ��
		//String putDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		System.out.println(date);
		Date today0=new Date();
		today0.setHours(0);
		today0.setMinutes(0);
		today0.setSeconds(0);
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//String str=sdf.format(today0);
		Date today23=new Date(today0.getTime());
		System.out.println(today23);*/
		/*Calendar today0=Calendar.getInstance();
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);
		today0.add(Calendar.DATE, -30);Date date=today0.getTime();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));*/
		Calendar today0=Calendar.getInstance();//�µ�һ��
		today0.set(Calendar.DAY_OF_MONTH, today0.getActualMinimum(today0.DAY_OF_MONTH));
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar today23=Calendar.getInstance();//�����һ��
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH),today0.getActualMaximum(today0.DAY_OF_MONTH));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);
		System.out.println(sdf.format(today0.getTime())+"  "+sdf.format(today23.getTime()));
		
		for(int i=1;i<13;i++){
			
			today0.add(Calendar.MONTH,-1);
			today0.set(Calendar.DAY_OF_MONTH, 1);
			today23.add(Calendar.MONTH, -1);
			today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));
			System.out.println(sdf.format(today0.getTime())+"  "+sdf.format(today23.getTime()));
		}
		
	
	}
	
	//ѧ���꼶�ֲ�
 	public String gradeAnalysis() throws Exception{
		
		ArrayList<Document> list=new ArrayList<Document>();
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.StudentInfo_Grade, 1);
		MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, new BasicDBObject(), projection);
		while(mc.hasNext()){
			list.add(mc.next());
		}	
		HashMap<Integer, Long> gradeMap=new HashMap<Integer, Long>();
		for(int i=0;i<list.size();i++){
			Integer key=(Integer) list.get(i).get(StaticString.StudentInfo_Grade);
			if(gradeMap.containsKey(key)){
				gradeMap.put(key, gradeMap.get(key)+1);				
			}
			else{
				gradeMap.put(key, (long) 1);
			}
		}
		ServletActionContext.getRequest().setAttribute("gradeMap", gradeMap);
		return "success";
	}
	
	public String studentByDay() throws Exception{
		
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����,����ѧ������
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//��¼ÿ��ѧУ������
		StudentInfo studentInfo1=new StudentInfo();
		StudentInfo studentInfo2=new StudentInfo();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.StudentInfo_SchoolId, 1);
		Date d1=new Date();
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
				
			}
			else{
				
				//��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
				
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(studentInfo1));
			andQuery.Add(CreateQueryFromBean.LteObj(studentInfo2));
			num=DaoImpl.GetSelectCount(StudentInfo.class, andQuery.GetResult());//ÿ�����ӵ���Ŀ
			increaseMap.put(sdf.format(today0.getTime()), num);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, andQuery.GetResult(), projection);
			ArrayList<Document> childList=new ArrayList<Document>();
			while(mc.hasNext()){
				
				childList.add(mc.next());
			}
			school.add(childList);
			schoolMap.put(sdf.format(today0.getTime()), "");
		}
		Date d2=new Date();
		System.out.println((d2.getTime()-d1.getTime())/1000.0+"s.��һ��ѭ��");
		int i=school.size()-1;//list���뵹���������ΪincreaseMap���Զ������ŵ� ����list�ǰ������˳��������������ڴ�С�ǵ����
		
		Date d3=new Date();
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//ѧУ����������ı� XXX������n��
			HashMap<String,Integer> map=new HashMap<String, Integer>();
			Date d4=new Date();
			for(int j=0;j<list.size();j++){
				School s=new School();
				s.set_id(list.get(j).getObjectId(StaticString.StudentInfo_SchoolId));
				BasicDBObject pro=new BasicDBObject();
				pro.put(StaticString.School_Name, 1);
				MongoCursor<Document>mc=DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.EqualObj(s), 1, pro);
				Document d=mc.next();
				String namekey=(String) d.get(StaticString.School_Name);
				if(map.containsKey(namekey)){
					map.put(namekey, map.get(namekey)+1);
				}
				else{
					map.put(namekey, 1);		
				}
			}
			Date d5=new Date();
			System.out.println((d5.getTime()-d4.getTime())/1000.0+"s.ÿ��ÿ��ѧУ����ѧ����");
			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String) entry.getKey();
			    Integer val = (Integer) entry.getValue();
			    schoolState+=key+"������"+val+"��; ";
			}
			Date d6=new Date();
			System.out.println((d6.getTime()-d5.getTime())/1000.0+"s.����˵��ÿ��ѧУѧ������������ַ���");
			schoolMap.put(daykey, schoolState);
			i--;
		}
		Date d7=new Date();
		
		System.out.println((d7.getTime()-d3.getTime())/1000.0+"s.�ڶ�����ѭ��");
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
	public String studentByDayPre() throws Exception{
		
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

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����,����ѧ������
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//��¼ÿ��ѧУ������

		StudentInfo studentInfo1=new StudentInfo();
		StudentInfo studentInfo2=new StudentInfo();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.StudentInfo_SchoolId, 1);
		Date d1=new Date();
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
				
			}
			else{
				
				//��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
				
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(studentInfo1));
			andQuery.Add(CreateQueryFromBean.LteObj(studentInfo2));
			num=DaoImpl.GetSelectCount(StudentInfo.class, andQuery.GetResult());
			increaseMap.put(sdf.format(today0.getTime()), num);
			
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, andQuery.GetResult(), projection);
			ArrayList<Document> childList=new ArrayList<Document>();
			while(mc.hasNext()){
				
				childList.add(mc.next());
			}
			school.add(childList);
			schoolMap.put(sdf.format(today0.getTime()), "");
		}
		Date d2=new Date();
		
		System.out.println((d2.getTime()-d1.getTime())/1000.0+"s.��1����ѭ��");

		int i=school.size()-1;//list���뵹���������ΪincreaseMap���Զ������ŵ� ����list�ǰ������˳��������������ڴ�С�ǵ����
		Date d3=new Date();
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//ѧУ����������ı� XXX������n��
			HashMap<String,Integer> map=new HashMap<String, Integer>();
			Date d4=new Date();
			for(int j=0;j<list.size();j++){
				School s=new School();
				s.set_id(list.get(j).getObjectId(StaticString.StudentInfo_SchoolId));
				BasicDBObject pro=new BasicDBObject();
				pro.put(StaticString.School_Name, 1);
				MongoCursor<Document>mc=DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.EqualObj(s), 1, pro);
				Document d=mc.next();
				String namekey=(String) d.get(StaticString.School_Name);
				if(map.containsKey(namekey)){
					map.put(namekey, map.get(namekey)+1);					
				}
				else{
					map.put(namekey, 1);
				}
			}
			Date d5=new Date();

			System.out.println((d5.getTime()-d4.getTime())/1000.0+"s.ÿ��ÿ��ѧУ����ѧ�����");

			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			Date d6=new Date();
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String) entry.getKey();
			    Integer val = (Integer) entry.getValue();
			    schoolState+=key+"������"+val+"��; ";
			}
			Date d7=new Date();
			System.out.println((d7.getTime()-d6.getTime())/1000.0+"s.��Уÿ��ѧ����������ַ���");
			schoolMap.put(daykey, schoolState);
			i--;
		}
		Date d8=new Date();
		
		System.out.println((d8.getTime()-d3.getTime())/1000.0+"s.�ڶ�����ѭ��");
		System.out.println((d8.getTime()-d1.getTime())/1000.0+"s.ȫ��ʱ��");
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}

	//������һҳ
	public String studentByDayNext() throws Exception{
		
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

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����,����ѧ������
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//��¼ÿ��ѧУ������

		StudentInfo studentInfo1=new StudentInfo();
		StudentInfo studentInfo2=new StudentInfo();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.StudentInfo_SchoolId, 1);
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
				
			}
			else{
				
				//��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
				
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(studentInfo1));
			andQuery.Add(CreateQueryFromBean.LteObj(studentInfo2));
			num=DaoImpl.GetSelectCount(StudentInfo.class, andQuery.GetResult());
			increaseMap.put(sdf.format(today0.getTime()), num);
			
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, andQuery.GetResult(), projection);
			ArrayList<Document> childList=new ArrayList<Document>();
			while(mc.hasNext()){
				
				childList.add(mc.next());
			}
			school.add(childList);
			schoolMap.put(sdf.format(today0.getTime()), "");
		}
		
		int i=school.size()-1;//list���뵹���������ΪincreaseMap���Զ������ŵ� ����list�ǰ������˳��������������ڴ�С�ǵ����
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//ѧУ����������ı� XXX������n��
			HashMap<String,Integer> map=new HashMap<String, Integer>();
			for(int j=0;j<list.size();j++){
				School s=new School();
				s.set_id(list.get(j).getObjectId(StaticString.StudentInfo_SchoolId));
				BasicDBObject pro=new BasicDBObject();
				pro.put(StaticString.School_Name, 1);
				MongoCursor<Document>mc=DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.EqualObj(s), 1, pro);
				Document d=mc.next();
				String namekey=(String) d.get(StaticString.School_Name);
				if(map.containsKey(namekey)){
					map.put(namekey, map.get(namekey)+1);	
				}
				else{
					map.put(namekey, 1);
				}
			}
			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String) entry.getKey();
			    Integer val = (Integer) entry.getValue();
			    schoolState+=key+"������"+val+"��; ";
			}
			schoolMap.put(daykey, schoolState);
			i--;
		}
		
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}
	
	//���²�ѯ������
	public String studentByMonth()throws Exception{
		
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

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����,����ѧ������
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//��¼ÿ��ѧУ������
		StudentInfo studentInfo1=new StudentInfo();
		StudentInfo studentInfo2=new StudentInfo();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.StudentInfo_SchoolId, 1);
		for(int i=0;i<=monthScale;i++){
			
			if(i==0){
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
			}
			else{
				
				//add��������ǰһ��.����������,������ǰ�ƶ�
				today0.add(Calendar.MONTH, -1);
				today0.set(Calendar.DAY_OF_MONTH, 1);//ÿ�µ�һ��
				today23.add(Calendar.MONTH, -1);
				today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));//ÿ�����һ��
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(studentInfo1));
			andQuery.Add(CreateQueryFromBean.LteObj(studentInfo2));
			num=DaoImpl.GetSelectCount(StudentInfo.class, andQuery.GetResult());//ÿ�����ӵ���Ŀ
			increaseMap.put(sdf.format(today0.getTime()), num);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, andQuery.GetResult(), projection);
			ArrayList<Document> childList=new ArrayList<Document>();
			while(mc.hasNext()){
				
				childList.add(mc.next());
			}
			school.add(childList);
			schoolMap.put(sdf.format(today0.getTime()), "");
		}
		int i=school.size()-1;//list���뵹���������ΪincreaseMap���Զ������ŵ� ����list�ǰ������˳��������������ڴ�С�ǵ����
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//ѧУ����������ı� XXX������n��
			HashMap<String,Integer> map=new HashMap<String, Integer>();
			for(int j=0;j<list.size();j++){
				School s=new School();
				s.set_id(list.get(j).getObjectId(StaticString.StudentInfo_SchoolId));
				BasicDBObject pro=new BasicDBObject();
				pro.put(StaticString.School_Name, 1);
				MongoCursor<Document>mc=DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.EqualObj(s), 1, pro);
				Document d=mc.next();
				String namekey=(String) d.get(StaticString.School_Name);
				if(map.containsKey(namekey)){
					map.put(namekey, map.get(namekey)+1);
				}
				else{
					map.put(namekey, 1);		
				}
			}
			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String) entry.getKey();
			    Integer val = (Integer) entry.getValue();
			    schoolState+=key+"������"+val+"��; ";
			}
			schoolMap.put(daykey, schoolState);
			i--;
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
	//���²�ѯ��һҳ
	public String studentByMonthPre() throws Exception{
		
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

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����,����ѧ������
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//��¼ÿ��ѧУ������

		StudentInfo studentInfo1=new StudentInfo();
		StudentInfo studentInfo2=new StudentInfo();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.StudentInfo_SchoolId, 1);
		for(int i=0;i<=monthScale;i++){
			
			if(i==0){
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
			}
			else{
				
				today0.add(Calendar.MONTH, -1);
				today0.set(Calendar.DAY_OF_MONTH, 1);//ÿ�µ�һ��
				today23.add(Calendar.MONTH, -1);
				today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));//ÿ�����һ��
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(studentInfo1));
			andQuery.Add(CreateQueryFromBean.LteObj(studentInfo2));
			num=DaoImpl.GetSelectCount(StudentInfo.class, andQuery.GetResult());
			increaseMap.put(sdf.format(today0.getTime()), num);
			
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, andQuery.GetResult(), projection);
			ArrayList<Document> childList=new ArrayList<Document>();
			while(mc.hasNext()){
				
				childList.add(mc.next());
			}
			school.add(childList);
			schoolMap.put(sdf.format(today0.getTime()), "");
		}
		
		int i=school.size()-1;//list���뵹���������ΪincreaseMap���Զ������ŵ� ����list�ǰ������˳��������������ڴ�С�ǵ����
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//ѧУ����������ı� XXX������n��
			HashMap<String,Integer> map=new HashMap<String, Integer>();
			for(int j=0;j<list.size();j++){
				School s=new School();
				s.set_id(list.get(j).getObjectId(StaticString.StudentInfo_SchoolId));
				BasicDBObject pro=new BasicDBObject();
				pro.put(StaticString.School_Name, 1);
				MongoCursor<Document>mc=DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.EqualObj(s), 1, pro);
				Document d=mc.next();
				String namekey=(String) d.get(StaticString.School_Name);
				if(map.containsKey(namekey)){
					map.put(namekey, map.get(namekey)+1);					
				}
				else{
					map.put(namekey, 1);
				}
			}
			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String) entry.getKey();
			    Integer val = (Integer) entry.getValue();
			    schoolState+=key+"������"+val+"��; ";
			}
			schoolMap.put(daykey, schoolState);
			i--;
		}
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}
	
	//���²�ѯ��һҳ
	public String studentByMonthNext() throws Exception{
		
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

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//Ĭ��key����,����ѧ������
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//Ĭ��key���򣬰�ѧУÿ������
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//��¼ÿ��ѧУ������

		StudentInfo studentInfo1=new StudentInfo();
		StudentInfo studentInfo2=new StudentInfo();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.StudentInfo_SchoolId, 1);
		for(int i=0;i<=monthScale;i++){
			
			if(i==0){
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
			}
			else{
				
				today0.add(Calendar.MONTH, -1);
				today0.set(Calendar.DAY_OF_MONTH, 1);//ÿ�µ�һ��
				today23.add(Calendar.MONTH, -1);
				today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));//ÿ�����һ��
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
				
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(studentInfo1));
			andQuery.Add(CreateQueryFromBean.LteObj(studentInfo2));
			num=DaoImpl.GetSelectCount(StudentInfo.class, andQuery.GetResult());
			increaseMap.put(sdf.format(today0.getTime()), num);
			
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, andQuery.GetResult(), projection);
			ArrayList<Document> childList=new ArrayList<Document>();
			while(mc.hasNext()){
				
				childList.add(mc.next());
			}
			school.add(childList);
			schoolMap.put(sdf.format(today0.getTime()), "");
		}
		
		int i=school.size()-1;//list���뵹���������ΪincreaseMap���Զ������ŵ� ����list�ǰ������˳��������������ڴ�С�ǵ����
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//ѧУ����������ı� XXX������n��
			HashMap<String,Integer> map=new HashMap<String, Integer>();
			for(int j=0;j<list.size();j++){
				School s=new School();
				s.set_id(list.get(j).getObjectId(StaticString.StudentInfo_SchoolId));
				BasicDBObject pro=new BasicDBObject();
				pro.put(StaticString.School_Name, 1);
				MongoCursor<Document>mc=DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.EqualObj(s), 1, pro);
				Document d=mc.next();
				String namekey=(String) d.get(StaticString.School_Name);
				if(map.containsKey(namekey)){
					map.put(namekey, map.get(namekey)+1);					
				}
				else{
					map.put(namekey, 1);
				}
			}
			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String) entry.getKey();
			    Integer val = (Integer) entry.getValue();
			    schoolState+=key+"������"+val+"��; ";
			}
			schoolMap.put(daykey, schoolState);
			i--;
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
