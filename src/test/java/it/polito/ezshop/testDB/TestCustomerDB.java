package it.polito.ezshop.testDB;

import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.Test;

import it.polito.ezshop.data.Customer;
import it.polito.ezshop.dataBase.CustomerDB;
import it.polito.ezshop.model.CustomerClass;

public class TestCustomerDB {

	@Test
	public void testAddCustomer() {
		CustomerDB.removeAllCustomers();
		
		Customer c=new CustomerClass("Maurizio","0000000000",1,300);
		CustomerDB.addCustomer(1, c);
		Customer c2=CustomerDB.getCustomers().get(1);
		
		assertEquals(c.getId(), c2.getId());
		assertEquals(c.getCustomerName(), c2.getCustomerName());
		assertEquals(c.getCustomerCard(), c2.getCustomerCard());
		assertEquals(c.getPoints(), c2.getPoints());
		
		CustomerDB.removeAllCustomers();
	}

	
	@Test
	public void testRemoveCustomer() {
		CustomerDB.removeAllCustomers();
		
		Customer c=new CustomerClass("Maurizio","0000000000",1,300);
		CustomerDB.addCustomer(1, c);
		CustomerDB.removeCustomer(1);
		TreeMap<Integer, Customer> map=CustomerDB.getCustomers();
		
		assertEquals(map.size(), 0);
		
		CustomerDB.removeAllCustomers();
	}
	
	@Test
	public void testUpdateName() {
		CustomerDB.removeAllCustomers();
		
		Customer c=new CustomerClass("Maurizio","0000000000",1,300);
		CustomerDB.addCustomer(1, c);
		CustomerDB.updateCustomerName(1, "Giuseppe");
		Customer c2=CustomerDB.getCustomers().get(1);
		
		assertEquals(c2.getCustomerName(), "Giuseppe");
		
		CustomerDB.removeAllCustomers();
	}
	
	@Test
	public void testUpdateCard() {
		CustomerDB.removeAllCustomers();
		
		Customer c=new CustomerClass("Maurizio","0000000000",1,300);
		CustomerDB.addCustomer(1, c);
		CustomerDB.updateCustomerCard(1, "1111111111");
		Customer c2=CustomerDB.getCustomers().get(1);
		
		assertEquals(c2.getCustomerCard(), "1111111111");
		
		CustomerDB.removeAllCustomers();
	}
	
	@Test
	public void testUpdatePoints() {
		CustomerDB.removeAllCustomers();
		
		Customer c=new CustomerClass("Maurizio","0000000000",1,300);
		CustomerDB.addCustomer(1, c);
		CustomerDB.updateCustomerPoints(1,100);
		Customer c2=CustomerDB.getCustomers().get(1);
		int points=c2.getPoints();
		
		assertEquals(points,100);
		
		CustomerDB.removeAllCustomers();
	}
	
	@Test
	public void testMaxCard() {
		CustomerDB.removeAllCustomers();
		
		Customer c1=new CustomerClass("Maurizio","0000000000",1,300);
		Customer c2=new CustomerClass("Marco","0000000011",2,300);
		CustomerDB.addCustomer(1, c1);
		CustomerDB.addCustomer(2, c2);
		String max=CustomerDB.maxCustomerCard();
		
		assertEquals(max,"0000000011");
		
		CustomerDB.removeAllCustomers();
	}
	
	@Test
	public void testUpdateCustomer() {
		CustomerDB.removeAllCustomers();
		
		Customer c=new CustomerClass("Maurizio","0000000000",1,300);
		CustomerDB.addCustomer(1, c);
		CustomerDB.updateCustomer(1, "Marco","1111111111");
		Customer c2=CustomerDB.getCustomers().get(1);
		
		assertEquals(c2.getCustomerName(), "Marco");
		assertEquals(c2.getCustomerCard(), "1111111111");
		
		CustomerDB.removeAllCustomers();
	}
}
