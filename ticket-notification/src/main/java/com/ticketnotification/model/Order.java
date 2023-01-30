package com.ticketnotification.model;

import java.util.List;


public class Order {

	private User user;
	private Integer amount;
	private double cost;
	private List<Ticket> ticketList;

	public Order() {
		super();
	}



	public Order(User user, Integer amount, double cost, List<Ticket> ticketList) {
		super();
		this.user = user;
		this.amount = amount;
		this.cost = cost;
		this.ticketList = ticketList;
	}



	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}



	public List<Ticket> getTicketList() {
		return ticketList;
	}



	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}




}
