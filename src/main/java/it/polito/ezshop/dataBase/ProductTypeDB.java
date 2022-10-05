package it.polito.ezshop.dataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import it.polito.ezshop.model.ProductTypeClass;
import it.polito.ezshop.data.ProductType;

public class ProductTypeDB {
	
	public static TreeMap<Integer, ProductType> getProducts(){
		Connection cn;
		String sql;
		PreparedStatement ps;
		ResultSet rs=null;
		TreeMap<Integer, ProductType> map=new TreeMap<Integer,ProductType>();
		
		// Check the presence of the JDBC drivers
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			cn = DriverManager.getConnection("jdbc:sqlite:EZshopDB.db");
			
			sql = "SELECT * FROM ProductTypes";
			ps = cn.prepareStatement(sql);
			if(ps.execute()) {
				rs=ps.getResultSet();
				while(rs.next()) {
					ProductType p= new ProductTypeClass(rs.getString("productDescription"), rs.getString("barCode"), rs.getDouble("pricePerUnit"), rs.getString("note"));
					p.setId(rs.getInt("id"));
					p.setLocation(rs.getString("location"));
					p.setQuantity(rs.getInt("quantity"));
					map.put(p.getId(), p);
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
	
	public static int addProductType(int id, ProductType p) {
			
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
				
				sql = "INSERT into ProductTypes (id, barCode,productDescription, pricePerUnit, note) values (?,?,?,?,?)";
				ps = cn.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setString(2, p.getBarCode());
				ps.setString(3, p.getProductDescription());
				ps.setDouble(4, p.getPricePerUnit());
				ps.setString(5, p.getNote());
				ps.execute();
				cn.close();
				ps.close();
			} catch (SQLException e) {
				System.out.println("errore: " + e.getMessage());
				return -1;
			}
			return 0;
			
	}

	public static int updateProductType(Integer id, String description, String barCode, double pricePerUnit, String note) {
		
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
			
			sql = "UPDATE productTypes set productDescription = ?,barCode=?, pricePerUnit=?, note=? WHERE id = ? ";
			ps = cn.prepareStatement(sql);
			ps.setString(1, description);
			ps.setString(2, barCode);
			ps.setDouble(3, pricePerUnit);
			ps.setString(4, note);
			ps.setInt(5, id);
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

public static int removeProductType(int id) {
		
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
			
			sql = "DELETE FROM productTypes WHERE id = ?";
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

	public static int updateProductQuantity(Integer id,int toBeAdded) {
		
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
			
			sql = "UPDATE productTypes set quantity=? WHERE id = ? ";
			ps = cn.prepareStatement(sql);
			ps.setInt(1, toBeAdded);
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
	
	public static int updateProductPosition(Integer id,String position) {
		
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
			
			sql = "UPDATE productTypes set location=? WHERE id = ? ";
			ps = cn.prepareStatement(sql);
			ps.setString(1, position);
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
			
			sql = "DELETE FROM productTypes";
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
