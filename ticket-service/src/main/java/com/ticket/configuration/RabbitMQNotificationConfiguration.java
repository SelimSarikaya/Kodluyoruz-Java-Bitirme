package com.ticket.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitMQNotificationConfiguration {

	private String SMSQueue = "ticket.notification.sms";
	private String SMSexchange = "ticket.notification.sms";

	private String emailQueue = "ticket.notification.email";
	private String emailExchange = "ticket.notification.email";

	private String pushQueue = "ticket.notification.push";
	private String pushExchange = "ticket.notification.push";

	@Bean("SMSQueue")
	public Queue SMSQueue() {
		return new Queue(SMSQueue, false);
	}

	@Bean("SMSexchange")
	public DirectExchange SMSexchange() {
		return new DirectExchange(SMSexchange);
	}

	@Bean("emailQueue")
	@Primary
	public Queue emailQueue() {
		return new Queue(emailQueue, false);
	}

	@Bean("emailExchange")
	@Primary
	public DirectExchange emailExchange() {
		return new DirectExchange(emailExchange);
	}

	@Bean("pushQueue")
	public Queue pushQueue() {
		return new Queue(pushQueue, false);
	}

	@Bean("pushExchange")
	public DirectExchange pushExchange() {
		return new DirectExchange(pushExchange);
	}

	@Bean
	public Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("");
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public String getEmailQueue() {
		return emailQueue;
	}

	public void setEmailQueue(String emailQueue) {
		this.emailQueue = emailQueue;
	}

	public String getEmailExchange() {
		return emailExchange;
	}

	public void setEmailExchange(String emailExchange) {
		this.emailExchange = emailExchange;
	}

	public String getSMSQueue() {
		return SMSQueue;
	}

	public void setSMSQueue(String sMSQueue) {
		SMSQueue = sMSQueue;
	}

	public String getSMSexchange() {
		return SMSexchange;
	}

	public void setSMSexchange(String sMSexchange) {
		SMSexchange = sMSexchange;
	}

	public String getPushQueue() {
		return pushQueue;
	}

	public void setPushQueue(String pushQueue) {
		this.pushQueue = pushQueue;
	}

	public String getPushExchange() {
		return pushExchange;
	}

	public void setPushExchange(String pushExchange) {
		this.pushExchange = pushExchange;
	}

}
