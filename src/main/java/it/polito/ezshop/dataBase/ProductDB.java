package it.polito.ezshop.dataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDB {
	
	public static int addProduct(String barCode,String RFID) {
			
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
				
				sql = "INSERT into Product (barCode,RFID) values (?,?)";
				ps = cn.prepareStatement(sql);
				ps.setString(1, barCode);
				ps.setString(2,RFID);
				ps.execute();
				cn.close();
				ps.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			return 0;
			
	}
	
	public static String getBarCode(String RFID) {
		
		Connection cn;
		String sql;
		PreparedStatement ps;
		ResultSet rs;
		String res;
		
		// Check the presence of the JDBC drivers
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
			
			sql = "SELECT barCode FROM product WHERE RFID=?";
			ps = cn.prepareStatement(sql);
			ps.setString(1, RFID);
			ps.execute();
			rs=ps.getResultSet();
			res = rs.getString("barCode");
			cn.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("errore: " + e.getMessage());
			return null;
		}
		return res;
		
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
			
			sql = "DELETE FROM product";
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