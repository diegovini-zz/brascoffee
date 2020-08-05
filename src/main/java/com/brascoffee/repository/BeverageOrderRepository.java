package com.brascoffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brascoffee.entity.BeverageOrder;

public interface BeverageOrderRepository extends JpaRepository<BeverageOrder, Long>{

}
