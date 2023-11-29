package com.holub.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinStrategy implements GroupStrategy {
    public Map<Object, Double> calculate(Map<Object, List<Double>> result) {
        Map<Object, Double> minValuesMap = new HashMap<>();

        for (Map.Entry<Object, List<Double>> entry : result.entrySet()) {
            Object key = entry.getKey();
            List<Double> values = entry.getValue();

            // 초기값을 Double.MAX_VALUE로 설정하여 모든 값이 양수일 때도 동작하도록 함
            double minValue = Double.MAX_VALUE;

            for (Double value : values) {
                if (value < minValue) {
                    minValue = value;
                }
            }

            minValuesMap.put(key, minValue);
        }

        return minValuesMap;
    }

    // 다른 필요한 메서드나 변수 등도 추가할 수 있음
}
