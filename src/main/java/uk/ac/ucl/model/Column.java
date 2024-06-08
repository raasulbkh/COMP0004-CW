package uk.ac.ucl.model;

import java.util.ArrayList;

public class Column {
    private String name;
    private ArrayList<String> rows;

    public Column(String name) {
        this.name = name;
        this.rows = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public int getSize() {
        return rows.size();
    }

    public String getRowValue(int rowNumber) throws IndexOutOfBoundsException {
        if (rowNumber >= 0 && rowNumber < rows.size()) {
            return  rows.get(rowNumber);
        } else {
            throw new IndexOutOfBoundsException("Row number is out of bounds.");
        }
    }

    public void setRowValue(int rowNumber, String value) throws IndexOutOfBoundsException {
        if (rowNumber >= 0 && rowNumber < rows.size()) {
            rows.set(rowNumber, value);
        } else {
            throw new IndexOutOfBoundsException("Row number is out of bounds.");
        }
    }

    public void addRowValue(String value) {
        rows.add(value);
    }

    @Override
    public String toString() {
        return rows.toString();
    }
}
