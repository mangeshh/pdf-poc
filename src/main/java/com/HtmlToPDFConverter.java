package com;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HtmlToPDFConverter {

    public static void mainTest(String[] args) throws FileNotFoundException, IOException {
        String HTML = new String(Files.readAllBytes(Paths.get("C://Users//mange//Downloads//report_html.html")), StandardCharsets.UTF_8);
        HtmlConverter.convertToPdf(HTML, new FileOutputStream("C://Users//mange//Downloads//string-to-pdf.pdf"));
        System.out.println("PDF Created!");
    }

    public static void main(String[] args) throws Exception {

        String inputPath = "C://Users//mange//Downloads//report_html.html";
        String outputPath = "C://Users//mange//Downloads//report_html_11.pdf";

        File file = new File(outputPath);
        file.getParentFile().mkdirs();
        try {
            new HtmlToPDFConverter().createPdf(inputPath, outputPath);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

class TableHeader extends PdfPageEventHelper {
    String header;
    PdfTemplate total;

    public void setHeader(String header) {
        this.header = header;
    }

    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }

    // like a footer
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();

        try {
            table.setWidths(new int[]{24, 24, 2});
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setBorder(Rectangle.TOP);
            table.addCell(header);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            //table.addCell(String.format("%s - Page %d of", dateFormat.format(date), writer.getPageNumber()));
            PdfPCell cell = new PdfPCell(Image.getInstance(total));
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);
            table.writeSelectedRows(0, -1, 34, 60, writer.getDirectContent());
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
    }

}

    public void createPdf(String inputPath, String outputPath) throws IOException, DocumentException {
        Document document = new Document(PageSize.A3, 10, 10, 10, 15);
        PdfWriter writer1 = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        TableHeader event = new TableHeader();
        writer1.setPageEvent(event);
        document.open();

        // CSS
        String css = "* {margin:0; padding:0; text-indent:0; }\n" +
                "         .s1 { color: black; font-family:Arial, sans-serif; font-style: normal; font-weight: bold; text-decoration: none; font-size: 12pt; }\n" +
                "         .s2 { color: black; font-family:Arial, sans-serif; font-style: normal; font-weight: bold; text-decoration: none; font-size: 8pt; }\n" +
                "         .s3 { color: #0D79BF; font-family:Arial, sans-serif; font-style: normal; font-weight: normal; text-decoration: underline; font-size: 8pt; }\n" +
                "         .s4 { color: #0D79BF; font-family:Arial, sans-serif; font-style: normal; font-weight: normal; text-decoration: none; font-size: 8pt; }\n" +
                "         .s5 { color: black; font-family:Arial, sans-serif; font-style: normal; font-weight: normal; text-decoration: none; font-size: 8pt; }\n" +
                "         table, tbody {vertical-align: top; overflow: visible; }";

        // HTML
        String html = Utilities.readFileToString(inputPath);

        // decode html
       // html = StringEscapeUtils.unescapeHtml3(html);
        ElementList list = XMLWorkerHelper.parseToElementList(html, "* {margin:0; padding:0; text-indent:0; }\n" +
                "         .s1 { color: black; font-family:Arial, sans-serif; font-style: normal; font-weight: bold; text-decoration: none; font-size: 12pt; }\n" +
                "         .s2 { color: black; font-family:Arial, sans-serif; font-style: normal; font-weight: bold; text-decoration: none; font-size: 8pt; }\n" +
                "         .s3 { color: #0D79BF; font-family:Arial, sans-serif; font-style: normal; font-weight: normal; text-decoration: underline; font-size: 8pt; }\n" +
                "         .s4 { color: #0D79BF; font-family:Arial, sans-serif; font-style: normal; font-weight: normal; text-decoration: none; font-size: 8pt; }\n" +
                "         .s5 { color: black; font-family:Arial, sans-serif; font-style: normal; font-weight: normal; text-decoration: none; font-size: 8pt; }\n" +
                "         table, tbody {vertical-align: top; overflow: visible; }");

        for (Element e : list) {
            document.add(e);
        }

        /*if(mode.equals("print")){
        	PdfAction action = new PdfAction(PdfAction.PRINTDIALOG);
        	writer1.setOpenAction(action);
        }*/

        document.close();

    }

    // CSS
   /* private String readCSS() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        InputStream is = XMLWorkerHelper.class.getResourceAsStream("/default.css");
        while ((length = is.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return new String(baos.toByteArray());
    }*/
}
