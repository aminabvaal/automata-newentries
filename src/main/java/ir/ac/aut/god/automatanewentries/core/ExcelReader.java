package ir.ac.aut.god.automatanewentries.core;

import com.google.gson.Gson;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String SAMPLE_XLSX_FILE_PATH = "conf/freshEnrollCourses.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
        Sheet sheet = workbook.getSheetAt(0);
        HashSet<String> courses = new HashSet<>();
        String headOfCollege = "";
        String sS = "";
        String typeOfentrance = "";
        int priority = 0;
        int priorityKHodgardan = 0;
        for (Row row : sheet) {
            try {

                String possibleCollege = row.getCell(0).getStringCellValue();
                if (!possibleCollege.contains("تدريس\u200Cيار")) {
                    System.out.println((priority++));
                    System.out.println(possibleCollege);

                }


            } catch (Exception e) {

            }
        }

        workbook.close();

    }


    public static void s() throws IOException, InvalidFormatException {
        String SAMPLE_XLSX_FILE_PATH = "conf/colleagereqs.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
        Sheet sheet = workbook.getSheetAt(0);
        HashSet<String> courses = new HashSet<>();
        String headOfCollege = "";
        String sS = "";
        String typeOfentrance = "";
        int priority = 0;
        int priorityKHodgardan = 0;
        for (Row row : sheet) {
            try {
                String possibleCollege = row.getCell(0).getStringCellValue();
                if (!possibleCollege.isEmpty()) {
                    headOfCollege = possibleCollege;
                }

                priority = (int) row.getCell(4).getNumericCellValue();
                priorityKHodgardan = (int) row.getCell(5).getNumericCellValue();
                typeOfentrance = row.getCell(3).getStringCellValue();
                sS = row.getCell(6).getStringCellValue();
                String cs = row.getCell(1).getStringCellValue();
                System.err.println(priorityKHodgardan + headOfCollege + " : " + cs + typeOfentrance + priority + sS);


            } catch (Exception e) {

            }
        }

        workbook.close();

    }

}