package com;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageToPDFByBox {

    /**
     * Add an image to an existing PDF document.
     *
     * @param inputFile  The input PDF to add the image to.
     * @param imagePath  The filename of the image to put in the PDF.
     * @param outputFile The file to write to the pdf to.
     * @throws IOException If there is an error writing the data.
     */
    public void createPDFFromImage(String inputFile, String imagePath, String outputFile)
            throws IOException {
        try (PDDocument doc = PDDocument.load(new File(inputFile))) {
            PDPage page = doc.getPage(0);
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                float scale = 1f;
                contentStream.drawImage(pdImage, 20, 20, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
            }
            doc.save(outputFile);
        }
    }

    /**
     * This will load a PDF document and add a single image on it.
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws IOException {
        ImageToPDFByBox app = new ImageToPDFByBox();
        //app.createPDFFromImage("C:\\Users\\mange\\Downloads\\Sample31.pdf", "C:\\Users\\mange\\Downloads\\jpeg_image.jpg", "C:\\Users\\mange\\Downloads\\Sample30_JPEG.pdf");
        app.createPDFFromImage("C:\\Users\\mange\\Downloads\\Sample32.pdf", "C:\\Users\\mange\\Downloads\\tif_image.tif", "C:\\Users\\mange\\Downloads\\Sample32_TIF.pdf");
    }

}