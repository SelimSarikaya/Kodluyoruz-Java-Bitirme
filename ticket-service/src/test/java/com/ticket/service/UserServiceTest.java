package com.ticket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

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
import com.ticket.converter.UserConverter;
import com.ticket.exception.UserNotFoundException;
import com.ticket.model.Role;
import com.ticket.model.User;
import com.ticket.model.enums.Sex;
import com.ticket.model.enums.UserType;
import com.ticket.repository.RoleRepository;
import com.ticket.repository.UserRepository;
import com.ticket.request.LoginRequest;
import com.ticket.request.UserRequest;
import com.ticket.response.UserResponse;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	@Mock
	private UserConverter userConverter;
	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private RabbitMQNotificationConfiguration rabbitMQNotificationConfiguration;
	@Mock
	private RabbitTemplate rabbitTemplate;
	@Test
	void it_should_create() {
		
		Mockito.when(userConverter.convert(Mockito.any(UserRequest.class), Mockito.anyString())).thenReturn(new User());  
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUser());
		Mockito.when(userConverter.convert(Mockito.any(User.class))).thenReturn(getUserResponse());
		Mockito.when(rabbitMQNotificationConfiguration.getEmailQueue()).thenReturn("queue.name");
		Mockito.when(roleRepository.save(Mockito.any(Role.class))).thenReturn(new Role());
	
		UserResponse userResponse = userService.create(getUserRequest());
		
		assertThat(userResponse).isNotNull();
		assertThat(userResponse.getName()).isEqualTo(getUser().getName());
		assertThat(userResponse.getEmail()).isEqualTo(getUser().getEmail());
		
		verify(rabbitTemplate).convertAndSend(Mockito.anyString(), Mockito.any(User.class));
		verify(roleRepository, Mockito.times(1)).save(Mockito.any(Role.class));
		verify(userRepository, Mockito.times(2)).save(Mockito.any(User.class));

	
		
	}
	@Test
	void it_should_get_all_users( ) {
		Mockito.when(userRepository.findAll()).thenReturn(getUserList());
		Mockito.when(userConverter.convert(Mockito.anyList())).thenReturn(getUserResponseList());
		
		List<UserResponse> getAll = userService.getAll();
		
		assertThat(getAll).isNotNull();
		assertEquals("test", getUserResponse().getName());
		assertEquals(2, getUserResponseList().size());
		assertEquals("test@gmail.com", getUserResponseList().get(1).getEmail());

		
	}
	@Test
	void it_should_get_by_id() {
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(getUserOptional());
		
		Optional<User> byId = userService.getById(1);
		
		assertThat(byId).isNotNull();
		assertTrue(byId.isPresent());
		assertEquals("test", byId.get().getName());
	}
	@Test
	void it_should_throw_user_not_found_exception_when_trying_to_login( ) {
		Throwable exception = catchThrowable(() -> userService.login(new LoginRequest()));
		
		assertThat(exception).isInstanceOf(UserNotFoundException.class);
	}
	@Test
	void it_should_login() {
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserOptional());


		
		String login = userService.login(getLoginRequest());
		
		assertThat(login).isNotNull();
		assertEquals("Email veya şifre yanlış", login);

	}

	
	private LoginRequest getLoginRequest() {
		// TODO Auto-generated method stub
		return new LoginRequest("test@gmail.com", "password");
	}
	private List<UserResponse> getUserResponseList() {
		// TODO Auto-generated method stub
		return List.of(getUserResponse(),getUserResponse());
	}
	private List<User> getUserList() {
		// TODO Auto-generated method stub
		return List.of(getUser(),getUser());
	}
	private User getUser() {
		// TODO Auto-generated method stub
		return new User(Sex.FEMALE, "test", "test1", "test123", "test@gmail.com", UserType.INDIVIDUAL, "123456");
	}
	private Optional<User> getUserOptional() {
		// TODO Auto-generated method stub
		return Optional.of(new User(Sex.FEMALE, "test", "test1", "password", "test@gmail.com", UserType.INDIVIDUAL, "123456"));
	}

	private UserRequest getUserRequest() {
		// TODO Auto-generated method stub
		return new UserRequest("test", "testsurname", "test123", "test@gmail.com", Sex.FEMALE, UserType.INDIVIDUAL, "123456789");
	}
	private UserResponse getUserResponse() {
		return new UserResponse("test", "test@gmail.com");
	}

}
