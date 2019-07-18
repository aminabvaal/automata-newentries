package ir.ac.aut.god.automatanewentries.core;

import com.google.gson.Gson;
import ir.ac.aut.god.automatanewentries.io.MyReader;
import ir.ac.aut.god.automatanewentries.model.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
        ArrayList<String> codes = new ArrayList<>();
        ArrayList<NeededClass> optinalMarefs = null;
        ArrayList<NeededClass> neededClasses = null;


        SameSchools sameSchools = new SameSchools();

// Inside loop
        for (Row row : sheet) {

            School school;
            try {
                String possibleCollege = row.getCell(0).getStringCellValue();

                if (!possibleCollege.isEmpty()) {

                    optinalMarefs= new ArrayList<>();
                    neededClasses = new ArrayList<>();

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
                    school.setOptionalMarefs(optinalMarefs);
                    schools.add(school);
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



                neededClass.setCourseId(courseId)
                        .setCourseName(cs);


                if (typeOfentrance.equals("عادی"))
                    neededClass.setPazireshType(PazireshType.Awdi);
                else if (typeOfentrance.equals("پردیس"))
                    neededClass.setPazireshType(PazireshType.Pardis);
                else
                    neededClass.setPazireshType(PazireshType.Both);


                if (!(cs.contains("معارف") || cs.contains("درس عمومی"))) {
                    neededClasses.add(neededClass);
                    System.out.println(cs);
                } else {
                    Scanner scanner = MyReader.of("conf/MaAref").getScanner();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] split = line.split(",");
                        String csid = split[0];
                        String s = split[1];
                        String[] split1 = s.split("-");
                        ArrayList<Integer> integers = new ArrayList<>();
                        for (String s1 : split1) {
                            integers.add(Integer.parseInt(s1));
                        }


                        NeededClass neededClass1 = new NeededClass().setPazireshType(neededClass.getPazireshType())
                                .setCourseId(csid)
                                .setCourseName("")
                                .setPriotryOfAwdiType(neededClass.getPriotryOfAwdiType())
                                .setPriotryOfPardisType(neededClass.getPriotryOfPardisType())
                                .setPossibleGroups(integers);
                        optinalMarefs.add(neededClass1);

                    }

                }


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

        //gout(sameSchools);

        String dp = sameSchools.getSameCodes().get(0).get(0);
        schools.stream().filter(school -> school.getCode().equals(dp)).forEach(SchoolReader:://gout);

        workbook.close();

        return schools;
    }


}