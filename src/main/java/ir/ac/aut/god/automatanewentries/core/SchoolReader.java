package ir.ac.aut.god.automatanewentries.core;

import com.google.gson.Gson;
import ir.ac.aut.god.automatanewentries.model.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SchoolReader {


    public static void gout(Object object) {
        System.out.println(new Gson().toJson(object));

    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        prepareSchools();

    }


    public static ArrayList<School> prepareSchools() throws IOException, InvalidFormatException {
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
        ArrayList<String> codes = new ArrayList<>();


        SameSchools sameSchools = new SameSchools();

// Inside loop
        for (Row row : sheet) {

            School school;
            try {
                String possibleCollege = row.getCell(0).getStringCellValue();

                if (!possibleCollege.isEmpty()) {

                    headOfCollege = possibleCollege;
                    school = new School();

                    String code = dataFormatter.formatCellValue(row.getCell(9));

                    String firstSchool = dataFormatter.formatCellValue(row.getCell(8));

                    school.setFromFirstSchool(firstSchool.length() != 0);

                    school.setCode(code);

                    codes.add(code);

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
        System.err.println(new Gson().toJson(schools));


        int i = 0;
        while (i < codes.size()) {
            String code = codes.get(i);
            ArrayList<String> strings = new ArrayList<>();
            for (String s : codes) {
                if (s.equals(code)) {
                    strings.add(s);
                }
            }
            if (strings.size() > 1) {
                sameSchools.getSameCodes().add(strings);
            }
            codes.remove(code);
            i++;
        }

        gout(sameSchools);

        String dp = sameSchools.getSameCodes().get(0).get(0);
        schools.stream().filter(school -> school.getCode().equals(dp)).forEach(SchoolReader::gout);

        workbook.close();

        return schools;
    }


}