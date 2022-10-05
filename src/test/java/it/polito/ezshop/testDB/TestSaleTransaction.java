package it.polito.ezshop.testDB;

import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.Test;

import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.dataBase.SaleTransactionDB;
import it.polito.ezshop.model.SaleTransactionClass;

public class TestSaleTransaction {

	@Test
	public void testAddTransaction() {
		SaleTransactionDB.removeAllSaleTransactions();
		
		SaleTransaction St=new SaleTransactionClass(1,0.3,50);
		SaleTransactionDB.addSaleTransaction(St.getTicketNumber(), St);
		TreeMap<Integer, SaleTransaction> resMap=SaleTransactionDB.getSaleTransactions();
		
		assertTrue(resMap.containsKey(St.getTicketNumber()));
		
		SaleTransactionDB.removeAllSaleTransactions();
	}

	
	@Test
	public void testRemoveTransaction() {
		SaleTransactionDB.removeAllSaleTransactions();
		
		SaleTransactionDB.removeAllSaleTransactions();
		SaleTransaction St=new SaleTransactionClass(1,0.3,50);
		SaleTransactionDB.addSaleTransaction(St.getTicketNumber(), St);
		SaleTransactionDB.removeSaleTransaction(St.getTicketNumber());
		
		TreeMap<Integer, SaleTransaction> resMap=SaleTransactionDB.getSaleTransactions();
		assertFalse(resMap.containsKey(St.getTicketNumber()));
		
		SaleTransactionDB.removeAllSaleTransactions();
	}
	
	@Test
	public void testGetByTicket() {
		SaleTransactionDB.removeAllSaleTransactions();
		
		SaleTransactionDB.removeAllSaleTransactions();
		SaleTransaction St=new SaleTransactionClass(1,0.3,50);
		SaleTransactionDB.addSaleTransaction(St.getTicketNumber(), St);
		
		SaleTransaction st2=SaleTransactionDB.getSaleTransactionByTicket(St.getTicketNumber());
		
		assertEquals(st2.getTicketNumber(), St.getTicketNumber());
		assertEquals(st2.getDiscountRate(), St.getDiscountRate(),0);
		assertEquals(st2.getPrice(), St.getPrice(),0);
		
		SaleTransactionDB.removeAllSaleTransactions();
	}
	
	@Test
	public void testUpdateTransaction() {
		SaleTransactionDB.removeAllSaleTransactions();
		
		SaleTransaction St=new SaleTransactionClass(1,0.3,50);
		SaleTransactionDB.addSaleTransaction(St.getTicketNumber(), St);
		SaleTransactionDB.updateSaleTransaction(1, 30.2);
		
		SaleTransaction st2=SaleTransactionDB.getSaleTransactionByTicket(St.getTicketNumber());
		assertEquals(80.2,st2.getPrice(),0);
		
		SaleTransactionDB.removeAllSaleTransactions();
	}
}
