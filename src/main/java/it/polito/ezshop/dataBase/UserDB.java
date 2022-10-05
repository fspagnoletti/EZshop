package it.polito.ezshop.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import it.polito.ezshop.data.User;
import it.polito.ezshop.model.UserClass;

public class UserDB {
	
	public static TreeMap<Integer, User> getUsers(){
		Connection cn;
		String sql;
		PreparedStatement ps;
		ResultSet rs=null;
		TreeMap<Integer, User> map=new TreeMap<Integer,User>();
		
		// Check the presence of the JDBC drivers
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
			
			sql = "SELECT * FROM Users";
			ps = cn.prepareStatement(sql);
			if(ps.execute()) {
				rs=ps.getResultSet();
				while(rs.next()) {
					User u= new UserClass(rs.getString("username"), rs.getString("password"), rs.getString("role"));
					u.setId(rs.getInt("id"));
					map.put(u.getId(), u);
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
	
	public static int addUser(int id, User u) {
		
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
			
			sql = "INSERT into Users (id, username, password, role) values (?,?,?,?)";
			ps = cn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, u.getUsername());
			ps.setString(3, u.getPassword());
			ps.setString(4, u.getRole());
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
	public static int removeUser(int id) {
		
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
			
			sql = "DELETE FROM Users WHERE id = ?";
			ps = cn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			
			System.out.println("Data has been removed");
			ps.close();
			cn.close();
		} catch (SQLException e) {
			System.out.println("errore: " + e.getMessage());
			return -1;
		}
		return 1;
	}
	


public static int updateUserRights(Integer id, String role) {
		
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
			
			sql = "UPDATE Users set role = ? WHERE id = ? ";
			ps = cn.prepareStatement(sql);
			ps.setString(1, role);
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

	public static int deleteAll() {
		
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
			
			sql = "DELETE FROM Users";
			ps = cn.prepareStatement(sql);
			ps.execute();
			System.out.println("Data has been removed");
			
			ps.close();
			cn.close();
		} catch (SQLException e) {
			System.out.println("errore: " + e.getMessage());
			return -1;
		}
		return 1;
	
	}
}

