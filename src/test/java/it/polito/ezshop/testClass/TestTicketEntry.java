package it.polito.ezshop.testClass;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.TicketEntryClass;

public class TestTicketEntry {

	@Test
	public void testId() {
		TicketEntry t=new TicketEntryClass("6291041500213", 10);
		t.setBarCode("6291041500312");
		String code=t.getBarCode();
		assertEquals(code, "6291041500312");
	}
	
	@Test
	public void testProductDescription() {
		TicketEntry t=new TicketEntryClass("6291041500213", 10);
		t.setProductDescription("acqua");
		String description=t.getProductDescription();
		assertEquals(description, "acqua");
	}

	@Test
	public void testAmount() {
		TicketEntry t=new TicketEntryClass("6291041500213", 10);
		t.setAmount(4);
		int a=t.getAmount();
		assertEquals(4, a);
	}
	
	@Test
	public void testPricePerUnit() {
		TicketEntry t=new TicketEntryClass("6291041500213", 10);
		t.setPricePerUnit(4.7);
		double a=t.getPricePerUnit();
		assertEquals(4.7, a,0);
	}
	
	@Test
	public void testDiscountRate() {
		TicketEntry t=new TicketEntryClass("6291041500213", 10);
		t.setDiscountRate(0.5);
		double a=t.getDiscountRate();
		assertEquals(0.5, a,0);
	}
}
