package com.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import bean.UserCount;

import com.opensymphony.xwork2.ActionSupport;

public class TableDataAction extends ActionSupport{
	private static final long serialVersionUID = -2198965093564411962L;
	private int page; //��һҳ����1;��һҳ����2;
	private String time;//ʱ��
	private String type;//����:�¡���month,�ա���day
	
	public void getTableDataMethord() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		/*===========����============*/
//		System.out.println("page:"+page);
//		System.out.println("time:"+time);
//		System.out.println("type:"+type);
		
		ArrayList<UserCount> userCounts = null;
		String data=""; //�洢����
		if("month".equals(getType())){//��ʼ��ʱ���������
			userCounts = new TableMonth().getData(time,page);//���������:yyyy
			for (int i = 0; i < userCounts.size(); i++) {
				data += userCounts.get(i).getCount()+",";
				//System.out.println(userCounts.get(i).getName()+" : "+userCounts.get(i).getCount());
			}
		}else{//��ʼ��ʱ����������+1
			userCounts = new TableDay().getData(time,page);//����������������������:yyyy-MM-dd
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
}
