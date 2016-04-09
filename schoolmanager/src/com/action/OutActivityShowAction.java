package com.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.bson.types.ObjectId;

import bean.ActivityJudge;
import bean.OutActivity;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("struts-default")
@Namespace("/outActivity")
public class OutActivityShowAction extends ActionSupport{
	private static final long serialVersionUID = -7540184803983121936L;
	private String outActivityId;
	
	/*
	 * 删除校外活动
	 */
	@Action("deleteOutActivity")
	public void deleteOutActivityMethord() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain ; chaset = utf-8");
		/*=======测试========*/
		System.out.println(outActivityId);
		
		OutActivity outActivity = new OutActivity();
		outActivity.set_id(new ObjectId(outActivityId));
		DaoImpl.DeleteDocment(OutActivity.class, CreateQueryFromBean.EqualObj(outActivity));
		
		ActivityJudge activityJudge = new ActivityJudge();
		activityJudge.setActivityId(new ObjectId(outActivityId));
		DaoImpl.DeleteDocment(ActivityJudge.class, CreateQueryFromBean.EqualObj(activityJudge));
		
		response.getWriter().print("true");
	}
	
	public String getOutActivityId() {
		return outActivityId;
	}
	public void setOutActivityId(String outActivityId) {
		this.outActivityId = outActivityId;
	}

}
