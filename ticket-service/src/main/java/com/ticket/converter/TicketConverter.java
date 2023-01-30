package com.ticket.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ticket.model.Ticket;
import com.ticket.model.enums.TicketStatus;
import com.ticket.request.TicketRequest;
import com.ticket.response.TicketResponse;

@Component
public class TicketConverter {

	public Ticket convert(TicketRequest ticketRequest) {
		Ticket ticket = new Ticket();
		ticket.setNo(ticketRequest.getNo());
		ticket.setArrivalLocation(ticketRequest.getArrivalLocation());
		ticket.setDepartureLocation(ticketRequest.getDepartureLocation());
		ticket.setArrivalTime(ticketRequest.getArrivalTime());
		ticket.setDepartureTime(ticketRequest.getDepartureTime());
		ticket.setNo(ticketRequest.getNo());
		ticket.setTransportationType(ticketRequest.getTransportationType());
		ticket.setStatus(TicketStatus.BOOKED);
		ticket.setTicketCost(ticketRequest.getTicketCost());
		return ticket;
	}

	public TicketResponse convert(Ticket ticket) {
		TicketResponse ticketResponse = new TicketResponse();
		ticketResponse.setNo(ticket.getNo());
		ticketResponse.setArrivalLocation(ticket.getArrivalLocation());
		ticketResponse.setArrivalTime(ticket.getArrivalTime());
		ticketResponse.setDepartureLocation(ticket.getDepartureLocation());
		ticketResponse.setDepartureTime(ticket.getDepartureTime());
		ticketResponse.setTransportationType(ticket.getTransportationType());
		ticketResponse.setTicketCost(ticket.getTicketCost());
		ticketResponse.setTicketStatus(ticket.getStatus());
		return ticketResponse;
	}

	public List<TicketResponse> convert(List<Ticket> ticketList) {
		List<TicketResponse> ticketResponses = new ArrayList<>();
		for (Ticket ticket : ticketList) {
			ticketResponses.add(convert(ticket));
		}
		return ticketList.stream().map(this::convert).toList();
	}
}
