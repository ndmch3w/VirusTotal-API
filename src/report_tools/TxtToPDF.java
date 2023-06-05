package report_tools;

import com.aspose.words.*;

public class TxtToPDF {
    public static void main(String[] args) throws Exception {
        Document doc = new Document("Results_txt/FileReport.txt");
        doc.save("Results_pdf/Test.pdf");
    }
}
