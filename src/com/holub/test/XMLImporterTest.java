package com.holub.test;
import com.holub.database.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;


class XMLImporterTest {

    Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });Table address = TableFactory.create("address", new String[] { "addrId", "street", "city", "state", "zip" });
    Database database;

    private void insertData() throws IOException {
        people.insert(new Object[] { "A1", "A2", "1" });
        people.insert(new Object[] { "B1", "B2", "2"});
        people.insert(new Object[] { "C1", "C2", "3"});
        database = new Database(new File("."));
    }
    @DisplayName("XML Importer Test")
    @Test
    public void testImport() throws IOException {
        insertData();
        String expectedXML =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                        "<root>\n"+
                        "<title>people</title>\n"+
                        "<rows>\n"+
                        "<row>\n"+
                        "<last>Holub</last>\n"+
                        "<first>Allen</first>\n"+
                        "<addrId>1</addrId>\n"+
                        "</row>\n"+
                        "<row>\n"+
                        "<last>Flintstone</last>\n"+
                        "<first>Wilma</first>\n"+
                        "<addrId>2</addrId>\n"+
                        "</row>\n"+
                        "<row>\n"+
                        "<last>Flintstone</last>\n"+
                        "<first>Fred</first>\n"+
                        "<addrId>2</addrId>\n"+
                        "</row>\n"+
                        "</rows>\n"+
                        "</root>";
        Writer out = new FileWriter("mypeople.xml");
        people.export(new XMLExporter(out));
        out.close();
        Reader in = new FileReader("mypeople.xml");
        //XMLImporter xmlImporter = new XMLImporter(in);
        Assertions.assertEquals(expectedXML, in.toString());



    }

}