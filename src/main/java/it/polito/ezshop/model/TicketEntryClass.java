package it.polito.ezshop.model;

import it.polito.ezshop.data.TicketEntry;

public class TicketEntryClass implements TicketEntry{
	
	Integer id;
	String barCode;
	String productDescription;
	int amount;
	double pricePerUnit;
	double discountRate;
	
	
	
	public TicketEntryClass(String barCode, String productDescription, int amount, double pricePerUnit) {
		super();
		this.barCode = barCode;
		this.productDescription = productDescription;
		this.amount = amount;
		this.pricePerUnit = pricePerUnit;
	}
	
	
	
	
	public TicketEntryClass() {
		super();
	}




	public TicketEntryClass(String barCode, int amount) {
		super();
		this.barCode = barCode;
		this.amount = amount;
	}

	


	public TicketEntryClass(String barCode, String productDescription, int amount, double pricePerUnit,
			double discountRate) {
		super();
		this.barCode = barCode;
		this.productDescription = productDescription;
		this.amount = amount;
		this.pricePerUnit = pricePerUnit;
		this.discountRate = discountRate;
	}




	public TicketEntryClass(Integer id, String barCode, String productDescription, int amount, double pricePerUnit) {
		super();
		this.id = id;
		this.barCode = barCode;
		this.productDescription = productDescription;
		this.amount = amount;
		this.pricePerUnit = pricePerUnit;
	}




	public TicketEntryClass(Integer id, String barCode, String productDescription, int amount, double pricePerUnit,
			double discountRate) {
		super();
		this.id = id;
		this.barCode = barCode;
		this.productDescription = productDescription;
		this.amount = amount;
		this.pricePerUnit = pricePerUnit;
		this.discountRate = discountRate;
	}




	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}




	@Override
	public String toString() {
		return "TicketEntryClass [id=" + id + ", barCode=" + barCode + ", productDescription=" + productDescription
				+ ", amount=" + amount + ", pricePerUnit=" + pricePerUnit + ", discountRate=" + discountRate + "]";
	}

	

}
