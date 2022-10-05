package it.polito.ezshop.testClass;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import it.polito.ezshop.model.BalanceOperationClass;
import it.polito.ezshop.data.BalanceOperation;


public class TestBalanceOperation {

	@Test
	public void testBalanceId() {
		LocalDate date = LocalDate.of(2020, 1, 8);
		BalanceOperation b=new BalanceOperationClass(date, 45, "debit");
		b.setBalanceId(1);
		int q=b.getBalanceId();
		assertEquals(1, q);
	}
	@Test
	public void testDate() {
		LocalDate date = LocalDate.of(2020, 1, 8);
		BalanceOperation b=new BalanceOperationClass(date, 45, "debit");
		LocalDate newDate=LocalDate.of(2021, 5 ,19);
		b.setDate(newDate);
		LocalDate q=b.getDate();
		assertEquals(newDate, q);
	}
	@Test
	public void testMoney() {
		LocalDate date = LocalDate.of(2020, 1, 8);
		BalanceOperation b=new BalanceOperationClass(date, 45, "debit");
		b.setMoney(23.5);
		double q=b.getMoney();
		assertEquals(23.5,q,0);

		LocalDate date1 = LocalDate.of(2020, 1, 8);
		BalanceOperation b1=new BalanceOperationClass(date1, 45, "debit");
		b1.setMoney(5);
		double q1=b1.getMoney();
		assertEquals(5,q1,0);

		LocalDate date2 = LocalDate.of(2020, 1, 8);
		BalanceOperation b2=new BalanceOperationClass(date2, 45, "debit");
		b2.setMoney(-5);
		double q2=b2.getMoney();
		assertEquals(-5,q2,0);


		LocalDate date3 = LocalDate.of(2020, 1, 8);
		BalanceOperation b3=new BalanceOperationClass(date3, 45, "debit");
		b3.setMoney(-23);
		double q3=b3.getMoney();
		assertEquals(-23,q3,0);



	}
	@Test
	public void testType() {
		LocalDate date = LocalDate.of(2020, 1, 8);
		BalanceOperation b=new BalanceOperationClass(date, 45, "debit");
		b.setType("credit");
		String q=b.getType();
		assertEquals("credit", q);
	}
}
