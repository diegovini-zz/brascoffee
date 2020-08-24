package com.brascoffee.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.brascoffee.abst.AbstractBeverage;
import com.brascoffee.abst.AbstractCondimentDecorator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CONDIMENT")
public class Condiment extends AbstractCondimentDecorator {
	@Transient
	@JsonIgnore
	private AbstractBeverage beverage;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description")
	@NotNull(message="The description must not be empty")
	private String description;
	
	@NotNull(message="The cost must not be empty")
	@Column(name = "cost")
	private BigDecimal cost;
	
	public Condiment() {
		
	}

	public Condiment(String description, BigDecimal cost) {
		this.description = description;
		this.cost = cost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public BigDecimal cost() {
		// TODO Auto-generated method stub
		return this.getCost().add(beverage.cost());
	}

	public AbstractBeverage getBeverage() {
		return beverage;
	}

	public void setBeverage(AbstractBeverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return beverage.getDescription() + "+" + description;
	}



}
