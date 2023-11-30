package com.holub.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvgStrategy implements GroupStrategy {
    public Map<Object, Double> calculate(Map<Object, List<Double>> result) {
        Map<Object, Double> avgValuesMap = new HashMap<>();

        for (Map.Entry<Object, List<Double>> entry : result.entrySet()) {
            Object key = entry.getKey();
            List<Double> values = entry.getValue();

            // 값들의 평균을 저장할 변수
            double sum = 0.0;

            for (Double value : values) {
                sum += value;
            }

            // 값들의 평균 계산
            double avg = values.isEmpty() ? 0.0 : sum / values.size();

            avgValuesMap.put(key, avg);
        }

        return avgValuesMap;
    }

    // 다른 필요한 메서드나 변수 등도 추가할 수 있음
}
