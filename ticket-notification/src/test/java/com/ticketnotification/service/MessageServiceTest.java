package com.ticketnotification.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ticketnotification.model.Message;
import com.ticketnotification.repository.MessageRepository;

@ExtendWith(SpringExtension.class)
class MessageServiceTest {

	@InjectMocks
	private MessageService messageService;
	@Mock
	private MessageRepository messageRepository;

	@Test
	void it_should_save_message() {

		Mockito.when(messageRepository.save(Mockito.any(Message.class))).thenReturn(getMessage());

		Message result = messageService.save("message");

		assertThat(result.getMessage()).isEqualTo("message");
	}

	private Message getMessage() {
		// TODO Auto-generated method stub
		return new Message(1, "message");
	}
}
