package com.example.a4diamonds.engine.conditions;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Triangle180Condition implements ICondition {
    @Override
    public Boolean[][] getExp() {
        return new Boolean[][]{
                {true, false, true},
                {false, true, false}
        };
    }

    @Override
    public List<Pair<Integer, Integer>> getStep() {
        return new ArrayList<Pair<Integer, Integer>>() {{
            add(new Pair<>(1, 0));
            add(new Pair<>(0, 1));
            add(new Pair<>(2, 1));
        }};
    }


    @Override
    public Pair<Integer, Integer> getSize() {
        return new Pair<>(2, 3);
    }
}
