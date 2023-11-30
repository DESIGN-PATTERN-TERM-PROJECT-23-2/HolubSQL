package com.holub.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumStrategy implements GroupStrategy {
    public Map<Object, Double> calculate(Map<Object, List<Double>> result) {
        Map<Object, Double> sumValuesMap = new HashMap<>();

        for (Map.Entry<Object, List<Double>> entry : result.entrySet()) {
            Object key = entry.getKey();
            List<Double> values = entry.getValue();

            // 값들의 합을 저장할 변수
            double sum = 0.0;

            for (Double value : values) {
                sum += value;
            }

            sumValuesMap.put(key, sum);
        }

        return sumValuesMap;
    }

    // 다른 필요한 메서드나 변수 등도 추가할 수 있음
}
