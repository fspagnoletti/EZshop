package it.polito.ezshop.testDB;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.dataBase.ProductTypeDB;
import it.polito.ezshop.dataBase.TicketEntryDB;
import it.polito.ezshop.model.ProductTypeClass;
import it.polito.ezshop.model.TicketEntryClass;

public class TestTicketEntryDB {

	@Test
	public void testAddTicket() {
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		
		ProductType p=new ProductTypeClass("acqua", "2332543211519", 0.3, "minerale");
		p.setId(1);
		ProductTypeDB.addProductType(1, p);
		TicketEntryClass t=new TicketEntryClass("2332543211519", 10);
		t.setId(1);
		TicketEntryDB.addTicket(1, t);
		ArrayList<TicketEntryClass> res=TicketEntryDB.getTickets(1);
		TicketEntryClass t2=res.get(0);
		
		
		assertEquals(t2.getBarCode(), t.getBarCode());
		assertEquals(t2.getDiscountRate(), t.getDiscountRate(),0);
		
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
	}
	
	@Test
	public void testRemoveTicket() {
		TicketEntryDB.removeAllTickets();
		
		TicketEntryClass t=new TicketEntryClass("2332543211519", 10);
		t.setId(1);
		TicketEntryDB.addTicket(1, t);
		TicketEntryDB.removeTicket(1);
		ArrayList<TicketEntryClass> res=TicketEntryDB.getTickets(1);
		
		assertEquals(res.size(), 0);
		TicketEntryDB.removeAllTickets();
	} 
	
	@Test
	public void testUpdateTicket() {
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		
		ProductType p=new ProductTypeClass("acqua", "2332543211519", 0.3, "minerale");
		p.setId(1);
		ProductTypeDB.addProductType(1, p);
		TicketEntryClass t=new TicketEntryClass("2332543211519", 10);
		t.setId(1);
		t.setAmount(50);
		TicketEntryDB.addTicket(1, t);
		TicketEntryDB.updateTicketEntry(100, t);
		ArrayList<TicketEntryClass> res=TicketEntryDB.getTickets(1);
		
		assertEquals(100,res.get(0).getAmount());
		TicketEntryDB.removeAllTickets();
	} 
	
}
