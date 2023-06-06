package report_tools;

import com.aspose.words.*;

public class TxtToPDF {
    private String txtFilePath;
    private String pdfFilePath;
    public TxtToPDF(String txtFilePath, String pdfFilePath){
        this.txtFilePath = txtFilePath;
        this.pdfFilePath = pdfFilePath;
    }
    public void convert() throws Exception {
        Document doc = new Document(txtFilePath);
        doc.save(pdfFilePath);
    }
}
