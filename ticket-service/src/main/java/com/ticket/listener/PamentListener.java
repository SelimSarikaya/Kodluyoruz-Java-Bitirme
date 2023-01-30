package com.ticket.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ticket.controller.OrderController;
import com.ticket.exception.GlobalException;
import com.ticket.exception.UserNotFoundException;
import com.ticket.repository.OrderRepository;
import com.ticket.request.OrderRequest;
import com.ticket.service.OrderService;
import com.ticket.service.TicketService;
import com.ticket.service.UserService;

@Component
public class PamentListener {

	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	@Autowired
	TicketService ticketService;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderController orderController;
	@Autowired
	RabbitTemplate rabbitTemplate;

	@RabbitListener(queues = "ticket.payment")
	public void paymentListener(Payment payment) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setAmount(payment.getAmount());
		orderRequest.setNameSurnames(payment.getNameSurnames());
		orderRequest.setSexes(payment.getSexes());
		orderRequest.setUserId(payment.getUserId());
		orderRequest.setTicketNo(payment.getTicketNo());
		try {
			orderService.create(orderRequest);
		} catch (UserNotFoundException e) {
			System.out.println(e);
		} catch (GlobalException s) {
			System.out.println(s);
		}

	}

}
