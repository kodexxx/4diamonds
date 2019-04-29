package com.example.a4diamonds.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerScoreEntity {
    private int score = 0;
    private int squareCounter = 0;

    public void incScore(int count) {
        this.score += count;
    }

    public void incScore() {
        this.score++;
    }

    public void incSquareCounter(int count) {
        this.squareCounter += count;
    }

    public void incSquareCounter() {
        this.squareCounter++;
    }
}
