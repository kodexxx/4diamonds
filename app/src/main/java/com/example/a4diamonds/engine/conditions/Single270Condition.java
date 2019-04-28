package com.example.a4diamonds.engine.conditions;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Single270Condition implements ICondition {

    @Override
    public Boolean[][] getExp() {
        return new Boolean[][]{
                {true, true},
                {true, false}
        };
    }

    @Override
    public List<Pair<Integer, Integer>> getStep() {
        return new ArrayList<Pair<Integer, Integer>>() {{
            add(new Pair<>(1, 1));
        }};
    }


    @Override
    public Pair<Integer, Integer> getSize() {
        return new Pair<>(2, 2);
    }
}
