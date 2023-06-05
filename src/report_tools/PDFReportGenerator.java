package report_tools;
/*
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;

public class PDFReportGenerator {
    public static void main(String[] args) throws IOException {

        String filePath = "FileReport.json";
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(filePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
        JsonObject resultsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("results");

        String json = resultsObject.toString();

        // Parse the JSON string into a JsonObject
        JsonObject updatedJsonObject = gson.fromJson(json, JsonObject.class);

        // Iterate over the entries in the JsonObject and print the breakdown
        for (String key : updatedJsonObject.keySet()) {
            JsonObject engineObject = updatedJsonObject.getAsJsonObject(key);

            String category = engineObject.get("category").toString();
            String engineName = engineObject.get("engine_name").toString();
            String engineVersion = engineObject.get("engine_version").toString();
            String result = engineObject.get("result").toString();
            String method = engineObject.get("method").toString();
            String engineUpdate = engineObject.get("engine_update").toString();

            System.out.println("Engine: " + engineName);
            System.out.println("Category: " + category);
            System.out.println("Engine Version: " + engineVersion);
            System.out.println("Result: " + result);
            System.out.println("Method: " + method);
            System.out.println("Engine Update: " + engineUpdate);
            System.out.println();
        }
    }
}
*/
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class PDFReportGenerator {
    private static final float PAGE_MARGIN = 50;
    private static final float LINE_HEIGHT = 14;

    public static void main(String[] args) throws IOException {

        String filePath = "FileReport.json";
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(filePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
        JsonObject resultsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("results");

        // Create the PDF document
        PDDocument document = new PDDocument();

        // Create a new page
        PDPage page = new PDPage();
        document.addPage(page);

        // Start a new content stream
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Set font properties
        contentStream.setFont(PDType1Font.HELVETICA, 12);

        // Set starting y position for the content
        float y = page.getMediaBox().getHeight() - PAGE_MARGIN;

        // Iterate over the entries in the JsonObject and add the breakdown information to the document
        for (String key : resultsObject.keySet()) {
            JsonObject engineObject = resultsObject.getAsJsonObject(key);

            String category = engineObject.get("category").toString();
            String engineName = engineObject.get("engine_name").toString();
            String engineVersion = engineObject.get("engine_version").toString();
            String result = engineObject.get("result").toString();
            String method = engineObject.get("method").toString();
            String engineUpdate = engineObject.get("engine_update").toString();

            // Check if the remaining space on the page is enough to fit the content
            if (y - LINE_HEIGHT < PAGE_MARGIN) {
                // Add a new page if there is not enough space
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                y = page.getMediaBox().getHeight() - PAGE_MARGIN;
            }

            // Add the breakdown information to the document
            contentStream.beginText();
            contentStream.newLineAtOffset(PAGE_MARGIN, y);
            contentStream.showText("Engine: " + engineName);
            contentStream.newLine();
            contentStream.showText("Category: " + category);
            contentStream.newLine();
            contentStream.showText("Engine Version: " + engineVersion);
            contentStream.newLine();
            contentStream.showText("Result: " + result);
            contentStream.newLine();
            contentStream.showText("Method: " + method);
            contentStream.newLine();
            contentStream.showText("Engine Update: " + engineUpdate);
            contentStream.newLine();
            contentStream.endText();

            y -= LINE_HEIGHT;
        }
        contentStream.close();
        // Save the document
        document.save(new FileOutputStream("Report2.pdf"));

        // Close the content stream
        contentStream.close();

        // Close the document
        document.close();

        System.out.println("PDF report created: Report.pdf");
    }
}
