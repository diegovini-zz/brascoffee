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

import com.brascoffee.entity.Condiment;
import com.brascoffee.service.CondimentService;

@RestController
public class CondimentController {
	@Autowired
	CondimentService condimentService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value="/condiments")
	public List<Condiment> getCondiments(){
		return condimentService.getCondiments();
	}
	
	@GetMapping(value="condiments/{id}")
	public ResponseEntity<Condiment> getCondimentById(@PathVariable long id) {
		Condiment condiment = condimentService.getCondimentById(id);
		return new ResponseEntity<Condiment>(condiment,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = "/condiments")	
	public ResponseEntity<Condiment> addCondiment(@RequestBody @Valid Condiment condiment) {
		
		return new ResponseEntity<Condiment>(condimentService.addCondiment(condiment),HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value="/condiments/{id}")
	public ResponseEntity<Condiment> updateCondiment(@PathVariable long id, @RequestBody Condiment condiment){
		Condiment updatedCondiment = condimentService.updateCondiment(id,condiment);
		return new ResponseEntity<Condiment>(updatedCondiment,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value="condiments/{id}")
	public ResponseEntity<Condiment> deleteCondiment(@PathVariable long id){
		condimentService.deleteCondiment(id);
		return new ResponseEntity<Condiment>(HttpStatus.OK);
	}

}
