package com.brascoffee.abst;

import java.math.BigDecimal;

public abstract class AbstractBeverage {
	String description;

	public String getDescription() {
		return description;
	}

	public abstract BigDecimal cost();

}
