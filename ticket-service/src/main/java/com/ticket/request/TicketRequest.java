package com.ticket.request;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ticket.model.enums.TransportationType;

public class TicketRequest {

	private Integer no;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate departureTime;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate arrivalTime;
	private String departureLocation;
	private String arrivalLocation;
	private TransportationType transportationType;
	private double ticketCost;

	public TicketRequest() {
		super();
	}

	public TicketRequest(Integer no, LocalDate departureTime, LocalDate arrivalTime, String departureLocation,
			String arrivalLocation, TransportationType transportationType, double ticketCost) {
		super();
		this.no = no;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departureLocation = departureLocation;
		this.arrivalLocation = arrivalLocation;
		this.transportationType = transportationType;
		this.ticketCost = ticketCost;
	}

	public TransportationType getTransportationType() {
		return transportationType;
	}

	public void setTransportationType(TransportationType transportationType) {
		this.transportationType = transportationType;
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

	public double getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(double ticketCost) {
		this.ticketCost = ticketCost;
	}



}
