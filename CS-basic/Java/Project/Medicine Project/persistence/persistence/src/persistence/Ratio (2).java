package persistence;

import java.io.Serializable;

public class Ratio implements Serializable {
	private double ratio;
	private String hospitalLevel;
	private String personnalCategory;
	private String medicalCategory;
	private int start;
	private int end;
	public Ratio(double ratio, String hospitalLevel, String personnalCategory,
			String medicalCategory, int start, int end) {
		super();
		this.ratio = ratio;
		this.hospitalLevel = hospitalLevel;
		this.personnalCategory = personnalCategory;
		this.medicalCategory = medicalCategory;
		this.start = start;
		this.end = end;
	}
	
	public Ratio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public String getHospitalLevel() {
		return hospitalLevel;
	}
	public void setHospitalLevel(String hospitalLevel) {
		this.hospitalLevel = hospitalLevel;
	}
	public String getPersonnalCategory() {
		return personnalCategory;
	}
	public void setPersonnalCategory(String personnalCategory) {
		this.personnalCategory = personnalCategory;
	}
	public String getMedicalCategory() {
		return medicalCategory;
	}
	public void setMedicalCategory(String medicalCategory) {
		this.medicalCategory = medicalCategory;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "Ratio [ratio=" + ratio + ", hospitalLevel=" + hospitalLevel
				+ ", personnalCategory=" + personnalCategory
				+ ", medicalCategory=" + medicalCategory + ", start=" + start
				+ ", end=" + end + "]";
	}
	
}
