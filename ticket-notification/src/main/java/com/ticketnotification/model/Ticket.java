package com.ticketnotification.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class Ticket {

	private Integer no;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate departureTime;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate arrivalTime;
	private String departureLocation;
	private String arrivalLocation;

	public Ticket() {
		super();
	}

	public Ticket(Integer no, LocalDate departureTime, LocalDate arrivalTime, String departureLocation,
			String arrivalLocation) {
		super();
		this.no = no;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departureLocation = departureLocation;
		this.arrivalLocation = arrivalLocation;
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

	@Override
	public String toString() {
		return "Ticket [no=" + no + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime
				+ ", departureLocation=" + departureLocation + ", arrivalLocation=" + arrivalLocation + "]";
	}

}
