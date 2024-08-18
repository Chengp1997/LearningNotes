package data;
import database.Data;
import javafx.collections.ObservableList;

public class AppData {
	/**
	 * list of customer data
	 */
	private static ObservableList<Customer> cusData;
	/**
	 * find data in database
	 */
	static{
		cusData = Data.get();
	}
	/**
	 * 
	 * @return the list of data
	 */
	public static ObservableList<Customer> getCusData() {
		
		return cusData;
	}
	/**
	 * use to save the data
	 * @param cusData to initialize
	 */
	public static void saveCusData(ObservableList<Customer> cusData) {
		AppData.cusData = cusData;
	}
	
}
