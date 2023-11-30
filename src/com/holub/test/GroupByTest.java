package com.holub.test;

import com.holub.database.*;
import com.holub.text.ParseFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

public class GroupByTest {
    Table university;
    Database database;

    private void insertData() throws IOException {

        Reader in_name = new FileReader("university.csv");
        CSVImporter csvImporter = new CSVImporter(in_name);
        university = TableFactory.create(csvImporter);

        database = new Database(new File("."));
    }

    @DisplayName("MAX Test")
    @Test
    public void MAX_test() throws IOException, ParseFailure {
        insertData();
        int a = 0;
        int b = 1;
        Table join_table = database.execute("select building, max(capacity) from university group by building ");

        Table expectedTable = TableFactory.create("<anonymous>", new String[]{"building", "max(capacity)"});
        expectedTable.insert(new Object[]{"A", 500.0});
        expectedTable.insert(new Object[]{"B", 100.0});
        String string_expected = expectedTable.toString();
        String string_group_table = join_table.toString();
        Assertions.assertEquals(string_expected,string_group_table);
    }

    @DisplayName("MIN Test")
    @Test
    public void MIN_test() throws IOException, ParseFailure {
        insertData();
        int a = 0;
        int b = 1;
        Table join_table = database.execute("select building, min(capacity) from university group by building ");

        Table expectedTable = TableFactory.create("<anonymous>", new String[]{"building", "min(capacity)"});
        expectedTable.insert(new Object[]{"A", 500.0});
        expectedTable.insert(new Object[]{"B", 30.0});
        String string_expected = expectedTable.toString();
        String string_group_table = join_table.toString();
        Assertions.assertEquals(string_expected,string_group_table);
    }

    @DisplayName("AVG Test")
    @Test
    public void AVG_test() throws IOException, ParseFailure {
        insertData();
        int a = 0;
        int b = 1;
        Table join_table = database.execute("select building, avg(capacity) from university group by building ");

        Table expectedTable = TableFactory.create("<anonymous>", new String[]{"building", "avg(capacity)"});
        expectedTable.insert(new Object[]{"A", 500.0});
        expectedTable.insert(new Object[]{"B", 65.0});
        String string_expected = expectedTable.toString();
        String string_group_table = join_table.toString();
        Assertions.assertEquals(string_expected,string_group_table);
    }

    @DisplayName("SUM Test")
    @Test
    public void SUM_test() throws IOException, ParseFailure {
        insertData();
        int a = 0;
        int b = 1;
        Table join_table = database.execute("select building, sum(capacity) from university group by building ");

        Table expectedTable = TableFactory.create("<anonymous>", new String[]{"building", "sum(capacity)"});
        expectedTable.insert(new Object[]{"A", 500.0});
        expectedTable.insert(new Object[]{"B", 130.0});
        String string_expected = expectedTable.toString();
        String string_group_table = join_table.toString();
        Assertions.assertEquals(string_expected,string_group_table);
    }

    @DisplayName("COUNT Test")
    @Test
    public void COUNT_test() throws IOException, ParseFailure {
        insertData();
        int a = 0;
        int b = 1;
        Table join_table = database.execute("select building, count(capacity) from university group by building ");

        Table expectedTable = TableFactory.create("<anonymous>", new String[]{"building", "count(capacity)"});
        expectedTable.insert(new Object[]{"A", 1});
        expectedTable.insert(new Object[]{"B", 2});
        String string_expected = expectedTable.toString();
        String string_group_table = join_table.toString();
        Assertions.assertEquals(string_expected,string_group_table);
    }
}
