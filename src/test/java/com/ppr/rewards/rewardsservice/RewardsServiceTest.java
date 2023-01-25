package com.ppr.rewards.rewardsservice;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ppr.rewards.service.RewardsService;

public class RewardsServiceTest {

	private final RewardsService rewardsSvc = new RewardsService();

	@ParameterizedTest
	@MethodSource("generateTestData")
	void getRewardPoints(int txnAmount, int points) {
		int rewardPoints = rewardsSvc.calculateRewardPoints(BigDecimal.valueOf(txnAmount));
		Assertions.assertThat(rewardPoints).isEqualTo(points);
	}

	private static Stream<Arguments> generateTestData() {
		return Stream.of(Arguments.of(120, 90),
				Arguments.of(213, 276),
				Arguments.of(49, 0),
				Arguments.of(0, 0),
				Arguments.of(51, 1), 
				Arguments.of(50, 0),
				Arguments.of(101, 52)

		);
	}

}
