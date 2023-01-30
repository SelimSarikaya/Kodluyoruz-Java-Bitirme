package com.ticketnotification.listener;

import com.ticketnotification.model.User;
import com.ticketnotification.repository.MessageRepository;
import com.ticketnotification.service.MessageService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ticketnotification.model.Message;
import com.ticketnotification.model.Ticket;

@Component
public class EmailListener implements NotificationListener {
	
	@Autowired
	MessageService messageService;
	@Autowired
	MessageRepository messageRepository;

	@Override
	@RabbitListener(queues = "ticket.notification.email")
	public void sendNotification(User user) {
		Message message = messageService.save("user created: " + user.getName() + " " + user.getEmail());
		messageRepository.save(message);
		System.out.println(message.getMessage());

	}

	@Override
	@RabbitListener(queues = "ticket.notification.email")
	public void sendNotification(Ticket ticket) {
		// TODO Auto-generated method stub
	}



}
