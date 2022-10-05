package it.polito.ezshop.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;

public class SaleTransactionClass implements SaleTransaction{
	
	private Integer ticketNumber;
	private List<TicketEntry> ticketsList = new ArrayList<TicketEntry>();
	private double discountRate;
	private double price;
	private String state;

	

	public SaleTransactionClass(List<TicketEntry> ticketsList, double discountRate, double price) {
		super();
		this.ticketsList = ticketsList;
		this.discountRate = discountRate;
		this.price = price;
	}
	
	public SaleTransactionClass(Integer ticketNumber, double price) {
		this.ticketNumber = ticketNumber;
		this.price = price;
	}
	
	

	public SaleTransactionClass(Integer ticketNumber, double discountRate, double price) {
		super();
		this.ticketNumber = ticketNumber;
		this.discountRate = discountRate;
		this.price = price;
	}

	

	public SaleTransactionClass(Integer ticketNumber, double discountRate, double price,
			String state) {
		super();
		this.ticketNumber = ticketNumber;
		this.discountRate = discountRate;
		this.price = price;
		this.state = state;
	}

	public SaleTransactionClass() {
		super();
	}



	public Integer getTicketNumber() {
		return ticketNumber;
	}



	public void setTicketNumber(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
	}



	public List<TicketEntry> getTicketsList() {
		return ticketsList;
	}



	public void setTicketsList(List<TicketEntry> ticketsList) {
		this.ticketsList = ticketsList;
	}



	public double getDiscountRate() {
		return discountRate;
	}



	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}




	public List<TicketEntry> getEntries() {
		return ticketsList;
	}




	public void setEntries(List<TicketEntry> ticketsList) {
		  this.ticketsList = ticketsList;

	}



	@Override
	public String toString() {
		return "SaleTransactionClass [ticketNumber=" + ticketNumber + ", ticketsList=" + ticketsList + ", discountRate="
				+ discountRate + ", price=" + price + "]";
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	


}
