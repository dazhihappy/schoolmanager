package bean;

public class UserCount {
	private String name;
	private int count;
		
	public UserCount(String name, int count) {
		super();
		this.name = name;
		this.count = count;
	}
	public UserCount() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
