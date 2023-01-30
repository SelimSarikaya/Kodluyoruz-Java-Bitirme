package com.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.request.LoginRequest;
import com.ticket.request.UserRequest;
import com.ticket.response.UserResponse;
import com.ticket.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
		UserResponse userResponse = userService.create(userRequest);
		return ResponseEntity.ok(userResponse);
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(userService.login(loginRequest));
	}

}
