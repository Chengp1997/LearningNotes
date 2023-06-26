package persistence;

public class Main {
	public static void main(String[] args) throws Exception {
		Persistence p = Persistence.getObjs();
		Persistence.saveToFile();
	}
}
