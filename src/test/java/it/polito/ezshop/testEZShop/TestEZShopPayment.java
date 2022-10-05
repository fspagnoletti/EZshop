package it.polito.ezshop.testEZShop;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.*;
import it.polito.ezshop.model.*;
import it.polito.ezshop.dataBase.*;
import it.polito.ezshop.dataBase.CustomerDB;
import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.SaleTransactionClass;
import it.polito.ezshop.model.UserClass;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEZShopPayment {
    public static final EZShop shop = new EZShop();



    private static final User user = new UserClass(1,"mikki","123456","Administrator");
    private static final SaleTransaction s = new SaleTransactionClass(-1,12.50);
    private static final SaleTransaction s1 = new SaleTransactionClass(1,-12);
    private static final SaleTransaction s_valid = new SaleTransactionClass(3,22);


    /*
    * check is invalid id exception works
    * */
    @Test(expected= InvalidTransactionIdException.class)
    public void testReceiveCashPaymentWithInvalidTransactionId() throws InvalidTransactionIdException, UnauthorizedException, InvalidPaymentException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        SaleTransactionDB.removeAllSaleTransactions();

        shop.startSaleTransaction();
        shop.endSaleTransaction(s.getTicketNumber());
        shop.receiveCashPayment(s.getTicketNumber(),s.getPrice());

    }


    /*
     * check is invalid payment exception works
     * */

    @Test(expected= InvalidPaymentException.class)
    public void testReceiveCashPaymentWithInvalidPayment() throws InvalidTransactionIdException, UnauthorizedException, InvalidPaymentException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        SaleTransactionDB.removeAllSaleTransactions();

        shop.receiveCashPayment(s1.getTicketNumber(),s1.getPrice());
    }



    @Test (timeout=500)
    public void testReceiveCashPayment() throws InvalidTransactionIdException, UnauthorizedException, InvalidPaymentException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        SaleTransactionDB.removeAllSaleTransactions();
        shop.startSaleTransaction();
        SaleTransactionDB.addSaleTransaction(s_valid.getTicketNumber(),s_valid);
        int tranId=SaleTransactionDB.getSaleTransactions().lastKey();
        shop.endSaleTransaction(tranId);

        shop.receiveCashPayment(tranId, s_valid.getPrice() );
        assertEquals(tranId,3);

         final double DELTA = 1e-15;
        assertEquals(s_valid.getPrice(),22,DELTA);


    }


    @Test(expected= InvalidTransactionIdException.class)
    public void testReceiveCreditCardPaymentWithInvalidTransactionId() throws InvalidTransactionIdException, UnauthorizedException, InvalidPaymentException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidCreditCardException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        SaleTransactionDB.removeAllSaleTransactions();

        shop.startSaleTransaction();
        shop.endSaleTransaction(s.getTicketNumber());

        String creditCard = "1111222233334444";
        shop.receiveCreditCardPayment(s.getTicketNumber(), creditCard );
        SaleTransactionDB.removeAllSaleTransactions();


    }

    @Test(expected= InvalidCreditCardException.class)
    public void testReceiveCreditCardPaymentWithInvalidPayment() throws InvalidTransactionIdException, UnauthorizedException, InvalidCreditCardException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidCreditCardException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        SaleTransactionDB.removeAllSaleTransactions();

        String creditCard = "111122223333444";
        shop.receiveCreditCardPayment(s1.getTicketNumber(),creditCard);
        SaleTransactionDB.removeAllSaleTransactions();

    }
    @Test (timeout=500)
    public void testReceiveCreditCardPayment() throws InvalidTransactionIdException, UnauthorizedException, InvalidPaymentException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidCreditCardException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        SaleTransactionDB.removeAllSaleTransactions();
        shop.startSaleTransaction();
        SaleTransactionDB.addSaleTransaction(s_valid.getTicketNumber(),s_valid);
        int tranId=SaleTransactionDB.getSaleTransactions().lastKey();
        shop.endSaleTransaction(tranId);


        String creditCard = "1111222233334444";
        shop.receiveCreditCardPayment(tranId, creditCard );
        assertEquals(tranId,3);

        final double DELTA = 1e-15;
        assertEquals(s_valid.getPrice(),22,DELTA);
        SaleTransactionDB.removeAllSaleTransactions();




    }

    @Test(expected= InvalidTransactionIdException.class)
    public void testReturnCashPaymentWithInvalidTransactionId() throws InvalidTransactionIdException, UnauthorizedException, InvalidPaymentException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());

        SaleTransactionDB.removeAllSaleTransactions();

        shop.startSaleTransaction();
        shop.endSaleTransaction(s.getTicketNumber());
        int returnId=-1;
        shop.returnCashPayment(returnId);

    }


    @Test(expected= InvalidTransactionIdException.class)
    public void testReturnCreditCardPaymentWithInvalidTransactionId() throws InvalidTransactionIdException, UnauthorizedException, InvalidPaymentException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidCreditCardException {
        shop.reset();
        shop.createUser(user.getUsername(),user.getPassword(),user.getRole());
        shop.login(user.getUsername(),user.getPassword());


        SaleTransactionDB.removeAllSaleTransactions();
        shop.startSaleTransaction();
        SaleTransactionDB.addSaleTransaction(s.getTicketNumber(),s);
        int tranId=SaleTransactionDB.getSaleTransactions().lastKey();
        shop.endSaleTransaction(tranId);


        String creditCard = "1111222233334444";
        shop.returnCreditCardPayment(5,creditCard);



    }











}
