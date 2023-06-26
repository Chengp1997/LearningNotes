package application.model;

import java.io.Serializable;

import javafx.beans.property.StringProperty;

public class Medicine implements Serializable{
	
	protected StringProperty name;
	protected StringProperty ID;
	protected StringProperty EnglishName;
	protected StringProperty chargeType;
	protected StringProperty sign;
	protected StringProperty chargeLevel;
	protected StringProperty medicineUnit;
	protected StringProperty limitPrice;
	protected StringProperty hospitalSign;
	protected StringProperty approval;
	protected StringProperty hospitalLevel;
	protected StringProperty formOfDrug;
	protected StringProperty scopeOfUse;
	protected StringProperty rate;
	protected StringProperty usage;
	protected StringProperty unit;
	protected StringProperty size;
	protected StringProperty limitDays;
	protected StringProperty tradingName;
	protected StringProperty factory;
	protected StringProperty OTC;
	protected StringProperty notes;
	protected StringProperty countryListOfCode;
	protected StringProperty origin;
	
	/*public Medicine(String name,String ID,String EnglishName,String chargeType,String sign,String chargeLevel,medicineUnit
			double limitPrice,String hospitalSign,boolean approval,String hospitalLevel,String formOfDrug,String scopeOfUse,
			double rate,String usage,String unit,String size,double limitDays,String tradingName,String factory,String OTC,
			String notes,String countryListOfCode,String origin){
		
	}*/
	
	public String getName(){
		return name.get();
	}
	public void setName(StringProperty name){
		this.name=name;
	}
	public StringProperty nameProperty(){
		return name;
	}
	public String getID(){
		return ID.get();
	}
	public void setID(StringProperty ID){
		this.ID=ID;
	}
	public StringProperty IDProperty(){
		return ID;
	}
	public String getEnglishName(){
		return EnglishName.get();
	}
	public void setEnglishName(StringProperty EnglishName){
		this.EnglishName=EnglishName;
	}
	public StringProperty EnglishNameProperty(){
		return EnglishName;
	}
	public String getChargeType(){
		return chargeType.get();
	}
	public void setChargeType(StringProperty chargeType){
		this.chargeType=chargeType;
	}
	public StringProperty chargeTypeProperty(){
		return chargeType;
	}
	public String getSign(){
		return sign.get();
	}
	public void setSign(StringProperty sign){
		this.sign=sign;
	}
	public StringProperty signProperty(){
		return sign;
	}
	public String getChargeLevel(){
		return chargeLevel.get();
	}
	public void setChargeLevel(StringProperty chargeLevel){
		this.chargeLevel=chargeLevel;
	}
	public StringProperty chargeLevelProperty(){
		return chargeLevel;
	}
	public String getLimitPrice(){
		return limitPrice.get();
	}
	public void setLimitPrice(StringProperty limitPrice){
		this.limitPrice=limitPrice;
	}
	public StringProperty limitPriceProperty(){
		return limitPrice;
	}
	public String getHospitalSign(){
		return hospitalSign.get();
	}
	public void sethospitalSign(StringProperty hospitalSign){
		this.hospitalSign=hospitalSign;
	}
	public StringProperty hospitalSignProperty(){
		return hospitalSign;
	}
	public String getApproval(){
		return approval.get();
	}
	public void setApproval(StringProperty approval){
		this.approval=approval;
	}
	public StringProperty approvalProperty(){
		return approval;
	}
	public String getHospitalLevel(){
		return hospitalLevel.get();
	}
	public void setHospitalLevel(StringProperty hospitalLevel){
		this.hospitalLevel=hospitalLevel;
	}
	public StringProperty hospitalLevelProperty(){
		return hospitalLevel;
	}
	public String getFormOfDrug(){
		return formOfDrug.get();
	}
	public void setFormOfDrug(StringProperty formOfDrug){
		this.formOfDrug=formOfDrug;
	}
	public StringProperty formOfDrugProperty(){
		return formOfDrug;
	}
	public String getScopeOfUse(){
		return scopeOfUse.get();
	}
	public void setScopeOfUse(StringProperty scopeOfUse){
		this.scopeOfUse=scopeOfUse;
	}
	public StringProperty scopeOfUseProperty(){
		return scopeOfUse;
	}
	public String getRate(){
		return rate.get();
	}
	public void setRate(StringProperty rate){
		this.rate=rate;
	}
	public StringProperty rateProperty(){
		return rate;
	}
	public String getUsage(){
		return usage.get();
	}
	public void setUsage(StringProperty usage){
		this.usage=usage;
	}
	public StringProperty usageProperty(){
		return usage;
	}
	public String getUnit(){
		return unit.get();
	}
	public void setUnit(StringProperty unit){
		this.unit=unit;
	}
	public StringProperty unitProperty(){
		return unit;
	}
	public String getSize(){
		return size.get();
	}
	public void setSize(StringProperty size){
		this.size=size;
	}
	public StringProperty sizeProperty(){
		return size;
	}
	public String getLimitDays(){
		return limitDays.get();
	}
	public void setLimitDays(StringProperty limitDays){
		this.limitDays=limitDays;
	}
	public StringProperty limitDaysProperty(){
		return limitDays;
	}
	public String getTradingName(){
		return tradingName.get();
	}
	public void setTradingName(StringProperty tradingName){
		this.tradingName=tradingName;
	}
	public StringProperty tradingNameProperty(){
		return tradingName;
	}
	public String getFactory(){
		return factory.get();
	}
	public void setFactory(StringProperty factory){
		this.factory=factory;
	}
	public StringProperty factoryProperty(){
		return factory;
	}
	public String getOTC(){
		return OTC.get();
	}
	public void setOTC(StringProperty OTC){
		this.OTC=OTC;
	}
	public StringProperty OTCProperty(){
		return OTC;
	}
	public String getNotes(){
		return notes.get();
	}
	public void setNotes(StringProperty notes){
		this.notes=notes;
	}
	public StringProperty notesProperty(){
		return notes;
	}
	public String getCountryListOfCode(){
		return countryListOfCode.get();
	}
	public void setCountryListOfCode(StringProperty countryListOfCode){
		this.countryListOfCode=countryListOfCode;
	}
	public StringProperty countryListOfCodeProperty(){
		return countryListOfCode;
	}
	public String getOrigin(){
		return origin.get();
	}
	public void setOrigin(StringProperty origin){
		this.origin=origin;
	}
	public StringProperty originProperty(){
		return origin;
	}
	public String getMedicineUnit(){
		return medicineUnit.get();
	}
	public void setMedicineUnit(StringProperty medicineUnit){
		this.medicineUnit=medicineUnit;
	}
	public StringProperty medicineUnitProperty(){
		return medicineUnit;
	}


}



