package com.brascoffee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brascoffee.abst.AbstractBeverage;
import com.brascoffee.entity.Beverage;
import com.brascoffee.entity.Condiment;

@RestController
public class OrderController {

	@PostMapping(value = "/orders")
	public AbstractBeverage placeOrder(@RequestBody List<Beverage> beverages) {
		List<Beverage> beveragesList = beverages;
		AbstractBeverage beverage2 = null;

		for (Beverage beverage : beveragesList) {
			beverage2 = beverage;
			if (beverage.getCondiments() != null) {
				for (Condiment condiment : beverage.getCondiments()) {
					condiment.setBeverage(beverage2);
					beverage2 = condiment;
				}
			}

			System.out.println("Beverage: " + beverage2.getDescription() + " cost:" + beverage2.cost());
		}

//		beverages.forEach(beverage -> {
//			if (beverage.getCondiments() != null) {
//				beverage.getCondiments().forEach(condiment -> {
//				
//				});
//				
//				
//			}
//
//		});

		return null;

	}

}
