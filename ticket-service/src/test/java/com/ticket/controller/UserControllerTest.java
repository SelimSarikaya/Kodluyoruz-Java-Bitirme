package com.ticket.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.ticket.model.enums.UserType;
import com.ticket.request.LoginRequest;
import com.ticket.request.UserRequest;
import com.ticket.response.UserResponse;
import com.ticket.service.UserService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	
	private ObjectMapper mapper = new ObjectMapper();


	@Test
	void it_should_get_all_users() throws Exception {
		
		Mockito.when(userService.getAll()).thenReturn(getAllUserResponse());

		ResultActions resultActions = mockMvc.perform(get("/users"));
		
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name").value("test"))
		.andExpect(jsonPath("$[0].email").value("test@gmail.com"));

	}
	@Test
	void it_should_create() throws Exception {
		
		Mockito.when(userService.create(Mockito.any(UserRequest.class))).thenReturn(getUserResponse());
		
		String request = mapper.writeValueAsString(getUserRequest());
		
		ResultActions resultActions = mockMvc.perform(post("/users").content(request).contentType(MediaType.APPLICATION_JSON));
		
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("test"))
		.andExpect(jsonPath("$.email").value("test@gmail.com"));
		
	}
	@Test
	void it_should_login() throws Exception {
		Mockito.when(userService.login(Mockito.any(LoginRequest.class))).thenReturn("test123");
		
		String request = mapper.writeValueAsString(loginRequest());
		
		ResultActions resultActions = mockMvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON).content(request));
		
		resultActions
		.andExpect(status().isOk());
	}

	private LoginRequest loginRequest() {
		// TODO Auto-generated method stub
		return new LoginRequest();
	}
	private UserRequest getUserRequest() {
		// TODO Auto-generated method stub
		return new UserRequest("test", "testsurname", "test123", "test@gmail.com", Sex.FEMALE, UserType.INDIVIDUAL, "123456789");
	}
	private List<UserResponse> getAllUserResponse() {
		return List.of(getUserResponse());
	}

	private UserResponse getUserResponse() {
		return new UserResponse("test", "test@gmail.com");
	}


}
