package com.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import bean.UserCount;

import com.opensymphony.xwork2.ActionSupport;

public class TableDataAction extends ActionSupport{
	private static final long serialVersionUID = -2198965093564411962L;
	private int page; //上一页——1;下一页——2;
	private String time;//时间
	private String type;//类型:月——month,日——day
	
	public void getTableDataMethord() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		/*===========测试============*/
//		System.out.println("page:"+page);
//		System.out.println("time:"+time);
//		System.out.println("type:"+type);
		
		ArrayList<UserCount> userCounts = null;
		String data=""; //存储数据
		if("month".equals(getType())){//初始化时传明年年份
			userCounts = new TableMonth().getData(time,page);//传当年年份:yyyy
			for (int i = 0; i < userCounts.size(); i++) {
				data += userCounts.get(i).getCount()+",";
				//System.out.println(userCounts.get(i).getName()+" : "+userCounts.get(i).getCount());
			}
		}else{//初始化时传当天日期+1
			userCounts = new TableDay().getData(time,page);//传坐标轴最左边那天的日期:yyyy-MM-dd
			String names ="";
			String counts = "";
			for (int i = 0; i < userCounts.size(); i++) {
				names += userCounts.get(i).getName()+",";//横坐标
				counts += userCounts.get(i).getCount()+",";//纵坐标
				//System.out.println(userCounts.get(i).getName()+" : "+userCounts.get(i).getCount());			
			}
			data += names+"~"+counts;//~分割
		}	
		response.getWriter().print(data);
	}
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
