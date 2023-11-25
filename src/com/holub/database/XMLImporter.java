package com.holub.database;

import com.holub.tools.ArrayIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLImporter implements Table.Importer {
    private BufferedReader in;      // null once end-of-file reached
    private String tableName;
    private String[] columnNames;
    private String[] firstRow;

    public XMLImporter(Reader in) {
        this.in = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);

    }

    @Override
    public void startTable() throws IOException {
        in.readLine(); // <?xml version="1.0" encoding="UTF-8"?>
        in.readLine(); // <root>

        String line = in.readLine(); // <title>people</title>

        if (line != null) {
            tableName = extractValueFromTag(line, "title");
            in.readLine(); // <rows>
            in.readLine(); // <row>


            line = in.readLine();

            if (line != null && line.trim().startsWith("<")) {
                List<String> columnList = new ArrayList<>();
                List<String> values = new ArrayList<>();

                while (!line.trim().equals("</row>")) {
                    Matcher matcher = Pattern.compile("<(.*?)>").matcher(line);

                    while (matcher.find()) {
                        String tag = matcher.group(1);
                        String value = extractValueFromTag(line, tag);
                        if(value != "") {
                            values.add(extractValueFromTag(line, tag));
                        }

                        if (!tag.startsWith("/")) {
                            columnList.add(tag);
                        }
                    }

                    line = in.readLine();
                }

                columnNames = columnList.toArray(new String[0]);
                firstRow = values.toArray(new String[0]);
            }
        }
    }
    public String loadTableName() throws IOException {
        return tableName;
    }

    public int loadWidth() throws IOException {
        return columnNames.length;
    }

    public Iterator loadColumnNames() throws IOException {
        return new ArrayIterator(columnNames);
    }

    public Iterator loadRow() throws IOException {
        if (columnNames == null) {
            return null;
        }


        if (firstRow != null) {
            String[] row = firstRow;
            firstRow = null;
            return new ArrayIterator(row);
        }

        String line = in.readLine();
        if (line == null || line.trim().equals("</rows>")) {
            columnNames = null; // 다음 테이블을 위해 열 이름 재설정
            return null;
        }



        List<String> values = new ArrayList<>();

        while (!line.trim().equals("</row>")) {
            Matcher matcher = Pattern.compile("<(.*?)>(.*?)</\\1>").matcher(line);

            while (matcher.find()) {
                String value = matcher.group(2);
                values.add(value);
            }

            line = in.readLine();
        }


        String[] row = values.toArray(new String[0]);
        return new ArrayIterator(row);
    }



    public void endTable() throws IOException {
    }

    private String extractValueFromTag(String line, String tagName) {
        String startTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";

        int startIndex = line.indexOf(startTag);
        int endIndex = line.indexOf(endTag);


        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            // 태그를 찾지 못하거나 순서가 올바르지 않음
            return "";
        }



        startIndex += startTag.length();
        return line.substring(startIndex, endIndex);
    }


}


