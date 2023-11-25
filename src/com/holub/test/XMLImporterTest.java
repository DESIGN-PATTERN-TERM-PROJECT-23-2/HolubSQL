package com.holub.test;
import com.holub.database.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


class XMLImporterTest {


    @DisplayName("XML Importer Test")
    @Test
    public void testImport() throws IOException {
        Reader reader = new FileReader("people.xml");
        XMLImporter importer = new XMLImporter(reader);

        try {
            importer.startTable();

            // 테이블 이름 가져오기
            String tableName = importer.loadTableName();
            Assertions.assertEquals("people",tableName);

            // 열의 수 가져오기
            int width = importer.loadWidth();
            Assertions.assertEquals(3,width);

            // 열 이름 가져오기
            Iterator<String> columnNamesIterator = importer.loadColumnNames();
            List<String> columnList = new ArrayList<>();
            while (columnNamesIterator.hasNext()) {
                String next = columnNamesIterator.next();
                columnList.add(next);
            }
            Assertions.assertTrue(columnList.contains("last"));
            Assertions.assertTrue(columnList.contains("first"));
            Assertions.assertTrue(columnList.contains("addrId"));




            StringBuilder expectedOutput = new StringBuilder();
            expectedOutput.append("Holub Allen 1 \n");
            expectedOutput.append("Flintstone Wilma 2 \n");
            expectedOutput.append("Flintstone Fred 2 \n");

            StringBuilder actualOutput = new StringBuilder();

            Iterator rowIterator;
            while ((rowIterator = importer.loadRow()) != null) {
                while (rowIterator.hasNext()) {
                    actualOutput.append(rowIterator.next()).append(" ");
                    //System.out.println(actualOutput);
                }
                actualOutput.append("\n");
            }

            Assertions.assertEquals(expectedOutput.toString(), actualOutput.toString());

            // 테이블 마무리 및 리더 닫기
            importer.endTable();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }








    }

}