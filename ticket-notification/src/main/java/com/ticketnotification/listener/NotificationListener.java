package com.ticketnotification.listener;

import org.springframework.stereotype.Component;

import com.ticketnotification.model.Ticket;
import com.ticketnotification.model.User;

@Component
public interface NotificationListener {

	void sendNotification(User user);

	void sendNotification(Ticket ticket);
}
