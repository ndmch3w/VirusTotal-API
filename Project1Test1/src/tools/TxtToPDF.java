package tools;

import com.aspose.words.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TxtToPDF {
    public static void convert(String txtFilePath, String pdfFilePath, String imageFilePath) throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        String[] lines = getTextLines(txtFilePath);

        builder.insertImage(imageFilePath);
        for (int i = 0; i<3; i++){
            builder.writeln();
        }

        String reputationLine = lines[0].replace("Reputation@@ ", "Reputation: ");
        builder.write(reputationLine);
        for (int i = 0; i<3; i++){
            builder.writeln();
        }

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
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
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

    private static String[] getTextLines(String txtFilePath)  {
        Path path = Paths.get(txtFilePath);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.toArray(String[]::new);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new String[0];
    }
}

