package com.brascoffee.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.brascoffee.entity.Beverage;
import com.brascoffee.entity.Condiment;
import com.brascoffee.entity.Order;
import com.brascoffee.entity.OrderCondiment;
import com.brascoffee.security.JwtUtil;
import com.brascoffee.service.OrderService;
import com.brascoffee.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = OrderController.class)

class OrderControllerTest {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	OrderService orderService;

	@MockBean
	JwtUtil jwtUtil;

	@MockBean
	UserService userService;

	@Test
	public void getOrdersShouldReturnHttpForbidden403WhenNotAuthenticated() throws Exception {
		mockMvc.perform(get(("/orders"))).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "MockedUser")
	public void getOrdersShouldReturnHttpOk200WhenNotAuthenticated() throws Exception {
		mockMvc.perform(get(("/orders"))).andExpect(status().isOk());
	}

	@Test
	public void placeOrderShouldReturnHttpForbidden403WhenNotAuthenticated() throws Exception {
		Order postedOrder = new Order();
		postedOrder.setBeverage(new Beverage("Coffee", BigDecimal.valueOf(2), null));
		postedOrder.setCondiments(null);

		mockMvc.perform(post(("/orders")).accept(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "Mockedused")
	public void placeOrderShouldReturnHttpCreated201WhenAuthenticated() throws Exception {
		List<Order> postedOrderList = new ArrayList<Order>();
		
		Order order = new Order();
		OrderCondiment orderCondiment = new OrderCondiment();
		Beverage beverage = new Beverage("Coffee",BigDecimal.valueOf(3),null);
		Condiment condiment = new Condiment("Condiment",BigDecimal.valueOf(2));
		beverage.setId(1L);
				
		orderCondiment.setBeverageOrder(order);
		orderCondiment.setCondiment(condiment);
		
		List<OrderCondiment> orderedCondimentsList = new ArrayList<OrderCondiment>();
		orderedCondimentsList .add(orderCondiment);
		
		order.setBeverage(beverage);
		order.setCondiments(orderedCondimentsList );
		
		postedOrderList.add(order);

		mockMvc.perform(post(("/orders"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postedOrderList)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@WithMockUser(username = "Mockedused")
	public void placeOrderShouldReturnHttpBadRequest400WhenBeverageIsNullAndWhenAuthenticated() throws Exception {
		List<Order> postedOrderList = new ArrayList<Order>();
		
		Order order = new Order();
		OrderCondiment orderCondiment = new OrderCondiment();
		Beverage beverage = null;
		Condiment condiment = new Condiment("Condiment",BigDecimal.valueOf(2));
				
		orderCondiment.setBeverageOrder(order);
		orderCondiment.setCondiment(condiment);
		
		List<OrderCondiment> orderedCondimentsList = new ArrayList<OrderCondiment>();
		orderedCondimentsList .add(orderCondiment);
		
		order.setBeverage(beverage);
		order.setCondiments(orderedCondimentsList );
		
		postedOrderList.add(order);

		mockMvc.perform(post(("/orders"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postedOrderList)))
				.andExpect(status().isBadRequest());
	}

}
