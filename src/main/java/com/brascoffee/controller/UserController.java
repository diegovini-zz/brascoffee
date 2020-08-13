package com.brascoffee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

}
