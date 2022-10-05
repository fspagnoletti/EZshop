package it.polito.ezshop.model;

import it.polito.ezshop.data.Order;

public class OrderClass implements Order{
	
	//private BalanceOperationClass balanceOperation;
	private Integer orderId; 
	private String productCode;
	private double pricePerUnit;
	private int quantity;
	private String status;
	private Integer balanceId;

	public OrderClass(String productCode, double pricePerUnit, int quantity, String status) {
		super();
		this.productCode = productCode;
		this.pricePerUnit = pricePerUnit;
		this.quantity = quantity;
		this.status = status;
	}




	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public String getProductCode() {
		return productCode;
	}


	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public double getPricePerUnit() {
		return pricePerUnit;
	}


	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}




	public Integer getBalanceId() {
		return balanceId;
	}




	public void setBalanceId(Integer balanceId) {
		this.balanceId = balanceId;
	}




	@Override
	public String toString() {
		return "OrderClass [orderId=" + orderId + ", productCode=" + productCode + ", pricePerUnit=" + pricePerUnit
				+ ", quantity=" + quantity + ", status=" + status + ", balanceId=" + balanceId + "]";
	}


	
	

}
