package com.holub.database;

import java.io.*;
import java.util.*;



public class HTMLExporter implements Table.Exporter {
    private final Writer out;
    private int width;

    public HTMLExporter(Writer out) {
        this.out = out;
    }

    @Override
    public void storeMetadata(String tableName,
                              int width,
                              int height,
                              Iterator columnNames) throws IOException
    {
        this.width = width;
        out.write("<table border=\"1\">\n");
        out.write("<caption>" + (tableName == null ? "&lt;anonymous&gt;" : tableName) + "</caption>\n");
        out.write("<thead><tr>\n");
        storeRow(columnNames); // table headers
        out.write("</tr></thead>\n");
        out.write("<tbody>\n");
    }


    public void storeRow(Iterator data) throws IOException
    {
        int i = width;
        out.write("<tr>\n");
        while (data.hasNext()) {
            Object datum = data.next();
            out.write("<td>");

            // Null columns are represented by an empty field
            // (an empty cell). There's nothing to write if the column data is null.
            if (datum != null)
                out.write(datum.toString());

            out.write("</td>");

            if (--i > 0)
                out.write("\t");
        }
        out.write("\n</tr>\n");
    }


    public void startTable() throws IOException {
        // Nothing to do for starting an HTML table
    }

    public void endTable() throws IOException {
        out.write("</tbody></table>\n");
    }
}
