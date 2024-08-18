package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Persistence implements java.io.Serializable {
	private final static String FILE_NAME = "c:\\abc";
	private HashMap<String, Person> personMap;
	private HashMap<String, Hospital> hospitalMap;
	private HashMap<String, Medicine> medicineMap;
	private List<Ratio> ratioRules;
	private HashMap<String, VisitingRecord> recordMap;
	public static Object o;
	
	private static Persistence p = null;
	
	public static Persistence getObjs(){
		if(p != null)
			return p;
		try {
			p = Persistence.loadFromFile(FILE_NAME);
		} catch (Exception e) {
			p = new Persistence();
			p.init();

			p.saveMedicine(new Medicine("12101019620104HN", "联苯苄唑"));
			p.saveHospital(new Hospital("11", "医大二院"));
			p.saveHospital(new Hospital("12", "医大一院"));
			p.savePerson(new Person("111", "张三", 0.0));
			p.savePerson(new Person("222", "李四", 0.0));
			VisitingRecord record = new VisitingRecord();
			record.setId("1");
			record.setDisease(Parameter.DISEASE[Parameter.DISEASE_HEART]);
			record.setPersonCode("111");
			p.saveRecode(record);
			record = new VisitingRecord();
			record.setId("2");
			record.setDisease(Parameter.DISEASE[Parameter.DISEASE_DIGESTION]);
			record.setPersonCode("222");
			p.saveRecode(record);
			try {
				Persistence.saveToFile(FILE_NAME, p);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		p.print();
		return p;
	}

	public void init() {
		personMap = new HashMap();
		hospitalMap = new HashMap();
		medicineMap = new HashMap();
		recordMap = new HashMap();
		ratioRules = new ArrayList();
	}

	public void print() {
		Utils.print(this.personMap);
		Utils.print(this.hospitalMap);
		Utils.print(this.medicineMap);
		Utils.print(this.recordMap);
		Utils.print(this.ratioRules);

	}

	public static Persistence loadFromFile(String name) throws Exception {
		File file = new File(name);

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		Persistence p = (Persistence) ois.readObject();
		ois.close();
		return p;
	}

	private static void saveToFile(String name, Object o) throws Exception {
		File file = new File(name);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				file));
		oos.writeObject(o);
		oos.close();
	}
	public static void saveToFile() throws Exception {
		File file = new File(FILE_NAME);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				file));
		oos.writeObject(p);
		oos.close();
	}

	public List<Ratio> getRatioRules() {
		return ratioRules;
	}

	public void setRatioRules(List<Ratio> ratioRules) {
		this.ratioRules = ratioRules;
	}

	public VisitingRecord getRecode(String id) {
		return recordMap.get(id);
	}

	public void saveRecode(VisitingRecord p) {
		recordMap.put(p.getId(), p);
	}

	public void deleteRecode(String id) {
		recordMap.remove(id);
	}

	public Medicine getMedicine(String code) {
		return medicineMap.get(code);
	}

	public void saveMedicine(Medicine p) {
		medicineMap.put(p.getCode(), p);
	}

	public void deleteMedicine(String code) {
		medicineMap.remove(code);
	}

	public Person getPerson(String code) {
		return personMap.get(code);
	}

	public void savePerson(Person p) {
		personMap.put(p.getCode(), p);
	}

	public void deletePerson(String code) {
		personMap.remove(code);
	}

	public Hospital getHospital(String code) {
		return hospitalMap.get(code);
	}

	public void saveHospital(Hospital p) {
		hospitalMap.put(p.getCode(), p);
	}

	public void deleteHospital(String code) {
		hospitalMap.remove(code);
	}

}
