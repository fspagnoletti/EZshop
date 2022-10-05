package it.polito.ezshop.testClass;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import it.polito.ezshop.model.ProductTypeClass;
import it.polito.ezshop.data.ProductType;


public class TestProductType {

	@Test
	public void testQuantity() {
		ProductType p=new ProductTypeClass("acqua","6291041500213",4,"minerale");
		p.setQuantity(10);
		int q=p.getQuantity();
		assertEquals(10, q);

		ProductType p1=new ProductTypeClass("acqua","6291041500213",4,"minerale");
		p1.setQuantity(100);
		int q1=p1.getQuantity();
		assertEquals(100, q1);
	}
	
	@Test
	public void testLocation() {
		ProductType p=new ProductTypeClass("acqua","6291041500213",4,"minerale");
		p.setLocation("1-2-3");
		String loc=p.getLocation();
		assertEquals("1-2-3",loc);
	}
	
	@Test
	public void testNote() {
		ProductType p=new ProductTypeClass("acqua","6291041500213",4,"minerale");
		p.setNote("naturale");
		String note=p.getNote();
		assertEquals("naturale",note);
	}
	
	@Test
	public void testProductDescription() {
		ProductType p=new ProductTypeClass("acqua","6291041500213",4,"minerale");
		p.setProductDescription("fanta");
		String description=p.getProductDescription();
		assertEquals("fanta",description);
	}
	
	@Test
	public void testBarCode() {
		ProductType p=new ProductTypeClass("acqua","6291041500213",4,"minerale");
		p.setBarCode("6291041500312");
		String code=p.getBarCode();
		assertEquals("6291041500312", code);
	}
	
	
	@Test
	public void testPricePerUnit() {
		ProductType p=new ProductTypeClass("acqua","6291041500213",4,"minerale");
		p.setPricePerUnit(10.4);
		double q=p.getPricePerUnit();
		assertEquals(10.4, q,0);
	}
	
	@Test
	public void testId() {
		ProductType p=new ProductTypeClass("acqua","6291041500213",4,"minerale");
		p.setId(10);
		int id=p.getId();
		assertEquals(10, id);

		ProductType p1=new ProductTypeClass("acqua","6291041500215",4,"minerale");
		p1.setId(-100);
		int id1=p1.getId();
		assertEquals(-100, id1);
	}

}
