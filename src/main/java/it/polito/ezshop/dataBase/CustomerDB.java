package it.polito.ezshop.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import it.polito.ezshop.data.Customer;
import it.polito.ezshop.model.CustomerClass;


public class CustomerDB {

    public static TreeMap<Integer, Customer> getCustomers(){
        Connection cn;
        String sql;
        PreparedStatement ps;
        ResultSet rs=null;
        TreeMap<Integer, Customer> map=new TreeMap<Integer,Customer>();

        /**/
        try {
            cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");

            sql = "SELECT * FROM Customers";
            ps = cn.prepareStatement(sql);
            if(ps.execute()) {
                rs=ps.getResultSet();
                while(rs.next()) {
                    Customer c= new CustomerClass(rs.getString("customerName"), rs.getString("customerCard"), rs.getInt("points"));
                    c.setId(rs.getInt("id"));
                    map.put(c.getId(), c);
                }
            }
            rs.close();
            cn.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("errore: " + e.getMessage());
            return null;
        }

        return map;

    }



    public static int addCustomer(int id, Customer c) {

        Connection cn;
        String sql;
        PreparedStatement ps;

        // Check the presence of the JDBC drivers
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");

            sql = "INSERT into Customers (id, customerName, customerCard, points) values (?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, c.getCustomerName());
            ps.setString(3, c.getCustomerCard());
            ps.setInt(4, c.getPoints());
            ps.execute();

            cn.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("errore: " + e.getMessage());
            return -1;
        }
        return 0;
    }


    // remove Customer by ID
    public static int removeCustomer(int id) {

        Connection cn;
        String sql;
        PreparedStatement ps;

        // Check the presence of the JDBC drivers
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");

            sql = "DELETE FROM Customers WHERE id = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();


            ps.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("errore: " + e.getMessage());
            return -1;
        }
        return 1;

    }

    /**/
    public static int updateCustomerName(Integer id, String customerName) {

        Connection cn;
        String sql;
        PreparedStatement ps;

        // Check the presence of the JDBC drivers
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");

            sql = "UPDATE Customers set customerName = ? WHERE id = ? ";
            ps = cn.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setInt(2, id);


            ps.execute();
            System.out.println("Data has been updated");

            ps.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("errore: " + e.getMessage());
            return -1;
        }
        return 1;

    }
    
    public static int updateCustomer(Integer id, String customerName, String customerCard) {

        Connection cn;
        String sql;
        PreparedStatement ps;

        // Check the presence of the JDBC drivers
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");

            sql = "UPDATE Customers set customerName = ?, customerCard = ? WHERE id = ? ";
            ps = cn.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, customerCard);
            ps.setInt(3, id);


            ps.execute();
            System.out.println("Data has been updated");

            ps.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("errore: " + e.getMessage());
            return -1;
        }
        return 1;

    }
    
    public static String maxCustomerCard() {

        Connection cn;
        String sql;
        PreparedStatement ps;
        ResultSet rs;
        String max;

        // Check the presence of the JDBC drivers
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");

            sql = "SELECT MAX(customerCard) FROM Customers";
            ps = cn.prepareStatement(sql);
            ps.execute();
            rs=ps.getResultSet();
            max = rs.getString("MAX(customerCard)");
            if (max == null || max.isEmpty()) {
            	max="0";
            }
            
            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("errore: " + e.getMessage());
            return null;
        }
        return max;

    }
    
    public static int updateCustomerCard(Integer id, String customerCard) {

        Connection cn;
        String sql;
        PreparedStatement ps;

        // Check the presence of the JDBC drivers
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");

            sql = "UPDATE Customers set customerCard = ? WHERE id = ? ";
            ps = cn.prepareStatement(sql);
            ps.setString(1, customerCard);
            ps.setInt(2, id);


            ps.execute();

            ps.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("errore: " + e.getMessage());
            return -1;
        }
        return 1;

    }
    
    public static int updateCustomerPoints(Integer id, int points) {

        Connection cn;
        String sql;
        PreparedStatement ps;

        // Check the presence of the JDBC drivers
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");

            sql = "UPDATE Customers set points = ? WHERE id = ? ";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, points);
            ps.setInt(2, id);


            ps.execute();

            ps.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("errore: " + e.getMessage());
            return -1;
        }
        return 1;

    }
    
    public static int removeAllCustomers() {

        Connection cn;
        String sql;
        PreparedStatement ps;

        // Check the presence of the JDBC drivers
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");

            sql = "DELETE FROM Customers ";
            ps = cn.prepareStatement(sql);
            ps.execute();


            ps.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("errore: " + e.getMessage());
            return -1;
        }
        return 1;

    }

}