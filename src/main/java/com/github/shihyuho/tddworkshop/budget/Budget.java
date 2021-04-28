package com.github.shihyuho.tddworkshop.budget;

import static java.time.YearMonth.from;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import lombok.Value;

@Value
public class Budget {
  YearMonth yearMonth;
  Integer amount;

  boolean allMatchBudgetYearMonth(LocalDate... dates) {
    return Arrays.stream(dates).map(YearMonth::from).allMatch(yearMonth::equals);
  }

  double calculateToEndOfMonth(LocalDate date) {
    return calculate(date, from(date).atEndOfMonth());
  }

  double calculateFromStartOfMonth(LocalDate date) {
    return calculate(from(date).atDay(1), date);
  }

  double calculate(LocalDate start, LocalDate end) {
    return (double) amount / from(start).lengthOfMonth() * daysBetweenClosed(start, end);
  }

  private int daysBetweenClosed(LocalDate startInclusive, LocalDate endInclusive) {
    return startInclusive.until(endInclusive).getDays() + 1;
  }
}
