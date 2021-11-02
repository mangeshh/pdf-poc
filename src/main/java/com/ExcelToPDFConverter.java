package com;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

public class ExcelToPDFConverter {

    public static final String[] FILE_TYPES = new String[]{"xls", "xlsx"};

    public static void main(String[] args) throws Exception {
        String inputPath = "C:\\Users\\mange\\Downloads\\IssuesF_B_Dependencies.xlsx";
        String outputPath = "C:\\Users\\mange\\Downloads\\IssuesF_B_Dependencies.pdf";
        // Read the Excel file in binary format into FileInputStream
        InputStream input_document = new FileInputStream(inputPath);

        // Read workbook into XSSFWorkbook
        XSSFWorkbook workbook = new XSSFWorkbook(input_document);
        // Read worksheet into XSSFSheet
        XSSFSheet worksheet = workbook.getSheetAt(0);
        // To iterate over the rows
        Iterator<Row> rows = worksheet.iterator();
        Iterator<Row> rowsTemp = worksheet.iterator();
        // We will create output PDF document objects at this point
        Document pdf = new Document();
        PdfWriter.getInstance(pdf, new FileOutputStream(outputPath));
        pdf.open();
        // Dynamic, Considering the above one as a header
        int noOfColumns = 0;
        try {
            while(rowsTemp.hasNext()){
                noOfColumns = Math.max(noOfColumns, rowsTemp.next().getPhysicalNumberOfCells());
                rowsTemp.next();
            }
        } catch (Exception e){

        }
        PdfPTable xlsTable = new PdfPTable(noOfColumns);

        // To dynamically add new data to the table
        PdfPCell tableCell;

        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                System.out.println(row.getRowNum() + " | " + cell.getColumnIndex() + " | " + cell.getCellType() + " | " + cell);

                // Identify CELL type
                switch (cell.getCellType()) {
                    case STRING:
                        tableCell = new PdfPCell(new Phrase(cell.getStringCellValue()));
                        xlsTable.addCell(tableCell);
                        break;
                    case NUMERIC:
                        String doubleValue = Double.toString(cell.getNumericCellValue());
                        tableCell = new PdfPCell(new Phrase(doubleValue));
                        xlsTable.addCell(tableCell);
                        break;
                    case BOOLEAN:
                        String boolValue = Boolean.toString(cell.getBooleanCellValue());
                        tableCell = new PdfPCell(new Phrase(boolValue));
                        xlsTable.addCell(tableCell);
                        break;
                    case FORMULA:
                        String formulaValue = Double.toString(cell.getNumericCellValue());
                        tableCell = new PdfPCell(new Phrase(formulaValue));
                        xlsTable.addCell(tableCell);
                        break;
                    case BLANK:
                        tableCell = new PdfPCell(new Phrase(cell.getStringCellValue()));
                        xlsTable.addCell(tableCell);
                        break;
                    case ERROR:
                        // Push the data from Excel to PDF Cell
                        tableCell = new PdfPCell(new Phrase(cell.getStringCellValue()));
                        xlsTable.addCell(tableCell);
                        break;
                }
            }
        }
        // Finally add the table to PDF document
        pdf.add(xlsTable);
        pdf.close();
        // we created our pdf file..
        input_document.close(); // close xls
        System.out.println("XLS " + inputPath + " to " + outputPath);
    }
}