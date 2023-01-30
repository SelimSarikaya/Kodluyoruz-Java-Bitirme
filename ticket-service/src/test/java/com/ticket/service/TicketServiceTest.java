package com.ticket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ticket.converter.TicketConverter;
import com.ticket.exception.GlobalException;
import com.ticket.model.Ticket;
import com.ticket.model.enums.TicketStatus;
import com.ticket.model.enums.TransportationType;
import com.ticket.repository.TicketRepository;
import com.ticket.request.TicketRequest;
import com.ticket.response.TicketResponse;

@ExtendWith(SpringExtension.class)
class TicketServiceTest {

	private static final Integer AIRPLANE_SEAT_SIZE = 189;
	private static final Integer BUS_SEAT_SIZE = 45;
	@InjectMocks
	private TicketService ticketService;
	@Mock
	private TicketRepository ticketRepository;
	@Mock
	private TicketConverter ticketConverter;

	@Test
	void it_should_throw_global_exception_if_ticket_no_exist() {
		Mockito.when(ticketRepository.findByNo(getTicket().get().getNo())).thenReturn(getTicket());

		Throwable exception = catchThrowable(() -> ticketService.create(getTicketRequest()));

		assertThat(exception).isInstanceOf(GlobalException.class);
	}

	@Test
	void it_should_create_airplane_ticket_when_ticket_requests_transportation_type_is_airplane() {

		TicketRequest ticketRequest = getTicketRequest();
		ticketRequest.setTransportationType(TransportationType.AIRPLANE);
		Ticket ticket = getTicket().get();
		TicketResponse expected = getTicketResponse();
		Mockito.when(ticketConverter.convert(ticketRequest)).thenReturn(ticket);
		Mockito.when(ticketRepository.save(ticket)).thenReturn(ticket);
		Mockito.when(ticketConverter.convert(ticket)).thenReturn(expected);

		TicketResponse ticketResponse = ticketService.create(ticketRequest);

		assertThat(ticketResponse).isEqualTo(expected);
		assertThat(ticketResponse.getArrivalLocation()).isEqualTo(getTicket().get().getArrivalLocation());
		assertThat(ticketResponse.getDepartureLocation()).isEqualTo(getTicket().get().getDepartureLocation());
		assertThat(ticketResponse.getArrivalTime()).isEqualTo(getTicket().get().getArrivalTime());
		assertThat(ticketResponse.getDepartureTime()).isEqualTo(getTicket().get().getDepartureTime());
		assertThat(ticketResponse.getNo()).isEqualTo(getTicket().get().getNo());
		assertEquals(ticketResponse.getTransportationType(), getTicket().get().getTransportationType());
		assertEquals(ticket.getSeats(), AIRPLANE_SEAT_SIZE);
		verify(ticketRepository, times(1)).save(ticket);
		verify(ticketConverter, times(1)).convert(ticket);
		verify(ticketConverter, times(1)).convert(ticketRequest);

	}

	@Test
	void it_should_create_bus_ticket_when_ticket_requests_transportation_type_is_abus() {

		TicketRequest ticketRequest = getTicketRequest();
		ticketRequest.setTransportationType(TransportationType.BUS);
		Ticket ticket = getTicket().get();
		TicketResponse expected = getTicketResponse();
		expected.setTransportationType(TransportationType.BUS);
		Mockito.when(ticketConverter.convert(ticketRequest)).thenReturn(ticket);
		Mockito.when(ticketRepository.save(ticket)).thenReturn(ticket);
		Mockito.when(ticketConverter.convert(ticket)).thenReturn(expected);

		TicketResponse ticketResponse = ticketService.create(ticketRequest);

		assertThat(ticketResponse).isEqualTo(expected);
		assertThat(ticketResponse.getArrivalLocation()).isEqualTo(getTicket().get().getArrivalLocation());
		assertThat(ticketResponse.getDepartureLocation()).isEqualTo(getTicket().get().getDepartureLocation());
		assertThat(ticketResponse.getArrivalTime()).isEqualTo(getTicket().get().getArrivalTime());
		assertThat(ticketResponse.getDepartureTime()).isEqualTo(getTicket().get().getDepartureTime());
		assertThat(ticketResponse.getNo()).isEqualTo(getTicket().get().getNo());
		assertEquals(ticketResponse.getTransportationType(), TransportationType.BUS);
		assertEquals(ticket.getSeats(), BUS_SEAT_SIZE);
		verify(ticketRepository, times(1)).save(ticket);
		verify(ticketConverter, times(1)).convert(ticket);
		verify(ticketConverter, times(1)).convert(ticketRequest);

	}

