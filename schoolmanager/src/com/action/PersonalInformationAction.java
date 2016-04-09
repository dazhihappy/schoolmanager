package com.action;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;

import bean.SystemManager;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;


public class PersonalInformationAction {
	
	String name;
	String phone;
	String sex;
	String mail;
	String newpwd;
	String userId=(String)ServletActionContext.getContext().getSession().get("userId");
	//String userId="5653f75d9a72311ce86a2cbd";
	
	//修改个人信息
	public String modifyPerson() throws Exception
	{	
		SystemManager sManager=new SystemManager();
		sManager.setEmail(mail);
		sManager.setName(name);
		sManager.setPhone(phone);
		if(sex.trim().equals("男")){
			sManager.setSex(0);
		}
		else
			sManager.setSex(1);//女
		SystemManager manager=new SystemManager();
		manager.set_id(new ObjectId(userId));
		BasicDBObject query=CreateQueryFromBean.EqualObj(manager);
		DaoImpl.update(query, sManager, false);
		return "success";
	}
	
	//修改密码
	public String modifyPwd() throws Exception{
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		SystemManager sManager=new SystemManager();
		sManager.setPwd(newpwd);
		SystemManager manager=new SystemManager();
		manager.set_id(new ObjectId(userId));
		BasicDBObject query=CreateQueryFromBean.EqualObj(manager);
		DaoImpl.update(query, sManager, false);
		response.getWriter().print("修改成功!");
		return null;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getSex() {
		return sex;
	}
	public String getMail() {
		return mail;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getNewpwd() {
		return newpwd;
	}


	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	
}
