package com.brascoffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brascoffee.entity.Order;
import com.brascoffee.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	OrderService beverageOrderService;

	@GetMapping(value = "/orders")
	public List<Order> getOrders() {
		return beverageOrderService.getOrders();
	}

	@PostMapping(value = "/orders")
	public List<Order> placeOrder(@RequestBody List<Order> order) {

		return beverageOrderService.placeOrder(order);

	}

}
