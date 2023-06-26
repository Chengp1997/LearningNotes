package persistence;

import java.io.Serializable;

public class Hospital implements Serializable {
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Hospital(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	@Override
	public String toString() {
		return "Hospital [code=" + code + ", name=" + name + "]";
	}
	public Hospital() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
