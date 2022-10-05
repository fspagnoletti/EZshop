package it.polito.ezshop.testClass;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.polito.ezshop.data.User;
import it.polito.ezshop.model.UserClass;


public class TestUser {
    @Test
    public void testId() {
        User u =new UserClass("Cashier1", "123456", "Cashier");
        u.setId(2);
        int id = u.getId();
        assertEquals(2, id);
        User u2 =new UserClass(2,"Cashier1", "123456", "Cashier");

        u2.setId(-2);
        int id2 = u2.getId();
        assertEquals(-2, id2);
    }

    @Test
    public void testUsername() {
        User u =new UserClass("Cashier1", "123456", "Cashier");
        u.setUsername("Cashier2");
        String userName = u.getUsername();
        assertEquals("Cashier2", userName);
    }

    @Test
    public void testPassword() {
        User u =new UserClass("Cashier1", "123456", "Cashier");
        u.setPassword("654321");
        String password = u.getPassword();
        assertEquals("654321", password);
    }

    @Test
    public void testRole() {
        User u =new UserClass("Cashier1", "123456", "Cashier");
        u.setRole("Adminstrator");
        String role = u.getRole();
        assertEquals("Adminstrator", role);
    }

}
