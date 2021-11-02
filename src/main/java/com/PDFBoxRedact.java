package com;

import com.google.gson.Gson;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
public class PDFBoxRedact {

    final static int MARGIN = 10;

    //case 1
    //final static String SRC_FILE = "C:\\Users\\mange\\Downloads\\sample.pdf";
    //final static String DEST_FILE_PARTIAL = "C:\\Users\\mange\\Downloads\\sample_redact_";

    //case 2
    final static String SRC_FILE = "C:\\Users\\mange\\Downloads\\pdf-sample.pdf";
    final static String DEST_FILE_PARTIAL = "C:\\Users\\mange\\Downloads\\pdf-sample_redact_";

    public static void redact(PDPageContentStream content, Color color, Rectangle rect, boolean fill) throws IOException {
        content.setStrokingColor(color);
        content.addRect(rect.x, rect.y, rect.width, rect.height);

        if (fill) {
            content.setNonStrokingColor(color);
            content.fill();
        } else {
            content.setStrokingColor(color);
            content.stroke();
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/redact")
    public static void execute(@RequestBody String payload) throws Exception {
        System.out.println("payload is " + payload);
        Gson gson = new Gson();
        Map<String, List> redactBoxMap = gson.fromJson(payload, Map.class);
        String DEST_FILE = DEST_FILE_PARTIAL + System.nanoTime() + ".pdf";
        PDDocument document = PDDocument.load(new File(SRC_FILE));
        PDPage sourcePage = document.getPage(0);
        float pageMid = sourcePage.getMediaBox().getHeight()/2;
        PDPageContentStream contentStream = new PDPageContentStream(document, sourcePage, PDPageContentStream.AppendMode.APPEND, false, false);
        java.util.List<Map> boxes = (java.util.List) redactBoxMap.get("rectSet");
        for (Map box : boxes) {
            if (box == null) {
                continue;
            }
            Rectangle rectangle = new Rectangle();
            PDRectangle mediaBox = sourcePage.getMediaBox();
            int X = (int)((double)box.get("startX"))/2;
            int Y = (int)((double)box.get("startY"))/2;
            int W = (int)(((double)box.get("w"))/2) ;
            int H = (int)(((double)box.get("h"))/2) ;

            float tempY =  (Y < pageMid) ? pageMid + (pageMid - (Y + 12.3f)) : pageMid - (pageMid - (Y + 12.3f));
            rectangle.setRect(X-5, (int)tempY, W, H);
            redact(contentStream, Color.BLACK, rectangle, true);
        }

        contentStream.close();
        document.save(DEST_FILE);
        document.close();
        System.out.println("redact completed.");
    }
}
