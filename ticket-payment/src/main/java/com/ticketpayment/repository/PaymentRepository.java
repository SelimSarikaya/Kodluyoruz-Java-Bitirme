package com.ticketpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketpayment.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
