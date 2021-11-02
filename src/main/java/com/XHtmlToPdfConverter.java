package com;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.*;
import java.nio.file.FileSystems;

public class XHtmlToPdfConverter {

    final static String OUTPUT_PDF_FILE = "C:\\backup\\Test-7.pdf";

    final static String HTML_CODE = "<html lang=\"en\">\n" +
            "  <head>\n" +
            "    <title>HTML File</title>  \n" +
            "    <link href=\"../css/style.css\" rel=\"stylesheet\" >\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <h1>HTML to PDF Java Example</h1>\n" +
            "    <p style=\"color:#F80000; font-size:20px\">Inline CSS</p>\n" +
            "    <p class=\"fontclass\">styling from font</p>\n" +
            "    <p class=\"styleclass\">external CSS class</p>\n" +
            "  </body>\n" +
            "</html>";

    final static String HTML_CODE2 = "<html lang=\"en\">\n" +
            "  <head>\n" +
            "    <title>HTML File</title>  \n" +
            "    <link href=\"../css/style.css\" rel=\"stylesheet\" >\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <h4>HTML to PDF Java Example</h4>\n" +
            "  </body>\n" +
            "</html>";

    final static String HTML_CODE3 = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            "table, th, td {\n" +
            "  border: 1px solid black;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h1>The th element</h1>\n" +
            "\n" +
            "<p>The th element defines a header cell in a table:</p>\n" +
            "\n" +
            "<table>\n" +
            "  <tr>\n" +
            "    <th>Month</th>\n" +
            "    <th>Savings</th>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>January</td>\n" +
            "    <td>$100</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>February</td>\n" +
            "    <td>$80</td>\n" +
            "  </tr>\n" +
            "</table>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";

    private XHtmlToPdfConverter() {
        super();
    }

    private File fileFromString(String htmlCode) {
        File htmlFile = null;
        FileWriter fileWriter = null;
        try {
            htmlFile = File.createTempFile("htmlToPdf-", ".tmp");
            fileWriter = new FileWriter(htmlFile);
            fileWriter.write(htmlCode);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException("can not convert HTML TO file");
            }
        }
        return htmlFile;
    }

    public static void main(String[] args) {
        try {
            XHtmlToPdfConverter converter = new XHtmlToPdfConverter();
            File htmlFile = converter.fileFromString(HTML_CODE);
            //File htmlFile = new File("C:\\Users\\mange\\Downloads\\report_html.html");
            org.w3c.dom.Document docUsingJsoup = converter.createXHtml(htmlFile);
            converter.xhtmlToPdf(docUsingJsoup, OUTPUT_PDF_FILE);
        } catch (IOException e) {
            System.out.println("Error, converting HTML to PDF " + e.getMessage());
            e.printStackTrace();
        }
    }

    private org.w3c.dom.Document createXHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        System.out.println("created XHtml document.");
        return new W3CDom().fromJsoup(document);
    }


    private void xhtmlToPdf(org.w3c.dom.Document doc, String outputPdf) throws IOException {
        String baseUri = FileSystems.getDefault()
                .getPath("C:/", "backup/", "src/main/resources")
                .toUri()
                .toString();
        OutputStream os = new FileOutputStream(outputPdf);
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useDefaultPageSize(260, 300, BaseRendererBuilder.PageSizeUnits.MM);

        builder.withUri(outputPdf);
        builder.toStream(os);
        // we can add extra fonts, if needed.
        //builder.useFont(new File(getClass().getClassLoader().getResource("fonts/PRISTINA.ttf").getFile()), "PRISTINA");
        builder.withW3cDocument(doc, baseUri);
        builder.run();
        System.out.println("COMPLETED..");
        os.close();
    }
}
