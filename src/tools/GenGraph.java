package tools;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenGraph {
    public static void generate(String csvFilePath, String chartFilePath) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (FileReader fileReader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            Map<String, Integer> categoryCount = new HashMap<>();

            for (CSVRecord record : csvParser) {
                String category = record.get("category"); // Assuming the "category" column contains categories

                categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
            }

            for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
                String category = entry.getKey();
                int count = entry.getValue();

                dataset.addValue(count, "Count", category);
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Category Chart",
                    "Results based on categories",
                    "Count",
                    dataset
            );

            barChart.getPlot().setBackgroundPaint(Color.WHITE);

            File chartFile = new File(chartFilePath);

            ChartUtils.saveChartAsJPEG(chartFile, barChart, 800, 600);

            System.out.println("Summary chart is generated successfully.");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
