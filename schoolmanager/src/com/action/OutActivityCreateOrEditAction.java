package com.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;

import utils.FileUpload;
import bean.OutActivity;

import com.dao.CreateQueryFromBean;
import com.dao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.opensymphony.xwork2.ActionSupport;

public class OutActivityCreateOrEditAction extends ActionSupport{
	private static final long serialVersionUID = -996451090288604944L;
	private String name;
	private String category;
	private Date deadLine;
	private Date startTime;
	private Date endTime;
	private String organization;
	private String content;
	
	private File img;
	private String imgContentType;
	private String imgFileName;
	
	private File attachment;
	private String attachmentContentType;
	private String attachmentFileName;

	private String attachmentSavePath;
	private String outActivityId;
	/*
	 * 创建活动
	 */
	public String outActivityCreateMethord() throws Exception{
		/*==============测试===============*/
		System.out.println("name:"+name+" category:"+category+" deadLine:"+deadLine);
		System.out.println("startTime:"+startTime+" endTime:"+endTime);
		System.out.println("organization:"+organization+" content:"+content);
		System.out.println("img:"+img+" imgContentType:"+imgContentType+" imgFileName:"+imgFileName);
		System.out.println("attachment:"+attachment+" attachmentContentType:"+attachmentContentType+" attachmentFileName:"+attachmentFileName);
		
		//创建
		OutActivity outActivity = new OutActivity();
		outActivity.set_id(new ObjectId());
		outActivity.setName(getName());
		outActivity.setCategory(getCategory());
		if(img!=null){
			if(imgContentType.equals("image/jpeg")||imgContentType.equals("image/png")
					||imgContentType.equals("image/bmp")){				
				String pictureSavePath = FileUpload.pictureUpload(img);				
				outActivity.setImgUrl(pictureSavePath);//存储图片URL
			}else{
				addActionError("图片格式不支持，只能上传jpg,png,bmp类型的图片");
				return INPUT;
			}
		}else{
			outActivity.setImgUrl("noUrl");//待修改?
		}		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1900, 0, 1, 0, 0, 0);
		Date date = calendar.getTime();
		if(deadLine!=null){
			outActivity.setDeadLine(deadLine);
		}else{
			outActivity.setDeadLine(date);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(startTime!=null&&endTime!=null){
			outActivity.setRunTime(sdf.format(startTime)+"~"+sdf.format(endTime));
		} else if (startTime != null && endTime == null){
			outActivity.setRunTime(sdf.format(startTime)+"~"+sdf.format(date));
		} else if(startTime == null && endTime != null) {
			outActivity.setRunTime(sdf.format(date)+"~"+sdf.format(endTime));
		} else {
			outActivity.setRunTime(sdf.format(date)+"~"+sdf.format(date));
		}		
		outActivity.setContent(getContent());		
		if(attachment!=null){
			if(attachmentContentType.equals("application/x-zip-compressed")){ //待测试
				attachmentSavePath = FileUpload.attachmentUpload(attachment, attachmentContentType, attachmentFileName,getAttachmentSavePath());
				outActivity.setAttachmentName(getAttachmentFileName());
				outActivity.setAttachmentUrl(attachmentSavePath);
			}else{
				addActionError("附件格式不支持，只能上传zip格式的打包文件");
				return INPUT;
			}
		}else{
			outActivity.setAttachmentName("nothing");//待修改
			outActivity.setAttachmentUrl("noUrl");
		}		
		outActivity.setReleaseTime(new Date());		
		if(organization!=null){
			outActivity.setOrganization(getOrganization());
		}
		DaoImpl.InsertWholeBean(outActivity);
		return SUCCESS;
	}
	
	/*
	 * 编辑活动
	 */
	public String outActivityEditMethord() throws Exception{
		/*==============测试===============*/
		System.out.println("name:"+name+" category:"+category+" deadLine:"+deadLine);
		System.out.println("startTime:"+startTime+" endTime:"+endTime);
		System.out.println("organization:"+organization+" content:"+content);
		System.out.println("img:"+img+" imgContentType:"+imgContentType+" imgFileName:"+imgFileName);
		System.out.println("attachment:"+attachment+" attachmentContentType:"+attachmentContentType+" attachmentFileName:"+attachmentFileName);
		System.out.println("outActivityId:"+outActivityId);
		
		//修改
		OutActivity outActivity = new OutActivity();
		outActivity.set_id(new ObjectId(getOutActivityId()));
		BasicDBObject query = CreateQueryFromBean.EqualObj(outActivity);
		
		outActivity.setName(getName());
		outActivity.setCategory(getCategory());
		if(img!=null){
			if(imgContentType.equals("image/jpeg")||imgContentType.equals("image/png")
					||imgContentType.equals("image/bmp")){				
				String pictureSavePath = FileUpload.pictureUpload(img);				
				outActivity.setImgUrl(pictureSavePath);//存储图片URL
			}else{
				addActionError("图片格式不支持，只能上传jpg,png,bmp类型的图片");
				return INPUT;
			}
		}		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1900, 0, 1, 0, 0, 0);
		Date date = calendar.getTime();
		if(deadLine!=null){
			outActivity.setDeadLine(deadLine);
		}else{
			outActivity.setDeadLine(date);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(startTime!=null&&endTime!=null){
			outActivity.setRunTime(sdf.format(startTime)+"~"+sdf.format(endTime));
		} else if (startTime != null && endTime == null){
			outActivity.setRunTime(sdf.format(startTime)+"~"+sdf.format(date));
		} else if(startTime == null && endTime != null) {
			outActivity.setRunTime(sdf.format(date)+"~"+sdf.format(endTime));
		} else {
			outActivity.setRunTime(sdf.format(date)+"~"+sdf.format(date));
		}		
		outActivity.setContent(getContent());		
		if(attachment!=null){
			if(attachmentContentType.equals("application/x-zip-compressed")){ //待测试
				attachmentSavePath = FileUpload.attachmentUpload(attachment, attachmentContentType, attachmentFileName,getAttachmentSavePath());
				outActivity.setAttachmentName(getAttachmentFileName());
				outActivity.setAttachmentUrl(attachmentSavePath);
			}else{
				addActionError("附件格式不支持，只能上传zip格式的打包文件");
				return INPUT;
			}
		}		
		//outActivity.setReleaseTime(new Date());		
		if(organization!=null){
			outActivity.setOrganization(getOrganization());
		}
		
		DaoImpl.update(query,outActivity , true);		
		return SUCCESS;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public File getImg() {
		return img;
	}


	public void setImg(File img) {
		this.img = img;
	}


	public File getAttachment() {
		return attachment;
	}


	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}


	public String getImgContentType() {
		return imgContentType;
	}


	public void setImgContentType(String imgContentType) {
		this.imgContentType = imgContentType;
	}


	public String getImgFileName() {
		return imgFileName;
	}


	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}


	public String getAttachmentContentType() {
		return attachmentContentType;
	}


	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}


	public String getAttachmentFileName() {
		return attachmentFileName;
	}


	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}

	@SuppressWarnings("deprecation")
	public String getAttachmentSavePath() {
		return ServletActionContext.getRequest().getRealPath(attachmentSavePath);
	}

	public void setAttachmentSavePath(String attachmentSavePath) {
		this.attachmentSavePath = attachmentSavePath;
	}

	public String getOutActivityId() {
		return outActivityId;
	}

	public void setOutActivityId(String outActivityId) {
		this.outActivityId = outActivityId;
	}
	
}
