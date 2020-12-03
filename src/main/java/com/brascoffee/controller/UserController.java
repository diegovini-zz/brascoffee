package com.brascoffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brascoffee.entity.User;
import com.brascoffee.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping(value = "register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		return userService.registerUser(user);
	}

	@GetMapping(value = "/users")
	public List<User> getUsers() {
		return userService.findAll();
	}

	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") long userId) {

		return userService.getUserById(userId);

	}

}
