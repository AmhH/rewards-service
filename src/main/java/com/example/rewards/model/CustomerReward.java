package com.example.rewards.model;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerReward {

  private long customerId;
  private String customerName;
  private Map<String, Long> rewardsByMonth;
  private long totalReward;
}
