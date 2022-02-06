package com.example.rewards.util;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RewardsStorageTest {

  @Test
  public void getNinetyDaysTransactionTest(){
    assertEquals(12, new RewardsStorage().getNinetyDaysTransaction().size());
  }
}