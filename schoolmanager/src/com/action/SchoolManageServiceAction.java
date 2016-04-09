package com.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;

import bean.School;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.opensymphony.xwork2.ActionSupport;

public class SchoolManageServiceAction extends ActionSupport{
	private static final long serialVersionUID = -5200951786739846628L;
	
	private String schoolId;
	private Boolean state;
	
	public void changeState() throws Exception{
		/*===========≤‚ ‘=============*/
		//System.out.println(schoolId+" "+state);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		School school = new School();
		school.set_id(new ObjectId(schoolId));
		BasicDBObject query = CreateQueryFromBean.EqualObj(school);
		
		school.setState(state);
		DaoImpl.update(query, school, false);
		response.getWriter().print("true");
	}
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
}
