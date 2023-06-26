package test.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.Account;
import javafx.beans.property.SimpleStringProperty;

public class AccountTest {
	private Account a = new Account();
	private Account b = new Account();
	@Before
	public void setUp() throws Exception {
		b.setName(new SimpleStringProperty("a"));
		a.setName(new SimpleStringProperty("a"));
		System.out.println("a");
	}

	@Test
	public void testHashCode() {
		
		assertEquals(true, a.hashCode()==b.hashCode());
	}

	@Test
	public void testEqualsObject() {
		assertEquals(true, a.equals(b));
	}
}
