package com.holub.test;

import static org.junit.jupiter.api.Assertions.*;
import com.holub.database.*;
import com.holub.text.ParseFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SelectTest {


    Table name;
    Table address;
    Database database;

    private void insertData() throws IOException {

        Reader in_name = new FileReader("name.csv");
        CSVImporter csvImporter = new CSVImporter(in_name);
        name = TableFactory.create(csvImporter);

        Reader in_address = new FileReader("address.csv");
        CSVImporter csvImporter_address = new CSVImporter(in_address);
        address = TableFactory.create(csvImporter_address);

        database = new Database(new File("."));
    }

    @DisplayName("Select Test")
    @Test
    public void join_test() throws IOException, ParseFailure {
        insertData();
        Table join_table = database.execute("select * from address, name where address.addrId = name.addrId");
        Table expectedTable = TableFactory.create("expected", new String[]{"addrId", "street", "city", "state", "zip", "addrId", "street", "city"});
        expectedTable.insert(new Object[]{"1", "AStreet", "ACity", "AState", "AZip", "A1", "A2", 1});
        expectedTable.insert(new Object[]{"2", "BStreet", "BCity", "BState", "BZip", "B1", "B2", 2});
        Assertions.assertEquals(expectedTable, join_table);
    }
}