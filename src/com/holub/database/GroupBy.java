package com.holub.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupBy {

    GroupStrategy groupStrategy;
    String strategy;
    String groupingCol; //Group By (A)
    String groupedCol; //MAX (A)


    public GroupBy(String groupingCol, String groupedCol) {
        this.groupingCol = groupingCol;
        this.groupedCol = groupedCol;
    }

    public void setGroupStrategy(String strategy) {
        this.strategy = strategy;
        switch (strategy) {
            case "max":
                this.groupStrategy = new MaxStrategy();
                break;
            case "min":
                this.groupStrategy = new MinStrategy();
                break;
            case "sum":
                this.groupStrategy = new SumStrategy();
                break;
            case "avg":
                this.groupStrategy = new AvgStrategy();
                break;
            case "count":
                this.groupStrategy = new CountStrategy();
                break;
        }
        this.groupStrategy = groupStrategy;
    }

    public Table groupCalculate(Table table) {

        //System.out.println("변수의 값은: " + groupStrategy.calculate());
        Map<Object, List<Double>> result  = groupList(table);
        Map<Object, ?> stResult = groupStrategy.calculate(result);
        System.out.println(stResult);
        Table groupTable = groupByTable(stResult);
        return groupTable;
    }

    private Map<Object, List<Double>> groupList(Table table) {
        List columns;
        Map<Object, List<Double>> result = new HashMap<>();
        Cursor cursor = table.rows();
        columns = new ArrayList();
        for (int i = 0; cursor.advance(); i++) {
            Object groupingKey = cursor.column(groupingCol);
            Object columnValue = cursor.column(groupedCol);
            Double groupedValue = Double.parseDouble(columnValue.toString());
            // 결과 맵에 키가 이미 존재하는지 확인하고 없으면 새로운 리스트를 생성하여 추가
            result.computeIfAbsent(groupingKey, k -> new ArrayList<>()).add(groupedValue);
        }


        //System.out.println(result);
        return result;
    }

    //strategy에서 만든 키-값쌍을 테이블로 만들어서 반환하는 함수

    private Table groupByTable(Map<Object, ?> stResult) {
        // factory 패턴 사용해서 table 만들기.
        // column 이름 할 때 strategy 사용해서 만들기.
        String strategyCol = strategy + "(" + groupedCol + ")";
        Table groupTable = TableFactory.create(null, new String[]{groupingCol, strategyCol});


        for (Map.Entry<Object, ?> entry : stResult.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            groupTable.insert(new Object[]{key, value});
        }

        //System.out.println(groupTable.toString());

        return groupTable;
    }


}


