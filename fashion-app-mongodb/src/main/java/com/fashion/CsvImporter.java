package com.fashion;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvImporter {

    public List<Fashion> importFromCsv() {
        List<Fashion> fashions = new ArrayList<>();
        InputStream is = getClass().getClassLoader().getResourceAsStream("fashion.csv");
        if (is == null) {
            return fashions;
        }
        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            String[] line;
            boolean firstLine = true;
            while ((line = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.length < 10) {
                    continue;
                }
                Fashion fashion = new Fashion(
                        line[0].trim(),
                        line[1].trim(),
                        line[2].trim(),
                        line[3].trim(),
                        line[4].trim(),
                        line[5].trim(),
                        line[6].trim(),
                        Integer.parseInt(line[7].trim()),
                        line[8].trim(),
                        Long.parseLong(line[9].trim())
                );
                fashions.add(fashion);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return fashions;
    }
}
