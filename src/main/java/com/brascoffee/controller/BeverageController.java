package com.brascoffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brascoffee.entity.Beverage;
import com.brascoffee.service.BeverageService;

@RestController
public class BeverageController {
	
	@Autowired
	BeverageService beverageService;
	
	@GetMapping(value = "/beverages")
	public List<Beverage> getAllBeverages() {
		return beverageService.getAllBeverages();
		
	}
	
	@PostMapping(value="/beverages")
	public Beverage addBeverage(@RequestBody Beverage beverage) {
		return beverageService.addBeverage(beverage);
	}

	

}
