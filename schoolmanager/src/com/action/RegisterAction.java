package com.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.bson.types.ObjectId;

import bean.SystemManager;

import com.dao.DaoImpl;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("struts-default")
@Namespace("/login")
public class RegisterAction extends ActionSupport{
	private static final long serialVersionUID = -4750587017910410574L;
	private String userId;
	private String password;
	private String name;
	private String email;
	private int sex;
	public static final int STATE = 0; //¥˝…Û∫À
	
	@Action(value="register")
	public void registerMethord() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		/*============≤‚ ‘==============*/
		System.out.println(userId+""+password+" "+name+" "+email+" "+sex);
		
		SystemManager systemManager = new SystemManager();
		systemManager.set_id(new ObjectId());
		systemManager.setPhone(userId);
		systemManager.setPwd(password);
		systemManager.setName(name);
		systemManager.setEmail(email);
		systemManager.setSex(sex);
		systemManager.setState(STATE);
		DaoImpl.InsertWholeBean(systemManager);
		
		response.getWriter().print("true");
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
}
