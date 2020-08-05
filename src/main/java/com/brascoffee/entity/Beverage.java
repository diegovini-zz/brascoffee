package com.brascoffee.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.brascoffee.abst.AbstractBeverage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "BEVERAGE")
public class Beverage extends AbstractBeverage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "description")
	private String description;
	@Column(name = "cost")
	private BigDecimal cost;
	
	
	
	@Transient
	private List<Condiment>condiments;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    @JsonInclude(Include.NON_NULL)
	public List<Condiment> getCondiments() {
		return condiments;
	}

	public void setCondiments(List<Condiment> condiments) {
		this.condiments = condiments;
	}

	@Override
	public BigDecimal cost() {
		// TODO Auto-generated method stub
		return this.getCost();
	}

}
