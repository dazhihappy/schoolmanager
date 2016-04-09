package com.action;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;

import bean.InActivityCategoty;
import bean.Major;
import bean.Manager;
import bean.Organization;
import bean.School;

import com.dao.DaoImpl;
import com.opensymphony.xwork2.ActionSupport;

public class SchoolManageAddSchoolAction extends ActionSupport{
	private static final long serialVersionUID = -2881686369080713351L;
	private String schoolName;
	private String province;
	private String city;
	private String phone;
	private String manageUserId;
	private String managerName;
	private String managerPass;
	private ArrayList<String> major;
	private ArrayList<String> categoty;
	
	@Override
	public String execute() throws Exception {
		/*=============测试=============*/
		System.out.println("schoolName:"+schoolName);
		System.out.println("province:"+province);
		System.out.println("city:"+city);
		System.out.println("phone:"+phone);
		System.out.println("managerName:"+managerName);
		System.out.println("managerPass:"+managerPass);
		if(major!=null){
			System.out.print("major:");
			for (int i = 0; i < major.size(); i++) {
				System.out.print(major.get(i)+" ");
			}
			System.out.println();
		}
		if(categoty!=null){
			System.out.print("categoty:");
			for (int i = 0; i < categoty.size(); i++) {
				System.out.print(categoty.get(i)+" ");			
			}
		}
		
		//插入学校信息
		School school = new School();
		ObjectId schoolId=new ObjectId();
		school.set_id(schoolId);
		school.setName(schoolName);
		school.setAddressP(province);
		school.setAddressC(city);
		school.setTel(phone);
		school.setLogoUrl("noUrl"); //待修改
		school.setShowUrl("noUrl"); //待修改
		Calendar openTime = Calendar.getInstance();
		school.setOpenTime(openTime.getTime());
		school.setState(true);
		
		if(major!=null){
			ArrayList<Major> majors = new ArrayList<Major>();
			for (int i = 0; i < major.size(); i++) {
				if(major.get(i)!=""){
					Major m = new Major();
					m.set_id(new ObjectId());
					m.setName(major.get(i));
					majors.add(m);
				}
			}
			school.setMajor(majors);
		}
		
		if(categoty!=null){
			ArrayList<InActivityCategoty> inActivityCategoties = new ArrayList<InActivityCategoty>();
			for (int i = 0; i < categoty.size(); i++) {
				if(categoty.get(i)!=""){
					InActivityCategoty inActivityCategoty = new InActivityCategoty();
					inActivityCategoty.set_id(new ObjectId());
					inActivityCategoty.setName(categoty.get(i));
					inActivityCategoties.add(inActivityCategoty);
				}
			}
			school.setInActivityCategoty(inActivityCategoties);
		}
		
		//插入组织信息
		Organization organization = new Organization();
		organization.set_id(new ObjectId());
		organization.setName(schoolName);
		organization.setLevelTopId(new ObjectId("000000000000000000000000"));
		organization.setSchoolId(schoolId);
		ArrayList<Manager> managers = new ArrayList<Manager>();
		Manager manager = new Manager();
		manager.setUserId(manageUserId);
		manager.setName(managerName);
		manager.setPwd(managerPass);
		managers.add(manager);
		organization.setManager(managers);
		
		//插入
		DaoImpl.InsertWholeBean(school);
		DaoImpl.InsertWholeBean(organization);
		
		ServletActionContext.getRequest().setAttribute("schoolId", schoolId.toString());
		return SUCCESS;
	}
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPass() {
		return managerPass;
	}
	public void setManagerPass(String managerPass) {
		this.managerPass = managerPass;
	}

	public ArrayList<String> getMajor() {
		return major;
	}

	public void setMajor(ArrayList<String> major) {
		this.major = major;
	}

	public ArrayList<String> getCategoty() {
		return categoty;
	}

	public void setCategoty(ArrayList<String> categoty) {
		this.categoty = categoty;
	}

	public String getManageUserId() {
		return manageUserId;
	}

	public void setManageUserId(String manageUserId) {
		this.manageUserId = manageUserId;
	}
}
