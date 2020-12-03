package com.brascoffee.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brascoffee.entity.Beverage;
import com.brascoffee.service.BeverageService;

@RestController
public class BeverageController {
	
	@Autowired
	BeverageService beverageService;
	
	@PreAuthorize("hasAuthority('ADMIN')")	
	@GetMapping(value = "/beverages")
	public List<Beverage> getAllBeverages() {
		return beverageService.getAllBeverages();	
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")	
	@GetMapping(value="/beverages/{id}")
	public ResponseEntity<Beverage> getBeverageById(@PathVariable long id){
		Beverage beverage = beverageService.getBeverageById(id);
		return new ResponseEntity<Beverage>(beverage,HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value="/beverages")
	public ResponseEntity<Beverage> addBeverage(@RequestBody @Valid Beverage beverage) {
		Beverage createdBeverage = beverageService.addBeverage(beverage);
		
		return new ResponseEntity<Beverage>(createdBeverage, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")	
	@PutMapping(value="/beverages/{id}")
	public ResponseEntity<Beverage> updateBeverage(@PathVariable long id, @RequestBody @Valid  Beverage beverage){
		Beverage updatedBeverage = beverageService.updateBeverage(id,beverage);
		return new ResponseEntity<Beverage>(updatedBeverage,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value="/beverages/{id}")
	public ResponseEntity<Beverage> deleteBeverate(@PathVariable long id){
		beverageService.deleteBeverage(id);
		return new ResponseEntity<Beverage>(HttpStatus.OK);
	}

	

}
