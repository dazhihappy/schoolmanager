package bean;


import java.util.Date;

import org.bson.types.ObjectId;

public class SchoolInfo {
	private ObjectId _id;
	private Date date;
	private String name;
	private boolean state;
	
	private String tel;
	private String province;
	private String city;
		
	
	
	public SchoolInfo(ObjectId _id, Date date, String name, boolean state,
			String tel, String province, String city) {
		super();
		this._id = _id;
		this.date = date;
		this.name = name;
		this.state = state;
		this.tel = tel;
		this.province = province;
		this.city = city;
	}
	
	public SchoolInfo(ObjectId _id, Date date, String name, boolean state) {
		super();
		this._id = _id;
		this.date = date;
		this.name = name;
		this.state = state;
	}
	
	public SchoolInfo() {
		super();
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	
}
