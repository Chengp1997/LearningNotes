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
	
	public final static String[] MEDICAL_CATEGORY = { "��ͨ����", "����ҩ�깺ҩ", "��������",
			"��ͨסԺ" };
	public final static String[] PERSONNAL_CATEGORY = { "ҽ�ƴ������-�κ�ũ��",
			"ҽ�ƴ������-�������", "ҽ�ƴ������-����ְ��", "ҽ����ͨ��Ա" };
	public final static String[] HOSPITAL_LEVEL = { "һ��ҽԺ",
		"����ҽԺ", "����ҽԺ", "����ҽԺ" };
	public final static String[] DISEASE = { "��Ѫ��ϵͳ����",
		"����ϵͳ����", "��л�ڷ��ڼ���", "��Ѫϵͳ����" };
}
