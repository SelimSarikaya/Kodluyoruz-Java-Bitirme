package com.ticket.response;

import java.util.List;

import com.ticket.model.enums.Sex;

public class OrderResponse {

	private TicketResponse ticketResponse;
	private UserResponse userResponse;
	private Integer amount;
	private List<Sex> sexes;
	private List<String> nameSurnames;
	private double cost;
	
	
	
	public OrderResponse() {
		super();
	}
	public OrderResponse(TicketResponse ticketResponse, UserResponse userResponse, Integer amount, List<Sex> sexes,
			List<String> nameSurnames, double cost) {
		super();
		this.ticketResponse = ticketResponse;
		this.userResponse = userResponse;
		this.amount = amount;
		this.sexes = sexes;
		this.nameSurnames = nameSurnames;
		this.cost = cost;
	}
	public TicketResponse getTicketResponse() {
		return ticketResponse;
	}
	public void setTicketResponse(TicketResponse ticketResponse) {
		this.ticketResponse = ticketResponse;
	}
	public UserResponse getUserResponse() {
		return userResponse;
	}
	public void setUserResponse(UserResponse userResponse) {
		this.userResponse = userResponse;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public List<Sex> getSexes() {
		return sexes;
	}
	public void setSexes(List<Sex> sexes) {
		this.sexes = sexes;
	}
	public List<String> getNameSurnames() {
		return nameSurnames;
	}
	public void setNameSurnames(List<String> nameSurnames) {
		this.nameSurnames = nameSurnames;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}

	
	
}
