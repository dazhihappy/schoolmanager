package com.action;

import java.io.File;

import org.bson.types.ObjectId;

import utils.FileUpload;

import bean.School;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.opensymphony.xwork2.ActionSupport;

public class SchoolManagerUploadPicAction extends ActionSupport{
	private static final long serialVersionUID = 2439993983773892335L;	
	private File webPic;
	private File appPic;	
	private String schoolId;
	
	@Override
	public String execute() throws Exception {
		/*==============≤‚ ‘===============*/
		System.out.println("webPic:"+webPic);
		System.out.println("appPic:"+appPic);
		
		School school = new School();
		school.set_id(new ObjectId(schoolId));
		BasicDBObject query = CreateQueryFromBean.EqualObj(school);
		
		String appPicUrl = FileUpload.pictureUpload(appPic);
		String webPicUrl = FileUpload.pictureWebUpload(webPic);
		
		school.setShowUrl(appPicUrl);
		school.setLogoUrl(webPicUrl);
		
		DaoImpl.update(query, school, false);		
		return SUCCESS;
	}
	
	public File getWebPic() {
		return webPic;
	}

	public void setWebPic(File webPic) {
		this.webPic = webPic;
	}

	public File getAppPic() {
		return appPic;
	}

	public void setAppPic(File appPic) {
		this.appPic = appPic;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
}
