package uk.ac.ucl.model;

import java.util.*;

public class DataFrame {
    private LinkedHashMap<String, Column> columns;

    public DataFrame() {
        this.columns = new LinkedHashMap<>();
    }

    public void addColumn(String columnName) {
        columns.putIfAbsent(columnName, new Column(columnName));
    }

    public ArrayList<String> getColumnNames(){
        return new ArrayList<>(columns.keySet());
    }

    public int getRowCount(String columnName) throws NoSuchElementException {
        if (columns.containsKey(columnName)) {
            return columns.get(columnName).getSize();
        } else {
            throw new NoSuchElementException("Column not found");
        }
    }

    public String getValue(String columnName, int row) throws NoSuchElementException, IndexOutOfBoundsException {
        if(columns.containsKey(columnName)) {
            return  columns.get(columnName).getRowValue(row);
        } else {
            throw new NoSuchElementException("Columnn not found");
        }
    }

    public void putValue(String columnName, int row, String value) throws NoSuchElementException, IndexOutOfBoundsException {
        if(columns.containsKey(columnName)) {
            if (row >= columns.get(columnName).getSize()) {
                throw new IndexOutOfBoundsException(("Row number is out of bounds"));
            }
            columns.get(columnName).setRowValue(row, value);
        } else {
            throw new NoSuchElementException("Column not found");
        }
    }

    public void addValue(String columnName, String value) throws  NoSuchElementException {
        if (columns.containsKey(columnName)) {
            columns.get(columnName).addRowValue(value);
        } else {
            throw new NoSuchElementException("column not found");
        }
    }

    // Later used to convert the DataFrame into a list of maps for JSON file (one map per row)
    public List<Map<String, String>> toList() {
        List<Map<String, String>> list = new ArrayList<>();
        int numRows = this.getRowCount("ID");
        for (int i = 0; i < numRows; i++) {
            Map<String, String> row = new LinkedHashMap<>();
            for (String columnName : this.getColumnNames()) {
                row.put(columnName, this.getValue(columnName, i));
            }
            list.add((row));
        }
        return list;
    }

    @Override
    public java.lang.String toString() {
        return columns.toString();
    }
}
