package it.polito.ezshop.testEZShop;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.Customer;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.Order;
import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.User;
import it.polito.ezshop.dataBase.BalanceOperationDB;
import it.polito.ezshop.dataBase.CustomerDB;
import it.polito.ezshop.dataBase.OrderDB;
import it.polito.ezshop.dataBase.ProductTypeDB;
import it.polito.ezshop.dataBase.SaleTransactionDB;
import it.polito.ezshop.dataBase.TicketEntryDB;
import it.polito.ezshop.dataBase.UserDB;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.BalanceOperationClass;
import it.polito.ezshop.model.CustomerClass;
import it.polito.ezshop.model.OrderClass;
import it.polito.ezshop.model.ProductTypeClass;
import it.polito.ezshop.model.SaleTransactionClass;
import it.polito.ezshop.model.TicketEntryClass;
import it.polito.ezshop.model.UserClass;

public class TestEZShopReset {

	@Test
	public void test1() throws UnauthorizedException, InvalidRoleException,InvalidPasswordException,InvalidUsernameException{
		CustomerDB.removeAllCustomers();
		EZShop shop=new EZShop();
		Customer c=new CustomerClass("mario","0000000000",1,0);
		CustomerDB.addCustomer(c.getId(), c);
		
		shop.reset();
		assertTrue(CustomerDB.getCustomers().isEmpty());
	}
	
	@Test
	public void test2() {
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 30, "minerale");
		ProductTypeDB.addProductType(0, p);
		EZShop shop=new EZShop();
		shop.reset();
		
		assertEquals(0,ProductTypeDB.getProducts().size());
	}
	@Test
	public void test3() {
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 30, "minerale");
		ProductTypeDB.addProductType(0, p);
		Order o=new OrderClass("6291041500213", 3, 10, "ISSUED");
		OrderDB.addOrder(1, 1, o);
		EZShop shop=new EZShop();
		shop.reset();
		assertEquals(0, OrderDB.getOrders().size());
		
	}
	@Test
	public void test4() {
		SaleTransaction sale=new SaleTransactionClass(1,0.2,30);
		SaleTransactionDB.addSaleTransaction(1, sale);
		EZShop shop=new EZShop();
		shop.reset();
		assertEquals(0, SaleTransactionDB.getSaleTransactions().size());
	}
	
	@Test
	public void test5() {
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 30, "minerale");
		ProductTypeDB.addProductType(0, p);
		TicketEntryClass t=new TicketEntryClass("6291041500213", 10);
		TicketEntryDB.addTicket(1, t);
		EZShop shop=new EZShop();
		shop.reset();
		assertEquals(0, TicketEntryDB.getTickets(1).size());
	}
	@Test
	public void test6() {
		BalanceOperation b=new BalanceOperationClass(LocalDate.now(), 10, "CREDIT");
		BalanceOperationDB.addBalanceOperation(1, b);
		EZShop shop=new EZShop();
		shop.reset();
		assertEquals(0, BalanceOperationDB.getBalanceOperations().size());
	}
	@Test
	public void test7() {
		User u=new UserClass("mario", "aaaa", "Administrator");
		UserDB.addUser(1, u);
		EZShop shop=new EZShop();
		shop.reset();
		assertEquals(0,UserDB.getUsers().size());
	}
	@Test (timeout=500)
	public void test8() {
		User u=new UserClass("mario", "aaaa", "Administrator");
		UserDB.addUser(1, u);
		BalanceOperation b=new BalanceOperationClass(LocalDate.now(), 10, "CREDIT");
		BalanceOperationDB.addBalanceOperation(1, b);
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 30, "minerale");
		ProductTypeDB.addProductType(0, p);
		TicketEntryClass t=new TicketEntryClass("6291041500213", 10);
		TicketEntryDB.addTicket(1, t);
		SaleTransaction sale=new SaleTransactionClass(1,0.2,30);
		SaleTransactionDB.addSaleTransaction(1, sale);
		Order o=new OrderClass("6291041500213", 3, 10, "ISSUED");
		OrderDB.addOrder(1, 1, o);
		Customer c=new CustomerClass("mario","0000000000",1,0);
		CustomerDB.addCustomer(c.getId(), c);
		EZShop shop=new EZShop();
		shop.reset();
		assertEquals(0,UserDB.getUsers().size());
	}
	
}