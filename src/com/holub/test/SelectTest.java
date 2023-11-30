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
        Table expectedTable = TableFactory.create("<anonymous>", new String[]{"addrId", "street", "city", "state", "zip", "first", "last", "addrId"});
        expectedTable.insert(new Object[]{"0", "12 MyStreet", "Berkeley", "CA", "99998", "Allen", "Holub", 0});
        expectedTable.insert(new Object[]{"1", "34 Quarry Ln.", "Bedrock", "AZ", "00000", "Fred", "Flintstone", 1});
        expectedTable.insert(new Object[]{"1", "34 Quarry Ln.", "Bedrock", "AZ", "00000", "Wilma", "Flintstone", 1});
        //join_table = database.execute("delete from join_table where addrId = 0");

        Writer out = new FileWriter("join_table");
        join_table.export(new CSVExporter(out));
        out.close();


        String string_expected = expectedTable.toString();

       String string_join_table = join_table.toString();

        Assertions.assertEquals(string_expected, string_join_table);
    }
}