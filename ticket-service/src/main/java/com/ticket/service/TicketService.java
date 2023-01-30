package com.ticket.service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.converter.TicketConverter;
import com.ticket.exception.GlobalException;
import com.ticket.model.Ticket;
import com.ticket.model.enums.TicketStatus;
import com.ticket.model.enums.TransportationType;
import com.ticket.repository.TicketRepository;
import com.ticket.request.TicketRequest;
import com.ticket.response.TicketResponse;

@Service
public class TicketService {

	private static final int BUS_SEAT_SIZE = 45;

	private static final int AIRPLANE_SEAT_SIZE = 189;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	TicketConverter ticketConverter;

	Logger logger = Logger.getLogger(TicketService.class.getName());

	public TicketResponse create(TicketRequest ticketRequest) {

		if (ticketRepository.findByNo(ticketRequest.getNo()).isPresent()) {
			throw new GlobalException("Bilet no zaten var");
		} else {

			if (ticketRequest.getTransportationType().equals(TransportationType.AIRPLANE)) {
				Ticket ticket = ticketConverter.convert(ticketRequest);
				ticket.setSeats(AIRPLANE_SEAT_SIZE);
				ticket.setTicketCost(ticketRequest.getTicketCost());
				ticketRepository.save(ticket);
				logger.log(Level.INFO, "Airplane Tickets Created");
				return ticketConverter.convert(ticket);
			} else {
				Ticket ticket = ticketConverter.convert(ticketRequest);
				ticket.setSeats(BUS_SEAT_SIZE);
				ticket.setTicketCost(ticketRequest.getTicketCost());
				ticketRepository.save(ticket);
				logger.log(Level.INFO, "Bus Tickets Created");
				return ticketConverter.convert(ticket);

			}
		}

	}

	public List<TicketResponse> getAll() {
		return ticketConverter.convert(ticketRepository.findAll());
	}

	public List<TicketResponse> getAllByDepartureLocation(String departureLocation) {
		return ticketConverter.convert(ticketRepository.findAllByDepartureLocation(departureLocation));
	}

	public List<TicketResponse> getAllByArrivalLocation(String arrivalLocation) {
		return ticketConverter.convert(ticketRepository.findAllByArrivalLocation(arrivalLocation));
	}

	public List<TicketResponse> getAllAirplaneTickets() {
		return ticketConverter.convert(ticketRepository.findAllByTransportationType(TransportationType.AIRPLANE));
	}

	public List<TicketResponse> getAllBusTickets() {
		return ticketConverter.convert(ticketRepository.findAllByTransportationType(TransportationType.BUS));
	}

	public List<TicketResponse> getAllByDepartureTime(LocalDate departureTime) {
		return ticketConverter.convert(ticketRepository.findAllByDepartureTime(departureTime));
	}

	public List<TicketResponse> getAllByArrivalTime(LocalDate arrivalTime) {
		return ticketConverter.convert(ticketRepository.findAllByArrivalTime(arrivalTime));
	}

	public Ticket getByNo(int no) {
		return ticketRepository.findByNo(no).orElseThrow(() -> new GlobalException("Ticket bulunamadı"));
	}

	public TicketResponse getById(int id) {
		return ticketConverter
				.convert(ticketRepository.findById(id).orElseThrow(() -> new GlobalException("Ticket bulunamadı")));
	}

	public TicketResponse cancel(int no) {
		Ticket ticket = ticketRepository.findByNo(no).orElseThrow(() -> new GlobalException("Ticket bulunamadı"));
		ticket.setStatus(TicketStatus.CANCELLED);
		ticketRepository.save(ticket);
		return ticketConverter.convert(ticket);
	}

	public Double totalMoneyFromTicketSales(int no) {
		Ticket ticket = ticketRepository.findByNo(no).orElseThrow(() -> new GlobalException("Ticket bulunamadı"));
		if (ticket.getTransportationType().equals(TransportationType.AIRPLANE)) {
			double i = 189 - ticket.getSeats();
			System.out.println(i);
			return i * ticket.getTicketCost();
		} else {
			int i = 45 - ticket.getSeats();
			System.out.println(i);
			return i * ticket.getTicketCost();
		}

	}

	public Integer totalTicketSales(int no) {
		Ticket ticket = ticketRepository.findByNo(no).orElseThrow(() -> new GlobalException("Ticket bulunamadı"));
		if (ticket.getTransportationType().equals(TransportationType.AIRPLANE)) {
			int i = AIRPLANE_SEAT_SIZE - ticket.getSeats();
			return i;
		} else {
			int i = BUS_SEAT_SIZE - ticket.getSeats();
			return i;
		}
	}

	public List<TicketResponse> search(String departureLocation, String arrivalLocation, LocalDate departureTime,
			LocalDate arrivalTime) {
		return ticketConverter.convert(ticketRepository
				.findByDepartureLocationAndArrivalLocationAndDepartureTimeAndArrivalTime(departureLocation,
						arrivalLocation, departureTime, arrivalTime)
				.orElseThrow(() -> new GlobalException("Ticket bulunamadı")));
	}

}
