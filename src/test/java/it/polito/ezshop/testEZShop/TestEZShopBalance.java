package it.polito.ezshop.testEZShop;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.dataBase.BalanceOperationDB;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.BalanceOperationClass;

public class TestEZShopBalance {
	
	
	@Test (expected=UnauthorizedException.class)
	public void testRecordBalanceUpdate1() throws UnauthorizedException{
		EZShop shop = new EZShop();
		BalanceOperationDB.removeAllBalanceOperations();
		shop.recordBalanceUpdate(50);
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test 
	public void testRecordBalanceUpdate2() throws UnauthorizedException,InvalidUsernameException,InvalidRoleException,InvalidPasswordException{
		EZShop shop = new EZShop();
		BalanceOperationDB.removeAllBalanceOperations();
		shop.createUser("mario", "pwd", "Administrator");
		shop.login("mario", "pwd");
		shop.recordBalanceUpdate(50);
		assertEquals(1, BalanceOperationDB.getBalanceOperations().size());
		String type=BalanceOperationDB.getBalanceOperations().get(1).getType();
		assertEquals("CREDIT", type);
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test 
	public void testRecordBalanceUpdate3() throws UnauthorizedException,InvalidUsernameException,InvalidRoleException,InvalidPasswordException{
		EZShop shop = new EZShop();
		BalanceOperationDB.removeAllBalanceOperations();
		shop.createUser("mario", "pwd", "Administrator");
		shop.login("mario", "pwd");
		shop.recordBalanceUpdate(50);
		shop.recordBalanceUpdate(-3);

		assertEquals(2, BalanceOperationDB.getBalanceOperations().size());
		String type=BalanceOperationDB.getBalanceOperations().get(2).getType();
		assertEquals("DEBIT", type);
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test 
	public void testGetCreditsDebits1() throws UnauthorizedException,InvalidUsernameException,InvalidRoleException,InvalidPasswordException{
		EZShop shop = new EZShop();
		BalanceOperationDB.removeAllBalanceOperations();
		shop.createUser("mario", "pwd", "Administrator");
		shop.login("mario", "pwd");
		shop.recordBalanceUpdate(50);
		shop.recordBalanceUpdate(-3);

		java.util.List<BalanceOperation> lista = shop.getCreditsAndDebits(null, null);
		assertEquals(2,lista.size());
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test 
	public void testGetCreditsDebits2() throws UnauthorizedException,InvalidUsernameException,InvalidRoleException,InvalidPasswordException{
		EZShop shop = new EZShop();
		BalanceOperationDB.removeAllBalanceOperations();
		shop.createUser("mario", "pwd", "Administrator");
		shop.login("mario", "pwd");
		shop.recordBalanceUpdate(50);
		shop.recordBalanceUpdate(-3);

		java.util.List<BalanceOperation> lista = shop.getCreditsAndDebits(null, null);
		assertEquals(2,lista.size());
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test (timeout=500)//Scenario 9.1
	public void testGetCreditsDebits3() throws UnauthorizedException,InvalidUsernameException,InvalidRoleException,InvalidPasswordException{
		EZShop shop = new EZShop();
		BalanceOperationDB.removeAllBalanceOperations();
		shop.createUser("mario", "pwd", "Administrator");
		shop.login("mario", "pwd");
		BalanceOperation b1=new BalanceOperationClass(LocalDate.of(1997, 6, 3), 10, "CREDIT");
		BalanceOperation b2=new BalanceOperationClass(LocalDate.of(1998, 6, 3), 10, "CREDIT");
		BalanceOperation b3=new BalanceOperationClass(LocalDate.of(1999, 6, 3), 10, "DEBIT");
		BalanceOperationDB.addBalanceOperation(1, b1);
		BalanceOperationDB.addBalanceOperation(2, b2);
		BalanceOperationDB.addBalanceOperation(3, b3);

		java.util.List<BalanceOperation> lista = shop.getCreditsAndDebits(LocalDate.of(1998, 5, 1), null);
		assertEquals(2,lista.size());
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test (timeout=500)//Scenario 9.1
	public void testGetCreditsDebits4() throws UnauthorizedException,InvalidUsernameException,InvalidRoleException,InvalidPasswordException{
		EZShop shop = new EZShop();
		BalanceOperationDB.removeAllBalanceOperations();
		shop.createUser("mario", "pwd", "Administrator");
		shop.login("mario", "pwd");
		BalanceOperation b1=new BalanceOperationClass(LocalDate.of(1997, 6, 3), 10, "CREDIT");
		BalanceOperation b2=new BalanceOperationClass(LocalDate.of(1998, 6, 3), 10, "CREDIT");
		BalanceOperation b3=new BalanceOperationClass(LocalDate.of(1999, 6, 3), 10, "DEBIT");
		BalanceOperationDB.addBalanceOperation(1, b1);
		BalanceOperationDB.addBalanceOperation(2, b2);
		BalanceOperationDB.addBalanceOperation(3, b3);

		java.util.List<BalanceOperation> lista = shop.getCreditsAndDebits(null, LocalDate.of(1998, 10, 20));
		assertEquals(2,lista.size());
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test (timeout=500)//Scenario 9.1
	public void testGetCreditsDebits5() throws UnauthorizedException,InvalidUsernameException,InvalidRoleException,InvalidPasswordException{
		EZShop shop = new EZShop();
		BalanceOperationDB.removeAllBalanceOperations();
		shop.createUser("mario", "pwd", "Administrator");
		shop.login("mario", "pwd");
		BalanceOperation b1=new BalanceOperationClass(LocalDate.of(1997, 6, 3), 10, "CREDIT");
		BalanceOperation b2=new BalanceOperationClass(LocalDate.of(1998, 6, 3), 10, "CREDIT");
		BalanceOperation b3=new BalanceOperationClass(LocalDate.of(1999, 6, 3), 10, "DEBIT");
		BalanceOperationDB.addBalanceOperation(1, b1);
		BalanceOperationDB.addBalanceOperation(2, b2);
		BalanceOperationDB.addBalanceOperation(3, b3);

		java.util.List<BalanceOperation> lista = shop.getCreditsAndDebits(LocalDate.of(1990, 5, 1), LocalDate.of(2000, 10, 20));
		assertEquals(3,lista.size());
		
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test (timeout=500)
	public void testGetCreditsDebits6() throws UnauthorizedException,InvalidUsernameException,InvalidRoleException,InvalidPasswordException{
		EZShop shop = new EZShop();
		BalanceOperationDB.removeAllBalanceOperations();
		shop.createUser("mario", "pwd", "Administrator");
		shop.login("mario", "pwd");
		shop.recordBalanceUpdate(10);
		shop.recordBalanceUpdate(510);
		shop.recordBalanceUpdate(-10.5);
		assertEquals(10+510-10.5, shop.computeBalance(),0);
		BalanceOperationDB.removeAllBalanceOperations();
	}
}
