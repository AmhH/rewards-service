package com.example.rewards.service;

import com.example.rewards.model.Customer;
import com.example.rewards.model.CustomerReward;
import com.example.rewards.model.Transaction;
import com.example.rewards.util.RewardsStorage;
import java.time.Month;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RewardsService {

  private final RewardsStorage rewardsStorage;

  //Assumes per month calculation is based on calender month not 30 day month cycle starting current server time
  public List<CustomerReward> calculateRewards() {
    List<Transaction> transactions = rewardsStorage.getNinetyDaysTransaction();
    final Map<Long, String> customerIdToNameMap = transactions.stream()
        .map(Transaction::getCustomer)
        .distinct()
        .collect(Collectors.toMap(Customer::getCustomerId,
            customer -> customer.getFirstName() + " " + customer.getLastNameName()));

    final Map<Long, Map<Month, List<Transaction>>> mapTxnByCustomerByMonth = transactions.stream()
        //Map transaction by customer id, list of transaction
        .collect(Collectors.groupingBy(txn -> txn.getCustomer().getCustomerId(),
            //Map transaction of each customer by month, list of transaction
            Collectors.groupingBy(txn -> txn.getTransactionDate().getMonth())));

    final List<CustomerReward> customerRewards = new LinkedList<>();
    mapTxnByCustomerByMonth.forEach((customerId, txnByMonth) -> {
      Map<String, Long> rewardByMonth = txnByMonth.entrySet()
          .stream()
          .collect(Collectors
              .toMap(entry -> entry.getKey().toString(),
                  entry -> calculateReward(entry.getValue())));
      CustomerReward reward = CustomerReward.builder()
          .customerId(customerId)
          .customerName(customerIdToNameMap.get(customerId))
          .rewardsByMonth(rewardByMonth)
          .totalReward(calculateTotalReward(rewardByMonth.values()))
          .build();
      customerRewards.add(reward);
    });

    return customerRewards;
  }

  private long calculateTotalReward(Collection<Long> rewardPerMonth) {
    return rewardPerMonth.stream()
        .reduce(0l, (tx1, tx2) -> tx1 + tx2);
  }

  private long calculateReward(List<Transaction> transactions) {
    return transactions.stream()
        .map(Transaction::getAmount)
        .filter(Objects::nonNull)
        .map(txnAmount -> getRewardsForTransaction(txnAmount))
        .reduce(0l, (txn1, txn2) -> txn1 + txn2);
  }

  private long getRewardsForTransaction(int txnAmount) {
    int aboveFifty = 0 > (txnAmount - 50) ? 0 : txnAmount - 50;
    int aboveHundred = 0 > (txnAmount - 100) ? 0 : txnAmount - 100;

    return (2 * aboveHundred) + (aboveHundred > 0 ? 50 : aboveFifty);
  }
}
