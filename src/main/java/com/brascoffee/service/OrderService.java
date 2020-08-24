package com.brascoffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brascoffee.abst.AbstractBeverage;
import com.brascoffee.entity.Order;
import com.brascoffee.entity.OrderCondiment;
import com.brascoffee.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository beverageOrderRepository;

	public List<Order> placeOrder(List<Order> order) {


		for (Order beverageOrder : order) {
			AbstractBeverage decoratedBeverage = beverageOrder.getBeverage();

			if (beverageOrder.getCondiments() != null) {

				for (OrderCondiment condimentOrder : beverageOrder.getCondiments()) {
					condimentOrder.setBeverageOrder(beverageOrder);
					condimentOrder.getCondiment().setBeverage(decoratedBeverage);
					decoratedBeverage = condimentOrder.getCondiment();

				}

			}
			beverageOrder.setFinalPrice(decoratedBeverage.cost());
			System.out.println(decoratedBeverage.getDescription());
		}

		return beverageOrderRepository.saveAll(order);
	}

	public List<Order> getOrders() {
		
		return beverageOrderRepository.findAll();
	}

}
