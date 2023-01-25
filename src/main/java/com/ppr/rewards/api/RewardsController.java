package com.ppr.rewards.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ppr.rewards.model.CustomerTransaction;
import com.ppr.rewards.model.RetailOffer;
import com.ppr.rewards.service.RewardsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RewardsController {

	@Autowired
	private RewardsService rewardsSvc;

	@PostMapping("/rewards")
	public ResponseEntity<RetailOffer> calculateRewardPoints(@RequestBody List<CustomerTransaction> transactions) {
		
		log.info("Transactions size:" + transactions.size());
		RetailOffer offerInfo = rewardsSvc.calculateRewards(transactions);
		log.info("Response:" + offerInfo);
		return ResponseEntity.status(HttpStatus.OK).body(offerInfo);
	}

}
