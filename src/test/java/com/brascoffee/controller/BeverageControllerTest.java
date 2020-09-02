package com.brascoffee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.brascoffee.entity.Beverage;
import com.brascoffee.security.JwtUtil;
import com.brascoffee.security.SecurityConfigurer;
import com.brascoffee.service.BeverageService;
import com.brascoffee.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = BeverageController.class)
@Import(SecurityConfigurer.class)
class BeverageControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BeverageService beverageService;

	@MockBean
	JwtUtil jwtUtil;

	@MockBean
	UserService userService;

	@Test
	public void getAllBeveragesShouldReturnHttpForbidden403WhenNotAuthenticated() throws Exception {
		mockMvc.perform(get("/beverages")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "mockUser")
	public void getAllBeveragesShouldReturnHttpOk200WhenAuthenticated() throws Exception {
		mockMvc.perform(get("/beverages")).andExpect(status().isOk());
	}

	@Test
	public void addBeverageShouldReturnHttpForbidden403WhenNotAuthenticated() throws Exception {
		Beverage beverage = new Beverage();

		mockMvc.perform(post("/beverages").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(beverage))).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "mockUser")
	public void addBeverageShouldReturnExpectedBeverageAndHttp201WhenAuthenticated() throws Exception {
		Beverage expectedBeverage = new Beverage();
		expectedBeverage.setId(1L);
		expectedBeverage.setDescription("Coffee");
		expectedBeverage.setCost(BigDecimal.valueOf(3));
		expectedBeverage.setCondiments(null);

		Mockito.when(beverageService.addBeverage(Mockito.any(Beverage.class))).thenReturn(expectedBeverage);

		Beverage postedBeverage = new Beverage("Coffee", BigDecimal.valueOf(3), null);

		mockMvc.perform(post("/beverages").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postedBeverage))).andExpect(status().isCreated())
				.andExpect(content().json(objectMapper.writeValueAsString(expectedBeverage)));
	}
	
	//Checks the business logic call. Argumentcaptor captures the beverage object that was passed to the beverageRepository and checks it
	@Test
	@WithMockUser(username = "mockUser")
	public void addBeverageShouldReturnExpectedBeverageAndHttp201WhenAuthenticatedWithCaptor() throws Exception {
		Beverage postedBeverage = new Beverage("Coffee", BigDecimal.valueOf(3), null);
		mockMvc.perform(post("/beverages").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postedBeverage)))
				.andExpect(status().isCreated());
		
		ArgumentCaptor<Beverage> beverageCaptor = ArgumentCaptor.forClass(Beverage.class);
		Mockito.verify(beverageService, Mockito.times(1)).addBeverage(beverageCaptor.capture());
		assertTrue(beverageCaptor.getValue().getDescription().equals("Coffee"));
		assertTrue(beverageCaptor.getValue().getCost().equals(BigDecimal.valueOf(3)));
	}
	
	//Checks  if http response contains a valid beverage json
	@Test
	@WithMockUser(username = "mockUser")
	void whenValidInput_thenReturnsBeveragResource() throws JsonProcessingException, Exception {
		Beverage postedBeverage = new Beverage("Coffee", BigDecimal.valueOf(3), null);
		Beverage expectedResponseBody = new Beverage("Coffee", BigDecimal.valueOf(3), null);
		expectedResponseBody.setId(1L);
		
		Mockito.when(beverageService.addBeverage(Mockito.any(Beverage.class))).thenReturn(expectedResponseBody);
		
		MvcResult mvcResult = mockMvc.perform(post("/beverages")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postedBeverage)))
				.andExpect(status().isCreated())
				.andReturn();
		
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		System.out.println(mvcResult.getResponse().getContentAsString());
		
		assertEquals(actualResponseBody,objectMapper.writeValueAsString(expectedResponseBody));
		
	}

	@Test
	@WithMockUser(username = "mockUser")
	public void addBeverageShouldReturnBadRequest400WhenBeverageDescriptionIsNull() throws Exception {
		Beverage descriptionlessBeverage = new Beverage(null, BigDecimal.ONE,null);

		mockMvc.perform(post("/beverages").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(descriptionlessBeverage))).andExpect(status().isBadRequest());

	}
	
	//checks the exception message
	@Test
	@WithMockUser(username = "mockUser")
	void whenNullValue_thenReturns400AndErrorResult() throws JsonProcessingException, Exception {
		Beverage descriptionlessBeverage = new Beverage(null, BigDecimal.ONE,null);

		MvcResult mvcResult = mockMvc.perform(post("/beverages").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(descriptionlessBeverage))).andExpect(status().isBadRequest()).andReturn();
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		Map<String,String> errors = new HashMap<>();
		errors.put("description", "The description must not be empty");
		
		assertEquals(actualResponseBody,objectMapper.writeValueAsString(errors));
	}

	@Test
	@WithMockUser(username = "mockUser")
	public void addBeverageShouldReturnBadRequest400WhenBeverageCostIsNull() throws Exception {
		Beverage costlessBeverage = new Beverage();
		costlessBeverage.setId(1L);
		costlessBeverage.setDescription("Coffee");
		costlessBeverage.setCost(null);
		costlessBeverage.setCondiments(null);

		mockMvc.perform(post("/beverages").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(costlessBeverage))).andExpect(status().isBadRequest());

	}

	@Test
	@WithMockUser(username = "mockUser")
	public void addBeverageShouldReturnHttpCreated201WhenBeverageCondimentsIsNull() throws Exception {
		Beverage condimentlessBeverage = new Beverage();
		condimentlessBeverage.setId(1L);
		condimentlessBeverage.setDescription("Coffee");
		condimentlessBeverage.setCost(BigDecimal.valueOf(2));
		condimentlessBeverage.setCondiments(null);

		Mockito.when(beverageService.addBeverage(Mockito.any(Beverage.class))).thenReturn(condimentlessBeverage);

		Beverage postedBeverage = new Beverage("Coffee", BigDecimal.valueOf(2), null);

		mockMvc.perform(post("/beverages").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postedBeverage))).andExpect(status().isCreated())
				.andExpect(content().json(objectMapper.writeValueAsString(condimentlessBeverage)));

	}
	
	@Test
	@WithMockUser(username="mockedUser")
	void whenValidInputInAddBeverage_thenReturns201() throws JsonProcessingException, Exception{
		Beverage beravege = new Beverage("Coffee",BigDecimal.ONE,null);
		
		mockMvc.perform(post("/beverages")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(beravege)))
		.andExpect(status().isCreated());
		
		
	}

}
