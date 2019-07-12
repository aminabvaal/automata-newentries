package ir.ac.aut.god.automatanewentries.core;

import com.google.gson.Gson;
import ir.ac.aut.god.automatanewentries.model.CapSchool;
import ir.ac.aut.god.automatanewentries.model.NeededClass;
import ir.ac.aut.god.automatanewentries.model.PazireshType;
import ir.ac.aut.god.automatanewentries.model.School;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExcelReader {


    public static void gout(Object object) {
        System.out.println(new Gson().toJson(object));

    }

    public static void main(String[] args) throws IOException, InvalidFormatException {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("t4_945_1045");
        strings.add("t4_800_1000");
        strings.add("t4_1000_1200");

        ArrayList<String> strings1 = extractConfilicts(strings);

        gout(strings1);


        //    s();
//        cap();
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

                } else
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


    public static ArrayList<CapSchool> cap() throws IOException, InvalidFormatException {

        ArrayList<CapSchool> capSchools = new ArrayList<>();

        String SAMPLE_XLSX_FILE_PATH = "conf/cap.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        for (Row row : sheet) {
            String schoolName = dataFormatter.formatCellValue(row.getCell(0))
                    .replaceAll("ي", "ی");
            String cosCode = dataFormatter.formatCellValue(row.getCell(1));
            String paziresh = dataFormatter.formatCellValue(row.getCell(3));
            String cap = dataFormatter.formatCellValue(row.getCell(4));

            if (schoolName.isEmpty())
                continue;


            CapSchool capSchool = new CapSchool().setSchoolName(schoolName)
                    .setChertCode(cosCode).setCap(Integer.parseInt(cap));

            if (paziresh.equals("روزانه"))
                capSchool.setPazireshType(PazireshType.Awdi);
            else if (paziresh.equals("پرديس خودگردان"))
                capSchool.setPazireshType(PazireshType.Pardis);
            else throw new RuntimeException("what is type??????????????");


            capSchools.add(capSchool);

            System.err.println(new Gson().toJson(capSchool));
        }
        return capSchools;

    }


    static public ArrayList<String> extractConfilicts(ArrayList<String> times) {
        ArrayList<String> response = new ArrayList<>();
        for (String time : times) {
            String[] s = time.split("_");
            String numberofweek = s[0].substring(1);
            String st = s[1];
            String ft = s[2];
            int startTime = (Integer.parseInt(numberofweek)) * 2000 + Integer.parseInt(st);
            int finishTime = (Integer.parseInt(numberofweek)) * 2000 + Integer.parseInt(ft);

            String tis = time + ":";

            for (String tt : times) {
                String[] tts = tt.split("_");
                String ttnumberofweek = tts[0].substring(1);
                String ttst = tts[1];
                String ttft = tts[2];
                int ttstartTime = (Integer.parseInt(ttnumberofweek)) * 2000 + Integer.parseInt(ttst);
                int ttfinishTime = (Integer.parseInt(ttnumberofweek)) * 2000 + Integer.parseInt(ttft);

                boolean b = !(
                        ((ttstartTime < startTime) && (ttfinishTime <= startTime)) ||
                                ((ttstartTime >= finishTime) && (ttfinishTime > finishTime))
                );

                boolean b0 = (startTime == ttstartTime) && (ttfinishTime == finishTime);
                if (b && !b0) {
                    tis += tt + ",";
                }
            }
            response.add(tis);
        }
        return response;
    }

}