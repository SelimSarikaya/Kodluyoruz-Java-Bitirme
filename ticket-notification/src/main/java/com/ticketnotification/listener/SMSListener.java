package com.ticketnotification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ticketnotification.model.Message;
import com.ticketnotification.model.Ticket;
import com.ticketnotification.model.User;
import com.ticketnotification.repository.MessageRepository;
import com.ticketnotification.service.MessageService;

@Component
public class SMSListener implements NotificationListener {
	@Autowired
	MessageService messageService;
	@Autowired
	MessageRepository messageRepository;

	@Override
	@RabbitListener(queues = "ticket.notification.sms")
	public void sendNotification(User user) {
		// TODO Auto-generated method stub
	}

	@Override
	@RabbitListener(queues = "ticket.notification.sms")
	public void sendNotification(Ticket ticket) {
		Message message = messageService.save("Bilet bilgileriniz :\n" + "Baslangic noktasi: "
				+ ticket.getDepartureLocation() + " Baslangic tarihi:  " + ticket.getDepartureTime() + "\nVaris yeri: "
				+ ticket.getArrivalLocation() + " Varis tarihi " + ticket.getArrivalTime());
		messageRepository.save(message);
		System.out.println(message.getMessage());
	}

}
