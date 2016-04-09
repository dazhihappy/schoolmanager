package com.action;


import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import bean.SystemManager;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.opensymphony.xwork2.ActionSupport;

public class SysMangerListAciton extends ActionSupport {
	private static final long serialVersionUID = 1202949058460876691L;
	private String delPhone;
	private String actPhone;
	
	
	//删除管理员
	public String del()throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		String usrPhone = (String)ServletActionContext.getContext().getSession().get("phone");
		//如果是bossPhone
		if(usrPhone.equals("13898624331")) {
			String[]phonesNumber = delPhone.split(",");
			System.out.println(delPhone);
			SystemManager systemManager = new SystemManager();
			BasicDBObject query = new BasicDBObject();
			for(int i = 0; i < phonesNumber.length; i++) {
				systemManager.setPhone(phonesNumber[i]);
				query = CreateQueryFromBean.EqualObj(systemManager);
				DaoImpl.DeleteDocment(SystemManager.class, query);
			}
			response.getWriter().print("success");
		} else {
			response.getWriter().print("error");
		}
		return null;
	}

	
	//接收管理员
	public String act()throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		String usrPhone = (String)ServletActionContext.getContext().getSession().get("phone");
		//如果是bossPhone
		if(usrPhone.equals("13898624331")) {
			SystemManager systemManager = new SystemManager();
			systemManager.setPhone(actPhone);
			System.out.println(actPhone);
			BasicDBObject query = CreateQueryFromBean.EqualObj(systemManager);
			
			SystemManager systemManager2 = new SystemManager();
			systemManager2.setState(1);
			DaoImpl.update(query, systemManager2, false);
			
			response.getWriter().print("success");
		} else {
			response.getWriter().print("error");
		}
		return null;
	}
	
	//拒绝管理员
public String ref()throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		String usrPhone = (String)ServletActionContext.getContext().getSession().get("phone");
		//如果是bossPhone
		if(usrPhone.equals("13898624331")) {
			SystemManager systemManager = new SystemManager();
			systemManager.setPhone(actPhone);
			BasicDBObject query = CreateQueryFromBean.EqualObj(systemManager);
			
			DaoImpl.DeleteDocment(SystemManager.class, query);
			
			response.getWriter().print("success");
		} else {
			response.getWriter().print("error");
		}
		return null;
	}
	
	public String getDelPhone() {
		return delPhone;
	}

	public void setDelPhone(String delPhone) {
		this.delPhone = delPhone;
	}


	public String getActPhone() {
		return actPhone;
	}


	public void setActPhone(String actPhone) {
		this.actPhone = actPhone;
	}
}
