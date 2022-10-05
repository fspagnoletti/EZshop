package it.polito.ezshop.testEZShop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import it.polito.ezshop.model.CustomerClass;
import it.polito.ezshop.model.UserClass;
import org.junit.Test;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.User;
import it.polito.ezshop.data.Customer;
import it.polito.ezshop.dataBase.CustomerDB;
import it.polito.ezshop.exceptions.*;

public class TestEZShopCustomer {
    /*
     * Tests for defineCustomer function
     */
    public static final EZShop shop = new EZShop();



    private static final User user = new UserClass(1,"mikki","123456","Administrator");
    //private static final User user2 = new UserClass(1,"mikki","123456","");


    private static final Customer c1 = new CustomerClass("Elon", "1111111119", 9, 0);
    private static final Customer c2 = new CustomerClass("", "1111111118", 8, 0);
    private static final Customer c3 = new CustomerClass("Musk", "111", 4, 0);
    private static final Customer c4 = new CustomerClass("", "1111111117", -1, 0);
    private static final Customer c6 = new CustomerClass("Bill","1111111115" , 5, 100);




    @Test (expected=InvalidCustomerNameException.class)
    public void testDefineCustomerWithEmptyName() throws InvalidCustomerNameException, UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        CustomerDB.removeAllCustomers();
        shop.defineCustomer(c2.getCustomerName());
        CustomerDB.removeAllCustomers();
        shop.logout();



        }
        public void testIfCustomerIsNotExist() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException,InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException,
                UnauthorizedException {
            shop.reset();
            shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
            shop.login(user.getUsername(),user.getPassword());

            /* generate an id that does not exist*/

            // CustomerDB.removeAllCustomers();
            shop.defineCustomer(c1.getCustomerName());
            int idNotExist=1010;

            assertFalse(shop.modifyCustomer(idNotExist, "Bill Gates", "1111111114"));
            CustomerDB.removeAllCustomers();

        }

        /**/
        public void testIfCardIsNotExist() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException,InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException,
                UnauthorizedException {
            shop.reset();
            shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
            shop.login(user.getUsername(),user.getPassword());

            // generate a card that does not exist
            int i = Integer.parseInt(shop.createCard()) + 9;
            String cardNotExist = String.format("%010d", i);



            assertFalse(shop.modifyCustomer(4, "Bill Gates", cardNotExist));
            CustomerDB.removeAllCustomers();

        }




    @Test (expected=InvalidCustomerNameException.class)
    public void testModifyCustomerInvalidCustomerName() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException,InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException,
            UnauthorizedException
    {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        CustomerDB.removeAllCustomers();
        shop.modifyCustomer(c2.getId(),c2.getCustomerName(),c2.getCustomerCard());
        CustomerDB.removeAllCustomers();
        shop.logout();



    }

    /**/

    /**
     * Test whether an existing card can be attached to a customer successfully
     */
    @Test (timeout=500)
    public void testAttachCardToCustomer() throws InvalidPasswordException, InvalidUsernameException,InvalidRoleException,
            InvalidCustomerIdException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerCardException {
        CustomerDB.removeAllCustomers();
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());
        
        shop.defineCustomer(c6.getCustomerName());
        String card1 = shop.createCard();
        shop.attachCardToCustomer(card1,1);

        // card is successfully attached to customer

        // verify that customer's card is indeed updated correctly
        String cardAttached=shop.getCustomer(1).getCustomerCard();
        assertEquals(card1, cardAttached);
        CustomerDB.removeAllCustomers();
    }

    /**/



    @Test (expected=InvalidCustomerIdException.class)
    public void testDeleteCustomerInvalidIdException() throws InvalidCustomerIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException,
            UnauthorizedException
    {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        shop.deleteCustomer(c4.getId());
        CustomerDB.removeAllCustomers();
        shop.logout();

    }

      @Test (expected=InvalidCustomerIdException.class)
    public void testGetCustomerInvalidIdException() throws InvalidCustomerIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException,
            UnauthorizedException
    {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        shop.getCustomer(c4.getId());
        CustomerDB.removeAllCustomers();
        shop.logout();
    }


    @Test (expected=InvalidCustomerIdException.class)
    public void testAttachCardToCustomerInvalidIdException() throws InvalidCustomerIdException, InvalidCustomerCardException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException,
            UnauthorizedException, InvalidCustomerNameException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        shop.attachCardToCustomer(c4.getCustomerCard(),c4.getId());
        CustomerDB.removeAllCustomers();
        shop.logout();

    }
    @Test (expected=InvalidCustomerCardException.class)
    public void testAttachCardToCustomerInvalidCardException() throws InvalidCustomerIdException, InvalidCustomerCardException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException,
            UnauthorizedException, InvalidCustomerNameException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        shop.defineCustomer(c3.getCustomerName());
        shop.createCard();

        shop.attachCardToCustomer(c3.getCustomerCard(),c3.getId());
        CustomerDB.removeAllCustomers();
        shop.logout();

    }

    /**
     * Test whether changing the customer's name to an already existing name returns an error value
     */
    @Test
    public void testChangedName() throws InvalidPasswordException, InvalidUsernameException,
            InvalidCustomerIdException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerCardException, InvalidRoleException {

        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());


        // try to change to already taken name
        shop.defineCustomer(c1.getCustomerName());
        shop.defineCustomer(c6.getCustomerName());
        assertFalse(shop.modifyCustomer(c1.getId(), c6.getCustomerName(),c1.getCustomerCard()));
        CustomerDB.removeAllCustomers();

        }
    @Test (timeout=500)
    public void testDefineCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());
        CustomerDB.removeAllCustomers();
        
        shop.defineCustomer(c1.getCustomerName());
        String nameString=shop.getAllCustomers().get(0).getCustomerName();
        assertEquals(c1.getCustomerName(), nameString);
        CustomerDB.removeAllCustomers();


        } 

    }















