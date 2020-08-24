package com.brascoffee.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@Table(name = "ORDERS_CONDIMENT")
public class OrderCondiment {
	
	
	public OrderCondiment() {
		
	}
	
	public OrderCondiment(Order beverageOrder, Condiment condiment) {
		
		this.beverageOrder = beverageOrder;
		this.condiment = condiment;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@JoinColumn(name = "condiment_id",referencedColumnName = "id")
	@JsonProperty(value = "")
	private Condiment condiment;

	@ManyToOne(fetch =FetchType.LAZY )
	@JoinColumn(name = "order_id")
	@JsonBackReference
	private Order beverageOrder;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Condiment getCondiment() {
		return condiment;
	}

	public void setCondiment(Condiment condiment) {
		this.condiment = condiment;
	}

	public Order getBeverageOrder() {
		return beverageOrder;
	}

	public void setBeverageOrder(Order beverageOrder) {
		this.beverageOrder = beverageOrder;
	};

}
