package com;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class XHtmlToPdfConverter {

    // TODO define all constants inside the another class.
    final static String UTF_8 = "UTF-8";
    final static float PAGE_WIDTH = 240f;
    final static float PAGE_HEIGHT = 290f;
    final static BaseRendererBuilder.PageSizeUnits PAGE_SIZE_UNIT = BaseRendererBuilder.PageSizeUnits.MM;

    static String HTML_CODE = "<h1>HTML to PDF Java Example</h1>";

    public XHtmlToPdfConverter() {
        super();
    }

    /**
     * This call will convert the HTML code to the byte[] -
     * Step 1 : convert HTML to XHTML.
     * Step 2 : Convert XHTML To PDF byte array.
     *
     * @param htmlCode
     * @return
     * @throws IOException
     */
    final private byte[] convertHtmlToPdf(final String htmlCode) {
        return xhtmlToPdf(xHtmlFromString(htmlCode));
    }

    /**
     * NOTE - THIS IS THE TEST METHOD
     * TEST METHOD
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            final XHtmlToPdfConverter converter = new XHtmlToPdfConverter();
            byte[] pdfBytes = converter.convertHtmlToPdf(HTML_CODE);

            //Below 3 lines are to test the convertHtmlToPdf method.
            OutputStream out = new FileOutputStream("C:\\backup\\Test-14.pdf");
            out.write(pdfBytes);
            out.close();
        } catch (IOException e) {
            System.out.println("Error, converting HTML to PDF " + e.getMessage());
        }
    }

    /**
     * @param htmlCode
     * @return
     */
    final private org.w3c.dom.Document xHtmlFromString(final String htmlCode) {
        final Document document = Jsoup.parse(htmlCode, UTF_8);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return new W3CDom().fromJsoup(document);
    }

    /**
     * If anything goes wrong, no point to send the letter so we need to stop the current letter generation with error.
     * Do you agree?
     *
     * @param doc
     * @return
     * @throws IOException
     */
    final private byte[] xhtmlToPdf(final org.w3c.dom.Document doc) {
        final ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        try {
            final PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useDefaultPageSize(PAGE_WIDTH, PAGE_HEIGHT, PAGE_SIZE_UNIT)
                    .toStream(pdfStream)
                    .withW3cDocument(doc, null)
                    .run();
            return pdfStream.toByteArray();
        } catch (IOException e) {
            // TODO add logger with the error and create Exception classes, depending on business cases.
            throw new RuntimeException("we can not proceed the request because of ", e);
        } finally {
            try {
                pdfStream.close();
            } catch (IOException e) {
                // TODO add logger with the error and create Exception classes, depending on business cases.
                throw new RuntimeException("we can not proceed the request because of ", e);
            }
            // TODO Logger to indicate SUCCESS, with some ID or Reference.
        }
    }
}
