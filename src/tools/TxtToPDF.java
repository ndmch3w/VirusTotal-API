package tools;

import com.aspose.words.*;

public class TxtToPDF {
    private String txtFilePath;
    private String pdfFilePath;

    public TxtToPDF(String txtFilePath, String pdfFilePath){
        this.txtFilePath = txtFilePath;
        this.pdfFilePath = pdfFilePath;
    }

    public static void convert(String txtFilePath, String pdfFilePath) throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        String[] lines = getTextLines(txtFilePath);
        Table table = builder.startTable();

        // Add table headers
        builder.insertCell();
        builder.write("Tool");
        builder.insertCell();
        builder.write("Category");
        builder.insertCell();
        builder.write("Result");
        builder.endRow();

        // Add table data
        for (String line : lines) {
            String[] parts = line.split(", ");

            builder.insertCell();
            builder.write(parts[0].split("@@ ")[1]);
            builder.insertCell();
            builder.write(parts[1].split("@@ ")[1]);
            builder.insertCell();
            builder.write(parts[2].split("@@ ")[1]);
            builder.endRow();
        }

        builder.endTable();
        doc.save(pdfFilePath);
    }

    private static String[] getTextLines(String txtFilePath) throws Exception {
        java.nio.file.Path path = java.nio.file.Paths.get(txtFilePath);
        return java.nio.file.Files.lines(path).toArray(String[]::new);
    }
}

