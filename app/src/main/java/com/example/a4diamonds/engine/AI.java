package com.example.a4diamonds.engine;

import android.util.Pair;

import com.example.a4diamonds.engine.conditions.ConditionFactory;
import com.example.a4diamonds.engine.conditions.ICondition;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {
    private List<ICondition> conditions;

    public AI() {
        ConditionFactory conditionFactory = new ConditionFactory();
        this.conditions = conditionFactory.getAllConditions();
    }

    public Pair getStep(List<ArrayList<Integer>> field) {
        Pair optimalStep = this.getOptimalStep(field);
        if (optimalStep != null) return optimalStep;

        return this.getRandomStep(field);
    }

    private Pair getRandomStep(List<ArrayList<Integer>> field) {
        Random r = new Random();
        Pair<Integer, Integer> ans = new Pair<>(r.nextInt(field.size()), r.nextInt(field.size()));
        int counter = 0;

        while (field.get(ans.first).get(ans.second) != Engine.FREE_DIAMOND) {
            if (counter > field.size() * field.size()) {
                return null;
            }
            ans = new Pair<>(r.nextInt(field.size()), r.nextInt(field.size()));
            counter++;
        }

        return ans;
    }

    private Pair getOptimalStep(List<ArrayList<Integer>> field) {
        System.out.println("sosi");
        for (int j = 0; j < field.size(); j++) {
            for (int i = 0; i < field.get(j).size(); i++) {
                List<Pair<Integer, Integer>> optimalSteps = hasCondition(field, i, j);

                if (optimalSteps == null) continue;

                for (Pair<Integer, Integer> mbStep : optimalSteps) {
                    if (field.get(mbStep.first).get(mbStep.second) == Engine.FREE_DIAMOND) {
                        return mbStep;
                    }
                }
            }
        }

        return null;
    }

    @Nullable
    private List<Pair<Integer, Integer>> hasCondition(List<ArrayList<Integer>> field, int x, int y) {
        System.out.println("sosi2");
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for (int ci = 0; ci < this.conditions.size(); ci++) {
            ICondition condition = this.conditions.get(ci);

            if (x + condition.getSize().first < field.size() && y + condition.getSize().second < field.size()) {
                if (this.isMaskCondition(condition, field, x, y)) {
                    System.out.println(condition.getClass().getCanonicalName());
                    result.addAll(this.processSteps(condition.getStep(), x, y));
                }
            }
        }

        if (result.size() == 0) {
            return null;
        }

        return result;
    }

    private boolean isMaskCondition(ICondition condition, List<ArrayList<Integer>> field, int x, int y) {
        Integer type = null;
        for (int i = 0; i < condition.getSize().first; i++) {
            for (int j = 0; j < condition.getSize().second; j++) {
                boolean conditionMask = condition.getExp()[i][j];
                Integer item = field.get(j + x).get(i + y);

                if (type == null && item != Engine.FREE_DIAMOND) {
                    type = item;
                }

                if (conditionMask && !item.equals(type)) {
                    return false;
                }

                if (!conditionMask && !item.equals(Engine.FREE_DIAMOND)) {
                    return false;
                }

                if (item != Engine.FREE_DIAMOND && !item.equals(type)) {
                    return false;
                }
            }
        }
        if (type == null) {
            return false;
        }
        return true;
    }

    private List<Pair<Integer, Integer>> processSteps(List<Pair<Integer, Integer>> steps, int x, int y) {
        List<Pair<Integer, Integer>> resultProcessed = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            resultProcessed.add(new Pair<>(steps.get(i).first + x, steps.get(i).second + y));
        }
        return resultProcessed;
    }
}
