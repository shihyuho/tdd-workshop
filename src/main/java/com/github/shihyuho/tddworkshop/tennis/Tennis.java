package com.github.shihyuho.tddworkshop.tennis;

import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Tennis {

  Map<Integer, String> table = Map.of(0, "love", 1, "fifteen", 2, "thirty", 3, "forty");

  private int firstPlayerScore;
  private int secondPlayerScore;

  String score() {
    if (isScoreDifferent()) {
      return lookupScore();
    }
    return sameScore();
  }

  private String sameScore() {
    return table.get(firstPlayerScore) + " all";
  }

  private boolean isScoreDifferent() {
    return firstPlayerScore != secondPlayerScore;
  }

  private String lookupScore() {
    return table.get(firstPlayerScore) + " " + table.get(secondPlayerScore);
  }

  public void firstPlayerScore() {
    firstPlayerScore++;
  }

  public void secondPlayerScore() {
    secondPlayerScore++;
  }
}
