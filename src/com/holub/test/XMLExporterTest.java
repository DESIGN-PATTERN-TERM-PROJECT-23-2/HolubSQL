package com.holub.test;

import com.holub.database.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

class XMLExporterTest {

    Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });Table address = TableFactory.create("address", new String[] { "addrId", "street", "city", "state", "zip" });
    Database database;

    private void insertData() throws IOException {
        people.insert(new Object[] { "A1", "A2", "1" });
        people.insert(new Object[] { "B1", "B2", "2"});
        people.insert(new Object[] { "C1", "C2", "3"});
        database = new Database(new File("."));
    }

    @DisplayName("XML Exporter Test")
    @Test
    public void testStore() throws IOException, ClassNotFoundException {
        insertData();
        String expectedXML =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<root>\n"+
                "<title>people</title>\n"+
                "<rows>\n"+
                "<row>\n"+
                "<last>A1</last>\n"+
                "<first>A2</first>\n"+
                "<addrId>1</addrId>\n"+
                "</row>\n"+
                "<row>\n"+
                "<last>B1</last>\n"+
                "<first>B2</first>\n"+
                "<addrId>2</addrId>\n"+
                "</row>\n"+
                "<row>\n"+
                "<last>C1</last>\n"+
                "<first>C2</first>\n"+
                "<addrId>3</addrId>\n"+
                "</row>\n"+
                "</rows>\n"+
                "</root>";



        StringWriter out = new StringWriter();
        people.export(new XMLExporter(out));
        Assertions.assertEquals(expectedXML, out.toString());
    }



}