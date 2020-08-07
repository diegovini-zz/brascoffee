package com.brascoffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brascoffee.entity.BeverageOrder;
import com.brascoffee.service.BeverageOrderService;

@RestController
public class BeverageOrderController {
	@Autowired
	BeverageOrderService beverageOrderService;

//	@PostMapping(value = "/orders")
//	public AbstractBeverage placeOrder(@RequestBody List<Beverage> beverages) {
//		List<Beverage> beveragesList = beverages;
//		AbstractBeverage decoratedBeverage = null;
//
//		for (Beverage beverage : beveragesList) {
//			decoratedBeverage = beverage;
//			if (beverage.getCondiments() != null) {
//				for (Condiment condiment : beverage.getCondiments()) {
//					condiment.setBeverage(decoratedBeverage);
//					decoratedBeverage = condiment;
//				}
//			}
//
//			System.out.println("Beverage: "+decoratedBeverage.getDescription()  + " cost:" +decoratedBeverage.cost() );
//		}
//
////		beverages.forEach(beverage -> {
////			if (beverage.getCondiments() != null) {
////				beverage.getCondiments().forEach(condiment -> {
////				
////				});
////				
////				
////			}
////
////		});
//
//		return null;
//
//	}
	
	@GetMapping(value="/orders")
	public List<BeverageOrder> getOrders(){
		return beverageOrderService.getOrders();
	}
	
	@PostMapping(value="/orders")
	public List<BeverageOrder> placeOrder(@RequestBody List<BeverageOrder> beverageOrders) {
		
		return beverageOrderService.placeOrder(beverageOrders);
		
	}

}
