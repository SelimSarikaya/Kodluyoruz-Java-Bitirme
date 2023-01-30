package com.ticket.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ticket.model.enums.TicketStatus;
import com.ticket.model.enums.TransportationType;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private Integer no;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate departureTime;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate arrivalTime;
	private String departureLocation;
	private String arrivalLocation;
	@Enumerated(EnumType.STRING)
	private TransportationType transportationType;
	@Enumerated(EnumType.STRING)
	private TicketStatus status;
	private Integer seats;
	private double ticketCost;

	public Ticket() {
		super();
	}

	public Ticket(Integer no, LocalDate departureTime, LocalDate arrivalTime, String departureLocation,
			String arrivalLocation, TransportationType transportationType, TicketStatus status, Integer seats,
			double ticketCost) {
		super();
		this.no = no;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departureLocation = departureLocation;
		this.arrivalLocation = arrivalLocation;
		this.transportationType = transportationType;
		this.status = status;
		this.seats = seats;
		this.ticketCost = ticketCost;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public LocalDate getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDate departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDate getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDate arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}

	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}

	public TransportationType getTransportationType() {
		return transportationType;
	}

	public void setTransportationType(TransportationType transportationType) {
		this.transportationType = transportationType;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public double getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(double ticketCost) {
		this.ticketCost = ticketCost;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", no=" + no + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime
				+ ", departureLocation=" + departureLocation + ", arrivalLocation=" + arrivalLocation
				+ ", transportationType=" + transportationType + ", status=" + status + ", seats=" + seats + "]";
	}

}
