package com.ticketnotification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketnotification.model.Message;
import com.ticketnotification.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;

	public Message save(String message) {
		Message newMessage = new Message();
		newMessage.setMessage(message);
		return messageRepository.save(newMessage);
	}

}
