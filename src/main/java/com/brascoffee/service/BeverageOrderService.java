package com.brascoffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brascoffee.abst.AbstractBeverage;
import com.brascoffee.entity.BeverageOrder;
import com.brascoffee.entity.CondimentOrder;
import com.brascoffee.repository.BeverageOrderRepository;

@Service
public class BeverageOrderService {
	@Autowired
	BeverageOrderRepository beverageOrderRepository;

	public List<BeverageOrder> placeOrder(List<BeverageOrder> beverageOrders) {

		beverageOrders.forEach(beverageOrder -> beverageOrder.getOrderedCondiments()
				.forEach(condiment -> condiment.setBeverageOrder(beverageOrder)));

		for (BeverageOrder beverageOrder : beverageOrders) {
			AbstractBeverage decoratedBeverage = beverageOrder.getOrderedBeverage();

			if (beverageOrder.getOrderedCondiments() != null) {

				for (CondimentOrder condimentOrder : beverageOrder.getOrderedCondiments()) {
					condimentOrder.getCondiment().setBeverage(decoratedBeverage);
					decoratedBeverage = condimentOrder.getCondiment();

				}

			}
			beverageOrder.setFinalPrice(decoratedBeverage.cost());
		}

		return beverageOrderRepository.saveAll(beverageOrders);
	}

	public List<BeverageOrder> getOrders() {
		//
		return beverageOrderRepository.findAll();
	}

}
