package com.example.a4diamonds.engine;

import com.example.a4diamonds.engine.entities.PlayerScoreEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

public class Engine {

    final static public int RED_DIAMOND = 0;
    final static public int BLUE_DIAMOND = 1;
    final static public int FREE_DIAMOND = 3;
    final static private Map<Integer, Integer> WIN_INCREMENT = new HashMap<Integer, Integer>() {
        {
            put(0, 0);
            put(1, 5);
            put(2, 15);
            put(3, 30);
            put(4, 50);
        }
    };
    private ChangeScoreCallback changeScoreCallback;
    private int nowStep = RED_DIAMOND;
    @Getter
    private PlayerScoreEntity scoreRed = new PlayerScoreEntity();
    @Getter
    private PlayerScoreEntity scoreBlue = new PlayerScoreEntity();

    private List<ArrayList<Integer>> field;

    public Engine(int size) {
        this.field = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            field.add(i, new ArrayList<Integer>(size));
            for (int j = 0; j < size; j++) {
                field.get(i).add(j, FREE_DIAMOND);
            }
        }
    }

    public void setDiamond(int x, int y) {
        if (field.get(x).get(y) != FREE_DIAMOND) return;

        field.get(x).set(y, this.nowStep);

        if (nowStep == RED_DIAMOND) {
            nowStep = BLUE_DIAMOND;
        } else {
            nowStep = RED_DIAMOND;
        }

        this.processSituation();
    }

    public List<ArrayList<Integer>> getField() {
        return this.field;
    }

    public void onScoreChanged(ChangeScoreCallback callback) {
        this.changeScoreCallback = callback;
    }

    private void processSituation() {
        int blueSq = 0;
        int redSq = 0;

        for (int i = 0; i < this.field.size() - 1; i++) {

            for (int j = 0; j < this.field.get(i).size() - 1; j++) {
                if (this.field.get(i).get(j).equals(this.field.get(i + 1).get(j))
                        && this.field.get(i + 1).get(j).equals(this.field.get(i).get(j + 1))
                        && this.field.get(i).get(j + 1).equals(this.field.get(i + 1).get(j + 1))
                ) {
                    switch (this.field.get(i).get(j)) {
                        case BLUE_DIAMOND:
                            blueSq++;
                            break;
                        case RED_DIAMOND:
                            redSq++;
                            break;
                    }
                }
            }
        }

        int addScoreForBlue = this.getPoints(this.scoreBlue.getSquareCounter(), blueSq);
        int addScoreForRed = this.getPoints(this.scoreRed.getSquareCounter(), redSq);


        if (this.changeScoreCallback != null) {
            if (addScoreForBlue != 0) {
                this.changeScoreCallback.changed(BLUE_DIAMOND);
            }

            if (addScoreForRed != 0) {
                this.changeScoreCallback.changed(RED_DIAMOND);
            }
        }


        this.scoreBlue.incSquareCounter(addScoreForBlue);
        this.scoreRed.incSquareCounter(addScoreForRed);

        this.scoreBlue.incScore(WIN_INCREMENT.get(addScoreForBlue));
        this.scoreRed.incScore(WIN_INCREMENT.get(addScoreForRed));
    }

    private int getPoints(int oldSq, int nowSq) {
        return nowSq - oldSq;
    }
}
