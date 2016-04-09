package bean;

import org.bson.types.ObjectId;

public class ForbidUser {
	private ObjectId _id;
	private ObjectId StudentId;
	public ObjectId get_id() {
		return _id;
	}
	public ObjectId getStudentId() {
		return StudentId;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public void setStudentId(ObjectId studentId) {
		StudentId = studentId;
	}
	
}
