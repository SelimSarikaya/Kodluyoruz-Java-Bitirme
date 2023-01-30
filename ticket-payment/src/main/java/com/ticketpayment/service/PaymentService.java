package com.ticketpayment.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketpayment.config.RabbitMQConfiguration;
import com.ticketpayment.model.Payment;
import com.ticketpayment.repository.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private RabbitMQConfiguration rabbitMQConfiguration;

	@Autowired
	PaymentRepository paymentRepository;

	public Payment createCard(Payment payment) {

		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getQueueName(), payment);

		return paymentRepository.save(payment);

	}

	public Payment createEft(Payment payment) {

		rabbitTemplate.convertAndSend(rabbitMQConfiguration.getQueueName(), payment);

		return paymentRepository.save(payment);

	}

	public List<Payment> getAll() {
		return paymentRepository.findAll();
	}

}
