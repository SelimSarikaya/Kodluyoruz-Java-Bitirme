package com.ticketpayment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketpayment.client.Sex;
import com.ticketpayment.model.Payment;
import com.ticketpayment.service.PaymentService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class PaymentControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PaymentService paymentService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void it_should_create_card_payment() throws Exception {

        Mockito.when(paymentService.createCard(Mockito.any(Payment.class))).thenReturn(getPayment());

        mockMvc.perform(post("/payments/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getPayment())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.amount").value(200));
   

	}
	@Test
	void it_should_create_eft_payment() throws Exception {
		
		Mockito.when(paymentService.createEft(Mockito.any(Payment.class))).thenReturn(getPayment());
		
		mockMvc.perform(post("/payments/eft")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(getPayment())))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.userId").value(1))
		.andExpect(jsonPath("$.amount").value(200));
		
		
	}
    @Test
    void it_should_get_all_payments() throws Exception {
        List<Payment> payments = Arrays.asList(getPayment());
        Mockito.when(paymentService.getAll()).thenReturn(payments);

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].amount").value(200));
    }
	
	
	private Payment getPayment() {
		// TODO Auto-generated method stub
		return new Payment(1, 1, "12345", null, 200, List.of(Sex.MALE, Sex.FEMALE) , List.of("test1", "test2"), 123);
	}
	
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
