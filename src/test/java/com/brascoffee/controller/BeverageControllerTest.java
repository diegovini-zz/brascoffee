package com.brascoffee.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.brascoffee.entity.Beverage;
import com.brascoffee.security.JwtUtil;
import com.brascoffee.security.SecurityConfigurer;
import com.brascoffee.service.BeverageService;
import com.brascoffee.service.UserService;
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

	@Test
	@WithMockUser(username = "mockUser")
	public void addBeverageShouldReturnBadRequest401WhenBeverageDescriptionIsNull() throws Exception {
		Beverage descriptionlessBeverage = new Beverage();
		descriptionlessBeverage.setId(1L);
		descriptionlessBeverage.setDescription(null);
		descriptionlessBeverage.setCost(BigDecimal.valueOf(3));
		descriptionlessBeverage.setCondiments(null);

		mockMvc.perform(post("/beverages").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(descriptionlessBeverage))).andExpect(status().isBadRequest());

	}

	@Test
	@WithMockUser(username = "mockUser")
	public void addBeverageShouldReturnBadRequest401WhenBeverageCostIsNull() throws Exception {
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

}
