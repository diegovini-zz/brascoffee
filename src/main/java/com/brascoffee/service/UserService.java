package com.brascoffee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.brascoffee.entity.User;
import com.brascoffee.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findUserByUsername(username).get();
		
		return new org.springframework.security.core.userdetails.User(user.getUsername() , user.getPassword(), new ArrayList<>());
	}

	public ResponseEntity<?> registerUser(User user) {
		User storedUser = userRepository.findByusername(user.getUsername()).orElse(null);

		if (storedUser != null) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("message", "User already registred");
			return ResponseEntity.badRequest().body(map);
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return ResponseEntity.ok(userRepository.save(user));

	}
	
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByusername(username);
	}

}
