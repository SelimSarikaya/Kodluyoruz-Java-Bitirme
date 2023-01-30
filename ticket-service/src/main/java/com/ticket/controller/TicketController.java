package com.ticket.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.converter.TicketConverter;
import com.ticket.request.TicketRequest;
import com.ticket.response.TicketResponse;
import com.ticket.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping
public class TicketController {

	@Autowired
	TicketService ticketService;
	@Autowired
	TicketConverter ticketConverter;

	@PostMapping(value = "/admin/tickets")
	public ResponseEntity<TicketResponse> create(@RequestBody TicketRequest ticketRequest) {
		TicketResponse ticketResponse = ticketService.create(ticketRequest);
		return ResponseEntity.ok(ticketResponse);
	}

	@GetMapping(value = "/tickets")
	public ResponseEntity<List<TicketResponse>> getAll() {
		return ResponseEntity.ok(ticketService.getAll());
	}

	@GetMapping(value = "/tickets/departureLocation/{departureLocation}")
	public ResponseEntity<List<TicketResponse>> getAllByDepartureLocation(@PathVariable String departureLocation) {
		return ResponseEntity.ok(ticketService.getAllByDepartureLocation(departureLocation));
	}

	@GetMapping(value = "/tickets/arrivalLocation/{arrivalLocation}")
	public ResponseEntity<List<TicketResponse>> getAllByArrivalLocation(@PathVariable String arrivalLocation) {
		return ResponseEntity.ok(ticketService.getAllByArrivalLocation(arrivalLocation));
	}

	@GetMapping(value = "/tickets/departureTime/{departureTime}")
	public ResponseEntity<List<TicketResponse>> getAllByDepartureTime(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureTime) {
		return ResponseEntity.ok(ticketService.getAllByDepartureTime(departureTime));
	}

	@GetMapping(value = "/tickets/arrivalTime/{arrivalTime}")
	public ResponseEntity<List<TicketResponse>> getAllByArrivalTime(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalTime) {
		return ResponseEntity.ok(ticketService.getAllByArrivalTime(arrivalTime));
	}

	@GetMapping(value = "/tickets/bus")
	public ResponseEntity<List<TicketResponse>> getAllBusTickets() {
		return ResponseEntity.ok(ticketService.getAllBusTickets());
	}

	@GetMapping(value = "/tickets/airplane")
	public ResponseEntity<List<TicketResponse>> getAllAirplaneTickets() {
		return ResponseEntity.ok(ticketService.getAllAirplaneTickets());
	}

	@GetMapping(value = "/tickets/{no}")
	public ResponseEntity<TicketResponse> getByNo(@PathVariable("no") int no) {
		return ResponseEntity.ok(ticketConverter.convert(ticketService.getByNo(no)));
	}

	@PutMapping("/admin/tickets/cancel/{no}")
	public ResponseEntity<TicketResponse> cancel(@PathVariable int no) {
		return ResponseEntity.ok(ticketService.cancel(no));
	}

	@GetMapping(value = "/admin/tickets/money/{no}")
	public ResponseEntity<Double> totalMoneyFromTicketSales(@PathVariable int no) {
		return ResponseEntity.ok(ticketService.totalMoneyFromTicketSales(no));
	}

	@GetMapping(value = "/admin/tickets/sales/{no}")
	public ResponseEntity<Integer> totalTicketSales(@PathVariable int no) {
		return ResponseEntity.ok(ticketService.totalTicketSales(no));
	}

	@GetMapping("/tickets/search")
	public List<TicketResponse> search(@RequestParam(value = "departureLocation") String departureLocation,
			@RequestParam(value = "arrivalLocation") String arrivalLocation,
			@RequestParam(value = "departureTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureTime,
			@RequestParam(value = "arrivalTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalTime) {
		return ticketService.search(departureLocation, arrivalLocation, departureTime, arrivalTime);
	}

}
