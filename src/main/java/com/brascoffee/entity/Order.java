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

@Entity
@Table(name = "ORDERS")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@OneToOne
	@JoinColumn(name = "beverage_id",referencedColumnName = "id")
	private Beverage beverage;
	
	@OneToMany(mappedBy = "beverageOrder",cascade = CascadeType.ALL)
	private List<OrderCondiment> condiments;
	
	@Column(name="final_price")
	private BigDecimal finalPrice;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Beverage getBeverage() {
		return beverage;
	}

	public void setBeverage(Beverage beverage) {
		this.beverage = beverage;
	}

	public List<OrderCondiment> getCondiments() {
		return condiments;
	}

	public void setCondiments(List<OrderCondiment> orderedCondiments) {
		this.condiments = orderedCondiments;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	
	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}





}
