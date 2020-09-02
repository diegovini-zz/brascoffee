package com.brascoffee.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.brascoffee.entity.Condiment;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CondimentRepositoryTest {
	
	@Autowired
	CondimentRepository condimentRepository;

	@Test
	public void createShouldPersist() {
		Condiment createdCondiment = new Condiment("Milk",BigDecimal.valueOf(2));
		Condiment persistedCondiment = condimentRepository.save(createdCondiment);
		assertEquals(persistedCondiment, createdCondiment );
	}
	
	@Test
	public void updateShouldUpdateData() {
		Condiment createdCondiment = new Condiment("Milk",BigDecimal.valueOf(2));
		condimentRepository.save(createdCondiment);
		createdCondiment.setDescription("Chocolate");
		createdCondiment = condimentRepository.save(createdCondiment);
		
		assertEquals(createdCondiment.getDescription(), "Chocolate");
	}
	
	@Test 
	public void createWhenDescriptionIsNullShouldThrowConstraintViolationException() {
		Condiment createdCondiment = new Condiment(null,BigDecimal.valueOf(2));
		assertThrows(ConstraintViolationException.class, () -> condimentRepository.save(createdCondiment));
	}
	
	@Test 
	public void createWhenCostIsNullShouldThrowConstraintViolationException() {
		Condiment createdCondiment = new Condiment("Milk",null);
		assertThrows(ConstraintViolationException.class, () -> condimentRepository.save(createdCondiment));
	}

}
