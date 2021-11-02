package com;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.OutputStream;

public class DocxToHtmlConverter {

    public static void main(String[] args)  throws Exception {

        String fileName = "C:\\Users\\mange\\Downloads\\word_test.docx";
        String htmlName = "C:\\Users\\mange\\Downloads\\word_test.html";

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(fileName));

        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
        htmlSettings.setWmlPackage(wordMLPackage);
        htmlSettings.setImageDirPath("java.io.tmpdir");
        htmlSettings.setImageTargetUri("java.io.tmpdir");

        String htmlFilePath = htmlName;
        OutputStream os = new java.io.FileOutputStream(htmlFilePath);

        // write html
        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

    }
}
