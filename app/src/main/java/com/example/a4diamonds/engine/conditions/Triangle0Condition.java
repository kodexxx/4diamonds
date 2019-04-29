package com.example.a4diamonds.engine.conditions;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Triangle0Condition implements ICondition {
    @Override
    public Boolean[][] getExp() {
        return new Boolean[][]{
                {false, true, false},
                {true, false, true}
        };
    }

    @Override
    public List<Pair<Integer, Integer>> getStep() {
        return new ArrayList<Pair<Integer, Integer>>() {{

            add(new Pair<>(1, 1));
            add(new Pair<>(0, 0));
            add(new Pair<>(2, 0));
        }};
    }


    @Override
    public Pair<Integer, Integer> getSize() {
        return new Pair<>(2, 3);
    }
}