	@Test
	void it_should_get_all_tickets() {
		Mockito.when(ticketRepository.findAll()).thenReturn(getTicketList());
		Mockito.when(ticketConverter.convert(Mockito.anyList())).thenReturn(getTicketResponseList());

		List<TicketResponse> list = ticketService.getAll();

		assertThat(list).isNotEmpty();
		assertEquals(2, list.size());
	}

	@Test
	void it_should_get_all_tickets_by_departure_location() {
		List<Ticket> ticketList = getTicketList();
		Mockito.when(ticketRepository.findAllByDepartureLocation(Mockito.anyString())).thenReturn(ticketList);
		Mockito.when(ticketConverter.convert(ticketList)).thenReturn(getTicketResponseList());

		List<TicketResponse> list = ticketService.getAllByDepartureLocation("Ankara");

		assertThat(list).hasSize(2);
		verify(ticketRepository, times(1)).findAllByDepartureLocation("Ankara");
		verify(ticketConverter, times(1)).convert(ticketList);
	}

	@Test
	void it_should_get_all_tickets_by_arrival_location() {
		List<Ticket> ticketList = getTicketList();
		Mockito.when(ticketRepository.findAllByArrivalLocation(Mockito.anyString())).thenReturn(ticketList);
		Mockito.when(ticketConverter.convert(ticketList)).thenReturn(getTicketResponseList());

		List<TicketResponse> list = ticketService.getAllByArrivalLocation("Denizli");

		assertThat(list).hasSize(2);
		verify(ticketRepository, times(1)).findAllByArrivalLocation("Denizli");
		verify(ticketConverter, times(1)).convert(ticketList);
	}

	@Test
	void it_should_get_all_tickets_by_departure_time() {
		List<Ticket> ticketList = getTicketList();
		Mockito.when(ticketRepository.findAllByDepartureTime(LocalDate.of(2020, 03, 12))).thenReturn(ticketList);
		Mockito.when(ticketConverter.convert(ticketList)).thenReturn(getTicketResponseList());

		List<TicketResponse> list = ticketService.getAllByDepartureTime(LocalDate.of(2020, 03, 12));

		assertThat(list).hasSize(2);
		assertThat(list.get(0).getDepartureTime()).isEqualTo(LocalDate.of(2020, 03, 12));
		verify(ticketRepository, times(1)).findAllByDepartureTime(LocalDate.of(2020, 03, 12));
		verify(ticketConverter, times(1)).convert(ticketList);
	}

	@Test
	void it_should_get_all_tickets_by_arrival_time() {
		List<Ticket> ticketList = getTicketList();
		Mockito.when(ticketRepository.findAllByArrivalTime(LocalDate.of(2020, 03, 12))).thenReturn(ticketList);
		Mockito.when(ticketConverter.convert(ticketList)).thenReturn(getTicketResponseList());

		List<TicketResponse> list = ticketService.getAllByArrivalTime(LocalDate.of(2020, 03, 12));

		assertThat(list).hasSize(2);
		assertThat(list.get(0).getDepartureTime()).isEqualTo(LocalDate.of(2020, 03, 12));
		verify(ticketRepository, times(1)).findAllByArrivalTime(LocalDate.of(2020, 03, 12));
		verify(ticketConverter, times(1)).convert(ticketList);
	}

	@Test
	void it_should_get_all_airplane_tickets() {
		List<Ticket> ticketList = getTicketList();
		Mockito.when(ticketRepository.findAllByTransportationType(TransportationType.AIRPLANE)).thenReturn(ticketList);
		Mockito.when(ticketConverter.convert(ticketList)).thenReturn(getTicketResponseList());

		List<TicketResponse> list = ticketService.getAllAirplaneTickets();

		assertThat(list).isNotEmpty();
		assertThat(list.get(0).getTransportationType()).isEqualTo(TransportationType.AIRPLANE);
		verify(ticketRepository, times(1)).findAllByTransportationType(TransportationType.AIRPLANE);
		verify(ticketConverter, times(1)).convert(ticketList);

	}

	@Test
	void it_should_get_all_bus_tickets() {
		List<Ticket> ticketList = getTicketList();
		Mockito.when(ticketRepository.findAllByTransportationType(TransportationType.BUS)).thenReturn(ticketList);
		List<TicketResponse> ticketResponseList = getTicketResponseList();
		ticketResponseList.get(0).setTransportationType(TransportationType.BUS);
		ticketResponseList.get(1).setTransportationType(TransportationType.BUS);
		Mockito.when(ticketConverter.convert(ticketList)).thenReturn(ticketResponseList);

		List<TicketResponse> list = ticketService.getAllBusTickets();

		assertThat(list).isNotEmpty();
		assertThat(list.get(0).getTransportationType()).isEqualTo(TransportationType.BUS);
		verify(ticketRepository, times(1)).findAllByTransportationType(TransportationType.BUS);
		verify(ticketConverter, times(1)).convert(ticketList);

	}

