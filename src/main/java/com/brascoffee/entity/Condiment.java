package com.brascoffee.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.brascoffee.abst.AbstractBeverage;
import com.brascoffee.abst.AbstractCondimentDecorator;

@Entity
@Table(name = "CONDIMENT")
public class Condiment extends AbstractCondimentDecorator {
	@Transient
	private AbstractBeverage beverage;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "description")
	private String description;
	@Column(name = "cost")
	private BigDecimal cost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return beverage.getDescription() + " + " + description;
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

}
