package it.polito.ezshop.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import it.polito.ezshop.data.Order;
import it.polito.ezshop.model.OrderClass;

public class OrderDB {
	
	// add a new Order in the DB
		public static int addOrder(int orderId, Integer balanceId, Order o) {
			
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
				
				sql = "INSERT into Orders (orderId, balanceId, productCode, pricePerUnit, quantity, status) values (?,?,?,?,?,?)";
				ps = cn.prepareStatement(sql);
				
				ps.setInt(1, orderId);
				ps.setInt(2, balanceId);
				ps.setString(3, o.getProductCode());
				ps.setDouble(4, o.getPricePerUnit());
				ps.setInt(5, o.getQuantity());
				ps.setString(6, o.getStatus());
				ps.execute();
				
				cn.close();
				ps.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			 return 0;
		}
		
		// remove User by ID
		public static int removeOrder(int orderId) {
			
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
				
				sql = "DELETE FROM Orders WHERE orderId = ?";
				ps = cn.prepareStatement(sql);
				ps.setInt(1, orderId);
				ps.execute();
				
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			
			return 0;
		}
		
		
		// See all the users in the DB 
		public static TreeMap<Integer, Order> getOrders() {
			
			Connection cn;
			String sql;
			PreparedStatement ps;
			ResultSet rs;
			TreeMap<Integer, Order> map = new TreeMap<Integer, Order>();
			
			// Check the presence of the JDBC drivers
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException: ");
				System.err.println(e.getMessage());
			}
			
			try {
				cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
				
				sql = "SELECT * FROM Orders";
				ps = cn.prepareStatement(sql);
				rs= ps.executeQuery();
				while(rs.next()) {
					Order o = new OrderClass(rs.getString("productCode") , rs.getDouble("pricePerUnit"), rs.getInt("quantity"), rs.getString("status"));
					o.setBalanceId(rs.getInt("balanceId"));
					o.setOrderId(rs.getInt("orderId"));
					map.put(o.getOrderId(), o);
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
		
		
		// ex. modify order's status to PAYED
		public static int updateOrderToCompleted(int orderId) {
			
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
				
				sql = "UPDATE Orders set status = ? WHERE orderId = ? AND status = ? ";
				ps = cn.prepareStatement(sql);
				ps.setString(1, "COMPLETED");
				ps.setInt(2, orderId);
				ps.setString(3, "PAYED");
				ps.execute();
				
				ps.close();
				cn.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			
			return 0;
		}
		
		// ex. modify order's status to PAYED
				public static int updateOrderToPayed(int orderId) {
					
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
						
						sql = "UPDATE Orders set status = ? WHERE orderId = ? AND status = ? ";
						ps = cn.prepareStatement(sql);
						ps.setString(1, "PAYED");
						ps.setInt(2, orderId);
						ps.setString(3, "ISSUED");
						ps.execute();
						
						ps.close();
						cn.close();
					} catch (SQLException e) {
						System.out.println("errore: " + e.getMessage());
						return -1;
					}
					
					return 0;
				}
				
				public static int removeAllOrders() {
					
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
						
						sql = "DELETE FROM Orders";
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
