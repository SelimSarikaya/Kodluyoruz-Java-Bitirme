package com.ticket.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.time.LocalDate;
import java.util.List;

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
import com.ticket.model.enums.Sex;
import com.ticket.model.enums.TicketStatus;
import com.ticket.model.enums.TransportationType;
import com.ticket.request.OrderRequest;
import com.ticket.response.OrderResponse;
import com.ticket.response.TicketResponse;
import com.ticket.response.UserResponse;
import com.ticket.service.OrderService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private OrderService orderService;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void it_should_create_order() throws Exception {
		Mockito.when(orderService.create(Mockito.any(OrderRequest.class))).thenReturn(getOrderResponse());
		String request = mapper.writeValueAsString(getOrderRequest());
		
		ResultActions resultActions = mockMvc.perform(post("/orders/admin").content(request).contentType(MediaType.APPLICATION_JSON));
		
		resultActions
		.andExpect(status().isOk())
	    .andExpect(jsonPath("$.ticketResponse.no").value(123))
	    .andExpect(jsonPath("$.userResponse.name").value("test"))
	    .andExpect(jsonPath("$.userResponse.email").value("test@gmail.com"))
	    .andExpect(jsonPath("$.amount").value(2))
	    .andExpect(jsonPath("$.sexes[0]").value("MALE"))
	    .andExpect(jsonPath("$.sexes[1]").value("FEMALE"));
	}
	
	
	
	
	@Test
	void it_should_get_all_orders() throws Exception {
		List<OrderResponse> orderResponses = getOrderResponseList();
		Mockito.when(orderService.getAll()).thenReturn(orderResponses);
		
		ResultActions resultActions = mockMvc.perform(get("/orders"));
		
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].amount").value("2"))
		.andExpect(jsonPath("$[0].sexes").value(hasSize(2)))
		.andExpect(jsonPath("$[0].nameSurnames").value(hasSize(2)))
		.andExpect(jsonPath("$[0].cost").value(2000.0));
	}
	@Test
	void it_should_get_order_details() throws Exception {
		int id = 1;
		int no = 123;
		Mockito.when(orderService.getOrderDetail(id, no)).thenReturn(getOrderResponse());
		
		ResultActions resultActions = mockMvc.perform(get("/orders/"+ id + "/ticketno/"+ no));
		
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.amount").value(2))
		.andExpect(jsonPath("$.sexes[0]").value("MALE"))
		.andExpect(jsonPath("$.sexes[1]").value("FEMALE"))
		.andExpect(jsonPath("$.nameSurnames[0]").value("test1"))
		.andExpect(jsonPath("$.nameSurnames[1]").value("test2"))
		.andExpect(jsonPath("$.cost").value(2000.0));
	}
	@Test
	void it_should_get_trips() throws Exception {
		int id = 1;
		List<TicketResponse> ticketResponses = getTicketResponseList();
		Mockito.when(orderService.getTrips(id)).thenReturn(ticketResponses);
		
		ResultActions resultActions = mockMvc.perform(get("/orders/trips/{id}", id));
		
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].no").value(123))
		.andExpect(jsonPath("$[0].departureTime").value("2020-03-12"))
		.andExpect(jsonPath("$[0].arrivalTime").value("2020-03-12"))
		.andExpect(jsonPath("$[0].departureLocation").value("Ankara"))
		.andExpect(jsonPath("$[0].arrivalLocation").value("Denizli"))
		.andExpect(jsonPath("$[0].transportationType").value("AIRPLANE"))
		.andExpect(jsonPath("$[0].ticketCost").value(2000.0))
		.andExpect(jsonPath("$[0].ticketStatus").value("BOOKED"));
	}

		
	private List<TicketResponse> getTicketResponseList() {
		// TODO Auto-generated method stub
		return List.of(getTicketResponse());
	}
	private List<OrderResponse> getOrderResponseList() {
		// TODO Auto-generated method stub
		return List.of(getOrderResponse());
	}
	private OrderRequest getOrderRequest() {
		// TODO Auto-generated method stub
		return new OrderRequest(123, 3, 2, List.of(Sex.MALE, Sex.FEMALE), List.of("test1", "test2"));
	}
	private OrderResponse getOrderResponse() {
		// TODO Auto-generated method stub
		return new OrderResponse(getTicketResponse(), getUserResponse(), 2, List.of(Sex.MALE, Sex.FEMALE),
				List.of("test1", "test2"), 2000.0);
	}
	private TicketResponse getTicketResponse() {
		// TODO Auto-generated method stub
		return new TicketResponse(123, LocalDate.of(2020, 03, 12), LocalDate.of(2020, 03, 12), "Ankara", "Denizli",
				TransportationType.AIRPLANE, 2000.0, TicketStatus.BOOKED);
	}
	private UserResponse getUserResponse() {
		return new UserResponse("test", "test@gmail.com");
	}
	
	private static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}


}
