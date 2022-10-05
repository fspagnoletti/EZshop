package it.polito.ezshop.testDB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.TreeMap;

import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.dataBase.ProductTypeDB;
import it.polito.ezshop.model.ProductTypeClass;

public class TestProductTypeDB {

	@Test
	public void testAddProduct1() {
		ProductTypeDB.deleteAll();
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 1.03, "minerale");
		p.setId(1);
		ProductTypeDB.addProductType(1, p);
		ProductType p2= ProductTypeDB.getProducts().get(1);
		
		assertNotNull(p2);
		assertEquals(p.getBarCode(), p2.getBarCode());
		assertEquals(p.getProductDescription(), p2.getProductDescription());
		assertEquals(p.getPricePerUnit(), p2.getPricePerUnit());
		assertEquals(p.getNote(), p2.getNote());
		assertEquals(p.getId(), p2.getId());
		
		ProductTypeDB.deleteAll();
	}
	
	@Test
	public void testAddProduct2() {
		ProductTypeDB.deleteAll();
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 1.03, "minerale");
		p.setId(1);
		ProductTypeDB.addProductType(1, p);
		
		ProductType p2=new ProductTypeClass("acqua", "6291041500312", 1.03, "minerale");
		p.setId(1);
		ProductTypeDB.addProductType(1, p2);
		
		TreeMap<Integer, ProductType> res=ProductTypeDB.getProducts();
		
		assertEquals(res.size(),1);
		
		ProductTypeDB.deleteAll();
	}
	@Test
	public void testUpdateQuantity() {
		ProductTypeDB.deleteAll();
		
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 1.03, "minerale");
		p.setId(1);
		ProductTypeDB.addProductType(1, p);
		
		ProductTypeDB.updateProductQuantity(1, 10);
		
		int q=ProductTypeDB.getProducts().get(1).getQuantity();
		
		assertEquals(q,10);
		ProductTypeDB.deleteAll();
	}
	
	@Test
	public void testUpdateProduct() {
		ProductTypeDB.deleteAll();
		
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 1.03, "minerale");
		p.setId(1);
		ProductTypeDB.addProductType(1, p);
		
		String description="olio";
		String barCode="6291041500312";
		double price=4.5;
		String noteString="vergine";
		ProductTypeDB.updateProductType(1, description,barCode,price,noteString);
		
		ProductType p1=ProductTypeDB.getProducts().get(1);
		
		assertEquals(p1.getProductDescription(), description);
		assertEquals(p1.getBarCode(), barCode);
		assertEquals(p1.getPricePerUnit(), price,0);
		assertEquals(p1.getNote(), noteString);
		
		ProductTypeDB.deleteAll();
	}
	
	@Test
	public void testUpdatePosition() {
		ProductTypeDB.deleteAll();
		
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 1.03, "minerale");
		p.setId(1);
		ProductTypeDB.addProductType(1, p);
		
		ProductTypeDB.updateProductPosition(1, "1-2-3");
		
		String pos=ProductTypeDB.getProducts().get(1).getLocation();
		
		assertEquals(pos,"1-2-3");
		ProductTypeDB.deleteAll();
	}
	@Test
	public void testRemoveProduct() {
		ProductTypeDB.deleteAll();
		
		ProductType p=new ProductTypeClass("acqua", "6291041500213", 1.03, "minerale");
		p.setId(1);
		ProductTypeDB.addProductType(1, p);
		
		ProductTypeDB.removeProductType(1);
		TreeMap<Integer, ProductType> res=ProductTypeDB.getProducts();
		assertEquals(res.size(),0);
		
		ProductTypeDB.deleteAll();
	}
}
