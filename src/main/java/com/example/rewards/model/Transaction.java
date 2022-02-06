package com.example.rewards.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {

  private UUID transactionId;
  private int amount; //took it as integer because the requirement doesn't specify what to do or how to handle cents
  private LocalDateTime transactionDate;
  private Customer customer;
}
