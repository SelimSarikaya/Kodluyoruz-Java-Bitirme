package com.ticketpayment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketpayment.model.Payment;
import com.ticketpayment.service.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;

	@PostMapping(value = "/card")
	public ResponseEntity<Payment> createCard(@RequestBody Payment payment) {
		return ResponseEntity.ok(paymentService.createCard(payment));
	}

	@PostMapping(value = "/eft")
	public ResponseEntity<Payment> createEft(@RequestBody Payment payment) {
		return ResponseEntity.ok(paymentService.createEft(payment));
	}

	@GetMapping
	public ResponseEntity<List<Payment>> getAll() {
		return ResponseEntity.ok(paymentService.getAll());
	}
}
