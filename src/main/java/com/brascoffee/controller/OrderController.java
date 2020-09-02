package com.brascoffee.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brascoffee.entity.Order;
import com.brascoffee.service.OrderService;

@Validated
@RestController
public class OrderController {
	@Autowired
	OrderService beverageOrderService;

	@GetMapping(value = "/orders")
	public List<Order> getOrders() {
		return beverageOrderService.getOrders();
	}

	@PostMapping(value = "/orders")
	
	public ResponseEntity<List<Order>> placeOrder(@RequestBody @NotEmpty(message="Order list must not be empty") List<@Valid Order> order) {

		
		return new ResponseEntity<List<Order>>(beverageOrderService.placeOrder(order), HttpStatus.CREATED);

	}

}
