package com.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.request.OrderRequest;
import com.ticket.response.OrderResponse;
import com.ticket.response.TicketResponse;
import com.ticket.service.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping(value = "/admin")
	public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest orderRequest) {
		OrderResponse orderResponse = orderService.create(orderRequest);
		return ResponseEntity.ok(orderResponse);
	}

	@GetMapping
	public ResponseEntity<List<OrderResponse>> getAll() {
		return ResponseEntity.ok(orderService.getAll());
	}

	@GetMapping(value = "/{id}/ticketno/{no}")
	public ResponseEntity<OrderResponse> getOrderDetail(@PathVariable int id, @PathVariable int no) {
		return ResponseEntity.ok(orderService.getOrderDetail(id, no));
	}

	@GetMapping(value = "/trips/{id}")
	public ResponseEntity<List<TicketResponse>> getTrips(@PathVariable int id) {
		return ResponseEntity.ok(orderService.getTrips(id));
	}

}
