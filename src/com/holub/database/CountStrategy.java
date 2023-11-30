package com.holub.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountStrategy implements GroupStrategy {
    public Map<Object, Integer> calculate(Map<Object, List<Double>> result) {
        Map<Object, Integer> countValuesMap = new HashMap<>();

        for (Map.Entry<Object, List<Double>> entry : result.entrySet()) {
            Object key = entry.getKey();
            List<Double> values = entry.getValue();

            // 값들의 개수를 저장
            int count = values.size();

            countValuesMap.put(key, count);
        }

        return countValuesMap;
    }
}