package com.ticket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
import com.ticket.model.enums.TransportationType;
import com.ticket.model.enums.UserType;
import com.ticket.repository.OrderRepository;
import com.ticket.repository.TicketRepository;
import com.ticket.request.OrderRequest;
import com.ticket.response.OrderResponse;
import com.ticket.response.TicketResponse;
import com.ticket.response.UserResponse;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;
	@Mock
	private UserService userService;
	@Mock
	private TicketService ticketService;
	@Mock
	private OrderConverter orderConverter;
	@Mock
	private TicketConverter ticketConverter;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private TicketRepository ticketRepository;
	@Mock
	private RabbitMQNotificationConfiguration rabbitMQNotificationConfiguration;
	@Mock
	private RabbitTemplate rabbitTemplate;

	@Test
	void it_should_throw_user_not_found_exception_when_user_is_null() {

		Throwable exception = catchThrowable(() -> orderService.create(new OrderRequest()));

		assertThat(exception).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	void it_should_throw_global_exception_when_ticket_status_cancelled() {
		Mockito.when(userService.getById(3)).thenReturn(getUser());
		Ticket ticket = getTicket();
		ticket.setStatus(TicketStatus.CANCELLED);
		Mockito.when(ticketService.getByNo(Mockito.anyInt())).thenReturn(ticket);

		Throwable exception = catchThrowable(() -> orderService.create(getOrderRequest()));

		assertThat(exception).isInstanceOf(GlobalException.class);
	}

	@Test
	void it_should_throw_global_exception_when_remaining_seat_is_zero() {
		Mockito.when(userService.getById(3)).thenReturn(getUser());
		Ticket ticket = getTicket();
		ticket.setSeats(0);
		Mockito.when(ticketService.getByNo(Mockito.anyInt())).thenReturn(ticket);

		Throwable exception = catchThrowable(() -> orderService.create(getOrderRequest()));

		assertThat(exception).isInstanceOf(GlobalException.class);

	}

	@Test
	void it_should_throw_global_exception_when_user_is_individual_and_amount_is_bigger_than_individual_max_seat_size() {
		Mockito.when(userService.getById(3)).thenReturn(getUser());
		Mockito.when(ticketService.getByNo(Mockito.anyInt())).thenReturn(getTicket());

		Throwable exception = catchThrowable(() -> orderService.create(getOrderRequest(6)));

		assertThat(exception).isInstanceOf(GlobalException.class);

	}

	@Test
	void it_should_throw_global_exception_when_user_is_individual_and_amount_is_bigger_than_corparete_max_seat_size() {
		Optional<User> user = getUser();
		user.get().setType(UserType.CORPARETE);
		Mockito.when(userService.getById(3)).thenReturn(user);
		Mockito.when(ticketService.getByNo(Mockito.anyInt())).thenReturn(getTicket());

		Throwable exception = catchThrowable(() -> orderService.create(getOrderRequest(21)));

		assertThat(exception).isInstanceOf(GlobalException.class);

	}

	@Test
	void it_should_throw_global_exception_when_individual_user_is_trying_to_buy_more_than_two_male_ticket() {
		Mockito.when(userService.getById(3)).thenReturn(getUser());
		Ticket ticket = getTicket();
		Mockito.when(ticketService.getByNo(Mockito.anyInt())).thenReturn(ticket);

		Throwable exception = catchThrowable(() -> orderService.create(getOrderRequestWithThreeMales()));

		assertThat(exception).isInstanceOf(GlobalException.class);

	}

	@Test
	void it_should_throw_global_exception_when_there_is_no_ticket_available() {
		Mockito.when(userService.getById(3)).thenReturn(getUser());
		Ticket ticket = getTicket();
		ticket.setSeats(1);
		Mockito.when(ticketService.getByNo(Mockito.anyInt())).thenReturn(ticket);

		Throwable exception = catchThrowable(() -> orderService.create(getOrderRequest(2)));

		assertThat(exception).isInstanceOf(GlobalException.class);

	}

	@Test
	void it_should_create_for_individual_users() {
		Mockito.when(userService.getById(Mockito.anyInt())).thenReturn(getUser());
		Mockito.when(ticketService.getByNo(Mockito.anyInt())).thenReturn(getTicket());
		Mockito.when(orderConverter.convert(Mockito.any(OrderRequest.class))).thenReturn(new Order());
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(getOrder().get());
		Mockito.when(orderConverter.convert(Mockito.any(Order.class))).thenReturn(getOrderResponse());
		Mockito.when(rabbitMQNotificationConfiguration.getSMSQueue()).thenReturn("queue");

		OrderResponse orderResponse = orderService.create(getOrderRequest());

		assertThat(orderResponse.getAmount()).isEqualTo(2);
		assertThat(orderResponse.getSexes()).isEqualTo(getOrderRequest().getSexes());
		assertThat(orderResponse.getNameSurnames()).isEqualTo(getOrderRequest().getNameSurnames());

		verify(rabbitTemplate).convertAndSend(Mockito.anyString(), Mockito.any(Ticket.class));

	}

	@Test
	void it_should_create_for_corparete_users() {
		Mockito.when(userService.getById(Mockito.anyInt())).thenReturn(getUserCorparete());
		Mockito.when(ticketService.getByNo(Mockito.anyInt())).thenReturn(getTicket());
		Mockito.when(orderConverter.convert(Mockito.any(OrderRequest.class))).thenReturn(new Order());
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(getOrder().get());
		Mockito.when(orderConverter.convert(Mockito.any(Order.class))).thenReturn(getOrderResponse());
		Mockito.when(rabbitMQNotificationConfiguration.getSMSQueue()).thenReturn("queue");

		OrderResponse orderResponse = orderService.create(getOrderRequest());

		assertThat(orderResponse.getAmount()).isEqualTo(2);
		assertThat(orderResponse.getSexes()).isEqualTo(getOrderRequest().getSexes());
		assertThat(orderResponse.getNameSurnames()).isEqualTo(getOrderRequest().getNameSurnames());

		verify(rabbitTemplate).convertAndSend(Mockito.anyString(), Mockito.any(Ticket.class));

	}

	@Test
	void it_should_get_all_orders() {
		Mockito.when(orderRepository.findAll()).thenReturn(getOrderList());
		Mockito.when(orderConverter.convert(Mockito.anyList())).thenReturn(getOrderResponseList());

		List<OrderResponse> allOrderResponses = orderService.getAll();

		assertThat(allOrderResponses).isNotEmpty();
		assertEquals(2, allOrderResponses.size());
		assertEquals(2, allOrderResponses.get(0).getAmount());
	}

	@Test
	void it_should_throw_global_exception_when_ticket_is_not_found_by_no() {
		Mockito.when(orderRepository.findById(Mockito.anyInt())).thenReturn(getOrder());

		Throwable exception = catchThrowable(() -> orderService.getOrderDetail(1, 1));

		assertThat(exception).isInstanceOf(GlobalException.class);
	}

	@Test
	void it_should_throw_global_exception_when_orders_ticket_is_not_found_by_no() {
		Ticket ticket = new Ticket();
		Order order = new Order();
		List<Ticket> ticketList = mock(List.class);
		order.setTicketList(ticketList);
		Mockito.when(orderRepository.findByUserId(Mockito.anyInt())).thenReturn(order);
		Mockito.when(ticketRepository.findByNo(Mockito.anyInt())).thenReturn(Optional.of(ticket));
		Mockito.when(ticketList.contains(ticket)).thenReturn(false);
		assertThrows(GlobalException.class, () -> orderService.getOrderDetail(1, 1));
		verify(orderRepository, Mockito.times(1)).findByUserId(Mockito.anyInt());
		verify(ticketRepository, Mockito.times(1)).findByNo(Mockito.anyInt());
		verify(ticketList, Mockito.times(1)).contains(ticket);
	}

	@Test
	void it_should_get_order_detail() {
		Ticket ticket = new Ticket();
		Order order = new Order();
		List<Ticket> ticketList = mock(List.class);
		order.setTicketList(ticketList);
		OrderResponse expected = new OrderResponse();
		Mockito.when(orderRepository.findByUserId(Mockito.anyInt())).thenReturn(order);
		Mockito.when(ticketRepository.findByNo(Mockito.anyInt())).thenReturn(Optional.of(ticket));
		Mockito.when(ticketList.contains(ticket)).thenReturn(true);
		Mockito.when(orderConverter.convert(order)).thenReturn(expected);
		OrderResponse result = orderService.getOrderDetail(1, 1);
		assertThat(result).isEqualTo(expected);
		verify(orderRepository, Mockito.times(1)).findByUserId(Mockito.anyInt());
		verify(ticketRepository, Mockito.times(1)).findByNo(Mockito.anyInt());
		verify(ticketList, Mockito.times(1)).contains(ticket);
		verify(orderConverter, Mockito.times(1)).convert(order);
	}

	@Test

	void it_should_get_trips() {

		Ticket ticket1 = getTicket();
		ticket1.setArrivalTime(LocalDate.now().minusDays(1));
		TicketResponse ticketResponse1 = new TicketResponse();
		Ticket ticket2 = getTicket();
		ticket2.setArrivalTime(LocalDate.now().plusDays(2));
		Ticket ticket3 = getTicket();
		ticket3.setArrivalTime(LocalDate.now().minusDays(2));
		TicketResponse ticketResponse3 = new TicketResponse();
		Order order = new Order();
		order.setTicketList(Arrays.asList(ticket1, ticket2, ticket3));
		List<TicketResponse> expected = Arrays.asList(ticketResponse1, ticketResponse3);
		Mockito.when(orderRepository.findByUserId(Mockito.anyInt())).thenReturn(order);
		Mockito.when(ticketConverter.convert(Arrays.asList(ticket1, ticket3))).thenReturn(expected);

		List<TicketResponse> result = orderService.getTrips(1);

		assertThat(result).isEqualTo(expected);
		verify(orderRepository, times(2)).findByUserId(Mockito.anyInt());
		verify(ticketConverter, times(1)).convert(Arrays.asList(ticket1, ticket3));

	}
	@Test
	void it_should_throw_global_exception_when_there_is_no_order() {
	    Order order = null;
	    Mockito.when(orderRepository.findByUserId(Mockito.anyInt())).thenReturn(order);

	    assertThrows(GlobalException.class, () -> orderService.getTrips(1));

	    verify(orderRepository, times(1)).findByUserId(Mockito.anyInt());
	}
	@Test
	void it_should_throw_global_exception_when_there_is_no_ticket() {
	    Order order = new Order();
	    order.setTicketList(Collections.emptyList());
	    Mockito.when(orderRepository.findByUserId(Mockito.anyInt())).thenReturn(order);

	    assertThrows(GlobalException.class, () -> orderService.getTrips(1));

	    verify(orderRepository, times(1)).findByUserId(Mockito.anyInt());
	}

	private List<OrderResponse> getOrderResponseList() {
		// TODO Auto-generated method stub
		return List.of(getOrderResponse(), getOrderResponse());
	}

	private List<Order> getOrderList() {
		// TODO Auto-generated method stub
		return List.of(getOrder().get(), getOrder().get());
	}

	private OrderResponse getOrderResponse() {
		// TODO Auto-generated method stub
		return new OrderResponse(getTicketResponse(), getUserResponse(), 2, List.of(Sex.MALE, Sex.FEMALE),
				List.of("test1", "test2"), 0);
	}

	private UserResponse getUserResponse() {
		return new UserResponse("test", "test@gmail.com");
	}

	private TicketResponse getTicketResponse() {
		// TODO Auto-generated method stub
		return new TicketResponse(123, LocalDate.of(2020, 03, 12), LocalDate.of(2020, 03, 12), "Ankara", "Denizli",
				TransportationType.AIRPLANE, 2000.0, TicketStatus.BOOKED);
	}

	private Optional<Order> getOrder() {
		// TODO Auto-generated method stub
		return Optional.of(new Order(1, List.of(getTicket()), getUser().get(), 2, 0, List.of(Sex.MALE, Sex.FEMALE),
				List.of("test1", "test2")));
	}

	private Ticket getTicket() {
		// TODO Auto-generated method stub
		return new Ticket(123, LocalDate.of(2020, 03, 12), LocalDate.of(2020, 03, 12), "Ankara", "Denizli",
				TransportationType.AIRPLANE, TicketStatus.BOOKED, 189, 2000.0);
	}

	private OrderRequest getOrderRequest() {
		// TODO Auto-generated method stub
		return new OrderRequest(123, 3, 2, List.of(Sex.MALE, Sex.FEMALE), List.of("test1", "test2"));
	}

	private OrderRequest getOrderRequest(Integer amount) {
		// TODO Auto-generated method stub
		return new OrderRequest(123, 3, amount, List.of(Sex.MALE, Sex.MALE), List.of("test1", "test2"));
	}

	private OrderRequest getOrderRequestWithThreeMales() {
		// TODO Auto-generated method stub
		return new OrderRequest(123, 3, 3, List.of(Sex.MALE, Sex.MALE, Sex.MALE), List.of("test1", "test2", "test3"));
	}

	private Optional<User> getUser() {
		// TODO Auto-generated method stub
		return Optional
				.of(new User(Sex.FEMALE, "test", "test1", "test123", "test@gmail.com", UserType.INDIVIDUAL, "123456"));
	}

	private Optional<User> getUserCorparete() {
		// TODO Auto-generated method stub
		return Optional
				.of(new User(Sex.FEMALE, "test", "test1", "test123", "test@gmail.com", UserType.CORPARETE, "123456"));
	}
}
