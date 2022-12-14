package it.polito.ezshop.model;

import java.time.LocalDate;

import it.polito.ezshop.data.BalanceOperation;

public class BalanceOperationClass implements BalanceOperation{
	
	private int balanceId;
	private LocalDate date;
	private double money;
	private String type;
	
	
	public BalanceOperationClass(LocalDate date, double money, String type) {
		super();
		this.date = date;
		this.money = money;
		this.type = type;
	}
	
	
	public int getBalanceId() {
		return balanceId;
	}
	public void setBalanceId(int balanceId) {
		this.balanceId = balanceId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


}
