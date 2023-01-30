package com.ticketnotification.listener;

import com.ticketnotification.model.User;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ticketnotification.model.Ticket;

@Component
public class PushNotificationListener implements NotificationListener {

	@Override
	@RabbitListener(queues = "ticket.notification.push")
	public void sendNotification(User user) {
		// TODO Auto-generated method stub
	}

	@Override
	@RabbitListener(queues = "ticket.notification.push")
	public void sendNotification(Ticket ticket) {
		// TODO Auto-generated method stub
	}

}
