package com.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.bson.Document;

import staticData.StaticString;

import bean.Province;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.opensymphony.xwork2.ActionSupport;

public class SchoolManagerGetCityAction extends ActionSupport{
	private static final long serialVersionUID = -4550772494219143885L;
	private String name;
	
	public void getCityInfo() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		Province province = new Province();
		province.setName(name);
		BasicDBObject query = CreateQueryFromBean.EqualObj(province);
		
		BasicDBObject projection = new BasicDBObject();
		projection.put(StaticString.Province_City, 1);
		
		MongoCursor<Document> cursor = DaoImpl.GetSelectCursor(Province.class, query, projection);
		String cities ="";
		while(cursor.hasNext()){
			@SuppressWarnings("unchecked")
			ArrayList<Document> documents =(ArrayList<Document>) cursor.next().get("City");
			for (int i = 0; i < documents.size(); i++) {
				cities+= documents.get(i).getString("Name")+"~";
			}
		}
		response.getWriter().print(cities);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
