package com.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;

import bean.ActivityComment;
import bean.ActivityJudge;
import bean.ActivityShare;
import bean.InActivity;
import bean.TableInfo;

import com.dao.CreateAndQuery;
import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;

public class ActiveStudentAction {
	
	final private int dayScale=6;//每次显示天数-1
	private String lastDay;
	private  String firstDay;
	
	public String praiseAnalysis() throws Exception{
		
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);
		
		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);
	
		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		ActivityJudge judge1=new ActivityJudge();
		ActivityJudge judge2=new ActivityJudge();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				judge1.setOccurrenceTime(today0.getTime());
				judge2.setOccurrenceTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				judge1.setOccurrenceTime(today0.getTime());
				judge2.setOccurrenceTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(judge1));
			andQuery.Add(CreateQueryFromBean.LteObj(judge2));
			num=DaoImpl.GetSelectCount(ActivityJudge.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);
		return "success";
	}
	
	//点赞上一页
	public String praisePre() throws Exception{
		
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
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		ActivityJudge judge1=new ActivityJudge();
		ActivityJudge judge2=new ActivityJudge();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				judge1.setOccurrenceTime(today0.getTime());
				judge2.setOccurrenceTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				judge1.setOccurrenceTime(today0.getTime());
				judge2.setOccurrenceTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(judge1));
			andQuery.Add(CreateQueryFromBean.LteObj(judge2));
			num=DaoImpl.GetSelectCount(ActivityJudge.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
		}
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
	}

	//点赞下一页
	public String praiseNext() throws Exception{
		
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
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		ActivityJudge judge1=new ActivityJudge();
		ActivityJudge judge2=new ActivityJudge();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<=dayScale;i++){
			if(i==0){
				judge1.setOccurrenceTime(today0.getTime());
				judge2.setOccurrenceTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				judge1.setOccurrenceTime(today0.getTime());
				judge2.setOccurrenceTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(judge1));
			andQuery.Add(CreateQueryFromBean.LteObj(judge2));
			num=DaoImpl.GetSelectCount(ActivityJudge.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
		}
	
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
		
	}
	//评论
	public String commentAnalysis() throws Exception{
		
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);
	
		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		ActivityComment comment1=new ActivityComment();
		ActivityComment comment2=new ActivityComment();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				comment1.setOccurrenceTime(today0.getTime());
				comment2.setOccurrenceTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				comment1.setOccurrenceTime(today0.getTime());
				comment2.setOccurrenceTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(comment1));
			andQuery.Add(CreateQueryFromBean.LteObj(comment2));
			num=DaoImpl.GetSelectCount(ActivityComment.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}
		
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
	}
	
	//评论上一页
	public String commentPre() throws Exception{
		
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
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		ActivityComment comment1=new ActivityComment();
		ActivityComment comment2=new ActivityComment();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				comment1.setOccurrenceTime(today0.getTime());
				comment2.setOccurrenceTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				comment1.setOccurrenceTime(today0.getTime());
				comment2.setOccurrenceTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(comment1));
			andQuery.Add(CreateQueryFromBean.LteObj(comment2));
			num=DaoImpl.GetSelectCount(ActivityComment.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
	}

	//评论下一页
	public String commentNext() throws Exception{
		
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
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		ActivityComment comment1=new ActivityComment();
		ActivityComment comment2=new ActivityComment();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				comment1.setOccurrenceTime(today0.getTime());
				comment2.setOccurrenceTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				comment1.setOccurrenceTime(today0.getTime());
				comment2.setOccurrenceTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(comment1));
			andQuery.Add(CreateQueryFromBean.LteObj(comment2));
			num=DaoImpl.GetSelectCount(ActivityComment.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}
	
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
		
	}
	//报名情况
	public String signupAnalysis() throws Exception{
		
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);
	
		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		TableInfo table1=new TableInfo();
		TableInfo table2=new TableInfo();
		TableInfo table3=new TableInfo();
		table3.setType(0);
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				table1.setCreateTime(today0.getTime());
				table2.setCreateTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				table1.setCreateTime(today0.getTime());
				table2.setCreateTime(today23.getTime());
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(table1));
			andQuery.Add(CreateQueryFromBean.LteObj(table2));
			andQuery.Add(CreateQueryFromBean.EqualObj(table3));
			num=DaoImpl.GetSelectCount(TableInfo.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}
		
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
	}
	
	//报名上一页
	public String signupPre() throws Exception{
		
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
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		TableInfo table1=new TableInfo();
		TableInfo table2=new TableInfo();
		TableInfo table3=new TableInfo();
		table3.setType(0);
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				table1.setCreateTime(today0.getTime());
				table2.setCreateTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				table1.setCreateTime(today0.getTime());
				table2.setCreateTime(today23.getTime());
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(table1));
			andQuery.Add(CreateQueryFromBean.LteObj(table2));
			andQuery.Add(CreateQueryFromBean.EqualObj(table3));
			num=DaoImpl.GetSelectCount(TableInfo.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}

		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
	}

	//报名下一页
	public String signupNext() throws Exception{
		
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
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		TableInfo table1=new TableInfo();
		TableInfo table2=new TableInfo();
		TableInfo table3=new TableInfo();
		table3.setType(0);
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				table1.setCreateTime(today0.getTime());
				table2.setCreateTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				table1.setCreateTime(today0.getTime());
				table2.setCreateTime(today23.getTime());
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(table1));
			andQuery.Add(CreateQueryFromBean.LteObj(table2));
			andQuery.Add(CreateQueryFromBean.EqualObj(table3));
			num=DaoImpl.GetSelectCount(TableInfo.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}

		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
		
	}
	//分享分析
	
	public String shareAnalysis() throws Exception{
		
		Calendar today0=Calendar.getInstance();
		today0.set(Calendar.HOUR_OF_DAY, 0);
		today0.set(Calendar.SECOND, 0);
		today0.set(Calendar.MINUTE, 0);
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);
	
		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		ActivityShare share1=new ActivityShare();
		ActivityShare share2=new ActivityShare();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				share1.setOccurrenceTime(today0.getTime());
				share2.setOccurrenceTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				share1.setOccurrenceTime(today0.getTime());
				share2.setOccurrenceTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(share1));
			andQuery.Add(CreateQueryFromBean.LteObj(share2));
			num=DaoImpl.GetSelectCount(ActivityShare.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
	}
	
	//分享上一页
	public String sharePre() throws Exception{
		
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
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		ActivityShare share1=new ActivityShare();
		ActivityShare share2=new ActivityShare();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				share1.setOccurrenceTime(today0.getTime());
				share2.setOccurrenceTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				share1.setOccurrenceTime(today0.getTime());
				share2.setOccurrenceTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(share1));
			andQuery.Add(CreateQueryFromBean.LteObj(share2));
			num=DaoImpl.GetSelectCount(ActivityShare.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
	}

	//分享下一页
	public String shareNext() throws Exception{
		
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
		Calendar end=Calendar.getInstance();
		end.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),23, 59,59);

		Calendar today23=Calendar.getInstance();
		today23.set(today0.get(Calendar.YEAR),today0.get(Calendar.MONTH) , today0.get(Calendar.DATE));
		today23.set(Calendar.HOUR_OF_DAY, 23);
		today23.set(Calendar.SECOND, 59);
		today23.set(Calendar.MINUTE, 59);

		TreeMap<String, Long> increaseMap=new TreeMap<String, Long>();//默认key升序,按日学生增长
		ActivityShare share1=new ActivityShare();
		ActivityShare share2=new ActivityShare();
		Long num=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<=dayScale;i++){
			
			if(i==0){
				share1.setOccurrenceTime(today0.getTime());
				share2.setOccurrenceTime(today23.getTime());	
			}
			else{
				
				//把日期往前一天.整数往后推,负数往前移动
				today0.add(Calendar.DATE, -1);
				today23.add(Calendar.DATE, -1);
				share1.setOccurrenceTime(today0.getTime());
				share2.setOccurrenceTime(today23.getTime());	
			}
			CreateAndQuery andQuery=new CreateAndQuery();
			andQuery.Add(CreateQueryFromBean.GteObj(share1));
			andQuery.Add(CreateQueryFromBean.LteObj(share2));
			num=DaoImpl.GetSelectCount(ActivityShare.class, andQuery.GetResult());//每天增加的数目
			increaseMap.put(sdf.format(today0.getTime()), num);
			
		}
		
		ServletActionContext.getRequest().setAttribute("increaseMap", increaseMap);
		Calendar begin=Calendar.getInstance();
		begin.set(today0.get(Calendar.YEAR), today0.get(Calendar.MONTH), today0.get(Calendar.DAY_OF_MONTH),0,0,0);
		Long activityNum=inactivityNum(begin, end);
		ServletActionContext.getRequest().setAttribute("activityNum", activityNum);

		return "success";
		
	}	
	
	private Long inactivityNum(Calendar begin,Calendar end) throws Exception {
		InActivity in1=new InActivity();
		InActivity in2=new InActivity();
		in1.setReleaseTime(begin.getTime());
		in2.setReleaseTime(end.getTime());
		CreateAndQuery andQuery=new CreateAndQuery();
		andQuery.Add(CreateQueryFromBean.GteObj(in1));
		andQuery.Add(CreateQueryFromBean.LteObj(in2));
		
		return DaoImpl.GetSelectCount(InActivity.class, andQuery.GetResult());
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
	
	
	
}
