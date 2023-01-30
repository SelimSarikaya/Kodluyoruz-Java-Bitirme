package com.ticketpayment.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ticketpayment.client.Sex;

@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private Integer userId;
	private String cardNo;
	private String eftNo;
	private Integer amount;
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Sex> sexes;
	@ElementCollection
	private List<String> nameSurnames;
	private Integer ticketNo;

	public Payment() {
		super();
	}

	public Payment(Integer id, Integer userId, String cardNo, String eftNo, Integer amount, List<Sex> sexes,
			List<String> nameSurnames, Integer ticketNo) {
		super();
		this.id = id;
		this.userId = userId;
		this.cardNo = cardNo;
		this.eftNo = eftNo;
		this.amount = amount;
		this.sexes = sexes;
		this.nameSurnames = nameSurnames;
		this.ticketNo = ticketNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(Integer ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getEftNo() {
		return eftNo;
	}

	public void setEftNo(String eftNo) {
		this.eftNo = eftNo;
	}

}