package com;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DocxToPDFConverter {

    public static void main(String[] args) {
        String fileName = "C:\\Users\\mange\\Downloads\\I-485 Supplement J WorkSheet v21";
        try {
            InputStream templateInputStream = new FileInputStream(fileName+".docx");
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
            String outputfilepath = fileName+".pdf";
            FileOutputStream os = new FileOutputStream(outputfilepath);
            Docx4J.toPDF(wordMLPackage, os);
            os.flush();
            os.close();
        } catch (Throwable e) {

            e.printStackTrace();
        }
    }
}
