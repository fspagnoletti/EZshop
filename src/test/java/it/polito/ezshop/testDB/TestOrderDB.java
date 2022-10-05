package it.polito.ezshop.testDB;

import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.Test;

import it.polito.ezshop.data.Order;
import it.polito.ezshop.dataBase.OrderDB;
import it.polito.ezshop.model.OrderClass;

public class TestOrderDB {

	@Test
	public void testAddOrder() {
		OrderDB.removeAllOrders();
		Order o=new OrderClass("6291041500213",5.1,10,"ISSUED");
		o.setOrderId(1);
		OrderDB.addOrder(1, 1, o);		
		TreeMap<Integer, Order> res=OrderDB.getOrders();
		
		assertEquals(res.size(), 1);
		assertEquals(res.get(1).getProductCode(),"6291041500213");
		assertEquals(res.get(1).getPricePerUnit(),5.1,0);
		assertEquals(res.get(1).getStatus(),"ISSUED");
		assertEquals(res.get(1).getQuantity(),10);
		OrderDB.removeAllOrders();
	}
	
	@Test
	public void testRemoveOrder() {
		OrderDB.removeAllOrders();
		
		Order o=new OrderClass("6291041500213",5.1,10,"ISSUED");
		o.setOrderId(1);
		OrderDB.addOrder(1, 1, o);		
		OrderDB.removeOrder(1);
		
		TreeMap<Integer, Order> res=OrderDB.getOrders();
		
		assertEquals(res.size(), 0);
		
		OrderDB.removeAllOrders();
	}
	
	@Test
	public void testUpdateOrderToCompleted() {
		OrderDB.removeAllOrders();
		
		Order o=new OrderClass("6291041500213",5.1,10,"PAYED");
		o.setOrderId(1);
		OrderDB.addOrder(1, 1, o);	
		OrderDB.updateOrderToCompleted(1);
		
		TreeMap<Integer, Order> res=OrderDB.getOrders();
		
		assertEquals(res.get(1).getStatus(),"COMPLETED");
		
		OrderDB.removeAllOrders();
	}
	
	@Test
	public void testUpdateOrderToPayed() {
		OrderDB.removeAllOrders();
		
		Order o=new OrderClass("6291041500213",5.1,10,"ISSUED");
		o.setOrderId(1);
		OrderDB.addOrder(1, 1, o);	
		OrderDB.updateOrderToPayed(1);
		
		TreeMap<Integer, Order> res=OrderDB.getOrders();
		
		assertEquals(res.get(1).getStatus(),"PAYED");
		
		OrderDB.removeAllOrders();
	}
	
	@Test
	public void testRemoveAllOrders() {
		OrderDB.removeAllOrders();
		
		Order o=new OrderClass("6291041500213",5.1,10,"ISSUED");
		o.setOrderId(1);
		OrderDB.addOrder(1, 1, o);	
		OrderDB.updateOrderToPayed(1);
		OrderDB.removeAllOrders();
		
		TreeMap<Integer, Order> res=OrderDB.getOrders();
		assertEquals(res.size(), 0);
	}

}
