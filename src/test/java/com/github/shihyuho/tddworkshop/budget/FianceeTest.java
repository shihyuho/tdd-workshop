package com.github.shihyuho.tddworkshop.budget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FianceeTest {

  IBudgetRepo repo;
  Finance finance;

  @BeforeEach
  public void setup() {
    repo = mock(IBudgetRepo.class);
    finance = new Finance(repo);
  }

  @Test
  void invalid_period() {
    when(repo.getAll()).thenReturn(List.of(new Budget(YearMonth.of(2020, 10), 31)));
    var actual = finance.queryBudget(LocalDate.of(2020, 10, 31), LocalDate.of(2020, 9, 10));
    assertThat(actual).isEqualTo(0);
  }

  @Test
  void one_month() {
    when(repo.getAll()).thenReturn(List.of(new Budget(YearMonth.of(2020, 4), 30)));
    var actual = finance.queryBudget(LocalDate.of(2020, 4, 1), LocalDate.of(2020, 4, 30));
    assertThat(actual).isEqualTo(30);
  }

  @Test
  void two_months() {
    when(repo.getAll())
        .thenReturn(
            List.of(new Budget(YearMonth.of(2020, 4), 30), new Budget(YearMonth.of(2020, 5), 31)));
    var actual = finance.queryBudget(LocalDate.of(2020, 4, 1), LocalDate.of(2020, 5, 31));
    assertThat(actual).isEqualTo(61);
  }

  @Test
  void part_of_month() {
    when(repo.getAll()).thenReturn(List.of(new Budget(YearMonth.of(2020, 10), 31)));
    var actual = finance.queryBudget(LocalDate.of(2020, 10, 1), LocalDate.of(2020, 10, 10));
    assertThat(actual).isEqualTo(10);
  }

  @Test
  void no_data() {
    when(repo.getAll()).thenReturn(List.of());
    var actual = finance.queryBudget(LocalDate.of(2020, 10, 1), LocalDate.of(2020, 10, 10));
    assertThat(actual).isEqualTo(0);
  }

  @Test
  void cross_two_months() {
    when(repo.getAll())
        .thenReturn(
            List.of(
                new Budget(YearMonth.of(2020, 10), 31), new Budget(YearMonth.of(2020, 11), 30)));
    var actual = finance.queryBudget(LocalDate.of(2020, 10, 31), LocalDate.of(2020, 11, 10));
    assertThat(actual).isEqualTo(11);
  }

  @Test
  void cross_three_months() {
    when(repo.getAll())
        .thenReturn(
            List.of(
                new Budget(YearMonth.of(2020, 4), 30),
                new Budget(YearMonth.of(2020, 5), 31),
                new Budget(YearMonth.of(2020, 6), 30)));
    var actual = finance.queryBudget(LocalDate.of(2020, 4, 30), LocalDate.of(2020, 6, 3));
    assertThat(actual).isEqualTo(35);
  }

  @Test
  void cross_year() {
    when(repo.getAll())
        .thenReturn(
            List.of(
                new Budget(YearMonth.of(2020, 4), 30),
                new Budget(YearMonth.of(2020, 5), 31),
                new Budget(YearMonth.of(2020, 6), 30),
                new Budget(YearMonth.of(2021, 4), 30),
                new Budget(YearMonth.of(2021, 5), 31),
                new Budget(YearMonth.of(2021, 6), 30)));
    var actual = finance.queryBudget(LocalDate.of(2020, 4, 30), LocalDate.of(2021, 6, 3));
    assertThat(actual).isEqualTo(126);
  }
}
