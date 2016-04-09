package com.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.bson.Document;
import org.bson.types.ObjectId;

import staticData.StaticString;

import bean.Organization;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.opensymphony.xwork2.ActionSupport;

public class SchoolManagerEditAction extends ActionSupport{
	private static final long serialVersionUID = 8153229178899535355L;
	
	private String schoolId;
	
	public void editManager() throws Exception{
		//System.out.println(schoolId);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		Organization organization = new Organization();
		organization.setSchoolId(new ObjectId(schoolId));
		BasicDBObject query = CreateQueryFromBean.EqualObj(organization);
		
		BasicDBObject projection = new BasicDBObject();
		projection.put(StaticString.Organization_Manager, 1);
		
		MongoCursor<Document> cursor = DaoImpl.GetSelectCursor(Organization.class, query, projection);
		String managerjson ="";
		while(cursor.hasNext()){
			@SuppressWarnings("unchecked")
			ArrayList<Document> lists = (ArrayList<Document>) cursor.next().get("Manager");
			Document document = lists.get(0);
			managerjson=document.getString("UserId")+"~"+document.getString("Pwd");
			break;
		}
		
		response.getWriter().print(managerjson);
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
}
