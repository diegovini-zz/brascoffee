package com.brascoffee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.brascoffee.entity.Condiment;
import com.brascoffee.repository.CondimentRepository;

@Service
public class CondimentService {
	
	private final CondimentRepository condimentRepository;
	
	public CondimentService(CondimentRepository condimentRepository) {
		this.condimentRepository = condimentRepository;
	}
	
	public Condiment addCondiment(Condiment condiment) {
		return condimentRepository.save(condiment);
	}

	public List<Condiment> getCondiments() {
		// TODO Auto-generated method stub
		return condimentRepository.findAll();
	}

	public void deleteCondiment(long id) {
		condimentRepository.deleteById(id);;
	}

	public Condiment getCondimentById(long id) {
		return condimentRepository.findById(id).get()	;
	}

	public Condiment updateCondiment(long id, Condiment condiment) {
//		Condiment persistedCondiment = getCondimentById(id);
//		persistedCondiment.setDescription(condiment.getDescription());
//		persistedCondiment.setCost(condiment.getCost());
//		return condimentRepository.save(persistedCondiment);
		
		return condimentRepository.findById(id).map(persistedCondiment->{
			persistedCondiment.setDescription(condiment.getDescription());
			persistedCondiment.setCost(condiment.getCost());
			return condimentRepository.save(persistedCondiment);
		}).get();
	}

}
