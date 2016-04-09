package com.action;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

import bean.Suggestion;

public class FeedbackAction {
	private int pageNu=1;//Ĭ�ϵ�1ҳ
	private int pageTag;//�����һҳ-0����һҳ-1
	private String oIdfirst;
	private String oIdlast;
    private static final int pageSize=10;//һҳ����ʾ��֪ͨ����
	private String solution;
	private String dealPerson;
	private String _id;
	
	public String showDetail() throws Exception{
		
		Suggestion s=new Suggestion();
    	s.set_id(new ObjectId(_id));
    	MongoCursor<Document> mCursor=DaoImpl.GetSelectCursor(Suggestion.class, CreateQueryFromBean.EqualObj(s), new BasicDBObject());
    	Document document=mCursor.next();
    	ServletActionContext.getRequest().setAttribute("selectedSuggestion", document);
		return "success";
	}
    //������  �������ݿ�
    public String handle() throws Exception{
    	
    	HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");

    	Suggestion s=new Suggestion();
    	s.set_id(new ObjectId(_id));
    	Suggestion change=new Suggestion();
    	change.setDealPerson(new ObjectId(dealPerson));
    	change.setDealTime(new Date());
    	change.setSolution(solution);
    	change.setState(1);
    	DaoImpl.update(CreateQueryFromBean.EqualObj(s),change,false);
    	response.getWriter().print("true");
    	return null;
    }
    
	//��һҳ,��һҳ��Ӧ����
	public String execute(){		
		ServletActionContext.getRequest().setAttribute("pageNu", pageNu);
		ServletActionContext.getRequest().setAttribute("oIdfirst", oIdfirst);
		ServletActionContext.getRequest().setAttribute("oIdlast", oIdlast);
		ServletActionContext.getRequest().setAttribute("pageTag", pageTag);
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

	public String getSolution() {
		return solution;
	}

	public String getDealPerson() {
		return dealPerson;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
}
