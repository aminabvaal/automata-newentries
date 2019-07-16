package ir.ac.aut.god.automatanewentries.core;

import com.google.gson.Gson;
import ir.ac.aut.god.automatanewentries.model.NeededClass;
import ir.ac.aut.god.automatanewentries.model.PazireshType;
import ir.ac.aut.god.automatanewentries.model.School;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public static void main(String[] args) throws IOException, InvalidFormatException {

//        prepareSchools();
//        System.exit(0);


        String SAMPLE_XLSX_FILE_PATH = "conf/freshEnrollCourses.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
        Sheet sheetAt1 = workbook.getSheetAt(1);
        Sheet sheetAt0 = workbook.getSheetAt(0);
        HashMap<String, String> hashing = new HashMap<>();
        HashMap<String, ArrayList<String>> hash = new HashMap<>();
        DataFormatter dataFormatter = new DataFormatter();
        ArrayList<String> css = new ArrayList<>();
        ArrayList<String> tss = new ArrayList<>();

        for (Row row : sheetAt1) {
            try {
                String tadrisyarCourseId = dataFormatter.formatCellValue(row.getCell(1));
                String tadrisyarGroupId = dataFormatter.formatCellValue(row.getCell(2));

                String CourseId = dataFormatter.formatCellValue(row.getCell(3));
                String GroupId = dataFormatter.formatCellValue(row.getCell(4));

                String sepCourse = CourseId + "__" + GroupId;
                String sepTadrisyar = tadrisyarCourseId + "__" + tadrisyarGroupId;


                css.add(sepCourse);
                tss.add(sepTadrisyar);


                if (hash.containsKey(sepCourse)) {
                    ArrayList<String> strings = hash.get(sepCourse);
                    strings.add(sepTadrisyar);
                    hash.put(sepCourse, strings);

                } else {
                    ArrayList<String> strings = new ArrayList<String>();
                    strings.add(sepTadrisyar);
                    hash.put(sepCourse, strings);
                }


            } catch (Exception e) {
                System.out.printf(e.getLocalizedMessage());
            }
            System.out.println(hashing.keySet().size());
        }
        System.out.println(css.size());
        System.out.println(tss.size());
        System.out.println(hash.keySet().size());


        for (Row row : sheetAt0) {
            try {
                ArrayList<String> times = new ArrayList<>();
                String courseName = dataFormatter.formatCellValue(row.getCell(0));
                String courseId = dataFormatter.formatCellValue(row.getCell(1));
                String groupId = dataFormatter.formatCellValue(row.getCell(2));
                String pazireshCode = dataFormatter.formatCellValue(row.getCell(5));
                String sexualCode = dataFormatter.formatCellValue(row.getCell(7));


                String id = courseId + "__" + groupId;

                System.out.println(new Gson().toJson(hash.get(id)));


                String firstTime = "t" + dataFormatter.formatCellValue(row.getCell(9))
                        + "_" + dataFormatter.formatCellValue(row.getCell(10))
                        + "_" + dataFormatter.formatCellValue(row.getCell(11));

                times.add(firstTime);
                if (!dataFormatter.formatCellValue(row.getCell(12)).equals("-1")) {
                    String second = "t" + dataFormatter.formatCellValue(row.getCell(12))
                            + "_" + dataFormatter.formatCellValue(row.getCell(13))
                            + "_" + dataFormatter.formatCellValue(row.getCell(14));
                    times.add(second);
                }

                if (!dataFormatter.formatCellValue(row.getCell(15)).equals("-1")) {
                    String third = "t" + dataFormatter.formatCellValue(row.getCell(15))
                            + "_" + dataFormatter.formatCellValue(row.getCell(16))
                            + "_" + dataFormatter.formatCellValue(row.getCell(17));
                    times.add(third);
                }

                String dateOfExam = dataFormatter.formatCellValue(row.getCell(18));
                String[] tarikh = dateOfExam.split("/");
                String year = tarikh[0];
                String month = tarikh[1];
                String day= tarikh[2];

                String timeofStartExam = dataFormatter.formatCellValue(row.getCell(19));
                String timeofStopExam = dataFormatter.formatCellValue(row.getCell(20));
                String capacity = dataFormatter.formatCellValue(row.getCell(21));


                System.out.println(new Gson().toJson(times));
                System.out.println(new Gson().toJson(dateOfExam));


            } catch (Exception e) {

            }

        }


        workbook.close();
    }


    public static void prepareSchools() throws IOException, InvalidFormatException {
        String SAMPLE_XLSX_FILE_PATH = "conf/colleagereqs.xlsx";

        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

        Sheet sheet = workbook.getSheetAt(0);

        String headOfCollege = "";
        String mustGroups = "";
        String typeOfentrance = "";
        String courseId = "";
        int priority = 0;
        int priorityKHodgardan = 0;

        DataFormatter dataFormatter = new DataFormatter();

        ArrayList<School> schools = new ArrayList<>();
        ArrayList<NeededClass> neededClasses = new ArrayList<>();

// Inside loop
        for (Row row : sheet) {

            School school;
            try {
                String possibleCollege = row.getCell(0).getStringCellValue();

                if (!possibleCollege.isEmpty()) {

                    headOfCollege = possibleCollege;
                    school = new School();
                    neededClasses = new ArrayList<>();
                    school.setName(headOfCollege);
                    school.setNeededClasses(neededClasses);
                    schools.add(school);
                }


                NeededClass neededClass = new NeededClass();
                neededClasses.add(neededClass);


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
        System.err.println(new Gson().toJson(schools));


        workbook.close();

    }

}