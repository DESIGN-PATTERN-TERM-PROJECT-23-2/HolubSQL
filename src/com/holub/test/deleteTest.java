package com.holub.test;

import com.holub.database.*;
import com.holub.text.ParseFailure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

public class deleteTest {

    Table name;
    Table address;
    Database database;

    private void insertData() throws IOException {

        Reader in_name = new FileReader("name.csv");


        CSVImporter csvImporter = new CSVImporter(in_name);

        name = TableFactory.create(csvImporter);
        //System.out.println(name.toString());

        Reader in_address = new FileReader("address.csv");
        CSVImporter csvImporter_address = new CSVImporter(in_address);
        address = TableFactory.create(csvImporter_address);

        database = new Database(new File("name"));
        database.tableList();
    }
    @DisplayName("Delete Test")
    @Test
    public void deleteTest() throws IOException, ParseFailure {
        insertData();

        //System.out.println(name.toString());


        //database.execute("delete from name where addrId = 1");
        //Cursor cursor = table.rows();

        //System.out.println(database.toString());


        Writer out = new FileWriter("name.csv");
        name.export(new CSVExporter(out));
        out.close();
    }

}
