package it.polito.ezshop.testDB;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.TreeMap;

import org.junit.Test;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.dataBase.BalanceOperationDB;
import it.polito.ezshop.model.BalanceOperationClass;

public class TestBalanceOperationDB {

	@Test
	public void testAddBalanceOp() {
		BalanceOperationDB.removeAllBalanceOperations();
		
		BalanceOperation b=new BalanceOperationClass(LocalDate.of(21, 5, 19), 50, "credit");
		b.setBalanceId(1);
		BalanceOperationDB.addBalanceOperation(1, b);
		BalanceOperation b2=BalanceOperationDB.getBalanceOperations().get(1);
		
		assertNotNull(b2);
		assertEquals(b.getBalanceId(), b2.getBalanceId());
		assertEquals(b.getDate(), b2.getDate());
		assertEquals(b.getMoney(), b2.getMoney(),0);
		assertEquals(b.getType(), b2.getType());
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test
	public void testRemoveBalanceOp() {
		BalanceOperationDB.removeAllBalanceOperations();
		
		BalanceOperation b=new BalanceOperationClass(LocalDate.of(21, 5, 19), 50, "credit");
		b.setBalanceId(1);
		BalanceOperationDB.addBalanceOperation(1, b);
		
		BalanceOperationDB.removeBalanceOperation(1);
		TreeMap<Integer, BalanceOperation> res=BalanceOperationDB.getBalanceOperations();
		
		assertEquals(res.size(),0);
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test
	public void testBalanceOpByDate() {
		BalanceOperationDB.removeAllBalanceOperations();
		
		BalanceOperation b=new BalanceOperationClass(LocalDate.of(2021, 5, 19), 50, "credit");
		b.setBalanceId(1);
		BalanceOperationDB.addBalanceOperation(1, b);
		BalanceOperation b2=new BalanceOperationClass(LocalDate.of(2021, 4, 1), 50, "credit");
		b2.setBalanceId(2);
		BalanceOperationDB.addBalanceOperation(2, b2);
		BalanceOperation b3=new BalanceOperationClass(LocalDate.of(2021, 7, 10), 50, "credit");
		b3.setBalanceId(3);
		BalanceOperationDB.addBalanceOperation(3, b3);
		
		TreeMap<Integer, BalanceOperation> res=BalanceOperationDB.getBalanceOperationsByDates(LocalDate.of(2021, 4, 30), LocalDate.of(2021, 9, 1));
		
		
		assertTrue(res.containsKey(1));
		assertTrue(res.containsKey(3));
		
		BalanceOperationDB.removeAllBalanceOperations();
	}

	
	@Test
	public void testBalanceOpByFrom() {
		BalanceOperationDB.removeAllBalanceOperations();
		
		BalanceOperation b=new BalanceOperationClass(LocalDate.of(2021, 5, 19), 50, "credit");
		b.setBalanceId(1);
		BalanceOperationDB.addBalanceOperation(1, b);
		BalanceOperation b2=new BalanceOperationClass(LocalDate.of(2021, 4, 1), 50, "credit");
		b2.setBalanceId(2);
		BalanceOperationDB.addBalanceOperation(2, b2);
		BalanceOperation b3=new BalanceOperationClass(LocalDate.of(2021, 7, 10), 50, "credit");
		b3.setBalanceId(3);
		BalanceOperationDB.addBalanceOperation(3, b3);
		
		TreeMap<Integer, BalanceOperation> res=BalanceOperationDB.getBalanceOperationsByFrom(LocalDate.of(2021, 5, 1));
		
		
		assertTrue(res.containsKey(1));
		assertTrue(res.containsKey(3));
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test
	public void testBalanceOpByTo() {
		BalanceOperationDB.removeAllBalanceOperations();
		
		BalanceOperation b=new BalanceOperationClass(LocalDate.of(2021, 5, 19), 50, "credit");
		b.setBalanceId(1);
		BalanceOperationDB.addBalanceOperation(1, b);
		BalanceOperation b2=new BalanceOperationClass(LocalDate.of(2021, 4, 1), 50, "credit");
		b2.setBalanceId(2);
		BalanceOperationDB.addBalanceOperation(2, b2);
		BalanceOperation b3=new BalanceOperationClass(LocalDate.of(2021, 7, 10), 50, "credit");
		b3.setBalanceId(3);
		BalanceOperationDB.addBalanceOperation(3, b3);
		
		TreeMap<Integer, BalanceOperation> res=BalanceOperationDB.getBalanceOperationsByTo(LocalDate.of(2021, 6, 1));
		
		
		assertTrue(res.containsKey(1));
		assertTrue(res.containsKey(2));
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test
	public void testSumBalance() {
		BalanceOperationDB.removeAllBalanceOperations();
		
		BalanceOperation b=new BalanceOperationClass(LocalDate.of(2021, 5, 19), 50.4, "credit");
		b.setBalanceId(1);
		BalanceOperationDB.addBalanceOperation(1, b);
		BalanceOperation b2=new BalanceOperationClass(LocalDate.of(2021, 4, 1), 50, "credit");
		b2.setBalanceId(2);
		BalanceOperationDB.addBalanceOperation(2, b2);
		BalanceOperation b3=new BalanceOperationClass(LocalDate.of(2021, 7, 10), 50, "credit");
		b3.setBalanceId(3);
		BalanceOperationDB.addBalanceOperation(3, b3);
		
		double sum=BalanceOperationDB.balanceSum();
		
		assertEquals(sum, 150.4,0);
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test
	public void testRemoveAll() {
		BalanceOperationDB.removeAllBalanceOperations();
		
		BalanceOperation b=new BalanceOperationClass(LocalDate.of(2021, 5, 19), 50.4, "credit");
		b.setBalanceId(1);
		BalanceOperationDB.addBalanceOperation(1, b);
		BalanceOperation b2=new BalanceOperationClass(LocalDate.of(2021, 4, 1), 50, "credit");
		b2.setBalanceId(2);
		BalanceOperationDB.addBalanceOperation(2, b2);
		BalanceOperation b3=new BalanceOperationClass(LocalDate.of(2021, 7, 10), 50, "credit");
		b3.setBalanceId(3);
		BalanceOperationDB.addBalanceOperation(3, b3);
		
		BalanceOperationDB.removeAllBalanceOperations();
		
		TreeMap<Integer, BalanceOperation> res=BalanceOperationDB.getBalanceOperations();
		assertEquals(res.size(), 0);
		
	}
	
}
