package com.example.a4diamonds.engine.conditions;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Single0Condition implements ICondition {

    @Override
    public Boolean[][] getExp() {
        return new Boolean[][]{
                {false, true},
                {true, true}
        };
    }

    @Override
    public List<Pair<Integer, Integer>> getStep() {
        return new ArrayList<Pair<Integer, Integer>>() {{
            add(new Pair<>(0, 0));
        }};
    }


    @Override
    public Pair<Integer, Integer> getSize() {
        return new Pair<>(2, 2);
    }
}
