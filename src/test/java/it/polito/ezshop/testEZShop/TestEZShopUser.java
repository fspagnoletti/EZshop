package it.polito.ezshop.testEZShop;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.User;
import it.polito.ezshop.dataBase.UserDB;
import it.polito.ezshop.exceptions.*;

public class TestEZShopUser {
	
	@Test (expected=InvalidUsernameException.class)
	public void testCreate1() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("", "pwd", "Administrator");
		UserDB.deleteAll();
	}
	@Test (expected=InvalidPasswordException.class)
	public void testCreate2() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "", "Administrator");
		
	}
	@Test (expected=InvalidRoleException.class)
	public void testCreate3() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "");
		UserDB.deleteAll();
	}
	
	@Test (expected=InvalidRoleException.class)
	public void testCreate4() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "nada");
		UserDB.deleteAll();
	}
	
	@Test
	public void testCreate5() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException{
		//test same username
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "smt", "Cashier");
		int id=es.createUser("user", "pwd", "Administrator");
		assertEquals(-1,id);
		UserDB.deleteAll();
	}
	
	@Test
	public void testCreate6() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException{
		//test everything okay
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("Marco", "smt", "Cashier");
		int id=es.createUser("Mario", "pwd", "Administrator");
		assertEquals(2,id);
		es.login("Mario", "pwd");
		User u= es.getUser(1);
		assertEquals(u.getUsername(), "Marco");
		assertEquals(u.getPassword(), "smt");
		assertEquals(u.getRole(), "Cashier");
		UserDB.deleteAll();
	}
	@Test (expected=InvalidUsernameException.class)
	public void testLogin1()  throws InvalidUsernameException, InvalidPasswordException{
		EZShop es=new EZShop();
		es.login(null, "pwd");
	}
	
	@Test (expected=InvalidUsernameException.class)
	public void testLogin2()  throws InvalidUsernameException, InvalidPasswordException{
		EZShop es=new EZShop();
		es.login("", "pwd");
	}
	@Test (expected=InvalidPasswordException.class)
	public void testLogin3()  throws InvalidUsernameException, InvalidPasswordException{
		EZShop es=new EZShop();
		es.login("user", null);
	}
	@Test (expected=InvalidPasswordException.class)
	public void testLogin4()  throws InvalidUsernameException, InvalidPasswordException{
		EZShop es=new EZShop();
		es.login("user", ""); 
	}
	@Test (timeout=500)
	public void testLogin5() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("Marco", "smt", "Cashier");
		User u=es.login("Marco", "smt");
		assertEquals("Marco", u.getUsername());
		assertEquals("smt", u.getPassword());
		assertEquals("Cashier", u.getRole());
	}
	@Test
	public void testLogin6() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("Marco", "smt", "Cashier");
		User u=es.login("Marco", "smthf");
		assertNull(u);
	}
	@Test (timeout=500)
	public void testLogout1() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException {
		EZShop es=new EZShop();
		es.createUser("Marco", "smt", "Cashier");
		es.login("Marco", "smt");
		
		assertTrue(es.logout());
	}
	@Test
	public void testLogout2() throws InvalidUsernameException,InvalidPasswordException, InvalidRoleException {
		EZShop es=new EZShop();
		
		assertFalse(es.logout());
	}
	
	@Test (expected=InvalidUserIdException.class)
	public void testDelete1()  throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException,InvalidPasswordException,InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		es.deleteUser(-3);
		
		UserDB.deleteAll();
	}
	@Test (expected=InvalidUserIdException.class)
	public void testDelete2()  throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException,InvalidPasswordException,InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		es.deleteUser(null);
		
		UserDB.deleteAll();
	}
	
	@Test (expected=UnauthorizedException.class)
	public void testDelete3()  throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException,InvalidPasswordException,InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Cashier");
		es.login("user", "pwd");
		es.deleteUser(1);
		
		UserDB.deleteAll();
	}
	/*Scenario 2.1 2.2*/ 
	@Test (timeout=500)
	public void testDelete4()  throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException,InvalidPasswordException,InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		es.createUser("Mario", "mypwd", "Cashier");
		
		assertTrue(es.deleteUser(2));
		UserDB.deleteAll();
	}
	public void testDelete5()  throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException,InvalidPasswordException,InvalidRoleException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		
		assertFalse(es.deleteUser(5));
		UserDB.deleteAll();
	}
	/*Scenario 2.3*/
	@Test (timeout=500)
	public void testUpdate1() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException,InvalidPasswordException,InvalidRoleException, InvalidUsernameException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		es.createUser("Mario", "mypwd", "Cashier");
		
		assertTrue(es.updateUserRights(2, "ShopManager"));
	}
	@Test (expected=InvalidUserIdException.class)
	public void testUpdate2() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException,InvalidPasswordException,InvalidRoleException, InvalidUsernameException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		es.createUser("Mario", "mypwd", "Cashier");
		
		es.updateUserRights(-2, "ShopManager");
	}
	@Test (expected=InvalidRoleException.class)
	public void testUpdate3() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException,InvalidPasswordException,InvalidRoleException, InvalidUsernameException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		es.createUser("Mario", "mypwd", "Cashier");
		es.updateUserRights(2, "");
	}
	
	@Test 
	public void testUpdate4() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException,InvalidPasswordException,InvalidRoleException, InvalidUsernameException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		es.createUser("Mario", "mypwd", "Cashier");
		assertFalse(es.updateUserRights(5, "Administrator"));
	}
	
	@Test
	public void testListAll() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException,InvalidPasswordException,InvalidRoleException, InvalidUsernameException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		es.createUser("Mario", "mypwd", "Cashier");
		List<User> list=es.getAllUsers();
		assertEquals(2,list.size());
	}
	@Test (expected=UnauthorizedException.class)
	public void testListAll2() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException,InvalidPasswordException,InvalidRoleException, InvalidUsernameException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.getAllUsers();
	}
	
	@Test (expected=UnauthorizedException.class)
	public void testGetUser1() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException,InvalidPasswordException,InvalidRoleException, InvalidUsernameException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.getUser(1);
	}
	
	@Test (expected=InvalidUserIdException.class)
	public void testGetUser2() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException,InvalidPasswordException,InvalidRoleException, InvalidUsernameException{
		UserDB.deleteAll();
		EZShop es=new EZShop();
		es.createUser("user", "pwd", "Administrator");
		es.login("user", "pwd");
		es.getUser(-1);
	}
	
	
	
	
	
	
	
	
	

}
