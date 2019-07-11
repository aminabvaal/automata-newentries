package ir.ac.aut.god.automatanewentries.core;

import com.google.gson.Gson;
import ir.ac.aut.god.automatanewentries.model.NeededClass;
import ir.ac.aut.god.automatanewentries.model.PazireshType;
import ir.ac.aut.god.automatanewentries.model.School;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public static void main(String[] args) throws IOException, InvalidFormatException {

        s();
        System.exit(0);


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
        String mustGroups = "";
        String typeOfentrance = "";
        String courseId = "";
        int priority = 0;
        int priorityKHodgardan = 0;

        DataFormatter dataFormatter = new DataFormatter();

        ArrayList<School> schools = new ArrayList<>();
// Inside loop
        for (Row row : sheet) {

            School school;
            try {
                String possibleCollege = row.getCell(0).getStringCellValue();
                if (!possibleCollege.isEmpty()) {
                    headOfCollege = possibleCollege;
                    school = new School();
                    school.setName(headOfCollege);
                }

                NeededClass neededClass = new NeededClass();


                priority = (int) row.getCell(4).getNumericCellValue();
                priorityKHodgardan = (int) row.getCell(5).getNumericCellValue();

                typeOfentrance = row.getCell(3).getStringCellValue();

                neededClass
                        .setPriotryOfAwdiType(priority)
                        .setPriotryOfPardisType(priorityKHodgardan);


                mustGroups = dataFormatter.formatCellValue(row.getCell(6));

                if (!mustGroups.isEmpty()) {
                    String[] split = mustGroups.split(",");
                    ArrayList<Integer> groups = new ArrayList<>();

                    for (String s : split) {
                        groups.add(new Integer(s));
                    }
                    neededClass.setPossibleGroups(groups);

                }
                else
                    neededClass.setPossibleGroups(null);

                courseId = dataFormatter.formatCellValue(row.getCell(2));
                String cs = row.getCell(1).getStringCellValue();


                System.err.println(priorityKHodgardan);


                neededClass.setCourseId(courseId)
                        .setCourseName(cs);

                if (typeOfentrance.equals("عادی"))
                    neededClass.setPazireshType(PazireshType.Awdi);
                else if (typeOfentrance.equals("پردیس"))
                    neededClass.setPazireshType(PazireshType.Pardis);
                else
                    neededClass.setPazireshType(PazireshType.Both);


                System.out.println(new Gson().toJson(neededClass));
//                System.exit(0);

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        workbook.close();

    }

}