package com.ticket.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ticket.model.enums.Sex;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticketlist", referencedColumnName = "id", nullable = true)
	private List<Ticket> ticketList;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	private Integer amount;
	private double cost;
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Sex> sexes;
	@ElementCollection
	private List<String> nameSurnames;

	public Order() {
		super();
	}

	public Order(List<Ticket> ticketList, List<Sex> sexes, List<String> nameSurnames) {
		super();
		this.ticketList = ticketList;
		this.sexes = sexes;
		this.nameSurnames = nameSurnames;
	}

	public Order(Integer id, List<Ticket> ticketList, User user, Integer amount, double cost, List<Sex> sexes,
			List<String> nameSurnames) {
		super();
		this.id = id;
		this.ticketList = ticketList;
		this.user = user;
		this.amount = amount;
		this.cost = cost;
		this.sexes = sexes;
		this.nameSurnames = nameSurnames;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
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
