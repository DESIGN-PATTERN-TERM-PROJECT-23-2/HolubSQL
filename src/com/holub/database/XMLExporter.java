package com.holub.database;

import java.io.*;
import java.util.*;

public class XMLExporter implements Table.Exporter {
    private final Writer out;
    private int width;
    private String[] tableHead;


    public XMLExporter(Writer out) {
        this.out = out;
    }

    @Override
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.width = width;
        out.write("<title>"+tableName+"</title>\n");
        out.write("<rows>\n");
        tableHead = new String[width];
        int index = 0;
        while (columnNames.hasNext())
            tableHead[index++] = columnNames.next().toString();
    }

    public void storeRow(Iterator data) throws IOException {
        int i = 0;
        out.write("<row>\n");
        while (data.hasNext()) {
            Object datum = data.next();
            out.write("<"+ tableHead[i]+">" + datum.toString()+ "</" + tableHead[i] + ">\n");
            i++;
        }
        out.write("</row>\n");
    }

    public void startTable() throws IOException {
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        out.write("<root>\n");
    }

    public void endTable() throws IOException {
        out.write("</rows>\n");
        out.write("</root>");
    }
}
