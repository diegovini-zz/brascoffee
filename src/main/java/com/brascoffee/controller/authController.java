package com.brascoffee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.brascoffee.entity.User;
import com.brascoffee.models.AuthenticationRequest;
import com.brascoffee.models.AuthenticationResponse;
import com.brascoffee.models.AuthenticationTokenRequest;
import com.brascoffee.models.AuthenticationTokenResponse;
import com.brascoffee.security.JwtUtil;
import com.brascoffee.service.UserService;

@RestController
public class authController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping(value = "/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			
		} catch (BadCredentialsException e) {
			//
			throw new BadCredentialsException("Incorrect username or password", e);
		}

		//
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		final String jwt = jwtUtil.generateToken(userDetails);
		final User user = userService.findUserByUsername(userDetails.getUsername()).get();

		return ResponseEntity.ok(new AuthenticationResponse(jwt,user));

	}
	
	@GetMapping(value="/user")
	public ResponseEntity<?> getUsernameFromToken(@RequestHeader("Authorization") AuthenticationTokenRequest authenticationTokenRequest){
		String username = jwtUtil.extractUsername(authenticationTokenRequest.getToken());
		final User user = userService.findUserByUsername(username).get();
		return ResponseEntity.ok(new AuthenticationTokenResponse(user));
		
	}

}
