package com.github.shihyuho.tddworkshop.tennis;

import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TennisOrigin {

  static Map<Integer, String> points = Map.of(0, "love", 1, "fifteen", 2, "thirty", 3, "forty");

  @NonNull private final String player1;
  @NonNull private final String player2;

  private int firstPlayerScoreTimes = 0;
  private int secondPlayerScoreTime = 0;
  private boolean inDeucePhase;
  private boolean gg;

  void reset() {
    firstPlayerScoreTimes = 0;
    secondPlayerScoreTime = 0;
    inDeucePhase = false;
    gg = false;
  }

  void firstPlayerScore() {
    if (gg) {
      throw new IllegalStateException("Game already finished");
    }
    firstPlayerScoreTimes++;
    determineDeucePhase();
  }

  void secondPlayerScore() {
    if (gg) {
      throw new IllegalStateException("Game already finished");
    }
    secondPlayerScoreTime++;
    determineDeucePhase();
  }

  void determineDeucePhase() {
    inDeucePhase = firstPlayerScoreTimes >= 3 && secondPlayerScoreTime >= 3;
  }

  String score() {
    if (inDeucePhase) {
      if (firstPlayerScoreTimes == secondPlayerScoreTime) {
        return "deuce";
      }
      if (firstPlayerScoreTimes > secondPlayerScoreTime) {
        if (firstPlayerScoreTimes - secondPlayerScoreTime == 2) {
          gg = true;
          return player1 + " win";
        }
        return player1 + " adv";
      }
      if (secondPlayerScoreTime - firstPlayerScoreTimes == 2) {
        gg = true;
        return player2 + " win";
      }
      return player2 + " adv";
    }
    if (firstPlayerScoreTimes == secondPlayerScoreTime) {
      return points.get(firstPlayerScoreTimes) + " all";
    }
    if (firstPlayerScoreTimes == 4) {
			gg = true;
			return points.get(firstPlayerScoreTimes) + " win";
		}
		if (secondPlayerScoreTime == 4) {
			gg = true;
			return points.get(secondPlayerScoreTime) + " win";
		}
    return points.get(firstPlayerScoreTimes) + " " + points.get(secondPlayerScoreTime);
  }
}
