package com.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;

import bean.Manager;
import bean.Organization;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.opensymphony.xwork2.ActionSupport;

public class SchoolManagerChangeAction extends ActionSupport{
	private static final long serialVersionUID = 144325035446500778L;
	
	private String schoolId;
	private String userId;
	private String password;
	
	public void  changeManager()throws Exception {
		//System.out.println(schoolId+" "+userId+" "+password);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		Organization organization = new Organization();
		organization.setSchoolId(new ObjectId(schoolId));
		BasicDBObject query = CreateQueryFromBean.EqualObj(organization);
		
		ArrayList<Manager> managers = new ArrayList<Manager>();
		Manager manager = new Manager();
		manager.setUserId(userId);
		manager.setName("超级管理员");
		manager.setPwd(password);
		managers.add(manager);
		organization.setManager(managers);
		DaoImpl.update(query, organization, true, true); //更新有问题，待修改
		response.getWriter().print("true");
	}
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
