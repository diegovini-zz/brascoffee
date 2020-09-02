package com.brascoffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brascoffee.entity.Order;
import com.brascoffee.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long>{

	Order findByUser(User user);

}
