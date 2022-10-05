package it.polito.ezshop.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.TreeMap;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.model.BalanceOperationClass;


public class BalanceOperationDB {

	// add a new BalanceOperation in the DB
	public static int addBalanceOperation(int id, BalanceOperation b) {
		
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
			
			sql = "INSERT into BalanceOperations (id, dateOperation, money, type) values (?,?,?,?)";
			ps = cn.prepareStatement(sql);
			ps.setInt(1, id);
			
			String sDate = b.getDate().toString();
			ps.setString(2, sDate);
			
			ps.setDouble(3, b.getMoney());
			ps.setString(4, b.getType());
			ps.execute();
			
			cn.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("errore: " + e.getMessage());
			return -1;
		}
		 return 0;
	}
	
	// remove Balance by ID
	public static int removeBalanceOperation(int balanceId) {
		
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
			
			sql = "DELETE FROM BalanceOperations WHERE id = ?";
			ps = cn.prepareStatement(sql);
			ps.setInt(1, balanceId);
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
	public static TreeMap<Integer, BalanceOperation> getBalanceOperations() {
		
		Connection cn;
		String sql;
		PreparedStatement ps;
		ResultSet rs=null;
		TreeMap<Integer, BalanceOperation> map = new TreeMap<Integer, BalanceOperation>();
		
		// Check the presence of the JDBC drivers
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
			
			sql = "SELECT * FROM BalanceOperations";
			ps = cn.prepareStatement(sql);
			if(ps.execute()) {
				rs=ps.getResultSet();
				while(rs.next()) {
					LocalDate sDate = LocalDate.parse(rs.getString("dateOperation"));
					BalanceOperation b = new BalanceOperationClass( sDate , rs.getDouble("money"), rs.getString("type"));
					b.setBalanceId(rs.getInt("id"));
					map.put(b.getBalanceId(), b);
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
	
	public static TreeMap<Integer, BalanceOperation> getBalanceOperationsByDates(LocalDate from, LocalDate to) {
		
		Connection cn;
		String sql;
		PreparedStatement ps;
		ResultSet rs=null;
		TreeMap<Integer, BalanceOperation> map = new TreeMap<Integer, BalanceOperation>();
		
		// Check the presence of the JDBC drivers
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
			
			sql = "SELECT * FROM BalanceOperations WHERE dateOperation BETWEEN ? AND ?";
			ps = cn.prepareStatement(sql);
			String sFrom = from.toString();
			String sTo = to.toString();
			ps.setString(1, sFrom);
			ps.setString(2, sTo);
			if(ps.execute()) {
				rs=ps.getResultSet();
				while(rs.next()) {
					LocalDate sDate = LocalDate.parse(rs.getString("dateOperation"));
					BalanceOperation b = new BalanceOperationClass( sDate , rs.getDouble("money"), rs.getString("type"));
					b.setBalanceId(rs.getInt("id"));
					map.put(b.getBalanceId(), b);
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
	
	public static TreeMap<Integer, BalanceOperation> getBalanceOperationsByFrom(LocalDate from) {
		
		Connection cn;
		String sql;
		PreparedStatement ps;
		ResultSet rs=null;
		TreeMap<Integer, BalanceOperation> map = new TreeMap<Integer, BalanceOperation>();
		
		// Check the presence of the JDBC drivers
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
			
			sql = "SELECT * FROM BalanceOperations WHERE dateOperation >= ?";
			ps = cn.prepareStatement(sql);
			String sFrom = from.toString();
			ps.setString(1, sFrom);
			if(ps.execute()) {
				rs=ps.getResultSet();
				while(rs.next()) {
					LocalDate sDate = LocalDate.parse(rs.getString("dateOperation"));
					BalanceOperation b = new BalanceOperationClass( sDate , rs.getDouble("money"), rs.getString("type"));
					b.setBalanceId(rs.getInt("id"));
					map.put(b.getBalanceId(), b);
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
	
	public static TreeMap<Integer, BalanceOperation> getBalanceOperationsByTo(LocalDate to) {
		
		Connection cn;
		String sql;
		PreparedStatement ps;
		ResultSet rs=null;
		TreeMap<Integer, BalanceOperation> map = new TreeMap<Integer, BalanceOperation>();
		
		// Check the presence of the JDBC drivers
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
			
			sql = "SELECT * FROM BalanceOperations WHERE dateOperation <= ?";
			ps = cn.prepareStatement(sql);
			String sTo = to.toString();
			ps.setString(1, sTo);
			if(ps.execute()) {
				rs=ps.getResultSet();
				while(rs.next()) {
					LocalDate sDate = LocalDate.parse(rs.getString("dateOperation"));
					BalanceOperation b = new BalanceOperationClass( sDate , rs.getDouble("money"), rs.getString("type"));
					b.setBalanceId(rs.getInt("id"));
					map.put(b.getBalanceId(), b);
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
	
	public static double balanceSum() {
		
		Connection cn;
		String sql;
		PreparedStatement ps;
		ResultSet rs=null;
		double res = 0;
		// Check the presence of the JDBC drivers
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
			
			sql = "SELECT SUM(money) FROM BalanceOperations WHERE type != 'ORDER_TO_PAY' ";
			ps = cn.prepareStatement(sql);

			if(ps.execute()) {
				rs=ps.getResultSet();
				res = rs.getDouble("SUM(money)");
			}
			
			
			rs.close();
			ps.close();
			cn.close();
		} catch (SQLException e) {
			System.out.println("errore: " + e.getMessage());
			return res;
		}
		
		return res;
		
	}
	
public static int removeAllBalanceOperations() {
		
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
			
			sql = "DELETE FROM BalanceOperations ";
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

public static int updateBalanceToOrder(Integer id) {
	
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
		
		sql = "UPDATE BalanceOperations set type = ? WHERE id = ? ";
		ps = cn.prepareStatement(sql);
		ps.setString(1, "ORDER");
		ps.setInt(2, id);
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
