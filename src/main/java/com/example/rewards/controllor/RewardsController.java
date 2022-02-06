package com.example.rewards.controllor;

import com.example.rewards.model.CustomerReward;
import com.example.rewards.service.RewardsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardsController {

  private final RewardsService rewardsService;

  @GetMapping
  public List<CustomerReward> calculateRewards(){
    log.info("Started calculating rewards for three months");
    return rewardsService.calculateRewards();
  }

}
