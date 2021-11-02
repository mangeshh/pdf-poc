package com;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class JpegToPDFConverter {

    public static void main(String[] args) throws Exception {
        String inputPath = "C://Users//mange//Downloads//IMG_20210910_124006544.jpg";
        String outputPath = "C://Users//mange//Downloads//IMG_20210910_124006544.pdf";
        URL url = new File(inputPath).toURI().toURL();
        Document document = new Document(PageSize.A2, 12, 12, 12, 12);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();
        Image image = Image.getInstance(url);
        document.add(image);
        document.close();
    }
}
