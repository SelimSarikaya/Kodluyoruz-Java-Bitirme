package com.ticket.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ticket.model.User;
import com.ticket.model.enums.UserType;
import com.ticket.request.UserRequest;
import com.ticket.response.UserResponse;

@Component
public class UserConverter {

	public User convert(UserRequest userRequest, String hash) {
		User user = new User();
		user.setName(userRequest.getName());
		user.setSurname(userRequest.getSurname());
		user.setSex(userRequest.getSex());
		user.setPassword(hash);
		user.setType(userRequest.getType());
		user.setTelNo(userRequest.getTelNo());
		user.setEmail(userRequest.getEmail());
		user.setType(userRequest.getType() == null ? UserType.INDIVIDUAL : userRequest.getType());
		return user;
	}
	
	public UserResponse convert(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setName(user.getName());
		userResponse.setEmail(user.getEmail());
		return userResponse;
	}

	public List<UserResponse> convert(List<User> userList) {
		List<UserResponse> userResponses = new ArrayList<>();
		
		for (User user : userList) {
			userResponses.add(convert(user));
		}
		
		return userList.stream().map(this::convert).toList();
	}
	
	
}
