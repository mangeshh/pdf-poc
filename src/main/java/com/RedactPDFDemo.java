package com;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;

public class RedactPDFDemo {

    final private class PDFMetaSrc {
        private final PdfReader reader;
        private final FileOutputStream outputStream;
        private final Integer pageNumber;
        private final RedactRectangle redactRectangle;

        private PDFMetaSrc(PdfReader reader, FileOutputStream outputStream, RedactRectangle redactRectangle, Integer pageNumber) {
            this.reader = reader;
            this.outputStream = outputStream;
            this.pageNumber = pageNumber;
            this.redactRectangle = redactRectangle;
        }
    }

    final private class RedactRectangle {
        private float x_coordinate;
        private float y_coordinate;
        private float height;
        private float width;

        private RedactRectangle(float x, float y, float h, float w) {
            this.x_coordinate = x;
            this.y_coordinate = y;
            this.height = h;
            this.width = w;
        }
    }

    final private void execute(PDFMetaSrc pdfMetadata) throws IOException, DocumentException {
        PdfStamper stamper = new PdfStamper(pdfMetadata.reader, pdfMetadata.outputStream);
        PdfContentByte content = stamper.getOverContent(pdfMetadata.pageNumber);
        content.setColorFill(BaseColor.BLACK);
        content.rectangle(pdfMetadata.redactRectangle.x_coordinate, pdfMetadata.redactRectangle.y_coordinate, pdfMetadata.redactRectangle.height, pdfMetadata.redactRectangle.width);
        content.fill();
        stamper.close();
        pdfMetadata.reader.close();
    }

    public static void main(String[] args) throws Exception {

        final RedactPDFDemo redact = new RedactPDFDemo();

        final String SRC_FILE = args[0];
        final String DEST_FILE = args[1];

        final float x_coordinate = 264f;
        final float y_coordinate = 141f;
        final float height = 54f;
        final float width = 20f;

        final int pageNumber = 2;

        final RedactRectangle redactRectangle = redact.new RedactRectangle(x_coordinate, y_coordinate, height, width);
        final PDFMetaSrc pdfMetadata = redact.new PDFMetaSrc(new PdfReader(SRC_FILE),
                new FileOutputStream(DEST_FILE), redactRectangle, pageNumber);
        redact.execute(pdfMetadata);
    }

}
