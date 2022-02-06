package com.example.rewards.controllor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.rewards.model.CustomerReward;
import com.example.rewards.service.RewardsService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RewardsControllerTest {

  @InjectMocks
  private RewardsController rewardsController;

  @Mock
  private RewardsService rewardsService;

  @Test
  public void calculateRewards() {
    when(rewardsService.calculateRewards()).thenReturn(Collections.singletonList(
        CustomerReward.builder().build()));

    final List<CustomerReward> customerRewards = rewardsController.calculateRewards();

    assertEquals(1, customerRewards.size());
    verify(rewardsService).calculateRewards();
  }
}