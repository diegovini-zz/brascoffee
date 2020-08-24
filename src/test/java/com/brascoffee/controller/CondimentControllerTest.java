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

import com.brascoffee.entity.Condiment;
import com.brascoffee.security.JwtUtil;
import com.brascoffee.security.SecurityConfigurer;
import com.brascoffee.service.CondimentService;
import com.brascoffee.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = CondimentController.class)
@Import(SecurityConfigurer.class)
class CondimentControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	CondimentService condimentService;

	@MockBean
	JwtUtil jwtUtil;

	@MockBean
	UserService userService;

	@Test
	public void getCondimentsShouldReturnHttpForbiddenWhenNotAuthenticated() throws Exception {
		mockMvc.perform(get("/condiments")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "MockUser")
	public void getCondimentsShouldReturnHttpOk200WhenAuthenticated() throws Exception {
		mockMvc.perform(get("/condiments")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "MockUser")
	public void addCondimentShouldReturnHttpCreated201WhenCondimentIsCreated() throws Exception {
		Condiment expectedCondiment = new Condiment();
			expectedCondiment.setId(1L);
			expectedCondiment.setCost(BigDecimal.valueOf(2));
			expectedCondiment.setDescription("Milk");
			
			Mockito.when(condimentService.addCondiment(Mockito.any(Condiment.class))).thenReturn(expectedCondiment);
			
			Condiment postedCondiment = new Condiment();
			postedCondiment.setCost(BigDecimal.valueOf(2));
			postedCondiment.setDescription("Milk");
			
			mockMvc.perform(post("/condiments").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(postedCondiment))).andExpect(status().isCreated())
			.andExpect(content().json(objectMapper.writeValueAsString(expectedCondiment)));
	}
	
	@Test
	@WithMockUser(username = "MockUser")
	public void addCOndimentShouldReturnHttpBadRequestWhenCondimentCostIsNull() throws Exception {
		Condiment postedCondiment = new Condiment();
		postedCondiment.setDescription("Milk");
		
		mockMvc.perform(post("/condiments").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).
		andExpect(status().isBadRequest());
		
	}
	
	@Test
	@WithMockUser(username="MockUser")
	public void addCondimentShouldReturnHttpBadRequestWhenCondimentDescriptionIsNull() throws Exception{
		Condiment postedCondiment = new Condiment();
		postedCondiment.setCost(BigDecimal.valueOf(2));
		
		mockMvc.perform(post("/condiments").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).
		andExpect(status().isBadRequest());
		
	}

}
