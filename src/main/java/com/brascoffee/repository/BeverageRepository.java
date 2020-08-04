package com.brascoffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brascoffee.entity.Beverage;


@Repository
public interface BeverageRepository extends JpaRepository<Beverage, Long>{

}
