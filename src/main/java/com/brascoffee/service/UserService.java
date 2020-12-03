package com.brascoffee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
		
		List<GrantedAuthority> grantedauthorities = new ArrayList<GrantedAuthority>();	
		user.getRoles().forEach(role -> grantedauthorities.add(new SimpleGrantedAuthority(role.getName())));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername() , user.getPassword(), grantedauthorities);
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

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public ResponseEntity<User> getUserById(long userId) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(userRepository.findById(userId).get());
	}

}
