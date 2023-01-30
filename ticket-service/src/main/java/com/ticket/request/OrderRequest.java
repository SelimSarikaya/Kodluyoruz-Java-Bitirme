package com.ticket.request;

import java.util.List;
import com.ticket.model.enums.Sex;

public class OrderRequest {

	private Integer ticketNo;
	private Integer userId;
	private Integer amount;
	private List<Sex> sexes;
	private List<String> nameSurnames;

	public OrderRequest() {
		super();
	}

	public OrderRequest(Integer ticketNo, Integer userId, Integer amount, List<Sex> sexes, List<String> nameSurnames) {
		super();
		this.ticketNo = ticketNo;
		this.userId = userId;
		this.amount = amount;
		this.sexes = sexes;
		this.nameSurnames = nameSurnames;

	}

	public Integer getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(Integer ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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


}
