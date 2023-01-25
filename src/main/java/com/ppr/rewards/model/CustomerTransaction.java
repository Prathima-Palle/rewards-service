package com.ppr.rewards.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CustomerTransaction {
	
	private String customerId;
	private BigDecimal txnAmount;
	private LocalDateTime dateTime;
}
