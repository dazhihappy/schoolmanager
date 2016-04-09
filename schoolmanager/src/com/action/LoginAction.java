package com.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.bson.Document;

import staticData.StaticString;
import bean.SystemManager;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("struts-default")
@Namespace("/login")
public class LoginAction extends ActionSupport{
	private static final long serialVersionUID = -6434128483294080524L;
	private String username;
	private String password;
	
	@Action(value="userLogin")
	public void loginMethord() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		/*==========≤‚ ‘===========*/
		System.out.println(username+" "+password);
		
		//≤È—Ø
		SystemManager systemManager = new SystemManager();
		systemManager.setPhone(username);
		systemManager.setPwd(password);
		systemManager.setState(1);
		BasicDBObject query = CreateQueryFromBean.EqualObj(systemManager);
		
		BasicDBObject projection = new BasicDBObject();
		projection.put(StaticString.SystemManager_id, 1);
		projection.put(StaticString.SystemManager_Name, 1);
		projection.put(StaticString.SystemManager_Phone, 1);
		
		MongoCursor<Document> cursor = DaoImpl.GetSelectCursor(SystemManager.class, query, projection);
		if(cursor.hasNext()){
			Document document = cursor.next();
			ActionContext.getContext().getSession().put("userId", document.get("_id").toString());
			ActionContext.getContext().getSession().put("userName", document.get("Name"));
			ActionContext.getContext().getSession().put("phone", document.get("Phone"));
			response.getWriter().print("true");			
		}else{
			response.getWriter().print("false");
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
