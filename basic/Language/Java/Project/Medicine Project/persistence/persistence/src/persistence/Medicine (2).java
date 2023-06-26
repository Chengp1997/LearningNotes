package persistence;

import java.io.Serializable;

public class Medicine implements Serializable {
	private String code;
	private String name;
	
	public Medicine(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public Medicine() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	@Override
	public String toString() {
		return "Medicine [code=" + code + ", name=" + name + "]";
	}
	
}
