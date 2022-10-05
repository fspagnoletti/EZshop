package it.polito.ezshop.testEZShop;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.dataBase.ProductTypeDB;
import it.polito.ezshop.dataBase.UserDB;
import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidProductIdException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class TestEZShopProduct {
	/*Scenario 1.1*/
	@Test (timeout=500)
	public void testCreate1() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "ShopManager");
		eShop.login("Marco", "smt");
		int id=eShop.createProductType("acqua", "6291041500213", 3.4, "minerale");
		assertEquals(1, id);
		assertTrue(eShop.updateProduct(1, "olio", "6291041500312", 5.7, "vergine"));
		assertTrue(eShop.deleteProductType(1));
	}
	/*map not empty*/ 
	@Test 
	public void testCreate2() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "ShopManager");
		eShop.login("Marco", "smt");
		eShop.createProductType("vino", "233254321519", 4, "doc");
		int id=eShop.createProductType("acqua", "6291041500213", 3.4, "minerale");
		assertEquals(2, id);
		assertTrue(eShop.updateProduct(2, "olio", "6291041500312", 5.7, "vergine"));
		assertTrue(eShop.deleteProductType(2));
	}
	@Test (expected=InvalidProductDescriptionException.class)
	public void testCreate3() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "ShopManager");
		eShop.login("Marco", "smt");
		eShop.createProductType("", "6291041500213", 3.4, "minerale");
	}
	
	@Test (expected=InvalidProductCodeException.class)
	public void testCreate4() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "ShopManager");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6290213", 3.4, "minerale");
	}
	
	@Test (expected=InvalidPricePerUnitException.class)
	public void testCreate5() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "ShopManager");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", -7, "minerale");
	}
	
	@Test (expected=UnauthorizedException.class)
	public void testCreate6() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Cashier");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", -7, "minerale");
	}
	
	@Test 
	public void testCreate7() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 2.7, "vergine");
		int n= eShop.createProductType("acqua", "6291041500213", 2.75, "minerale");
		assertEquals(-1, n);
	}
	
	@Test
	public void testGetProducts() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException ,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "minerale");
		List<ProductType> res=eShop.getAllProductTypes();
		assertEquals(res.size(),1);
	}
	/* Scenario 1.2*/
	@Test (timeout=500)
	public void testUpdateLocation() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException ,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException, InvalidLocationException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "minerale");
		ProductType p= eShop.getProductTypeByBarCode("6291041500213");
		eShop.updatePosition(p.getId(), "1-a-3");
	}
	
	@Test (expected= InvalidProductIdException.class)
	public void testUpdateLocation2() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException ,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException, InvalidLocationException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "minerale");
		eShop.getProductTypeByBarCode("6291041500213");
		eShop.updatePosition(-4, "1-a-3");
	}
	
	@Test (expected= InvalidLocationException.class)
	public void testUpdateLocation3() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException ,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException, InvalidLocationException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "minerale");
		ProductType p=eShop.getProductTypeByBarCode("6291041500213");
		eShop.updatePosition(p.getId(), "a-a-a");
	}
	@Test 
	public void testUpdateLocation4() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException ,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException, InvalidLocationException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "vergine");
		ProductType p=eShop.getProductTypeByBarCode("6291041500213");
		eShop.updatePosition(p.getId(), "3-a-2");
		eShop.createProductType("acqua", "6291041500312", 0.4, "minerale");
		ProductType p2=eShop.getProductTypeByBarCode("6291041500312");
		assertFalse(eShop.updatePosition(p2.getId(), "3-a-2"));
	}
	
	@Test 
	public void testListAll() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
	UnauthorizedException ,InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidProductIdException, InvalidLocationException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "minerale");
		List<ProductType> p=eShop.getAllProductTypes();
		assertEquals(p.size(), 1);
	}
	
	@Test 
	public void testUpdateQuantity1() throws InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "minerale");
		
		assertFalse(eShop.updateQuantity(1, 3));
		
	}
	@Test 
	public void testUpdateQuantity2() throws  InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "minerale");
		eShop.updatePosition(1, "1-s-5");
		assertFalse(eShop.updateQuantity(5, 3));
		
	}
	@Test 
	public void testUpdateQuantity3() throws  InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "minerale");
		eShop.updatePosition(1, "1-s-5");
		assertTrue(eShop.updateQuantity(1, 3));
		
	}
	@Test 
	public void testUpdateQuantity4() throws  InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "minerale");
		eShop.updatePosition(1, "1-s-5");
		assertFalse(eShop.updateQuantity(1, -203));
		
	}
	
	@Test
	public void testGetByDescription1()throws  InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException {
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "vergine");
		eShop.createProductType("acqua", "6291041500312", 2.3, "minerale");
		List<ProductType> map=eShop.getProductTypesByDescription("");
		assertEquals(map.size(), 2);
	}
	
	@Test
	public void testGetByDescription2() throws  InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "vergine");
		eShop.createProductType("acqua", "6291041500312", 2.3, "minerale");
		List<ProductType> map=eShop.getProductTypesByDescription("qu");
		assertEquals(map.size(), 1);
	}
	
	@Test
	public void testUpdateProduct1() throws InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "vergine");
		boolean bool=eShop.updateProduct(6, "acqua", "6291041500312", 3, "minerale");
		assertFalse(bool);
	}
	
	@Test (expected = InvalidProductDescriptionException.class)
	public void testUpdateProduct2() throws InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "vergine");
		eShop.updateProduct(6, "", "6291041500312", 3, "minerale");
	}
	
	@Test (expected=InvalidProductCodeException.class)
	public void testUpdateProduct3() throws InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "vergine");
		eShop.updateProduct(6, "acqua", "6291041500317", 3, "minerale");
	}
	@Test (expected=InvalidProductIdException.class)
	public void testUpdateProduct4() throws InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "vergine");
		eShop.updateProduct(null, "acqua", "6291041500312", 3, "minerale");
	}
	
	@Test 
	public void testDeleteProduct1() throws InvalidLocationException,InvalidProductDescriptionException, InvalidProductIdException, UnauthorizedException,InvalidUsernameException,InvalidPasswordException, InvalidRoleException,InvalidProductCodeException, InvalidPricePerUnitException{
		ProductTypeDB.deleteAll();
		UserDB.deleteAll();
		EZShop eShop=new EZShop();
		eShop.createUser("Marco", "smt", "Administrator");
		eShop.login("Marco", "smt");
		eShop.createProductType("olio", "6291041500213", 8, "vergine");
		assertFalse( eShop.deleteProductType(7));
	}
}
