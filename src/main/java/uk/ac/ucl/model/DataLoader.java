package uk.ac.ucl.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DataLoader {
    public DataFrame loadDataFrame(String path) {
        DataFrame dataFrame = new DataFrame();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            List<String> columnNames = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",", -1); //-1 for to include empty strings and the very last element
                if (columnNames == null) {
                    columnNames = Arrays.asList(values);
                    for (String columnName : columnNames) {
                        dataFrame.addColumn(columnName.trim()); //delete whitespaces even though csv is well formatted
                    }
                    continue;
                }
                for (int i = 0; i < values.length; i++) {
                    if (i < columnNames.size()) {
                        dataFrame.addValue(columnNames.get(i), values[i].trim());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the CSV file: " + e.getMessage());
        }
        return dataFrame;
    }
}
