package com.action;

import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;
import org.bson.Document;
import org.bson.types.ObjectId;

import staticData.StaticString;
import bean.School;
import bean.SchoolInfo;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.opensymphony.xwork2.ActionSupport;

public class SchoolManagerPageAction extends ActionSupport{
	private static final long serialVersionUID = 7652910535339177862L;
	private String schoolId;
	private int tag;//1——上一页;2——下一页
	private int pageCount;//总页数
	private int pageNum;//当前页数
	private final int limit = 10;
	
	@Override
	public String execute() throws Exception {
//		System.out.println("schoolId:"+schoolId);
//		System.out.println("tag:"+tag);
//		System.out.println("pageCount:"+pageCount);
//		System.out.println("pageNum:"+pageNum);
		
		BasicDBObject sort = new BasicDBObject();		
		
		BasicDBObject projection = new BasicDBObject();//显示数据
		projection.put(StaticString.School_id, 1);
		projection.put(StaticString.School_Name, 1);
		projection.put(StaticString.School_OpenTime, 1);
		projection.put("State", 1);
		projection.put(StaticString.School_Tel, 1);
		projection.put(StaticString.School_AddressP, 1);
		projection.put(StaticString.School_AddressC, 1);
		
		School school = new School();//查询条件
		school.set_id(new ObjectId(schoolId));
		MongoCursor<Document> cursor =null; 
		if(tag==1){ //上一页	
			System.out.println("上一页");
			pageNum--;
			sort.put(StaticString.School_OpenTime, 1);
			cursor=	DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.GtObj(school), sort,limit, projection);
		}else{
			System.out.println("下一页");
			pageNum++;
			sort.put(StaticString.School_OpenTime, -1);
			cursor=	DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.LtObj(school), sort,limit, projection);
		}
		ArrayList<SchoolInfo> schoolInfos = new ArrayList<SchoolInfo>();
		Document document = null;
		while(cursor.hasNext()){
			document = cursor.next();
			SchoolInfo info = new SchoolInfo();
			info.set_id(document.getObjectId("_id"));
			info.setDate(document.getDate("OpenTime"));
			info.setName(document.getString("Name"));
			info.setState(document.getBoolean("State"));
			info.setTel(document.getString("Tel"));
			info.setProvince(document.getString("AddressP"));
			info.setCity(document.getString("AddressC"));
			schoolInfos.add(info);
		}
		ServletActionContext.getRequest().setAttribute("schoolInfos",schoolInfos);
		ServletActionContext.getRequest().setAttribute("pageCount",pageCount);
		ServletActionContext.getRequest().setAttribute("pageNum",pageNum);
		return SUCCESS;
	}

	
	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
}
