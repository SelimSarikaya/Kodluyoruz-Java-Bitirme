package com.ticket.service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.configuration.RabbitMQNotificationConfiguration;
import com.ticket.converter.OrderConverter;
import com.ticket.converter.TicketConverter;
import com.ticket.exception.GlobalException;
import com.ticket.exception.UserNotFoundException;
import com.ticket.model.Order;
import com.ticket.model.Ticket;
import com.ticket.model.User;
import com.ticket.model.enums.Sex;
import com.ticket.model.enums.TicketStatus;
import com.ticket.model.enums.UserType;
import com.ticket.repository.OrderRepository;
import com.ticket.repository.TicketRepository;
import com.ticket.request.OrderRequest;
import com.ticket.response.OrderResponse;
import com.ticket.response.TicketResponse;

@Service
public class OrderService {

	private static final int CORPARETE_MAX_SEAT_SIZE = 20;
	private static final int MAXIMUM_MALE_SIZE = 2;
	private static final int INDIVIDUAL_MAX_SEAT_SIZE = 5;
	private static final int MINIMUM_SEAT_SIZE = 0;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	UserService userService;
	@Autowired
	TicketService ticketService;
	@Autowired
	OrderConverter orderConverter;
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	RabbitMQNotificationConfiguration rabbitMQNotificationConfiguration;
	@Autowired
	TicketConverter ticketConverter;

	Logger logger = Logger.getLogger(OrderService.class.getName());

	public OrderResponse create(OrderRequest orderRequest) {

		User foundUser = userService.getById(orderRequest.getUserId())
				.orElseThrow(() -> new UserNotFoundException("user bulunamadı"));
		Ticket foundTicket = ticketService.getByNo(orderRequest.getTicketNo());

		if (foundTicket.getStatus().equals(TicketStatus.CANCELLED)) {
			throw new GlobalException("Bilet iptal olmuştur lütfen başka bir bilet almayı deneyin");

		} else {
			if (foundTicket.getSeats() > MINIMUM_SEAT_SIZE) {
				if (UserType.INDIVIDUAL.equals(foundUser.getType())
						&& orderRequest.getAmount() <= INDIVIDUAL_MAX_SEAT_SIZE) {
					if (orderRequest.getSexes().stream().filter(sex -> sex.equals(Sex.MALE))
							.count() <= MAXIMUM_MALE_SIZE) {
						return seatControlAndProcessOrder(orderRequest, foundUser, foundTicket);
					} else {

						logger.log(Level.WARNING,
								"Individual users can only get maximum 2 ticket for Male passengers!");
						throw new GlobalException(
								"Individual users can only get maximum 2 ticket for Male passengers!");
					}

				} else if (UserType.INDIVIDUAL.equals(foundUser.getType())
						&& orderRequest.getAmount() >= INDIVIDUAL_MAX_SEAT_SIZE) {

					logger.log(Level.WARNING, "Individual accounts can not get more than 5 tickets.");
					throw new GlobalException("Individual accounts can not get more than 5 tickets.");

				} else if (UserType.CORPARETE.equals(foundUser.getType())
						&& orderRequest.getAmount() <= CORPARETE_MAX_SEAT_SIZE) {
					return seatControlAndProcessOrder(orderRequest, foundUser, foundTicket);

				} else {
					logger.log(Level.WARNING, "Corparete accounts can not get more than 20 tickets.");
					throw new GlobalException("Corparete accounts can not get more than 20 tickets.");
				}

			} else {
				logger.log(Level.WARNING, "No available tickets!");
				throw new GlobalException("No available tickets!");
			}
		}

	}

	private OrderResponse seatControlAndProcessOrder(OrderRequest orderRequest, User foundUser, Ticket foundTicket) {
		if (foundTicket.getSeats() - orderRequest.getAmount() < 0) {
			logger.log(Level.WARNING, "Almak istediğiniz kadar bilet kalmadı. Kalan bilet: {0}",
					foundTicket.getSeats());
			throw new GlobalException("Almak istediğiniz kadar bilet kalmadı. Kalan bilet: " + foundTicket.getSeats());
		} else {
			Order order = orderConverter.convert(orderRequest);
			order.setCost(orderRequest.getAmount() * foundTicket.getTicketCost());
			order.setUser(foundUser);
			order.setTicketList(List.of(foundTicket));
			orderRepository.save(order);
			foundTicket.setSeats(foundTicket.getSeats() - orderRequest.getAmount());
			ticketRepository.flush();
			ticketRepository.save(foundTicket);
			rabbitTemplate.convertAndSend(rabbitMQNotificationConfiguration.getSMSQueue(), foundTicket);
			logger.log(Level.INFO, "Order oluşturuldu.");
			return orderConverter.convert(order);

		}
	}

	public List<OrderResponse> getAll() {
		return orderConverter.convert(orderRepository.findAll());
	}

	public OrderResponse getOrderDetail(int id, int no) {
		Order order = orderRepository.findByUserId(id);
		Ticket ticket = ticketRepository.findByNo(no).orElseThrow(() -> new GlobalException("Ticket bulunamadı"));
		if (order.getTicketList().contains(ticket)) {
			return orderConverter.convert(order);
		} else {
			throw new GlobalException("Ticket bulunamadı");
		}

	}

	public List<TicketResponse> getTrips(int id) {
		Order order = orderRepository.findByUserId(id);

		if (order == null || order.getTicketList().isEmpty()) {
			throw new GlobalException("Tamamlanmış seyahatiniz bulunmamaktadır.");
		} else {
			List<Ticket> ticket = orderRepository.findByUserId(id).getTicketList().stream()
					.filter(t -> t.getArrivalTime().isBefore(LocalDate.now())).toList();
			return ticketConverter.convert(ticket);
		}

	}
}
