package com.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

import bean.SystemNotice;

public class BulletinAction {
	private String _id;
	private int pageNu=1;//默认第1页
	private int pageTag;//标记上一页-0，下一页-1
	private String oIdfirst;
	private String oIdlast;
	private String title;
	private String publisher;
	private String receiver;
	private String content;
  
	public String detail() throws Exception{
		
		SystemNotice notice=new SystemNotice();
		notice.set_id(new ObjectId(_id));
		MongoCursor<Document> mc=DaoImpl.GetSelectCursor(SystemNotice.class, CreateQueryFromBean.EqualObj(notice), new BasicDBObject());
		Document document=mc.next();
		ServletActionContext.getRequest().setAttribute("notice", document);
		return "success";
	}
	
	public String turn(){		
		ServletActionContext.getRequest().setAttribute("pageNu", pageNu);
		ServletActionContext.getRequest().setAttribute("oIdfirst", oIdfirst);
		ServletActionContext.getRequest().setAttribute("oIdlast", oIdlast);
		ServletActionContext.getRequest().setAttribute("pageTag", pageTag);
		return "success";
	}
	public String batch() throws Exception{
		System.out.println(content+publisher+title+receiver);
		SystemNotice notice=new SystemNotice();
		notice.set_id(new ObjectId());
		notice.setContent(content);
		notice.setPublisher(publisher);
		notice.setReceiver(Integer.parseInt(receiver));
		notice.setReleaseTime(new Date());
		notice.setTitle(title);
		DaoImpl.InsertWholeBean(notice);
		return "success";
	}
	public int getPageNu() {
		return pageNu;
	}
	public void setPageNu(int pageNu) {
		this.pageNu = pageNu;
	}
	
	public int getPageTag() {
		return pageTag;
	}
	public void setPageTag(int pageTag) {
		this.pageTag = pageTag;
	}	
	
	public String getOIdfirst() {
		return oIdfirst;
	}
	public String getOIdlast() {
		return oIdlast;
	}
	public void setOIdfirst(String oIdfirst) {
		this.oIdfirst = oIdfirst;
	}
	public void setOIdlast(String oIdlast) {
		this.oIdlast = oIdlast;
	}


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getContent() {
		return content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
