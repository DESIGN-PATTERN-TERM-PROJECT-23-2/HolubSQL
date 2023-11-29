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

    @DisplayName("Group By Test")
    @Test
    public void groupBy_test() throws IOException, ParseFailure {
        insertData();
        int a = 0;
        int b = 1;
        Table join_table = database.execute("select building, max(capacity) from university group by building ");
        /*
        Table expectedTable = TableFactory.create("<anonymous>", new String[]{"addrId", "street", "city", "state", "zip", "first", "last", "addrId"});
        expectedTable.insert(new Object[]{"0", "12 MyStreet", "Berkeley", "CA", "99998", "Allen", "Holub", 0});
        expectedTable.insert(new Object[]{"1", "34 Quarry Ln.", "Bedrock", "AZ", "00000", "Fred", "Flintstone", 1});
        expectedTable.insert(new Object[]{"1", "34 Quarry Ln.", "Bedrock", "AZ", "00000", "Wilma", "Flintstone", 1});


        String string_expected = expectedTable.toString();
        */


        String string_join_table = join_table.toString();



        Assertions.assertEquals(a,string_join_table);
    }
}
