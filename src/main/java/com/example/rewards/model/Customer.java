package com.example.rewards.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

  private long customerId;
  private String firstName;
  private String lastNameName;
}
  /*@Test
  public void calculateRewardsTest_transactionAbove100() {
    Customer customer = Customer.builder().customerId(1l).firstName("John").lastNameName("Doe")
        .build();
    List<Transaction> transactions = List
        .of(Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2021, 12, 23, 22, 56)).amount(123).customer(customer).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2021, 12, 01, 22, 56)).amount(101).customer(customer).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2021, 12, 31, 22, 56)).amount(100).customer(customer).build()
        );

    when(rewardsStorage.getNinetyDaysTransaction()).thenReturn(transactions);

    List<CustomerReward> customerRewards = rewardsService.calculateRewards();

    assertEquals(4, customerRewards.size());
    assertEquals(1l, customerRewards.get(0).getCustomerId());
    assertEquals("John Doe", customerRewards.get(0).getCustomerName());
    assertEquals(1, customerRewards.get(0).getRewardsByMonth().size());
    assertEquals(198, customerRewards.get(0).getRewardsByMonth().get("DECEMBER"));
    assertEquals(198, customerRewards.get(0).getTotalReward());
    verify(rewardsStorage).getNinetyDaysTransaction();
  }

  @Test
  public void calculateRewardsTest_transactionAbove50() {
    Customer customer = Customer.builder().customerId(2l).firstName("Sam").lastNameName("Smith")
        .build();
    List<Transaction> transactions = List.of(Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
        LocalDateTime.of(2022, 01, 12, 12, 12)).amount(101).customer(customer).build(),
        Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2022, 01, 12, 12, 12)).amount(99).customer(customer).build(),
        Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2022, 01, 12, 12, 12)).amount(65).customer(customer).build());
  }

  @Test
  public void calculateRewardsTest_transactionBelow50() {
    Customer customer = Customer.builder().customerId(3l).firstName("Foo").lastNameName("Bar")
        .build();
    List<Transaction> transactions = List
        .of(  Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2021, 12, 23, 22, 56)).amount(12).customer(customer).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(27).customer(customer).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(1).customer(customer).build());

  }

  @Test
  public void calculateRewardsTest_transactionMixed() {
    Customer customer = Customer.builder().customerId(4l).firstName("Fizz").lastNameName("Buzz")
        .build();
    List<Transaction> transactions = List.of(Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
        LocalDateTime.of(2022, 02, 04, 2, 34)).amount(199).customer(customer).build(),
        Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2022, 02, 04, 2, 34)).amount(50).customer(customer).build(),
        Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2022, 02, 04, 2, 34)).amount(49).customer(customer).build());
  }
*/