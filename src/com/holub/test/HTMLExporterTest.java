package com.holub.test;

import com.holub.database.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;


//import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class HTMLExporterTest {
    Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });

    private void insertData() throws IOException {
        people.insert(new Object[] { "A1", "A2", 1 });
        people.insert(new Object[] { "B1", "B2", 2});
        people.insert(new Object[] { "C1", "C2", 3});
    }

    @DisplayName("HTML Exporter Test")
    @Test
    public void testStore() throws IOException, ClassNotFoundException {
        insertData();
        String expectedHTML =
                "<table border=\"1\">\n" +
                "<caption>people</caption>\n" +
                "<thead><tr>\n" +
                "<tr>\n" +
                "<td>last</td>	<td>first</td>	<td>addrId</td>\n" +
                "</tr>\n" +
                "</tr></thead>\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>A1</td>	<td>A2</td>	<td>1</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>B1</td>	<td>B2</td>	<td>2</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>C1</td>	<td>C2</td>	<td>3</td>\n" +
                "</tr>\n" +
                "</tbody></table>"+
                        "\n";


        StringWriter out = new StringWriter();
        people.export(new HTMLExporter(out));
        Assertions.assertEquals(expectedHTML, out.toString());
    }

}