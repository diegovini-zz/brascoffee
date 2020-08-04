package com.brascoffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brascoffee.entity.Condiment;

public interface CondimentRepository extends JpaRepository<Condiment, Long>{

}
