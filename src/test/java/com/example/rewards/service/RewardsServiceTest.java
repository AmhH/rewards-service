package com.example.rewards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.rewards.model.Customer;
import com.example.rewards.model.CustomerReward;
import com.example.rewards.model.Transaction;
import com.example.rewards.util.RewardsStorage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {

  @InjectMocks
  private RewardsService rewardsService;

  @Mock
  private RewardsStorage rewardsStorage;
  @Test
  public void calculateRewardsTest_transactionAboveHundred() {
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

    assertEquals(1, customerRewards.size());
    assertEquals(1l, customerRewards.get(0).getCustomerId());
    assertEquals("John Doe", customerRewards.get(0).getCustomerName());
    assertEquals(1, customerRewards.get(0).getRewardsByMonth().size());
    assertEquals(198, customerRewards.get(0).getRewardsByMonth().get("DECEMBER"));
    assertEquals(198, customerRewards.get(0).getTotalReward());
    verify(rewardsStorage).getNinetyDaysTransaction();
  }

  @Test
  public void calculateRewardsTest_transactionAboveFifty() {
    Customer customer = Customer.builder().customerId(2l).firstName("Sam").lastNameName("Smith")
        .build();
    List<Transaction> transactions = List.of(Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
        LocalDateTime.of(2022, 01, 12, 12, 12)).amount(101).customer(customer).build(),
        Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2022, 01, 12, 12, 12)).amount(99).customer(customer).build(),
        Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2022, 01, 12, 12, 12)).amount(65).customer(customer).build());

    when(rewardsStorage.getNinetyDaysTransaction()).thenReturn(transactions);

    List<CustomerReward> customerRewards = rewardsService.calculateRewards();

    assertEquals(1, customerRewards.size());
    assertEquals(2l, customerRewards.get(0).getCustomerId());
    assertEquals("Sam Smith", customerRewards.get(0).getCustomerName());
    assertEquals(1, customerRewards.get(0).getRewardsByMonth().size());
    assertEquals(116, customerRewards.get(0).getRewardsByMonth().get("JANUARY"));
    assertEquals(116, customerRewards.get(0).getTotalReward());
    verify(rewardsStorage).getNinetyDaysTransaction();
  }

  @Test
  public void calculateRewardsTest_transactionBelowFifty() {
    Customer customer = Customer.builder().customerId(3l).firstName("Foo").lastNameName("Bar")
        .build();
    List<Transaction> transactions = List
        .of(  Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2021, 12, 23, 22, 56)).amount(12).customer(customer).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(27).customer(customer).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(1).customer(customer).build());

    when(rewardsStorage.getNinetyDaysTransaction()).thenReturn(transactions);

    List<CustomerReward> customerRewards = rewardsService.calculateRewards();

    assertEquals(1, customerRewards.size());
    assertEquals(3l, customerRewards.get(0).getCustomerId());
    assertEquals("Foo Bar", customerRewards.get(0).getCustomerName());
    assertEquals(3, customerRewards.get(0).getRewardsByMonth().size());
    assertEquals(0, customerRewards.get(0).getRewardsByMonth().get("DECEMBER"));
    assertEquals(0, customerRewards.get(0).getRewardsByMonth().get("JANUARY"));
    assertEquals(0, customerRewards.get(0).getRewardsByMonth().get("FEBRUARY"));
    assertEquals(0, customerRewards.get(0).getTotalReward());
    verify(rewardsStorage).getNinetyDaysTransaction();
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
    when(rewardsStorage.getNinetyDaysTransaction()).thenReturn(transactions);

    List<CustomerReward> customerRewards = rewardsService.calculateRewards();

    assertEquals(1, customerRewards.size());
    assertEquals(4l, customerRewards.get(0).getCustomerId());
    assertEquals("Fizz Buzz", customerRewards.get(0).getCustomerName());
    assertEquals(1, customerRewards.get(0).getRewardsByMonth().size());
    assertEquals(248, customerRewards.get(0).getRewardsByMonth().get("FEBRUARY"));
    assertEquals(248, customerRewards.get(0).getTotalReward());
    verify(rewardsStorage).getNinetyDaysTransaction();
  }

  @Test
  public void calculateRewardsTest_whenCustomerHasUniqueNames() {
    Customer customer1 = Customer.builder().customerId(1l).firstName("John").lastNameName("Doe")
        .build();
    Customer customer2 = Customer.builder().customerId(2l).firstName("Sam").lastNameName("Smith")
        .build();
    Customer customer3 = Customer.builder().customerId(3l).firstName("Foo").lastNameName("Bar")
        .build();
    Customer customer4 = Customer.builder().customerId(4l).firstName("Fizz").lastNameName("Buzz")
        .build();

    List<Transaction> transactions = getTransactions(customer1,
        customer2, customer3, customer4);

    when(rewardsStorage.getNinetyDaysTransaction()).thenReturn(transactions);

    List<CustomerReward> customerRewards = rewardsService.calculateRewards();

    assertEquals(4, customerRewards.size());
    assertEquals(1l, customerRewards.get(0).getCustomerId());
    assertEquals(2l, customerRewards.get(1).getCustomerId());
    assertEquals(3l, customerRewards.get(2).getCustomerId());
    assertEquals(4l, customerRewards.get(3).getCustomerId());

    assertEquals(198, customerRewards.get(0).getTotalReward());
    assertEquals(466, customerRewards.get(1).getTotalReward());
    assertEquals(248, customerRewards.get(2).getTotalReward());
    assertEquals(0l, customerRewards.get(3).getTotalReward());

    verify(rewardsStorage).getNinetyDaysTransaction();
  }

  @Test
  public void calculateRewardsTest_multipleMonth() {
    Customer customer1 = Customer.builder().customerId(1l).firstName("John").lastNameName("Doe")
        .build();

    List<Transaction> transactions = getTransactions(customer1,
        customer1, customer1, customer1);

    when(rewardsStorage.getNinetyDaysTransaction()).thenReturn(transactions);

    List<CustomerReward> customerRewards = rewardsService.calculateRewards();

    assertEquals(1, customerRewards.size());
    assertEquals(1l, customerRewards.get(0).getCustomerId());
    assertEquals("John Doe", customerRewards.get(0).getCustomerName());
    assertEquals(3, customerRewards.get(0).getRewardsByMonth().size());
    assertEquals(198, customerRewards.get(0).getRewardsByMonth().get("DECEMBER"));
    assertEquals(466, customerRewards.get(0).getRewardsByMonth().get("JANUARY"));
    assertEquals(248, customerRewards.get(0).getRewardsByMonth().get("FEBRUARY"));
    assertEquals(912, customerRewards.get(0).getTotalReward());

    verify(rewardsStorage).getNinetyDaysTransaction();
  }

  @Test
  public void calculateRewardsTest_multipleID() {
    Customer customer1 = Customer.builder().customerId(1l).firstName("John").lastNameName("Doe")
        .build();
    Customer customer2 = Customer.builder().customerId(2l).firstName("John").lastNameName("Doe")
        .build();

    List<Transaction> transactions = getTransactions(customer1,
        customer1, customer2, customer2);

    when(rewardsStorage.getNinetyDaysTransaction()).thenReturn(transactions);

    List<CustomerReward> customerRewards = rewardsService.calculateRewards();

    assertEquals(2, customerRewards.size());
    assertEquals(1l, customerRewards.get(0).getCustomerId());
    assertEquals(2l, customerRewards.get(1).getCustomerId());
    assertEquals("John Doe", customerRewards.get(0).getCustomerName());
    assertEquals("John Doe", customerRewards.get(1).getCustomerName());

    assertEquals(2, customerRewards.get(0).getRewardsByMonth().size());
    assertEquals(3, customerRewards.get(1).getRewardsByMonth().size());

    assertEquals(198, customerRewards.get(0).getRewardsByMonth().get("DECEMBER"));
    assertEquals(466, customerRewards.get(0).getRewardsByMonth().get("JANUARY"));
    assertEquals(0, customerRewards.get(1).getRewardsByMonth().get("DECEMBER"));
    assertEquals(0, customerRewards.get(1).getRewardsByMonth().get("JANUARY"));
    assertEquals(248, customerRewards.get(1).getRewardsByMonth().get("FEBRUARY"));

    assertEquals(664, customerRewards.get(0).getTotalReward());
    assertEquals(248, customerRewards.get(1).getTotalReward());

    verify(rewardsStorage).getNinetyDaysTransaction();
  }

  private List<Transaction> getTransactions(Customer customer1, Customer customer2,
      Customer customer3, Customer customer4) {
    List<Transaction> transactions = List
        .of(Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
            LocalDateTime.of(2021, 12, 23, 22, 56)).amount(123).customer(customer1).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(276).customer(customer2).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(199).customer(customer3).build(),

            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2021, 12, 23, 22, 56)).amount(101).customer(customer1).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(99).customer(customer2).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(50).customer(customer3).build(),

            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2021, 12, 23, 22, 56)).amount(100).customer(customer1).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(65).customer(customer2).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(49).customer(customer3).build(),

            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2021, 12, 23, 22, 56)).amount(12).customer(customer4).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 01, 12, 12, 12)).amount(27).customer(customer4).build(),
            Transaction.builder().transactionId(UUID.randomUUID()).transactionDate(
                LocalDateTime.of(2022, 02, 04, 2, 34)).amount(1).customer(customer4).build());
    return transactions;
  }
}