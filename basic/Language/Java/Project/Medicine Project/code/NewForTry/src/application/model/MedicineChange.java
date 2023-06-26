package application.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class MedicineChange implements  Serializable {
	protected String name;
	protected String ID;
	protected String EnglishName;
	protected String chargeType;
	protected String sign;
	protected String chargeLevel;
	protected String medicineUnit;
	protected String limitPrice;
	protected String hospitalSign;
	protected String approval;
	protected String hospitalLevel;
	protected String formOfDrug;
	protected String scopeOfUse;
	protected String rate;
	protected String usage;
	protected String unit;
	protected String size;
	protected String limitDays;
	protected String tradingName;
	protected String factory;
	protected String OTC;
	protected String notes;
	protected String countryListOfCode;
	protected String origin;
	
	public void initialize(Medicine per){
		this.name=per.getName();
		this.ID=per.getID();
		this.EnglishName=per.getEnglishName();
		this.chargeType=per.getChargeType();
		this.sign=per.getSign();
		this.chargeLevel=per.getChargeLevel();
		this.medicineUnit=per.getMedicineUnit();
		this.limitPrice=per.getLimitPrice();
		this.hospitalSign=per.getHospitalSign();
		this.approval=per.getApproval();
		this.hospitalLevel=per.getHospitalLevel();
		this.formOfDrug=per.getFormOfDrug();
		this.scopeOfUse=per.getScopeOfUse();
		this.rate=per.getRate();
		this.usage=per.getUsage();
		this.unit=per.getUnit();
		this.size=per.getSize();
		this.limitDays=per.getLimitDays();
		this.tradingName=per.getTradingName();
		this.factory=per.getFactory();
		this.OTC=per.getOTC();
		this.notes=per.getNotes();
		this.countryListOfCode=per.getCountryListOfCode();
		this.origin=per.getOrigin();
	}
	 public Medicine getMedicine(){
			Medicine per=new Medicine();
			per.setName(new SimpleStringProperty(name));
			per.setID(new SimpleStringProperty(ID));
			per.setEnglishName(new SimpleStringProperty(EnglishName));
			per.setChargeType(new SimpleStringProperty(chargeType));
			per.setSign(new SimpleStringProperty(sign));
			per.setChargeLevel(new SimpleStringProperty(chargeLevel));
			per.setMedicineUnit(new SimpleStringProperty(medicineUnit));
			per.setLimitPrice(new SimpleStringProperty(limitPrice));
			per.sethospitalSign(new SimpleStringProperty(hospitalSign));
			per.setApproval(new SimpleStringProperty(approval));
			per.setHospitalLevel(new SimpleStringProperty(hospitalLevel));
			per.setFormOfDrug(new SimpleStringProperty(formOfDrug));
			per.setScopeOfUse(new SimpleStringProperty(scopeOfUse));
			per.setRate(new SimpleStringProperty(rate));
			per.setUsage(new SimpleStringProperty(usage));
			per.setUnit(new SimpleStringProperty(unit));
			per.setSize(new SimpleStringProperty(size));
			per.setLimitDays(new SimpleStringProperty(limitDays));
			per.setTradingName(new SimpleStringProperty(tradingName));
			per.setFactory(new SimpleStringProperty(factory));
			per.setOTC(new SimpleStringProperty(OTC));
			per.setNotes(new SimpleStringProperty(notes));
			per.setCountryListOfCode(new SimpleStringProperty(countryListOfCode));
			per.setOrigin(new SimpleStringProperty(origin));
			return per;
		}
}
