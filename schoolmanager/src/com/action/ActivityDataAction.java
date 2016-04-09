package com.action;

import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;

import bean.UserCount;

import com.opensymphony.xwork2.ActionSupport;

public class ActivityDataAction extends ActionSupport{
	private static final long serialVersionUID = 2267642429311408555L;
	private int page; //��һҳ����1;��һҳ����2;
	private String time;//ʱ��
	private String type;//����:�¡���month,�ա���day
	
	@Override
	public String execute() throws Exception {
		/*===========����============*/
		System.out.println("page:"+page);
		System.out.println("time:"+time);
		System.out.println("type:"+type);
		
		ArrayList<UserCount> userCounts = null;
		if("day".equals(type)){ //yyyy-MM-dd
			userCounts = new ActivityDay().getData(time, page);
		}else{
			userCounts = new ActivityMonth().getData(time, page);
		}		
		ServletActionContext.getRequest().setAttribute("activityCount", userCounts);
		return SUCCESS;
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
