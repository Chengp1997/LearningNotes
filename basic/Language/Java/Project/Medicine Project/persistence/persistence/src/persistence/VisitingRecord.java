package persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class VisitingRecord implements Serializable {
	private String id;
	private String personCode;
	private Date inHospital;
	private Date leaveHospital;
	private String disease;
	private float personnal;
	private float insurance;
	private float total;
	private ArrayList itemList;
	public ArrayList getItemList() {
		return itemList;
	}
	public void setItemList(ArrayList itemList) {
		this.itemList = itemList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public Date getInHospital() {
		return inHospital;
	}
	public void setInHospital(Date inHospital) {
		this.inHospital = inHospital;
	}
	public Date getLeaveHospital() {
		return leaveHospital;
	}
	public void setLeaveHospital(Date leaveHospital) {
		this.leaveHospital = leaveHospital;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	public float getPersonnal() {
		return personnal;
	}
	public void setPersonnal(float personnal) {
		this.personnal = personnal;
	}
	public float getInsurance() {
		return insurance;
	}
	public void setInsurance(float insurance) {
		this.insurance = insurance;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public VisitingRecord(String id, String personCode, Date inHospital,
			Date leaveHospital, String disease, float personnal,
			float insurance, float total) {
		super();
		this.id = id;
		this.personCode = personCode;
		this.inHospital = inHospital;
		this.leaveHospital = leaveHospital;
		this.disease = disease;
		this.personnal = personnal;
		this.insurance = insurance;
		this.total = total;
	}
	
	public VisitingRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "VisitingRecord [id=" + id + ", personCode=" + personCode
				+ ", inHospital=" + inHospital + ", leaveHospital="
				+ leaveHospital + ", disease=" + disease + ", personnal="
				+ personnal + ", insurance=" + insurance + ", total=" + total
				+ "]";
	}
	
}
