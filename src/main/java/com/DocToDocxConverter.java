/**
 * Need code Fix - TODO
 */







/*
package com;

import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DocToDocxConverter {

    static String fileName = "C:\\Users\\mange\\Downloads\\word_doc_test.";

    public static void main(String[] args) {
        String docPath = fileName + "doc";
        String docxPath = fileName + "docx";
        convertDoc(docPath);
        convertDocx(docxPath);
    }

    public static void convertDoc(String path) {
        try {
            HWPFDocumentCore wordDocument = WordToHtmlUtils.loadDoc(new FileInputStream(path));

            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .newDocument());
            wordToHtmlConverter.processDocument(wordDocument);
            Document htmlDocument = wordToHtmlConverter.getDocument();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(out);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            out.close();
            String result = new String(out.toByteArray());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void convertDocx(String path) {
        try {
            //convert .docx to HTML string
            InputStream in = new FileInputStream(new File(path));
            XWPFDocument document = new XWPFDocument(in);
            XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(new File("word/media")));
            OutputStream out = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(document, out, options);
            String html = out.toString();
            System.out.println(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
