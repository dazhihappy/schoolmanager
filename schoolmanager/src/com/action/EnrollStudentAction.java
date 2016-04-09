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
	
	final private int dayScale=6;//每次显示天数
	final private int monthScale=5;//每次月数
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
		calendar.add(calendar.DATE,-30);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果
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
		Calendar today0=Calendar.getInstance();//月第一天
		today0.set(Calendar.DAY_OF_MONTH, today0.getActualMinimum(today0.DAY_OF_MONTH));
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar today23=Calendar.getInstance();//月最后一天
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
	
	//学生年级分布
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

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//默认key升序，按学校每日增长
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//记录每天学校的增长
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
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
				
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(studentInfo1));
			andQuery.Add(CreateQueryFromBean.LteObj(studentInfo2));
			num=DaoImpl.GetSelectCount(StudentInfo.class, andQuery.GetResult());//每天增加的数目
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
		System.out.println((d2.getTime()-d1.getTime())/1000.0+"s.第一个循环");
		int i=school.size()-1;//list必须倒序输出，因为increaseMap是自动升序排的 但是list是按加入的顺序输出的所以日期大小是倒序的
		
		Date d3=new Date();
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//学校增长情况的文本 XXX增加了n人
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
			System.out.println((d5.getTime()-d4.getTime())/1000.0+"s.每天每个学校增加学生数");
			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String) entry.getKey();
			    Integer val = (Integer) entry.getValue();
			    schoolState+=key+"增加了"+val+"人; ";
			}
			Date d6=new Date();
			System.out.println((d6.getTime()-d5.getTime())/1000.0+"s.创建说明每天学校学生增长情况的字符串");
			schoolMap.put(daykey, schoolState);
			i--;
		}
		Date d7=new Date();
		
		System.out.println((d7.getTime()-d3.getTime())/1000.0+"s.第二个大循环");
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
	//按日上一页
	public String studentByDayPre() throws Exception{
		
		String[] tempDay=firstDay.split("-");
		int year=Integer.parseInt(tempDay[0]);
		int month=Integer.parseInt(tempDay[1]);
		int day=Integer.parseInt(tempDay[2]);
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.YEAR, year);
		today0.set(Calendar.MONTH, month-1);//月从0开始
		today0.set(Calendar.DATE,day-1);//向前一天
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//默认key升序，按学校每日增长
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//记录每天学校的增长

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
				
				//把日期往前一天.整数往后推,负数往前移动
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
		
		System.out.println((d2.getTime()-d1.getTime())/1000.0+"s.第1个大循环");

		int i=school.size()-1;//list必须倒序输出，因为increaseMap是自动升序排的 但是list是按加入的顺序输出的所以日期大小是倒序的
		Date d3=new Date();
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//学校增长情况的文本 XXX增加了n人
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

			System.out.println((d5.getTime()-d4.getTime())/1000.0+"s.每天每个学校增长学生情况");

			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			Date d6=new Date();
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String) entry.getKey();
			    Integer val = (Integer) entry.getValue();
			    schoolState+=key+"增加了"+val+"人; ";
			}
			Date d7=new Date();
			System.out.println((d7.getTime()-d6.getTime())/1000.0+"s.各校每天学生增长情况字符串");
			schoolMap.put(daykey, schoolState);
			i--;
		}
		Date d8=new Date();
		
		System.out.println((d8.getTime()-d3.getTime())/1000.0+"s.第二个大循环");
		System.out.println((d8.getTime()-d1.getTime())/1000.0+"s.全部时间");
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}

	//按日下一页
	public String studentByDayNext() throws Exception{
		
		String[] tempDay=lastDay.split("-");
		int year=Integer.parseInt(tempDay[0]);
		int month=Integer.parseInt(tempDay[1]);
		int day=Integer.parseInt(tempDay[2]);
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.YEAR, year);
		today0.set(Calendar.MONTH, month-1);//月从0开始
		today0.set(Calendar.DATE,day+1+dayScale);//得到该页最后一天
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//默认key升序，按学校每日增长
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//记录每天学校的增长

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
				
				//把日期往前一天.整数往后推,负数往前移动
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
		
		int i=school.size()-1;//list必须倒序输出，因为increaseMap是自动升序排的 但是list是按加入的顺序输出的所以日期大小是倒序的
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//学校增长情况的文本 XXX增加了n人
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
			    schoolState+=key+"增加了"+val+"人; ";
			}
			schoolMap.put(daykey, schoolState);
			i--;
		}
		
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}
	
	//按月查询增加量
	public String studentByMonth()throws Exception{
		
		Calendar today0=Calendar.getInstance();//月第一天
		today0.set(Calendar.DAY_OF_MONTH, today0.getActualMinimum(today0.DAY_OF_MONTH));
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		
		Calendar today23=Calendar.getInstance();//月最后一天
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH),today0.getActualMaximum(today0.DAY_OF_MONTH));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//默认key升序，按学校每日增长
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//记录每天学校的增长
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
				
				//add把日期往前一月.整数往后推,负数往前移动
				today0.add(Calendar.MONTH, -1);
				today0.set(Calendar.DAY_OF_MONTH, 1);//每月第一天
				today23.add(Calendar.MONTH, -1);
				today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));//每月最后一天
				studentInfo1.setCreateTime(today0.getTime());
				studentInfo2.setCreateTime(today23.getTime());
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(studentInfo1));
			andQuery.Add(CreateQueryFromBean.LteObj(studentInfo2));
			num=DaoImpl.GetSelectCount(StudentInfo.class, andQuery.GetResult());//每月增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, andQuery.GetResult(), projection);
			ArrayList<Document> childList=new ArrayList<Document>();
			while(mc.hasNext()){
				
				childList.add(mc.next());
			}
			school.add(childList);
			schoolMap.put(sdf.format(today0.getTime()), "");
		}
		int i=school.size()-1;//list必须倒序输出，因为increaseMap是自动升序排的 但是list是按加入的顺序输出的所以日期大小是倒序的
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//学校增长情况的文本 XXX增加了n人
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
			    schoolState+=key+"增加了"+val+"人; ";
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
	//按月查询上一页
	public String studentByMonthPre() throws Exception{
		
		String[] tempDay=firstMonth.split("-");
		int year=Integer.parseInt(tempDay[0]);
		int month=Integer.parseInt(tempDay[1]);
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.YEAR, year);
		today0.set(Calendar.MONTH, month-1);//月从0开始
		today0.set(Calendar.DAY_OF_MONTH, 1);//第一天
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		today0.add(Calendar.MONTH, -1);//前一月
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.getActualMaximum(today0.DAY_OF_MONTH));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//默认key升序，按学校每日增长
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//记录每天学校的增长

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
				today0.set(Calendar.DAY_OF_MONTH, 1);//每月第一天
				today23.add(Calendar.MONTH, -1);
				today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));//每月最后一天
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
		
		int i=school.size()-1;//list必须倒序输出，因为increaseMap是自动升序排的 但是list是按加入的顺序输出的所以日期大小是倒序的
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//学校增长情况的文本 XXX增加了n人
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
			    schoolState+=key+"增加了"+val+"人; ";
			}
			schoolMap.put(daykey, schoolState);
			i--;
		}
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		ServletActionContext.getRequest().setAttribute("schoolMap", schoolMap);
		return "success";
	}
	
	//按月查询下一页
	public String studentByMonthNext() throws Exception{
		
		String[] tempDay=lastMonth.split("-");
		int year=Integer.parseInt(tempDay[0]);
		int month=Integer.parseInt(tempDay[1]);
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.YEAR, year);
		today0.set(Calendar.MONTH, month-1);//月从0开始
		today0.set(Calendar.DAY_OF_MONTH, 1);//第一天
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		today0.add(Calendar.MONTH, monthScale);//下一页最后一月
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.getActualMaximum(today0.DAY_OF_MONTH));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		TreeMap<String, String> schoolMap=new TreeMap<String, String>();//默认key升序，按学校每日增长
		ArrayList<ArrayList<Document>> school=new ArrayList<ArrayList<Document>>();//记录每天学校的增长

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
				today0.set(Calendar.DAY_OF_MONTH, 1);//每月第一天
				today23.add(Calendar.MONTH, -1);
				today23.set(Calendar.DAY_OF_MONTH, today23.getActualMaximum(today23.DAY_OF_MONTH));//每月最后一天
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
		
		int i=school.size()-1;//list必须倒序输出，因为increaseMap是自动升序排的 但是list是按加入的顺序输出的所以日期大小是倒序的
		for(String daykey :increaseMap.keySet()){
			
			ArrayList<Document> list=school.get(i);
			String schoolState="";//学校增长情况的文本 XXX增加了n人
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
			    schoolState+=key+"增加了"+val+"人; ";
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
