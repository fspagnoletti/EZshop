package it.polito.ezshop.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.polito.ezshop.model.TicketEntryClass;

public class TicketEntryDB {
	
		// add a new SaleTransaction in the DB
			public static int addTicket(int id, TicketEntryClass t) {
				
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
					
					sql = "INSERT into TicketEntries (id, barcode, amount, discountRate, pricePerUnit, productDescription) values (?,?,?,?,?,?)";
					ps = cn.prepareStatement(sql);
					ps.setInt(1, id);
					ps.setString(2, t.getBarCode());
					ps.setInt(3, t.getAmount());
					ps.setDouble(4, t.getDiscountRate());
					ps.setDouble(5, t.getPricePerUnit());
					ps.setString(6, t.getProductDescription());
					ps.execute();
					
					cn.close();
					ps.close();
				} catch (SQLException e) {
					System.out.println("errore: " + e.getMessage());
					return -1;
				}
				 return 0;
			}
			
			// remove ticket by ID
			public static int removeTicket(int Id) {
				
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
					
					sql = "DELETE FROM TicketEntries WHERE id = ?";
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
			
			
			// See all the tickets in the DB 
			public static ArrayList<TicketEntryClass> getTickets(Integer ticketNumber) {
				
				Connection cn;
				String sql;
				PreparedStatement ps;
				ResultSet rs=null;
				ArrayList<TicketEntryClass> list = new ArrayList<TicketEntryClass>();
				
				// Check the presence of the JDBC drivers
				try {
					Class.forName("org.sqlite.JDBC");
				} catch (ClassNotFoundException e) {
					System.out.println("ClassNotFoundException: ");
					System.err.println(e.getMessage());
				}
				
				try {
					cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
					
					sql = "SELECT TicketEntries.id, TicketEntries.barCode, amount, discountRate, TicketEntries.productDescription, TicketEntries.pricePerUnit, note, location, quantity FROM TicketEntries JOIN ProductTypes ON ProductTypes.barCode = TicketEntries.barCode WHERE TicketEntries.id = ? ";
					ps = cn.prepareStatement(sql);
					ps.setInt(1, ticketNumber);
					if(ps.execute()) {
						rs=ps.getResultSet();
						while(rs.next()) {
							TicketEntryClass t = new TicketEntryClass( rs.getString("barCode") , rs.getString("productDescription"), rs.getInt("amount"), rs.getDouble("pricePerUnit"), rs.getDouble("discountRate"));
							t.setId(rs.getInt("id"));
							list.add(t);
						}
					}
					
					rs.close();
					ps.close();
					cn.close();
				} catch (SQLException e) {
					System.out.println("errore: " + e.getMessage());
					return null;
				}
				
				return list;
				
			}

			public static int updateTicketEntry(int amount, TicketEntryClass t) {
				
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
					
					sql = "UPDATE TicketEntries set amount = amount + ? WHERE id = ? AND barCode = ?";
					ps = cn.prepareStatement(sql);
					ps.setInt(1, amount);
					ps.setInt(2, t.getId());
					ps.setString(3, t.getBarCode());

					ps.execute();
					
					ps.close();
					cn.close();
				} catch (SQLException e) {
					System.out.println("errore: " + e.getMessage());
					return -1;
				}
				return 0;
				
			}
			
			public static int removeAllTickets() {
				
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
					
					sql = "DELETE FROM TicketEntries ";
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
			

}
