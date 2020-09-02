package com.brascoffee.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.brascoffee.entity.Condiment;
import com.brascoffee.repository.CondimentRepository;

@ExtendWith(MockitoExtension.class)
class CondimentServiceUnitTest {
//	private CondimentRepository condimentRepository = Mockito.mock(CondimentRepository.class); 
//	private CondimentService condimentService;


	
//	@BeforeEach
//	void initTest() {
//		condimentService = new CondimentService(condimentRepository);
//	}
//	
//	@Test
//	void savedCondimentHadId() {
//		Condiment condiment = new Condiment("Milk",BigDecimal.TEN);
//		Mockito.when(condimentRepository.save(Mockito.any(Condiment.class))).then(Mockito.RETURNS_MOCKS);
//		Condiment savedCondiment = condimentService.addCondiment(condiment);
//		assertNotNull(savedCondiment.getId());
//	}
	
	//another way to do the same test
	
	
	@Mock
	private CondimentRepository mockedCondimentRepository;
	
	@InjectMocks
	private CondimentService injectMockedCondimentService;
	
	@Test
	void savedCondimentHadIdTest2() {
		Condiment condiment = new Condiment("Milk",BigDecimal.TEN);
		Mockito.when(mockedCondimentRepository.save(Mockito.any(Condiment.class))).then(Mockito.RETURNS_MOCKS);
		Condiment savedCondiment = injectMockedCondimentService.addCondiment(condiment);
		assertNotNull(savedCondiment.getId());
	}

}
