package com.holub.database;

import java.util.ArrayList;
import java.util.List;

public class GroupBy {
    // 여기서 할 일
    // group by 하면 해당 테이블 여기로 끌고 오기

    GroupStrategy groupStrategy;
    String groupingCol; //Group By (A)
    String groupedCol; //MAX (A)




    public GroupBy(String groupingCol, String groupedCol){
        this.groupingCol = groupingCol;
        this.groupedCol = groupedCol;
    }

    public void setGroupStrategy(String strategy){
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
        }
        this.groupStrategy = groupStrategy;
    }
    public double groupCalculate(Table table){

        //System.out.println("변수의 값은: " + groupStrategy.calculate());
        groupList(table);
        return groupStrategy.calculate();
    }

    //여기에 따로 떼어주는 함수 만들자.
    //컬럼 기준으로 find해서 리스트로 묶어서 보내주자. hashmap 사용해서 키-값 쌍으로 만들기
    //이때 select 쓰면 될듯?? 만약에 해당 이름이 있으면 해당 이름이 있는 해쉬맵에 값 추가. 없으면 새로운 해쉬맵 만들기.
    //근데 그러면 테이블 한 컬럼의 모든 값들을 봐야된다는건데, 해당 테이블을 모두 순회해야겠네.
    private void groupList(Table table){
        List columns;
        Cursor cursor = table.rows();
        //cursor.advance();

        columns = new ArrayList();
        for (int i = 0; cursor.advance() == true; i++) {
            System.out.println(cursor.column(cursor.columnName(0))+ ":" + cursor.column(cursor.columnName(1)));
        }
    }

}
