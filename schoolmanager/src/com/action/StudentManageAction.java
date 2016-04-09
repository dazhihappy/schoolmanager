package com.action;

import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;
import org.bson.Document;
import org.bson.types.ObjectId;

import staticData.StaticString;
import utils.Util;
import bean.ForbidUser;
import bean.StudentInfo;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
public class StudentManageAction {
	
	private String choice;
	private String content;
	private String _id;//studentid
	private String tempPhone;//暂时登陆的电话
	private int tag;//1禁言 2置换学号并禁言 3置换邮箱并禁言
	public String search() throws Exception{
		
		//System.out.print(choice+"  "+content);
		
		StudentInfo sInfo=new StudentInfo();
		
		int tag=1;
		
		if(choice.equals(StaticString.StudentInfo_id)){
			try{
			sInfo.set_id(new ObjectId(content));
			}catch (Exception e) {
				
				tag=0;
			}
		}
		else if(choice.equals(StaticString.StudentInfo_Phone)){
			sInfo.setPhone(content);
		}
		else if(choice.equals(StaticString.StudentInfo_IdCard)){
			sInfo.setIdCard(content);
		}
		else{//邮箱查询
			sInfo.setEmail(content);
		}
		ArrayList<Document> list=new ArrayList<Document>();
		if(tag==0){
			
		}
		else{
			BasicDBObject projection=new BasicDBObject();
			projection.put(StaticString.StudentInfo_Name, 1);
			projection.put(StaticString.StudentInfo_Phone, 1);
			projection.put(StaticString.StudentInfo_IdCard, 1);
			projection.put(StaticString.StudentInfo_Email, 1);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, 
					CreateQueryFromBean.EqualObj(sInfo), projection);
			while(mc.hasNext()){
				//Document document=mc.next();
				list.add(mc.next());
				//list.add(document);
				//System.out.print(document.get("Name")+" "+document.get("IdCard"));
			}
		}
		//System.out.print(list.size());
		ServletActionContext.getRequest().setAttribute("studentList", list);
		return "success";
	}

	
	public String forbid() throws Exception{
		
		ForbidUser fUser=new ForbidUser();
		fUser.set_id(new ObjectId());
		fUser.setStudentId(new ObjectId(_id));
		DaoImpl.InsertWholeBean(fUser);//加入禁言名单
		
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.StudentInfo_Name, 1);
		projection.put(StaticString.StudentInfo_Phone, 1);
		projection.put(StaticString.StudentInfo_IdCard, 1);
		projection.put(StaticString.StudentInfo_Email, 1);
		
		StudentInfo sInfo=new StudentInfo();
		sInfo.set_id(new ObjectId(_id));
		
		StudentInfo changeInfo=new StudentInfo();
		changeInfo.setPhone(Util.GetRandomString(11));
		
		if(tag==1){
			DaoImpl.update(CreateQueryFromBean.EqualObj(sInfo), changeInfo, false);
		}
		else if(tag==2){//重置学号
			changeInfo.setIdCard("000000000");
			DaoImpl.update(CreateQueryFromBean.EqualObj(sInfo), changeInfo, false);
		}
		else{//重置邮箱
			changeInfo.setEmail("forbid@mail.com");
			DaoImpl.update(CreateQueryFromBean.EqualObj(sInfo), changeInfo, false);
		}
		
		MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, 
				CreateQueryFromBean.EqualObj(sInfo), projection);
		ArrayList<Document> list=new ArrayList<Document>();

		if(mc.hasNext()){
			list.add(mc.next());
			ServletActionContext.getRequest().setAttribute("studentList", list);
		}
		//System.out.println(_id);
		return "success";
	}
	
	public String recover() throws Exception{
		
		//从forbidUser中删除该学生
		ForbidUser fUser=new ForbidUser();
		fUser.setStudentId(new ObjectId(_id));
		DaoImpl.DeleteDocment(ForbidUser.class,CreateQueryFromBean.EqualObj(fUser));
		
		StudentInfo sInfo=new StudentInfo();
		sInfo.set_id(new ObjectId(_id));
		//改变学生电话
		StudentInfo changeInfo=new StudentInfo();
		changeInfo.setPhone(tempPhone);
		DaoImpl.update(CreateQueryFromBean.EqualObj(sInfo), changeInfo, false);
		
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.StudentInfo_Name, 1);
		projection.put(StaticString.StudentInfo_Phone, 1);
		projection.put(StaticString.StudentInfo_IdCard, 1);
		projection.put(StaticString.StudentInfo_Email, 1);
		
		MongoCursor<Document> mc=DaoImpl.GetSelectCursor(StudentInfo.class, 
				CreateQueryFromBean.EqualObj(sInfo), projection);
		ArrayList<Document> list=new ArrayList<Document>();

		if(mc.hasNext()){
			list.add(mc.next());
			ServletActionContext.getRequest().setAttribute("studentList", list);
		}
		
		return "success";
	}
	public String getChoice() {
		return choice;
	}

	public String getContent() {
		return content;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}


	public String getTempPhone() {
		return tempPhone;
	}


	public void setTempPhone(String tempPhone) {
		this.tempPhone = tempPhone;
	}


	public int getTag() {
		return tag;
	}


	public void setTag(int tag) {
		this.tag = tag;
	}
	
}
