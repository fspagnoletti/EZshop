package it.polito.ezshop.testEZShop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.dataBase.BalanceOperationDB;
import it.polito.ezshop.dataBase.OrderDB;
import it.polito.ezshop.dataBase.ProductDB;
import it.polito.ezshop.dataBase.ProductTypeDB;
import it.polito.ezshop.dataBase.SaleTransactionDB;
import it.polito.ezshop.dataBase.TicketEntryDB;
import it.polito.ezshop.model.BalanceOperationClass;
import it.polito.ezshop.model.ProductTypeClass;

public class TestEZShopSales {
	
	EZShop es=new EZShop();
	
	@Test //empty map
	public void shouldStartSaleTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		assertEquals(1, id);
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //empty not map
	public void shouldStartSaleTransaction1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		es.startSaleTransaction();
		int id = es.startSaleTransaction();
		assertEquals(2, id);
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal
	public void shouldAddProductToSale() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		assertTrue(es.addProductToSale(id, "6291041500213", 1));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //sale do not exist
	public void shouldAddProductToSale1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
//		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		assertFalse(es.addProductToSale(1, "6291041500213", 1));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
//	@Test //quantity < amount
//	public void shouldAddProductToSale2() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
//	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
//	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException {
//		SaleTransactionDB.removeAllSaleTransactions();
//		TicketEntryDB.removeAllTickets();
//		ProductTypeDB.deleteAll();
//		es.createUser("Marco", "smt", "ShopManager");
//		es.login("Marco", "smt");	
//		int id = es.startSaleTransaction();
//		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
//		es.updatePosition(idp, "1-a-3");
//		es.updateQuantity(idp, 4);
//		assertFalse(es.addProductToSale(id, "6291041500213", 10));
//		ProductTypeDB.deleteAll();
//		SaleTransactionDB.removeAllSaleTransactions();
//		TicketEntryDB.removeAllTickets();
//	}
	
//	@Test //product do not exist
//	public void shouldAddProductToSale3() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
//	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
//	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException {
//		SaleTransactionDB.removeAllSaleTransactions();
//		TicketEntryDB.removeAllTickets();
//		ProductTypeDB.deleteAll();
//		es.createUser("Marco", "smt", "ShopManager");
//		es.login("Marco", "smt");	
//		int id = es.startSaleTransaction();
////		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
////		es.updatePosition(idp, "1-a-3");
////		es.updateQuantity(idp, 4);
//		assertFalse(es.addProductToSale(id, "6291041500213", 10));
//		ProductTypeDB.deleteAll();
//		SaleTransactionDB.removeAllSaleTransactions();
//		TicketEntryDB.removeAllTickets();
//	}
	
	@Test (expected = InvalidQuantityException.class) //amount < 0
	public void shouldAddProductToSale4() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 5);
		es.addProductToSale(id, "6291041500213", -1);
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test (expected = InvalidTransactionIdException.class) //invalid transactionId
	public void shouldAddProductToSale5() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
//		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 5);
		es.addProductToSale(0, "6291041500213", 1);
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal amount < quantity
	public void shouldDeleteProductFromSale() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		es.addProductToSale(id, "6291041500213", 10);
		assertTrue(es.deleteProductFromSale(id, "6291041500213", 3));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal amount >= quantity
	public void shouldDeleteProductFromSale1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		es.addProductToSale(id, "6291041500213", 10);
		assertTrue(es.deleteProductFromSale(id, "6291041500213", 11));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal 
	public void shouldApplyDiscountRateToProduct() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidDiscountRateException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		es.addProductToSale(id, "6291041500213", 10);
		assertTrue(es.applyDiscountRateToProduct(id, "6291041500213" , 0.10));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal 
	public void shouldApplyDiscountRateToSale() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidDiscountRateException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		es.addProductToSale(id, "6291041500213", 10);
		assertTrue(es.applyDiscountRateToSale(id, 0.10));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal 
	public void shouldComputePointsForSale() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidDiscountRateException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		es.addProductToSale(id, "6291041500213", 10);
		assertEquals(es.computePointsForSale(id), 3);
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal 
	public void shouldEndSaleTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidDiscountRateException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		assertTrue(es.endSaleTransaction(id));
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		assertTrue(es.endSaleTransaction(id1));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal 
	public void shoulDeleteSaleTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidDiscountRateException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		es.endSaleTransaction(id);
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		assertTrue(es.deleteSaleTransaction(id1));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal 
	public void shouldGetSaleTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
	InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException, 
	InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidDiscountRateException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		es.endSaleTransaction(id);
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		SaleTransaction s = es.getSaleTransaction(id1);
		List<SaleTransaction> map = new ArrayList<SaleTransaction>();
		map.add(s);
		assertEquals(map.size(), 1);
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}
	
	@Test //Nominal empty map
	public void shouldStartReturnTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
	UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, 
	InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		assertTrue(es.endSaleTransaction(id));
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		int idR =es.startReturnTransaction(id); 
		assertEquals(idR, 1);
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();	
	}
	
	@Test //Nominal 
	public void shouldReturnProduct() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
	UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, 
	InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		assertTrue(es.endSaleTransaction(id));
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		int idR =es.startReturnTransaction(id); 
		es.returnProduct(idR, "6291041500213", 2);
		assertTrue(es.returnProduct(idR, "6291041500312", 3));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();	
	}
	
	@Test (expected = InvalidQuantityException.class)//amount <= 0 
	public void shouldReturnProduct1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
	UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, 
	InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		assertTrue(es.endSaleTransaction(id));
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		int idR =es.startReturnTransaction(id); 
		es.returnProduct(idR, "6291041500213", -2);
