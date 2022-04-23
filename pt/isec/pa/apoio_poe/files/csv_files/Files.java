package pt.isec.pa.apoio_poe.files.csv_files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Files {
    public static List<List<String>> openFile(String pathFile) {
        List<List<String>> fileTokens = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                fileTokens.add(Arrays.asList(values));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileTokens;
    }
}