	@Test
	void it_should_throw_global_exception_when_ticket_is_not_found_to_get_by_no() {
		Mockito.when(ticketRepository.findByNo(123)).thenReturn(Optional.empty());

		Throwable exception = catchThrowable(() -> ticketService.getByNo(123));


		assertThat(exception).isInstanceOf(GlobalException.class);
		verify(ticketRepository, times(1)).findByNo(123);
	}

	@Test
	void it_should_get_ticket_by_no() {

		List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
		List<TicketResponse> expectedTicketResponses = ticketConverter.convert(tickets);

		// Mock repository to return test data
		Mockito.when(ticketRepository.findAll()).thenReturn(tickets);

		// Call the method and check the response
		List<TicketResponse> actualTicketResponses = ticketService.getAll();
		assertEquals(expectedTicketResponses, actualTicketResponses);
	}

	@Test
	void it_should_throw_global_exception_when_ticket_is_not_found_to_get_by_id() {
		Mockito.when(ticketRepository.findById(1)).thenReturn(Optional.empty());

		Throwable exception = catchThrowable(() -> ticketService.getById(1));
		

		assertThat(exception).isInstanceOf(GlobalException.class);

	}

	@Test
	void it_should_get_ticket_by_id() {
		int id = 1;
		Ticket ticket = new Ticket();
		TicketResponse expectedTicketResponse = new TicketResponse();
		Optional<Ticket> optionalTicket = Optional.of(ticket);
		Mockito.when(ticketRepository.findById(id)).thenReturn(optionalTicket);
		Mockito.when(ticketConverter.convert(ticket)).thenReturn(expectedTicketResponse);

		TicketResponse ticketResponse = ticketService.getById(id);

		assertThat(ticketResponse).isEqualTo(expectedTicketResponse);
		verify(ticketRepository, times(1)).findById(id);
		verify(ticketConverter, times(1)).convert(ticket);
	}

	@Test
	void it_should_throw_global_exception_when_ticket_not_found_to_cancel() {
		Mockito.when(ticketRepository.findByNo(1)).thenReturn(Optional.empty());

		Throwable exception = catchThrowable(() -> ticketService.cancel(1));

		assertThat(exception).isInstanceOf(GlobalException.class).hasMessage("Ticket bulunamadı");
		verify(ticketRepository, times(1)).findByNo(1);
	}

	@Test
	void it_should_cancel_ticket_when_ticket_found() {
		Ticket ticket = new Ticket();
		ticket.setStatus(TicketStatus.CANCELLED);
		Mockito.when(ticketRepository.findByNo(anyInt())).thenReturn(Optional.of(ticket));
		TicketResponse ticketResponse = getTicketResponse();
		ticketResponse.setTicketStatus(TicketStatus.CANCELLED);
		Mockito.when(ticketConverter.convert(ticket)).thenReturn(getTicketResponse());

		TicketResponse response = ticketService.cancel(1);


		assertEquals(TicketStatus.CANCELLED, ticket.getStatus());

	}

	@Test
	void it_should_throw_global_exception_when_ticket_is_not_found_to_total_money_from_ticket_sales() {
		Mockito.when(ticketRepository.findByNo(1)).thenReturn(Optional.empty());

		Throwable exception = catchThrowable(() -> ticketService.totalMoneyFromTicketSales(1));

		assertThat(exception).isInstanceOf(GlobalException.class).hasMessage("Ticket bulunamadı");
		verify(ticketRepository, times(1)).findByNo(1);
	}

	@Test
	void it_should_calculate_total_money_from_ticket_sales_when_transportation_type_is_airplane() {
		Ticket ticket = new Ticket();
		ticket.setTransportationType(TransportationType.AIRPLANE);
		ticket.setSeats(10);
		ticket.setTicketCost(2000.0);
		Mockito.when(ticketRepository.findByNo(1)).thenReturn(Optional.of(ticket));

		Double totalMoney = ticketService.totalMoneyFromTicketSales(1);

		assertThat(totalMoney).isEqualTo(179 * 2000.0);
		verify(ticketRepository, times(1)).findByNo(1);
	}

	@Test
	void it_should_calculate_total_money_from_ticket_sales_when_transportation_type_is_bus() {
		Ticket ticket = new Ticket();
		ticket.setTransportationType(TransportationType.BUS);
		ticket.setSeats(5);
		ticket.setTicketCost(500.0);
		Mockito.when(ticketRepository.findByNo(1)).thenReturn(Optional.of(ticket));

		Double totalMoney = ticketService.totalMoneyFromTicketSales(1);

		assertThat(totalMoney).isEqualTo(40 * 500.0);
		verify(ticketRepository, times(1)).findByNo(1);
	}

