package com.brascoffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brascoffee.entity.Condiment;
import com.brascoffee.repository.CondimentRepository;

@Service
public class CondimentService {
	@Autowired
	CondimentRepository condimentRepository;
	
	public Condiment addCondiment(Condiment condiment) {
		return condimentRepository.save(condiment);
	}

	public List<Condiment> getCondiments() {
		// TODO Auto-generated method stub
		return condimentRepository.findAll();
	}

}
