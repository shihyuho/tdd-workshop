package com.github.shihyuho.tddworkshop.tennis;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TennisTest {

  Tennis tennis;

  @BeforeEach
  void setup() {
    tennis = new Tennis();
  }

  @Test
  void love_all() {
    assertThat(tennis.score()).isEqualTo("love all");
  }

  @Test
  void fifteen_love() {
    tennis.firstPlayerScore();
    assertThat(tennis.score()).isEqualTo("fifteen love");
  }

  @Test
  void thirty_love() {
    givenFirstPlayerScore(2);
    assertThat(tennis.score()).isEqualTo("thirty love");
  }

  @Test
  void forty_love() {
    givenFirstPlayerScore(3);
    assertThat(tennis.score()).isEqualTo("forty love");
  }

  @Test
  void love_fifteen() {
    givenSecondPlayerScore(1);
    assertThat(tennis.score()).isEqualTo("love fifteen");
  }

  @Test
  void love_thirty() {
    givenSecondPlayerScore(2);
    assertThat(tennis.score()).isEqualTo("love thirty");
  }

  @Test
  void love_forty() {
    givenSecondPlayerScore(3);
    assertThat(tennis.score()).isEqualTo("love forty");
  }

  @Test
  void fifteen_all() {
    givenFirstPlayerScore(1);
    givenSecondPlayerScore(1);
    assertThat(tennis.score()).isEqualTo("fifteen all");
  }

  private void givenSecondPlayerScore(int times) {
    range(0, times).forEachOrdered(i -> tennis.secondPlayerScore());
  }

  private void givenFirstPlayerScore(int times) {
    range(0, times).forEachOrdered(i -> tennis.firstPlayerScore());
  }
}
