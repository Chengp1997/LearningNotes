package persistence;

public class Parameter implements java.io.Serializable {
	public final static byte MEDICAL_CATEGORY_NORMAL = 0;
	public final static byte MEDICAL_CATEGORY_SHOP = 1;
	
	public final static byte PERSONNAL_CATEGORY_FAMER = 0;
	public final static byte PERSONNAL_CATEGORY_DENIZEN = 1;
	
	public final static byte HOSPITAL_LEVEL_FIRST = 0;	
	public final static byte HOSPITAL_LEVEL_SECOND = 1;
	
	public final static byte DISEASE_HEART = 0;	
	public final static byte DISEASE_DIGESTION = 1;
	
	public final static String[] MEDICAL_CATEGORY = { "普通门诊", "定点药店购药", "急诊抢救",
			"普通住院" };
	public final static String[] PERSONNAL_CATEGORY = { "医疗待遇类别-参合农民",
			"医疗待遇类别-城镇居民", "医疗待遇类别-城镇职工", "医疗普通人员" };
	public final static String[] HOSPITAL_LEVEL = { "一级医院",
		"二级医院", "三级医院", "社区医院" };
	public final static String[] DISEASE = { "心血管系统疾病",
		"消化系统疾病", "代谢内分泌疾病", "造血系统疾病" };
}
