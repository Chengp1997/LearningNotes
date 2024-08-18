package test.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.Customer;
import javafx.beans.property.SimpleStringProperty;

public class CustomerTest {
	private Customer a = new Customer();
	private Customer b = new Customer();
	@Before
	public void setUp() throws Exception {
		a.setName(new SimpleStringProperty("a"));
		b.setName(new SimpleStringProperty("a"));
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
