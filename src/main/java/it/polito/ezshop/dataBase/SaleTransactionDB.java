package it.polito.ezshop.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.SaleTransactionClass;
import it.polito.ezshop.model.TicketEntryClass;



public class SaleTransactionDB {
	
	// add a new SaleTransaction in the DB
		public static int addSaleTransaction(int tickeNumber, SaleTransaction s) {
			
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
				
				sql = "INSERT into SaleTransactions (ticketNumber, discountRate, price) values (?,?,?)";
				ps = cn.prepareStatement(sql);
				ps.setInt(1, tickeNumber);
				ps.setDouble(2, s.getDiscountRate());
				ps.setDouble(3, s.getPrice());
				ps.execute();
				
				cn.close();
				ps.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			 return 0;
		}
		
		// remove SaleTransaction by ID
		public static int removeSaleTransaction(int Id) {
			
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
				
				sql = "DELETE FROM SaleTransactions WHERE ticketNumber = ?";
				ps = cn.prepareStatement(sql);
				ps.setInt(1, Id);
				ps.execute();
				
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			
			return 0;
		}
		
		
		// See all the sales in the DB TO DO
		public static TreeMap<Integer, SaleTransaction> getSaleTransactions() {
			
			Connection cn;
			String sql;
			PreparedStatement ps;
			ResultSet rs=null;
			TreeMap<Integer, SaleTransaction> map = new TreeMap<Integer, SaleTransaction>();
			SaleTransaction s;
			
			// Check the presence of the JDBC drivers
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException: ");
				System.err.println(e.getMessage());
			}
			
			try {
				cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
				
				sql = "SELECT * FROM SaleTransactions";
				ps = cn.prepareStatement(sql);
				if(ps.execute()) {
					rs=ps.getResultSet();
					while(rs.next()) {
						
						s = SaleTransactionDB.getSaleTransactionByTicket(rs.getInt("ticketNumber"));
						if (s == null) {
							s = SaleTransactionDB.getSaleTransactionNoTickets(rs.getInt("ticketNumber"));
							if (s == null) return null;
						}
//						s = new SaleTransactionClass( rs.getInt("ticketNumber") , rs.getDouble("discountRate"), rs.getDouble("price"));
						map.put(s.getTicketNumber(), s);
					}
				}
				
				rs.close();
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return null;
			}
			
			return map;
			
		}
		
		public static TreeMap<Integer, SaleTransaction> getReturnTransactions() {
			
			Connection cn;
			String sql;
			PreparedStatement ps;
			ResultSet rs=null;
			TreeMap<Integer, SaleTransaction> map = new TreeMap<Integer, SaleTransaction>();
			
			// Check the presence of the JDBC drivers
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException: ");
				System.err.println(e.getMessage());
			}
			
			try {
				cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
				
				sql = "SELECT * FROM SaleTransactions WHERE state = 'RETURN_DONE' ";
				ps = cn.prepareStatement(sql);
				if(ps.execute()) {
					rs=ps.getResultSet();
					while(rs.next()) {
						
						SaleTransaction s = new SaleTransactionClass( rs.getInt("ticketNumber") , rs.getDouble("discountRate"), rs.getDouble("price"));
						map.put(s.getTicketNumber(), s);
					}
				}
				
				rs.close();
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return null;
			}
			
			return map;
			
		}
		
		public static SaleTransaction getSaleTransactionByTicket(Integer transactionId) {
			
			Connection cn;
			String sql;
			PreparedStatement ps;
			ResultSet rs;
			SaleTransaction s = new SaleTransactionClass();;
			TicketEntry t;
			ArrayList<TicketEntry> entries = new ArrayList<TicketEntry>();
			
			// Check the presence of the JDBC drivers
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException: ");
				System.err.println(e.getMessage());
			}
			
			try {
				cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
				
				sql = "SELECT * From SaleTransactions as s JOIN TicketEntries as t on s.ticketNumber = t.id WHERE s.ticketNumber = ?";
				ps = cn.prepareStatement(sql);
				ps.setInt(1, transactionId);
				if(ps.execute()) {

					rs=ps.getResultSet();
					s = new SaleTransactionClass( rs.getInt("ticketNumber") , rs.getDouble(2), rs.getDouble("price"), rs.getString("state"));
					while(rs.next()) {
						t = new TicketEntryClass(rs.getInt("id"), rs.getString("barCode"), rs.getString("productDescription"), rs.getInt("amount"), rs.getDouble("pricePerUnit"), rs.getDouble(8));
						entries.add(t);		
					}
					rs.close();
					
					s.setEntries(entries);
				}
				
				
				
				
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return null;
			}
			
			return s;
			
		}
		
public static SaleTransaction getSaleTransactionNoTickets(Integer transactionId) {
			
			Connection cn;
			String sql;
			PreparedStatement ps;
			ResultSet rs;
			SaleTransaction s = new SaleTransactionClass();;
			
			// Check the presence of the JDBC drivers
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException: ");
				System.err.println(e.getMessage());
			}
			
			try {
				cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
				
				sql = "SELECT * From SaleTransactions WHERE ticketNumber = ?";
				ps = cn.prepareStatement(sql);
				ps.setInt(1, transactionId);
				if(ps.execute()) {

					rs=ps.getResultSet();
					s = new SaleTransactionClass( rs.getInt("ticketNumber") , rs.getDouble(2), rs.getDouble("price"), rs.getString("state"));

					rs.close();

				}
				
				
				
				
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return null;
			}
			
			return s;
			
		}
		
		public static int updateSaleTransaction(int id, double price) {
			
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
				
				sql = "UPDATE SaleTransactions set price = price + ? WHERE ticketNumber = ? ";
				ps = cn.prepareStatement(sql);
				ps.setInt(2, id);
				ps.setDouble(1, price);

				ps.execute();
				
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			return 0;
			
		}
		
		public static int removeAllSaleTransactions() {
			
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
				
				sql = "DELETE FROM SaleTransactions";
				ps = cn.prepareStatement(sql);
				ps.execute();
				
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			
			return 0;
		}
		
		public static int updateSaleTransactionStatus(int id) {
			
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
				
				sql = "UPDATE SaleTransactions set state = 'RETURN_DONE' WHERE ticketNumber = ? ";
				ps = cn.prepareStatement(sql);
				ps.setInt(1, id);

				ps.execute();
				
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			return 0;
			
		}
		
		public static int addReturnTransaction(int tickeNumber, int ticketNumberR, SaleTransaction s) {
			
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
				
				sql = "INSERT into SaleTransactions (ticketNumber, discountRate, price, state) values (?,?,?,?)";
				ps = cn.prepareStatement(sql);
				ps.setInt(1, tickeNumber);
				ps.setDouble(2, s.getDiscountRate());
				ps.setDouble(3, s.getPrice());
				String status = "RETURN_"+Integer.toString(ticketNumberR);
				ps.setString(4, status);
				ps.execute();
				
				cn.close();
				ps.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			 return 0;
		}
}
