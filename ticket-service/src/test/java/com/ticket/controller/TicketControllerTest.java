package com.ticket.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.converter.TicketConverter;
import com.ticket.model.Ticket;
import com.ticket.model.enums.TicketStatus;
import com.ticket.model.enums.TransportationType;
import com.ticket.request.TicketRequest;
import com.ticket.response.TicketResponse;
import com.ticket.service.TicketService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class TicketControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TicketService ticketService;
	@MockBean
	private TicketConverter ticketConverter;
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void it_should_create_ticket() throws Exception {
		Mockito.when(ticketService.create(Mockito.any(TicketRequest.class))).thenReturn(getTicketResponse());
		String request = mapper.writeValueAsString(getTicketRequest());

		ResultActions resultActions = mockMvc
				.perform(post("/admin/tickets").content(request).contentType(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.departureLocation").value("Ankara"))
				.andExpect(jsonPath("$.arrivalLocation").value("Denizli")).andExpect(jsonPath("$.no").value("123"))
				.andExpect(jsonPath("$.departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$.arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$.transportationType").value("AIRPLANE"));
	}

	@Test
	void it_should_get_all_tickets() throws Exception {
		Mockito.when(ticketService.getAll()).thenReturn(getTicketResponseList());

		ResultActions resultActions = mockMvc.perform(get("/tickets"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].departureLocation").value("Ankara"))
				.andExpect(jsonPath("$[0].arrivalLocation").value("Denizli"))
				.andExpect(jsonPath("$[0].no").value("123"))
				.andExpect(jsonPath("$[0].departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].transportationType").value("AIRPLANE"));
	}

	@Test
	void it_should_get_all_tickets_by_departure_location() throws Exception {
		Mockito.when(ticketService.getAllByDepartureLocation("Ankara")).thenReturn(getTicketResponseList());

		ResultActions resultActions = mockMvc.perform(get("/tickets/departureLocation/" + "Ankara"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].departureLocation").value("Ankara"))
				.andExpect(jsonPath("$[0].arrivalLocation").value("Denizli"))
				.andExpect(jsonPath("$[0].no").value("123"))
				.andExpect(jsonPath("$[0].departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].transportationType").value("AIRPLANE"));
	}

	@Test
	void it_should_get_all_tickets_by_arrival_location() throws Exception {
		Mockito.when(ticketService.getAllByArrivalLocation("Denizli")).thenReturn(getTicketResponseList());

		ResultActions resultActions = mockMvc.perform(get("/tickets/arrivalLocation/" + "Denizli"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].departureLocation").value("Ankara"))
				.andExpect(jsonPath("$[0].arrivalLocation").value("Denizli"))
				.andExpect(jsonPath("$[0].no").value("123"))
				.andExpect(jsonPath("$[0].departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].transportationType").value("AIRPLANE"));
	}

	@Test
	void it_should_get_all_tickets_by_departure_time() throws Exception {
		Mockito.when(ticketService.getAllByDepartureTime(LocalDate.of(2020, 03, 12)))
				.thenReturn(getTicketResponseList());

		ResultActions resultActions = mockMvc.perform(get("/tickets/departureTime/2020-03-12"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].departureLocation").value("Ankara"))
				.andExpect(jsonPath("$[0].arrivalLocation").value("Denizli"))
				.andExpect(jsonPath("$[0].no").value("123"))
				.andExpect(jsonPath("$[0].departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].transportationType").value("AIRPLANE"));
	}

	@Test
	void it_should_get_all_tickets_by_arrival_time() throws Exception {

		Mockito.when(ticketService.getAllByArrivalTime(LocalDate.of(2020, 03, 12))).thenReturn(getTicketResponseList());

		ResultActions resultActions = mockMvc.perform(get("/tickets/arrivalTime/2020-03-12"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].departureLocation").value("Ankara"))
				.andExpect(jsonPath("$[0].arrivalLocation").value("Denizli"))
				.andExpect(jsonPath("$[0].no").value("123"))
				.andExpect(jsonPath("$[0].departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].transportationType").value("AIRPLANE"));
	}

	@Test
	void it_should_get_all_bus_tickets() throws Exception {
		List<TicketResponse> ticketResponses = getTicketResponseList();
		ticketResponses.forEach(t -> t.setTransportationType(TransportationType.BUS));
		Mockito.when(ticketService.getAllBusTickets()).thenReturn(ticketResponses);

		ResultActions resultActions = mockMvc.perform(get("/tickets/bus"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].departureLocation").value("Ankara"))
				.andExpect(jsonPath("$[0].arrivalLocation").value("Denizli"))
				.andExpect(jsonPath("$[0].no").value("123"))
				.andExpect(jsonPath("$[0].departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].transportationType").value("BUS"));
	}

	@Test
	void it_should_get_all_airplane_tickets() throws Exception {
		Mockito.when(ticketService.getAllAirplaneTickets()).thenReturn(getTicketResponseList());

		ResultActions resultActions = mockMvc.perform(get("/tickets/airplane"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].departureLocation").value("Ankara"))
				.andExpect(jsonPath("$[0].arrivalLocation").value("Denizli"))
				.andExpect(jsonPath("$[0].no").value("123"))
				.andExpect(jsonPath("$[0].departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$[0].transportationType").value("AIRPLANE"));
	}

	@Test
	void it_should_get_ticket_by_no() throws Exception {
		int no = 123;
		Ticket ticket = getTicket().get();
		TicketResponse ticketResponse = getTicketResponse();
		Mockito.when(ticketService.getByNo(no)).thenReturn(ticket);
		Mockito.when(ticketConverter.convert(ticket)).thenReturn(ticketResponse);

		ResultActions resultActions = mockMvc.perform(get("/tickets/{no}", no));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.departureLocation").value("Ankara"))
				.andExpect(jsonPath("$.arrivalLocation").value("Denizli")).andExpect(jsonPath("$.no").value(123))
				.andExpect(jsonPath("$.departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$.arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$.transportationType").value("AIRPLANE"));

	}

	@Test
	void it_should_cancel_ticket_by_no() throws Exception {
		Mockito.when(ticketService.cancel(123)).thenReturn(getTicketResponse());

		ResultActions resultActions = mockMvc.perform(put("/admin/tickets/cancel/{no}", 123));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.departureLocation").value("Ankara"))
				.andExpect(jsonPath("$.arrivalLocation").value("Denizli")).andExpect(jsonPath("$.no").value(123))
				.andExpect(jsonPath("$.departureTime").value("2020-03-12"))
				.andExpect(jsonPath("$.arrivalTime").value("2020-03-12"))
				.andExpect(jsonPath("$.transportationType").value("AIRPLANE"));

	}
	@Test
    void it_should_get_total_money_from_ticket_sales_by_no() throws Exception {
        int no = 123;
        double expectedTotalMoney = 1000.0;
        Mockito.when(ticketService.totalMoneyFromTicketSales(no)).thenReturn(expectedTotalMoney);
        ResultActions resultActions = mockMvc.perform(get("/admin/tickets/money/{no}", no));
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(expectedTotalMoney)));
	}
    @Test
    void it_should_get_total_ticket_sales_by_no() throws Exception {
        int no = 123;
        int expectedTotalTicketSales = 100;
        Mockito.when(ticketService.totalTicketSales(no)).thenReturn(expectedTotalTicketSales);
        ResultActions resultActions = mockMvc.perform(get("/admin/tickets/sales/{no}", no));
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(expectedTotalTicketSales)));
    }
    @Test
    void it_should_search_ticket() throws Exception {
        List<TicketResponse> expectedTicketList = getTicketResponseList();
        String departureLocation = "Ankara";
        String arrivalLocation = "Denizli";
        LocalDate departureTime = LocalDate.parse("2020-03-12");
        LocalDate arrivalTime = LocalDate.parse("2020-03-12");
        Mockito.when(ticketService.search(departureLocation, arrivalLocation, departureTime, arrivalTime)).thenReturn(expectedTicketList);
        ResultActions resultActions = mockMvc.perform(get("/tickets/search")
            .param("departureLocation", departureLocation)
            .param("arrivalLocation", arrivalLocation)
            .param("departureTime", departureTime.toString())
            .param("arrivalTime", arrivalTime.toString())
        );
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].departureLocation").value("Ankara"))
            .andExpect(jsonPath("$[0].arrivalLocation").value ("Denizli"))
            .andExpect(jsonPath("$[0].no").value("123"))
            .andExpect(jsonPath("$[0].departureTime").value("2020-03-12"))
            .andExpect(jsonPath("$[0].arrivalTime").value("2020-03-12"))
            .andExpect(jsonPath("$[0].transportationType").value("AIRPLANE"));
    }
    
    
	private Optional<Ticket> getTicket() {
		// TODO Auto-generated method stub
		return Optional.of(new Ticket(123, LocalDate.of(2020, 03, 12), LocalDate.of(2020, 03, 12), "Ankara", "Denizli",
				TransportationType.AIRPLANE, TicketStatus.BOOKED, 189, 2000.0));
	}

	private List<TicketResponse> getTicketResponseList() {
		// TODO Auto-generated method stub
		return List.of(getTicketResponse());
	}

	private TicketRequest getTicketRequest() {
		// TODO Auto-generated method stub
		return new TicketRequest(123, LocalDate.of(2020, 03, 12), LocalDate.of(2020, 03, 12), "Ankara", "Denizli",
				TransportationType.AIRPLANE, 2000.0);
	}

	private TicketResponse getTicketResponse() {
		// TODO Auto-generated method stub
		return new TicketResponse(123, LocalDate.of(2020, 03, 12), LocalDate.of(2020, 03, 12), "Ankara", "Denizli",
				TransportationType.AIRPLANE, 2000.0, TicketStatus.BOOKED);
	}

}
