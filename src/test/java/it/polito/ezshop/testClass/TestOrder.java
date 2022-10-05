package it.polito.ezshop.testClass;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import it.polito.ezshop.data.Order;
import it.polito.ezshop.model.OrderClass;

public class TestOrder {

	@Test
	public void testBalanceId() {
		Order o=new OrderClass("6291041500213",5.4,10,"ISSUED");
		o.setBalanceId(1);
		int id=o.getBalanceId();
		assertEquals(1, id);

		Order o1=new OrderClass("6291041500215",5.4,10,"ISSUED");
		o1.setBalanceId(-100);
		int id1=o1.getBalanceId();
		assertEquals(-100, id1);
	}
	
	@Test
	public void testProductCode() {
		Order o=new OrderClass("6291041500213",5.4,10,"ISSUED");
		o.setProductCode("6291041500312");
		String p=o.getProductCode();
		assertEquals("6291041500312", p);
	}
	
	@Test
	public void testPricePerUnit() {
		Order o=new OrderClass("6291041500213",5.4,10,"ISSUED");
		o.setPricePerUnit(1.67);
		double q=o.getPricePerUnit();
		assertEquals(1.67, q,0);
	}
	
	@Test
	public void testQuantity() {
		Order o=new OrderClass("6291041500213",5.4,10,"ISSUED");
		o.setQuantity(5);
		int q=o.getQuantity();
		assertEquals(5,q);
	}
	
	@Test
	public void testStatus() {
		Order o=new OrderClass("6291041500213",5.4,10,"ISSUED");
		o.setStatus("ORDERED");
		String status=o.getStatus();
		assertEquals("ORDERED", status);
	}
	
	@Test
	public void testOrderId() {
		Order o=new OrderClass("6291041500213",5.4,10,"ISSUED");
		o.setOrderId(1);
		int id=o.getOrderId();
		assertEquals(1, id);

		Order o1=new OrderClass("6291041500215",5.4,10,"ISSUED");
		o1.setOrderId(-100);
		int id1=o1.getOrderId();
		assertEquals(-100, id1);
	}
	
}
