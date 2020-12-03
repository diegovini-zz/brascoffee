package com.brascoffee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.brascoffee.entity.Beverage;
import com.brascoffee.repository.BeverageRepository;

@Service
public class BeverageService {

	private final BeverageRepository beverageRepository;

	public BeverageService(BeverageRepository beverageRepository) {
		this.beverageRepository = beverageRepository;
	}

	public Beverage getBeverageById(long id) {
		return beverageRepository.findById(id).get();
	}

	public Beverage addBeverage(Beverage beverage) {
		return beverageRepository.save(beverage);
	}

	public Beverage updateBeverage(Long id, Beverage beverage) {
		Beverage persistedBeverage = beverageRepository.findById(id).get();
		persistedBeverage.setDescription(beverage.getDescription());
		persistedBeverage.setCost(beverage.getCost());
		return beverageRepository.save(persistedBeverage);

	}

	public List<Beverage> getAllBeverages() {
		return beverageRepository.findAll();

	}

	public void deleteBeverage(long id) {
		beverageRepository.deleteById(id);

	}

}
