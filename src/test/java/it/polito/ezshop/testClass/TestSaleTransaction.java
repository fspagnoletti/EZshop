package it.polito.ezshop.testClass;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.SaleTransactionClass;
import it.polito.ezshop.model.TicketEntryClass;

public class TestSaleTransaction {

	@Test
	public void testPrice() {
		TicketEntry ticket=new TicketEntryClass("6291041500213", 10);
		List<TicketEntry> t=new ArrayList<TicketEntry>();
		t.add(ticket);
		SaleTransaction sale=new SaleTransactionClass(t,0.3,30);
		sale.setPrice(50.7);
		double price=sale.getPrice();
		assertEquals(50.7, price,0);


		TicketEntry ticket1=new TicketEntryClass("6291041500212", 10);
		List<TicketEntry> t1=new ArrayList<TicketEntry>();
		t1.add(ticket1);
		SaleTransaction sale1=new SaleTransactionClass(t1,0.3,30);
		sale1.setPrice(5);
		double price1=sale1.getPrice();
		assertEquals(5, price1,0);


		TicketEntry ticket2=new TicketEntryClass("6291041500215", 10);
		List<TicketEntry> t2=new ArrayList<TicketEntry>();
		t.add(ticket2);
		SaleTransaction sale2=new SaleTransactionClass(t2,0.3,30);
		sale2.setPrice(-5);
		double price2=sale2.getPrice();
		assertEquals(-5, price2,0);


		TicketEntry ticket3=new TicketEntryClass("6291041500217", 10);
		List<TicketEntry> t3=new ArrayList<TicketEntry>();
		t3.add(ticket3);
		SaleTransaction sale3=new SaleTransactionClass(t3,0.3,30);
		sale3.setPrice(-4.46);
		double price3=sale3.getPrice();
		assertEquals(-4.46, price3,0);



	}
	
	@Test
	public void testDiscountRate() {
		TicketEntry ticket=new TicketEntryClass("6291041500213", 10);
		List<TicketEntry> t=new ArrayList<TicketEntry>();
		t.add(ticket);
		SaleTransaction sale=new SaleTransactionClass(t,0.3,30);
		sale.setDiscountRate(0.5);
		double discount=sale.getDiscountRate();
		assertEquals(0.5, discount,0);
	}
	@Test
	public void testTicketNumber() {
		TicketEntry ticket=new TicketEntryClass("6291041500213", 10);
		List<TicketEntry> t=new ArrayList<TicketEntry>();
		t.add(ticket);
		SaleTransaction sale=new SaleTransactionClass(t,0.3,30);
		sale.setTicketNumber(1);
		double n=sale.getTicketNumber();
		assertEquals(1, n,0);
	}
	@Test
	public void testTicketsList() {
		TicketEntry ticket=new TicketEntryClass("6291041500213", 10);
		List<TicketEntry> t=new ArrayList<TicketEntry>();
		t.add(ticket);
		SaleTransaction sale=new SaleTransactionClass(t,0.3,30);
		TicketEntry newT=new TicketEntryClass("6291041500212", 10);
		t.add(newT);
		sale.setEntries(t);
		List<TicketEntry> list=sale.getEntries();
		assertEquals(list, t);
	}

}
