package it.polito.ezshop.testEZShop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.Order;
import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.dataBase.BalanceOperationDB;
import it.polito.ezshop.dataBase.OrderDB;
import it.polito.ezshop.dataBase.ProductDB;
import it.polito.ezshop.dataBase.ProductTypeDB;
import it.polito.ezshop.model.BalanceOperationClass;
import it.polito.ezshop.model.ProductTypeClass;



public class TestEZShopOrders {
	
	EZShop es=new EZShop();
	
	
	@Test (expected=InvalidProductCodeException.class)
	public void shouldIssueOrder2() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException{
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", null , 3.4, "minerale");
		es.issueOrder(p.getBarCode(), 2, 2.0);
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test (expected=InvalidQuantityException.class)
	public void shouldIssueOrder3() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException{
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "6291041500213" , 3.4, "minerale");
		es.issueOrder(p.getBarCode(), -2, 2.0);
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test (expected=InvalidPricePerUnitException.class)
	public void shouldIssueOrder4() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException{
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "6291041500213" , 3.4, "minerale");
		es.issueOrder(p.getBarCode(), 2, -2.0);
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	
	@Test //Not enough money in balance 
	public void shouldpayOrderFor1() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "6291041500213", 3.4, "minerale");
		int id = es.payOrderFor(p.getBarCode(), 2, 2.0);
		assertEquals(-1, id);
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	@Test (expected=InvalidProductCodeException.class)
	public void shouldpayOrderFor2() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "500213", 3.4, "minerale");
		int id = es.payOrderFor(p.getBarCode(), 2, 2.0);
		assertEquals(1, id);
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	@Test (expected=InvalidPricePerUnitException.class)
	public void shouldpayOrderFor3() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "6291041500213", 3.4, "minerale");
		int id = es.payOrderFor(p.getBarCode(), 2, -2.0);
		assertEquals(1, id);
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	@Test (expected=InvalidQuantityException.class)
	public void shouldpayOrderFor4() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "6291041500213", 3.4, "minerale");
		int id = es.payOrderFor(p.getBarCode(), -2, 2.0);
		assertEquals(1, id);
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	@Test (timeout=500)//Nominal from ISSUE to PAYED
	public void shouldpayOrder() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, 
	InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException, 
	InvalidOrderIdException, UnauthorizedException  {
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "6291041500213", 3.4, "minerale");
		int id = es.issueOrder(p.getBarCode(), 2, 2.0);
		assertTrue(es.payOrder(id));
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	@Test //Order already payed
	public void shouldpayOrder1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, 
	InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException, 
	InvalidOrderIdException, UnauthorizedException  {
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "6291041500213", 3.4, "minerale");
		int id = es.payOrderFor(p.getBarCode(), 2, 2.0);
		assertTrue(es.payOrder(id));
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	@Test //Not enough money
	public void shouldpayOrder2() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, 
	InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException, 
	InvalidOrderIdException, UnauthorizedException  {
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "6291041500213", 3.4, "minerale");
		int id = es.issueOrder(p.getBarCode(), 2, 2.0);
		es.payOrder(id);
		assertFalse(es.payOrder(id));
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	@Test (timeout=500) //Nominal
	public void shouldRecordOrderArrival() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, 
	InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException, 
	InvalidOrderIdException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductIdException, InvalidLocationException{
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createProductType("olio", "6291041500213", 8, "minerale");
		ProductType p= es.getProductTypeByBarCode("6291041500213");
		es.updatePosition(p.getId(), "1-a-3");
		int id = es.payOrderFor(p.getBarCode(), 2, 2.0);
		assertTrue(es.recordOrderArrival(id));
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
	}
	@Test (expected=InvalidLocationException.class) //No location
	public void shouldRecordOrderArrival1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, 
	InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException, 
	InvalidOrderIdException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductIdException, InvalidLocationException{
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createProductType("olio", "6291041500213", 8, "minerale");
		ProductType p= es.getProductTypeByBarCode("6291041500213");
		int id = es.payOrderFor(p.getBarCode(), 2, 2.0);
		es.recordOrderArrival(id);
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
	}
	@Test //Nominal 
	public void shouldGetAllOrders() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, 
	InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException, 
	InvalidOrderIdException, UnauthorizedException  {
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		ProductType p = new ProductTypeClass("acqua", "6291041500213", 3.4, "minerale");
		es.issueOrder(p.getBarCode(), 2, 2.0);
		List<Order> l =es.getAllOrders();
		assertEquals(l.size(), 1);
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}


	@Test (timeout=500) //Nominal
	public void shouldRecordOrderArrivalRFID() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
			InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
			InvalidOrderIdException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException {
		ProductDB.deleteAll();
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createProductType("olio", "6291041500213", 8, "minerale");
		ProductType p= es.getProductTypeByBarCode("6291041500213");
		es.updatePosition(p.getId(), "1-a-3");
		int id = es.payOrderFor(p.getBarCode(), 2, 2.0);
		String RFID = "135698254778";
		assertTrue(es.recordOrderArrivalRFID(id,RFID));
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
	}

	@Test (expected=InvalidOrderIdException.class)
	public void shouldRecordOrderArrivalRFIDInvalidID() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
			InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
			InvalidOrderIdException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException {
		ProductDB.deleteAll();
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createProductType("olio", "6291041500213", 8, "minerale");
		ProductType p= es.getProductTypeByBarCode("6291041500213");
		es.updatePosition(p.getId(), "1-a-3");
		int id = 0;
		String RFID = "135698254778";
		assertTrue(es.recordOrderArrivalRFID(id,RFID));
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
	}



	@Test (expected=InvalidRFIDException.class)
	public void shouldRecordOrderArrivalRFIDInvalidRFID1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
			InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
			InvalidOrderIdException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException {
		ProductDB.deleteAll();
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createProductType("olio", "6291041500213", 8, "minerale");
		ProductType p= es.getProductTypeByBarCode("6291041500213");
		es.updatePosition(p.getId(), "1-a-3");
		int id = es.payOrderFor(p.getBarCode(), 2, 2.0);
		String RFID = "13569";
		assertTrue(es.recordOrderArrivalRFID(id,RFID));
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
	}
	@Test (expected=InvalidRFIDException.class)
	public void shouldRecordOrderArrivalRFIDInvalidRFID2() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
			InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
			InvalidOrderIdException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException {
		ProductDB.deleteAll();
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		es.createProductType("olio", "6291041500213", 8, "minerale");
		ProductType p= es.getProductTypeByBarCode("6291041500213");
		es.updatePosition(p.getId(), "1-a-3");
		int id = es.payOrderFor(p.getBarCode(), 2, 2.0);
		String RFID = "";
		assertTrue(es.recordOrderArrivalRFID(id,RFID));
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
		ProductTypeDB.deleteAll();
	}
	
}
