package com.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import bean.UserCount;

import com.opensymphony.xwork2.ActionSupport;

public class ActiveDataAction extends ActionSupport{
	private static final long serialVersionUID = 1363915928990854998L;
	private int page; //��һҳ����1;��һҳ����2;
	private String time;//ʱ��
	private String type;//����:ʱ����hour,�¡���month,�ա���day
	
	private ActiveDateInterface dateInterface;
	
	public void getActiveDataMethord() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		/*===========����============*/
//		System.out.println("page:"+page);
//		System.out.println("time:"+time);
//		System.out.println("type:"+type);
		
		ArrayList<UserCount> userCounts = null;
		String data=""; //�洢����
		if("hour".equals(getType())){//��ʼ��ʱ����������
			userCounts = new ActiveHour().getData(time,page);//����������:yyyy-MM-dd
			for (int i = 0; i < userCounts.size()-1; i++) {
				data += userCounts.get(i).getCount()+",";
				//System.out.println(userCounts.get(i).getName()+" : "+userCounts.get(i).getCount());
			}
			data += "~"+userCounts.get(userCounts.size()-1).getName();			
		}else if("month".equals(getType())){//��ʼ��ʱ���������
			userCounts = new ActiveMonth().getData(time,page);//���������:yyyy
			for (int i = 0; i < userCounts.size(); i++) {
				data += userCounts.get(i).getCount()+",";
				//System.out.println(userCounts.get(i).getName()+" : "+userCounts.get(i).getCount());
			}
		}else{//��ʼ��ʱ����������+1
			userCounts = new ActiveDay().getData(time,page);//����������������������:yyyy-MM-dd
			String names ="";
			String counts = "";
			for (int i = 0; i < userCounts.size(); i++) {
				names += userCounts.get(i).getName()+",";//������
				counts += userCounts.get(i).getCount()+",";//������
				//System.out.println(userCounts.get(i).getName()+" : "+userCounts.get(i).getCount());			
			}
			data += names+"~"+counts;//~�ָ�
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

	public ActiveDateInterface getDateInterface() {
		return dateInterface;
	}

	public void setDateInterface(ActiveDateInterface dateInterface) {
		this.dateInterface = dateInterface;
	}
}
