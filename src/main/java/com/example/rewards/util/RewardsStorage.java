package com.example.rewards.util;

import com.example.rewards.model.Customer;
import com.example.rewards.model.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class RewardsStorage {

  public List<Transaction> getNinetyDaysTransaction() {
    Customer customer1 = Customer.builder().customerId(1l).firstName("John").lastNameName("Doe")
        .build();
    Customer customer2 = Customer.builder().customerId(2l).firstName("Sam").lastNameName("Smith")
        .build();

    return List
        .of(Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2021, 12, 23, 22, 56)).amount(123).customer(customer1).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(276).customer(customer1).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(199).customer(customer2).build(),

            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2021, 12, 23, 22, 56)).amount(101).customer(customer1).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(99).customer(customer1).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(50).customer(customer2).build(),

            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2021, 12, 23, 22, 56)).amount(100).customer(customer1).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(65).customer(customer1).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(49).customer(customer2).build(),

            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2021, 12, 23, 22, 56)).amount(12).customer(customer2).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(27).customer(customer2).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(1).customer(customer2).build());
  }
}
