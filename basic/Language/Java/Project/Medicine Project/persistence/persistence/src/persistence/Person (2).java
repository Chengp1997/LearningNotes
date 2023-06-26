package persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Person implements Serializable {
	private String code;
	private String name;
	
	private Date lastLeave;
	private double total;
	
	private ArrayList<String> records;

	public Person(String code, String name, Date lastLeave, double total) {
		super();
		this.code = code;
		this.name = name;
		this.lastLeave = lastLeave;
		this.total = total;
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String code, String name, double total) {
		super();
		this.code = code;
		this.name = name;
		this.total = total;
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

	public Date getLastLeave() {
		return lastLeave;
	}

	public void setLastLeave(Date lastLeave) {
		this.lastLeave = lastLeave;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public ArrayList<String> getRecords() {
		return records;
	}

	public void addRecord(String code) {
		this.records.add(code);
	}

	@Override
	public String toString() {
		return "Person [code=" + code + ", name=" + name + ", lastLeave="
				+ lastLeave + ", total=" + total + ", records=" + records + "]";
	}
	
}
