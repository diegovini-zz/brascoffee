package com.brascoffee.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.brascoffee.entity.Beverage;
import com.brascoffee.repository.BeverageRepository;

class BeverageServiceUnitTest {
	
	private BeverageRepository beverageRepository = Mockito.mock(BeverageRepository.class);
	private BeverageService beverageService;
	
	@BeforeEach
		void initTest() {
			beverageService = new BeverageService(beverageRepository);
		}
	

	@Test
	void addedBeverageHasId() {
		Beverage beverage = new Beverage("Coffee", BigDecimal.ONE,null);
		Mockito.when(beverageRepository.save(Mockito.any(Beverage.class))).then(Mockito.RETURNS_MOCKS);
		Beverage savedBeverage = beverageService.addBeverage(beverage);
		assertNotNull(savedBeverage.getId());
	}
	
	@Test
	void addedBeverageCanHaveNullCondiments() {
		Beverage beverage = new Beverage("Coffee", BigDecimal.ONE,null);
		Mockito.when(beverageRepository.save(Mockito.any(Beverage.class))).then(Mockito.RETURNS_MOCKS);
		Beverage savedBeverage = beverageService.addBeverage(beverage);
		assertTrue(savedBeverage.getCondiments().isEmpty());
	}

}
