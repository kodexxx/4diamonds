package com.example.a4diamonds.engine.conditions;

import java.util.ArrayList;
import java.util.List;

public class ConditionFactory {
    public List<ICondition> getAllConditions() {
        return new ArrayList<ICondition>(){{
            add(new Single0Condition());
            add(new Single90Condition());
            add(new Single180Condition());
            add(new Single270Condition());
            add(new Triangle0Condition());
            add(new Triangle90Condition());
            add(new Triangle180Condition());
            add(new Triangle270Condition());
        }};
    }
}
