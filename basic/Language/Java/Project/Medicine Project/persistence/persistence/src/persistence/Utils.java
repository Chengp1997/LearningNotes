package persistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Utils {
	public static void print(HashMap m) {
		Set set = m.entrySet();
		Iterator i = set.iterator();
		while(i.hasNext()){
			System.out.println(m.get(i.next()).toString());
		}
	}
	public static void print(List list) {
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i).toString());
		}
	}
	public static void main(String[] args){
		HashMap m = new HashMap();
		m.put("a", "1");
		m.put("b", "2");
		m.put("c", "3");
		print(m);
	}
}
