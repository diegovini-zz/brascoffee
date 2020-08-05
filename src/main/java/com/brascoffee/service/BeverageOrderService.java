package com.brascoffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brascoffee.entity.BeverageOrder;
import com.brascoffee.repository.BeverageOrderRepository;

@Service
public class BeverageOrderService {
	@Autowired
	BeverageOrderRepository beverageOrderRepository;

	public BeverageOrder placeOrder(BeverageOrder beverageOrder) {
		
		beverageOrder.getOrderedCondiments().forEach(condiment -> condiment.setBeverageOrder(beverageOrder));
		return beverageOrderRepository.save(beverageOrder);
	}

	public List<BeverageOrder> getOrders() {
		// 
		return beverageOrderRepository.findAll();
	}
	
	

}
