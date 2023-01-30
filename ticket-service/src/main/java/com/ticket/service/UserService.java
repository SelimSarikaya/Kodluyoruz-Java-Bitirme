package com.ticket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.configuration.RabbitMQNotificationConfiguration;
import com.ticket.converter.UserConverter;
import com.ticket.exception.UserNotFoundException;
import com.ticket.model.Role;
import com.ticket.model.User;
import com.ticket.repository.RoleRepository;
import com.ticket.repository.UserRepository;
import com.ticket.request.LoginRequest;
import com.ticket.request.UserRequest;
import com.ticket.response.UserResponse;
import com.ticket.util.PasswordUtilization;

@Service
public class UserService {

	private static final String EMAIL_VEYA_ŞIFRE_YANLIŞ = "Email veya şifre yanlış";

	private static final String LOGIN_BAŞARILI = "Login Başarılı";

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserConverter userConverter;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private RabbitMQNotificationConfiguration rabbitMQNotificationConfiguration;
	@Autowired
	private RoleRepository roleRepository;

	Logger logger = Logger.getLogger(UserService.class.getName());

	public UserResponse create(UserRequest userRequest) {

		String hash = PasswordUtilization.preparePasswordHash(userRequest.getPassword());
		logger.log(Level.INFO, "password hash created {0}", hash);

		User savedUser = userRepository.save(userConverter.convert(userRequest, hash));
		Role userRole = new Role();
		List<Role> userRoles = new ArrayList<>();
		userRole.setRoleName("ROLE_USER");
		roleRepository.save(userRole);
		userRoles.add(userRole);
		savedUser.setRoles(userRoles);

		userRepository.save(savedUser);

		logger.log(Level.INFO, "user created {0}", savedUser.getId());

		rabbitTemplate.convertAndSend(rabbitMQNotificationConfiguration.getEmailQueue(), savedUser);

		return userConverter.convert(savedUser);

	}

	public List<UserResponse> getAll() {
		return userConverter.convert(userRepository.findAll());
	}

	public Optional<User> getById(Integer userId) {
		return userRepository.findById(userId);
	}

	public String login(LoginRequest loginRequest) {

		User foundUser = userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new UserNotFoundException("kullanıcı bulunamadı"));
		boolean isValid = PasswordUtilization.matchPassword(loginRequest.getPassword(), foundUser.getPassword());
		return isValid ? LOGIN_BAŞARILI : EMAIL_VEYA_ŞIFRE_YANLIŞ;

	}

}
