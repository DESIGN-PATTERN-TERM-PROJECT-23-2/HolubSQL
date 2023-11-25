package com.holub.database;
import com.holub.tools.ArrayIterator;

import java.io.*;
import java.util.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLImporter implements Table.Importer {
    private XMLStreamReader reader;  // null once end-of-file reached
    private String[] columnNames;
    private String tableName;

    public XMLImporter(Reader in) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        reader = factory.createXMLStreamReader(in);
    }

    public void startTable() throws IOException {
        try {
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    tableName = reader.getLocalName().trim();
                    break;
                }
            }

            // Assuming the first child of the table element contains column names
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    String rowElement = reader.getLocalName();
                    reader.next(); // Move to text node
                    columnNames = reader.getText().split("\\s*,\\s*");
                    break;
                }
            }
        } catch (XMLStreamException e) {
            throw new IOException(e);
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
        Iterator row = null;
        try {
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    String rowElement = reader.getLocalName();
                    reader.next(); // Move to text node
                    String[] values = reader.getText().split("\\s*,\\s*");
                    row = new ArrayIterator(values);
                    break;
                } else if (event == XMLStreamConstants.END_ELEMENT && reader.getLocalName().equals(tableName)) {
                    reader.close();
                    reader = null;
                    break;
                }
            }
        } catch (XMLStreamException e) {
            throw new IOException(e);
        }
        return row;
    }

    public void endTable() throws IOException {
        if (reader != null) {
            try {
                while (reader.hasNext()) {
                    int event = reader.next();
                    if (event == XMLStreamConstants.END_ELEMENT && reader.getLocalName().equals(tableName)) {
                        reader.close();
                        reader = null;
                        break;
                    }
                }
            } catch (XMLStreamException e) {
                throw new IOException(e);
            }
        }
    }
}

