package com.ppr.rewards.service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ppr.rewards.model.CustomerRewards;
import com.ppr.rewards.model.CustomerTransaction;
import com.ppr.rewards.model.RetailOffer;
import com.ppr.rewards.model.Rewards;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class RewardsService {

	public RetailOffer calculateRewards(List<CustomerTransaction> transactions) {
		RetailOffer offerInfo = new RetailOffer();
		Map<String, List<CustomerTransaction>> txnsPerCustomer = transactions.stream()
				.collect(Collectors.groupingBy(CustomerTransaction::getCustomerId));

		List<CustomerRewards> custRewards = txnsPerCustomer.entrySet().stream().map(customerEntry -> {
			String customerId = customerEntry.getKey();
			List<CustomerTransaction> customerTransactions = customerEntry.getValue();
			Map<Month, List<CustomerTransaction>> customerMonthlyTxns = customerTransactions.stream()
					.collect(Collectors.groupingBy(transaction -> transaction.getDateTime().getMonth()));
			List<Rewards> custMonthlyRewards = customerMonthlyTxns.entrySet().stream().map(monthWiseEntry -> {
				Month month = monthWiseEntry.getKey();
				List<CustomerTransaction> monthlyTransactions = monthWiseEntry.getValue();
				int monthlyPoints = monthlyTransactions.stream()
						.mapToInt(transaction -> calculateRewardPoints(transaction.getTxnAmount())).sum();
				Rewards rewardsData = new Rewards();
				rewardsData.setRewardPoints(monthlyPoints);
				rewardsData.setMonth(month);
				return rewardsData;
			}).collect(Collectors.toList());
			CustomerRewards custData = new CustomerRewards();
			custData.setCustomerId(customerId);
			custData.setMonthWiseRewards(custMonthlyRewards);
			custData.setTotalRewardPoints(custMonthlyRewards.stream().mapToInt(Rewards::getRewardPoints).sum());
			return custData;
		}).collect(Collectors.toList());
		offerInfo.setOfferDetails(custRewards);
		return offerInfo;

	}

	public int calculateRewardPoints(BigDecimal amount) {
		int rewPoints = 0;
		int txnAmount = amount.intValue();
		rewPoints = rewPoints + calculatePointsAbove50(txnAmount);
		rewPoints = rewPoints + calculatePointsAbove100(txnAmount);
		log.info("Total reward points for txn Amt "+ amount + "is::"+rewPoints);
		return rewPoints;
	}

	private int calculatePointsAbove50(int txnAmount) {
		if (txnAmount <= 50) {
			return 0;
		} else if (txnAmount < 100) {
			return txnAmount - 50;
		} else {
			return 50;
		}
	}

	private int calculatePointsAbove100(int txnAmount) {
		int amountAbove100 = txnAmount > 100 ? (txnAmount - 100) : 0;
		return 2 * amountAbove100;
	}

}
