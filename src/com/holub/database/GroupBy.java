package com.holub.database;

public class GroupBy {
    // 여기서 할 일
    // group by 하면 해당 테이블 여기로 끌고 오기

    GroupStrategy groupStrategy;
    String groupingCol; //Group By (A)
    String groupedCol; //MAX (A)
    public GroupBy(GroupStrategy groupStrategy, String groupingCol, String groupedCol){
        this.groupStrategy = groupStrategy;
        this.groupingCol = groupingCol;
        this.groupedCol = groupedCol;
    }
    public double groupCalculate(){
        System.out.println("변수의 값은: " + groupStrategy.calculate());
        return groupStrategy.calculate();

    }

}
