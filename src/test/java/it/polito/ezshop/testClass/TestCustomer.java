package it.polito.ezshop.testClass;

import static org.junit.Assert.assertEquals;

import it.polito.ezshop.model.CustomerClass;
import org.junit.Test;

import it.polito.ezshop.data.Customer;
import it.polito.ezshop.model.CustomerClass;


public class TestCustomer {
    @Test
    public void testCustomerName() {
        Customer c =new CustomerClass(" Maurizio", "xxxx5", 5, 0);
        c.setCustomerName("Maurizio Morisio");
        String name=c.getCustomerName();
        assertEquals("Maurizio Morisio", name);
    }

    @Test
    public void testCustomerCard() {
        Customer c =new CustomerClass(" Maurizio", "xxxx5", 5, 0);
        c.setCustomerCard("xxxx55");
        String card=c.getCustomerCard();
        assertEquals("xxxx55", card);
    }

    @Test
    public void testId() {
        Customer c =new CustomerClass(" Maurizio", "xxxx5", 5, 0);
        c.setId(55);
        int id = c.getId();
        assertEquals(55, id);

        Customer c1 =new CustomerClass(" Maurizio", "xxxx5", 5, 0);
        c1.setId(-55);
        int id1 = c1.getId();
        assertEquals(-55, id1);
    }

    @Test
    public void testPoints() {
        Customer c =new CustomerClass(" Maurizio", "xxxx5", 5, 0);
        c.setPoints(10);
        int points = c.getPoints();
        assertEquals(10, points);
    }




}
