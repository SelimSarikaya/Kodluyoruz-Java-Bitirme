package com.ticket.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ticket.model.Order;
import com.ticket.request.OrderRequest;
import com.ticket.response.OrderResponse;

@Component
public class OrderConverter {
	@Autowired
	TicketConverter ticketConverter;
	@Autowired
	UserConverter userConverter;

	public Order convert(OrderRequest orderRequest) {
		Order order = new Order();
		order.setNameSurnames(orderRequest.getNameSurnames());
		order.setSexes(orderRequest.getSexes());
		order.setAmount(orderRequest.getAmount());
		return order;
	}

	public OrderResponse convert(Order order) {
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setTicketResponse(ticketConverter.convert(order.getTicketList().stream().findAny().get()));
		orderResponse.setUserResponse(userConverter.convert(order.getUser()));
		orderResponse.setAmount(order.getAmount());
		orderResponse.setNameSurnames(order.getNameSurnames());
		orderResponse.setSexes(order.getSexes());
		orderResponse.setCost(order.getCost());
		return orderResponse;
	}

	public List<OrderResponse> convert(List<Order> orderList) {
		List<OrderResponse> orderResponses = new ArrayList<>();
		for (Order order : orderList) {
			orderResponses.add(convert(order));
		}
		return orderList.stream().map(this::convert).toList();
	}
}
