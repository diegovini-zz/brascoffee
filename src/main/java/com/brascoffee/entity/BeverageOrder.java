package com.brascoffee.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "BEVERAGE_ORDER")
public class BeverageOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@OneToOne
	@JoinColumn(name = "beverage_id",referencedColumnName = "id")
	private Beverage orderedBeverage;
	
	@OneToMany(mappedBy = "beverageOrder",cascade = CascadeType.ALL)
	private List<CondimentOrder> orderedCondiments;
	
	@Column(name="final_price")
	private BigDecimal finalPrice;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Beverage getOrderedBeverage() {
		return orderedBeverage;
	}

	public void setOrderedBeverage(Beverage orderedBeverage) {
		this.orderedBeverage = orderedBeverage;
	}

	public List<CondimentOrder> getOrderedCondiments() {
		return orderedCondiments;
	}

	public void setOrderedCondiments(List<CondimentOrder> orderedCondiments) {
		this.orderedCondiments = orderedCondiments;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	
	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}





}
