package com.brascoffee.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brascoffee.entity.Condiment;
import com.brascoffee.service.CondimentService;

@RestController
public class CondimentController {
	@Autowired
	CondimentService condimentService;
	
	@GetMapping(value="/condiments")
	public List<Condiment> getCondiments(){
		return condimentService.getCondiments();
	}
	
	@PostMapping(value = "/condiments")
	public ResponseEntity<Condiment> addCondiment(@RequestBody @Valid Condiment condiment) {
		
		return new ResponseEntity<Condiment>(condimentService.addCondiment(condiment),HttpStatus.CREATED);
	}

}
