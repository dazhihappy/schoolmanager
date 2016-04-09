package com.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;

import bean.ActivityComment;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;

public class CommentManageAction {

	private int pageNu=1;//默认第1页
	private int pageTag;//标记上一页-0，下一页-1	
	private String timefirst;
	private String timelast;
	private String activityCommentId;
	public static final int pageSize=10;//一页中显示的通知数量
	
	//下一页,上一页响应函数
	public String preNext(){		
		ServletActionContext.getRequest().setAttribute("pageNu", pageNu);
		ServletActionContext.getRequest().setAttribute("timefirst", timefirst);
		ServletActionContext.getRequest().setAttribute("timelast", timelast);
		ServletActionContext.getRequest().setAttribute("pageTag", pageTag);
		return "success";
	}
	
	public String delete() throws Exception{
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		ActivityComment comment=new ActivityComment();
		comment.set_id(new ObjectId(activityCommentId));
		BasicDBObject query=CreateQueryFromBean.EqualObj(comment);
		DaoImpl.DeleteDocment(ActivityComment.class, query);
		response.getWriter().print("删除成功！");
		return "success";
		
	}
	public int getPageNu() {
		return pageNu;
	}

	public int getPageTag() {
		return pageTag;
	}

	public String getTimefirst() {
		return timefirst;
	}

	public String getTimelast() {
		return timelast;
	}

	public void setPageNu(int pageNu) {
		this.pageNu = pageNu;
	}

	public void setPageTag(int pageTag) {
		this.pageTag = pageTag;
	}

	public void setTimefirst(String timefirst) {
		this.timefirst = timefirst;
	}

	public void setTimelast(String timelast) {
		this.timelast = timelast;
	}

	public String getActivityCommentId() {
		return activityCommentId;
	}

	public void setActivityCommentId(String activityCommentId) {
		this.activityCommentId = activityCommentId;
	}

	
}
