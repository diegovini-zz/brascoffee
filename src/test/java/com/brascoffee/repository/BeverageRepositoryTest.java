package com.brascoffee.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.brascoffee.entity.Beverage;
import com.brascoffee.entity.Condiment;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BeverageRepositoryTest {

	@Autowired
	private BeverageRepository beverageRepository;

	@Test
	public void createShouldPersistData() {
		Beverage beverage = new Beverage("Café", BigDecimal.valueOf(2),
				Arrays.asList(new Condiment(), new Condiment()));

		Beverage createdBeverave = this.beverageRepository.save(beverage);

		assertEquals(createdBeverave, beverage);
	}

	@Test
	public void updateShouldUpdateData() {
		Beverage beverage = new Beverage("Cappuccino", BigDecimal.valueOf(3), Arrays.asList(new Condiment()));
		this.beverageRepository.save(beverage);
		Beverage persistedBeverage = beverageRepository.findById(beverage.getId()).get();
		
		persistedBeverage.setCost(BigDecimal.valueOf(5));
		this.beverageRepository.save(persistedBeverage);

		assertEquals(beverage.getCost(), BigDecimal.valueOf(5));
	}

	@Test
	public void deleteShouldDeleteData() {
		Beverage beverage = new Beverage("Café", BigDecimal.valueOf(2),
				Arrays.asList(new Condiment(), new Condiment()));
		this.beverageRepository.save(beverage);
		this.beverageRepository.delete(beverage);

		assertThrows(NoSuchElementException.class, () -> beverageRepository.findById(beverage.getId()).get());

	}
	
	@Test
	public void createWhenDescriptionisNullShouldThrowConstraintViolationException() {
		Beverage beverage = new Beverage(null, BigDecimal.ONE,Arrays.asList(new Condiment()));
		assertThrows(ConstraintViolationException.class, () -> beverageRepository.save(beverage));
	}
	
	@Test
	public void createWhenPricesNullShouldThrowConstraintViolationException() {
		Beverage beverage = new Beverage("Coffee", null ,Arrays.asList(new Condiment()));
		assertThrows(ConstraintViolationException.class, () -> beverageRepository.save(beverage));
	}

}
