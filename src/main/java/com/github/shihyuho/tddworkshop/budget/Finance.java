package com.github.shihyuho.tddworkshop.budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Finance {

  @NonNull private final IBudgetRepo repo;

  public double queryBudget(@NonNull LocalDate start, @NonNull LocalDate end) {
    if (start.isAfter(end)) {
      return 0;
    }
    return repo.getAll().stream()
        .filter(containing(start, end))
        .mapToDouble(calculateAmount(start, end))
        .sum();
  }

  private ToDoubleFunction<Budget> calculateAmount(
      @NonNull LocalDate start, @NonNull LocalDate end) {
    return b -> {
      if (b.allMatchBudgetYearMonth(start, end)) {
        return b.calculate(start, end);
      }

      if (b.allMatchBudgetYearMonth(start)) {
        return b.calculateToEndOfMonth(start);
      }

      if (b.allMatchBudgetYearMonth(end)) {
        return b.calculateFromStartOfMonth(end);
      }

      return b.getAmount();
    };
  }

  private Predicate<Budget> containing(LocalDate startInclusive, LocalDate endInclusive) {
    return b ->
        isBeforeClosed(startInclusive, b.getYearMonth())
            && isAfterClosed(endInclusive, b.getYearMonth());
  }

  private boolean isBeforeClosed(LocalDate date, YearMonth ym) {
    return YearMonth.from(date).isBefore(ym) || YearMonth.from(date).equals(ym);
  }

  private boolean isAfterClosed(LocalDate date, YearMonth ym) {
    return YearMonth.from(date).isAfter(ym) || YearMonth.from(date).equals(ym);
  }
}
