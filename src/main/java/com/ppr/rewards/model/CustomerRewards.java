package com.ppr.rewards.model;

import java.util.List;

import lombok.Data;

@Data
public class CustomerRewards {

	private String customerId;
	private int totalRewardPoints;
	private List<Rewards> monthWiseRewards;

}
