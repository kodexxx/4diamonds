package com.example.a4diamonds.engine.conditions;

import android.util.Pair;

import java.util.List;

public interface ICondition {
    Boolean[][] getExp();
    List<Pair<Integer, Integer>> getStep();
    Pair<Integer, Integer> getSize();
}
