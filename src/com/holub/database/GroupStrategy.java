package com.holub.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GroupStrategy {
    public Map<Object, ?> calculate(Map<Object, List<Double>> result);
}