	@Test
	void it_should_throw_exception_when_ticket_not_found_to_get_total_ticket_sales() {
		Mockito.when(ticketRepository.findByNo(anyInt())).thenReturn(Optional.empty());

		Throwable exception = catchThrowable(() -> ticketService.totalTicketSales(123));

		assertThat(exception).isInstanceOf(GlobalException.class).hasMessage("Ticket bulunamadı");
		verify(ticketRepository, times(1)).findByNo(123);
	}

	@Test
	void it_should_return_total_sales_when_ticket_is_airplane() {
		Ticket ticket = new Ticket();
		ticket.setTransportationType(TransportationType.AIRPLANE);
		ticket.setSeats(5);
		Mockito.when(ticketRepository.findByNo(anyInt())).thenReturn(Optional.of(ticket));

		int result = ticketService.totalTicketSales(123);

		assertEquals(AIRPLANE_SEAT_SIZE - 5, result);
		verify(ticketRepository, times(1)).findByNo(123);
	}

	@Test
	void it_should_return_total_sales_when_ticket_is_bus() {
		Ticket ticket = new Ticket();
		ticket.setTransportationType(TransportationType.BUS);
		ticket.setSeats(5);
		Mockito.when(ticketRepository.findByNo(anyInt())).thenReturn(Optional.of(ticket));

		int result = ticketService.totalTicketSales(123);

		assertEquals(BUS_SEAT_SIZE - 5, result);
		verify(ticketRepository, times(1)).findByNo(123);
	}

	@Test
	void it_should_throw_global_exception_when_tickets_not_found_to_search() {
		Mockito.when(ticketRepository.findByDepartureLocationAndArrivalLocationAndDepartureTimeAndArrivalTime(
				anyString(), anyString(), any(), any())).thenReturn(Optional.empty());

		Throwable exception = catchThrowable(
				() -> ticketService.search("Istanbul", "Ankara", LocalDate.now(), LocalDate.now()));

		assertThat(exception).isInstanceOf(GlobalException.class);
		assertThat(exception).hasMessage("Ticket bulunamadı");
		verify(ticketRepository, times(1)).findByDepartureLocationAndArrivalLocationAndDepartureTimeAndArrivalTime(
				"Istanbul", "Ankara", LocalDate.now(), LocalDate.now());
	}

	@Test
	void it_should_return_tickets_when_search() {
		List<Ticket> tickets = new ArrayList<>();
		tickets.add(new Ticket());
		tickets.add(new Ticket());
		Mockito.when(ticketRepository.findByDepartureLocationAndArrivalLocationAndDepartureTimeAndArrivalTime(
				anyString(), anyString(), any(), any())).thenReturn(Optional.of(tickets));
		List<TicketResponse> responses = new ArrayList<>();
		responses.add(new TicketResponse());
		responses.add(new TicketResponse());
		Mockito.when(ticketConverter.convert(tickets)).thenReturn(responses);
		List<TicketResponse> result = ticketService.search("Istanbul", "Ankara", LocalDate.now(), LocalDate.now());

		assertEquals(2, result.size());
		verify(ticketRepository, times(1)).findByDepartureLocationAndArrivalLocationAndDepartureTimeAndArrivalTime(
				"Istanbul", "Ankara", LocalDate.now(), LocalDate.now());
	}

	private List<TicketResponse> getTicketResponseList() {
		// TODO Auto-generated method stub
		return List.of(getTicketResponse(), getTicketResponse());
	}

	private List<Ticket> getTicketList() {
		// TODO Auto-generated method stub
		return List.of(getTicket().get(), getTicket().get());
	}

	private TicketRequest getTicketRequest() {
		// TODO Auto-generated method stub
		return new TicketRequest(123, LocalDate.of(2020, 03, 12), LocalDate.of(2020, 03, 12), "Ankara", "Denizli",
				TransportationType.AIRPLANE, 2000.0);
	}

	private Optional<Ticket> getTicket() {
		// TODO Auto-generated method stub
		return Optional.of(new Ticket(123, LocalDate.of(2020, 03, 12), LocalDate.of(2020, 03, 12), "Ankara", "Denizli",
				TransportationType.AIRPLANE, TicketStatus.BOOKED, 189, 2000.0));
	}

	private TicketResponse getTicketResponse() {
		// TODO Auto-generated method stub
		return new TicketResponse(123, LocalDate.of(2020, 03, 12), LocalDate.of(2020, 03, 12), "Ankara", "Denizli",
				TransportationType.AIRPLANE, 2000.0, TicketStatus.BOOKED);
	}

}
