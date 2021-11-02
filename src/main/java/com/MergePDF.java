package com;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import java.io.File;
import java.io.IOException;

public class MergePDF {

    final static File file1 = new File("C:\\backup\\pdf\\sample.pdf");
    final static File file2 = new File("C:\\backup\\pdf\\sample_1.pdf");
    final static String file_dest = "C:\\backup\\pdf\\combined_6.pdf";

    public static void main(String[] args) throws IOException {
        PDFMergerUtility obj = new PDFMergerUtility();
        obj.setDestinationFileName(file_dest);
        obj.addSource(file1);



        obj.addSource(file2);
        obj.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        System.out.println("PDF Documents merged to a single file");
    }
}
