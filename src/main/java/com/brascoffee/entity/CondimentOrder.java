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

@Entity
@Table(name = "CONDIMENT_ORDER")
public class CondimentOrder {
	
	
	public CondimentOrder() {
		
	}
	
	public CondimentOrder(BeverageOrder beverageOrder) {
		
		this.beverageOrder = beverageOrder;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@JoinColumn(name = "condiment_id",referencedColumnName = "id")
	
	private Condiment condiment;

	@ManyToOne(fetch =FetchType.LAZY )
	@JoinColumn(name = "beverageorder_id")
	@JsonBackReference
	private BeverageOrder beverageOrder;

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

	public BeverageOrder getBeverageOrder() {
		return beverageOrder;
	}

	public void setBeverageOrder(BeverageOrder beverageOrder) {
		this.beverageOrder = beverageOrder;
	};

}
