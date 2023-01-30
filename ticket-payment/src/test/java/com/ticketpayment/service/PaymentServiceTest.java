package com.ticketpayment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ticketpayment.client.Sex;
import com.ticketpayment.config.RabbitMQConfiguration;
import com.ticketpayment.model.Payment;
import com.ticketpayment.repository.PaymentRepository;


@ExtendWith(SpringExtension.class)
class PaymentServiceTest {
	
	@InjectMocks
	private PaymentService paymentService;
	@Mock
	private PaymentRepository paymentRepository;
	@Mock
	private RabbitTemplate rabbitTemplate;
	@Mock
	private RabbitMQConfiguration rabbitMQConfiguration;
	
	@Test
	void it_shold_create_card_payment() {
        Payment payment = new Payment();
        when(rabbitMQConfiguration.getQueueName()).thenReturn("queueName");
        when(paymentRepository.save(payment)).thenReturn(payment);
        Payment result = paymentService.createCard(payment);
        verify(rabbitTemplate).convertAndSend("queueName", payment);
        assertEquals(result, payment);
	
	}
	@Test
	void it_shold_create_eft_payment() {
		Payment payment = new Payment();
		when(rabbitMQConfiguration.getQueueName()).thenReturn("queueName");
		when(paymentRepository.save(payment)).thenReturn(payment);
		Payment result = paymentService.createEft(payment);
		verify(rabbitTemplate).convertAndSend("queueName", payment);
		assertEquals(result, payment);
		
	}
	@Test
	void it_should_get_all() {
        List<Payment> payments = new ArrayList<>();
        when(paymentRepository.findAll()).thenReturn(payments);
        List<Payment> result = paymentService.getAll();
        assertEquals(result, payments);
	}

	private Payment getPayment() {
		// TODO Auto-generated method stub
		return new Payment(1, 1, "12345", null, 200, List.of(Sex.MALE, Sex.FEMALE) , List.of("test1", "test2"), 123);
	}

}
