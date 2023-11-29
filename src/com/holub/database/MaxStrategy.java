package com.holub.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxStrategy implements GroupStrategy {
    public Map<Object, Double> calculate(Map<Object, List<Double>> result) {
        Map<Object, Double> maxValuesMap = new HashMap<>();

        for (Map.Entry<Object, List<Double>> entry : result.entrySet()) {
            Object key = entry.getKey();
            List<Double> values = entry.getValue();

            // 초기값을 Double.MIN_VALUE로 설정하여 모든 값이 양수일 때도 동작하도록 함
            double maxValue = Double.MIN_VALUE;

            for (Double value : values) {
                if (value > maxValue) {
                    maxValue = value;
                }
            }

            maxValuesMap.put(key, maxValue);
        }

        return maxValuesMap;
    }
}