//		assertTrue(es.returnProduct(idR, "6291041500312", 3));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();	
	}
	@Test //amount > quantity
	public void shouldReturnProduct2() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
	UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, 
	InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		assertTrue(es.endSaleTransaction(id));
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		int idR =es.startReturnTransaction(id); 
		es.returnProduct(idR, "6291041500213", 2);
		assertFalse(es.returnProduct(idR, "6291041500312", 30));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();	
	}
	
//	@Test (timeout=500) //Nominal commit true
//	public void shouldEndReturnTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
//	UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, 
//	InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException,InvalidPaymentException {
//		SaleTransactionDB.removeAllSaleTransactions();
//		TicketEntryDB.removeAllTickets();
//		ProductTypeDB.deleteAll();
//		es.createUser("Marco", "smt", "ShopManager");
//		es.login("Marco", "smt");	
//		es.recordBalanceUpdate(1000);
//		int id = es.startSaleTransaction();
//		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
//		es.updatePosition(idp, "1-a-3");
//		es.updateQuantity(idp, 20);
//		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
//		es.updatePosition(idp1, "1-a-4");
//		es.updateQuantity(idp1, 10);
//		es.addProductToSale(id, "6291041500213", 10);
//		es.addProductToSale(id, "6291041500312", 10);
//		es.endSaleTransaction(id);
//		int id1 = es.startSaleTransaction();
//		es.addProductToSale(id1, "6291041500312", 3);
//		es.endSaleTransaction(id1);
//		int idR =es.startReturnTransaction(id); 
//		es.returnProduct(idR, "6291041500213", 10);
//		es.returnProduct(idR, "6291041500312", 10);
//		assertTrue(es.endReturnTransaction(idR, true));
//		double rest= es.returnCashPayment(idR);
//		assertEquals(114, rest,0);
//		ProductTypeDB.deleteAll();
//		SaleTransactionDB.removeAllSaleTransactions();
//		TicketEntryDB.removeAllTickets();	
//	}
	
	@Test //Nominal commit false
	public void shouldEndReturnTransaction1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
	UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, 
	InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		es.endSaleTransaction(id);
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		int idR =es.startReturnTransaction(id); 
		es.returnProduct(idR, "6291041500213", 2);
		es.returnProduct(idR, "6291041500312", 3);
		assertFalse(es.endReturnTransaction(idR, false));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();	
	}
	
	@Test //Nominal commit true TO FIX
	public void shouldDeleteReturnTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
	UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, 
	InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");	
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		es.endSaleTransaction(id);
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		int id2 = es.startSaleTransaction();
		es.addProductToSale(id2, "6291041500312", 9);
		es.endSaleTransaction(id2);
		int idR =es.startReturnTransaction(id2); 
		es.returnProduct(idR, "6291041500312", 4);
		es.endReturnTransaction(idR, true);
		int idR1 =es.startReturnTransaction(id2); 
		es.returnProduct(idR1, "6291041500312", 3);
		es.endReturnTransaction(idR1, true);
		es.deleteReturnTransaction(idR1);
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();	
	}
	/*****************/
	@Test //Nominal
	public void shouldReturnProductRFID() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
			UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException,
			InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException, InvalidRFIDException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		assertTrue(es.endSaleTransaction(id));
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		int idR =es.startReturnTransaction(id);
		String RFID = "135698254778";
		es.returnProductRFID(idR, RFID );
		assertFalse(es.returnProductRFID(idR, RFID));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}

	@Test (expected = InvalidTransactionIdException.class)//amount <= 0
	public void shouldReturnProductRFID1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,
			UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException,
			InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException, InvalidRFIDException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		int id = 0;
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int idp1 = es.createProductType("olio", "6291041500312", 8, "vergine");
		es.updatePosition(idp1, "1-a-4");
		es.updateQuantity(idp1, 10);
		es.addProductToSale(id, "6291041500213", 10);
		es.addProductToSale(id, "6291041500312", 10);
		assertTrue(es.endSaleTransaction(id));
		int id1 = es.startSaleTransaction();
		es.addProductToSale(id1, "6291041500312", 3);
		es.endSaleTransaction(id1);
		int idR =es.startReturnTransaction(id);
		es.returnProductRFID(idR, "12222222222223");
		//assertFalse(es.returnProductRFID(idR, "122222222223"));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}

	@Test (expected = InvalidRFIDException.class) //invalid transactionId
	public void shouldAddProductToSaleRFID1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
			InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException,
			InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		assertTrue(es.addProductToSaleRFID(id, "12222222222223"));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}

	/*****************/
	@Test (expected = InvalidTransactionIdException.class) //invalid transactionId
	public void shouldAddProductToSaleRFID2() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
			InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException,
			InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		int id = 0;
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		assertTrue(es.addProductToSaleRFID(id, "12222222222223"));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}

	@Test (expected = InvalidTransactionIdException.class) //invalid transactionId
	public void shouldDeleteProductFromSaleRFID1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
			InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException,
			InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		int id = 0;
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		es.addProductToSaleRFID(id, "122222222223");
		assertTrue(es.deleteProductFromSaleRFID(id, "122222222223"));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}



	@Test (expected = InvalidRFIDException.class) //invalid transactionId
	public void shouldDeleteProductFromSaleRFID2() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
			InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException,
			InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		int id = es.startSaleTransaction();
		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		es.addProductToSaleRFID(id, "12222222222223");
		assertTrue(es.deleteProductFromSaleRFID(id, "12222222222223"));
		ProductTypeDB.deleteAll();
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
	}



	 @Test//nominal
	public void shouldDeleteProductFromSaleRFID() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
			InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException,
			InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException, InvalidOrderIdException {
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		ProductDB.deleteAll();
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();

		es.createUser("Marco", "smt", "ShopManager");
		es.login("Marco", "smt");
		BalanceOperation b = new BalanceOperationClass(LocalDate.now(), 1000.00, "CREDIT");
		BalanceOperationDB.addBalanceOperation(0, b);
		
		int idp = es.createProductType("acqua", "000012354788", 3.4, "minerale");
		es.updatePosition(idp, "1-a-3");
		es.updateQuantity(idp, 20);
		int id1 = es.payOrderFor("000012354788", 2, 2.0);
		String RFID = "122222222223";
		es.recordOrderArrivalRFID(id1,RFID);
		Integer id = es.startSaleTransaction();
		assertTrue(es.addProductToSaleRFID(id, RFID));
		assertTrue(es.deleteProductFromSaleRFID(id, RFID));
		SaleTransactionDB.removeAllSaleTransactions();
		TicketEntryDB.removeAllTickets();
		ProductTypeDB.deleteAll();
		ProductDB.deleteAll();
		OrderDB.removeAllOrders();
		BalanceOperationDB.removeAllBalanceOperations();
	}
	 



//	@Test
//	public void shouldAddProductToSaleRFID() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException,
//			InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException,
//			InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidRFIDException {
//		SaleTransactionDB.removeAllSaleTransactions();
//		TicketEntryDB.removeAllTickets();
//		ProductTypeDB.deleteAll();
//		es.createUser("Marco", "smt", "ShopManager");
//		es.login("Marco", "smt");
//		int id = es.startSaleTransaction();
//		int idp = es.createProductType("acqua", "6291041500213", 3.4, "minerale");
//		es.updatePosition(idp, "1-a-3");
//		es.updateQuantity(idp, 20);
//		assertTrue(es.addProductToSaleRFID(id, "122222222223"));
//		ProductTypeDB.deleteAll();
//		SaleTransactionDB.removeAllSaleTransactions();
//		TicketEntryDB.removeAllTickets();
//	}
}
