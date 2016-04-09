package com.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;

import bean.City;
import bean.Province;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

public class SchoolManagerAction {
	private String province;
	private String city;
	private int flag;//1���ʡ�ݺͳ��� 0ֻ�ӳ���
	//���ӵ���
	public void addArea() throws Exception{
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");

		Province pro=new Province();
		pro.setName(province);
		BasicDBObject query=CreateQueryFromBean.EqualObj(pro);
		ArrayList<City> list=new ArrayList<City>();
		City c=new City();
		c.setName(city);
		list.add(c);
		if(flag==1){//ʡ+��
			pro.setCity(list);
			pro.set_id(new ObjectId());
			DaoImpl.InsertWholeBean(pro);
		}
		else{//��
			
			DaoImpl.InsertSonBean(Province.class, query, City.class, list);
		}
		response.getWriter().print("true");
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public void setProvince(String province) throws Exception {
		this.province = utils.Util.DoGetString(province); //��������
	}
	public void setCity(String city) throws Exception {
		this.city = utils.Util.DoGetString(city);
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
