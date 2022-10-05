package it.polito.ezshop.testDB;

import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.Test;

import it.polito.ezshop.data.User;
import it.polito.ezshop.dataBase.UserDB;
import it.polito.ezshop.model.UserClass;

public class TestUserDB {

	@Test
	public void testAddUser() {
		UserDB.deleteAll();
		
		User u=new UserClass("Mario", "pwd", "Cashier");
		u.setId(1);
		UserDB.addUser(1, u);
		TreeMap<Integer, User> resMap=UserDB.getUsers();
		User u2=resMap.get(1);
		
		assertEquals(resMap.size(), 1);
		assertEquals(u2.getId(), u.getId());
		assertEquals(u2.getPassword(), u.getPassword());
		assertEquals(u2.getUsername(), u.getUsername());
		assertEquals(u2.getRole(), u.getRole());
		
		UserDB.deleteAll();
	}

	@Test
	public void testRemoveUser() {
		UserDB.deleteAll();
		
		User u=new UserClass("Mario", "pwd", "Cashier");
		u.setId(1);
		UserDB.addUser(1, u);
		UserDB.removeUser(1);
		TreeMap<Integer, User> resMap=UserDB.getUsers();
		
		assertEquals(resMap.size(), 0);
		
		UserDB.deleteAll();
	}
	
	@Test
	public void testUpdateUser() {
		UserDB.deleteAll();
		
		User u=new UserClass("Mario", "pwd", "Cashier");
		u.setId(1);
		UserDB.addUser(1, u);
		UserDB.updateUserRights(1, "Administrator");
		TreeMap<Integer, User> resMap=UserDB.getUsers();
		
		assertEquals(resMap.get(1).getRole(), "Administrator");
		
		UserDB.deleteAll();
	}
}
