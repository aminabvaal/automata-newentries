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

import static ir.ac.aut.god.automatanewentries.core.SchoolReader.prepareSchools;

public class ExcelReader {


    public static void gout(Object object) {
        System.out.println(new Gson().toJson(object));

    }

    public static void main(String[] args) throws IOException, InvalidFormatException {


        ArrayList<School> schools = prepareSchools();
        ArrayList<CapSchool> caps = cap();

        caps.forEach(capSchool -> {
            String schoolName = capSchool.getSchoolName();
            schools.stream().filter(school -> school.getName().replaceAll("","")
                    .equals(schoolName.replaceAll("","")))
                    .forEach(ExcelReader::gout);
            ;


        });


//        prepareSchools();
//        System.exit(0);
//
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("t4_945_1045");
//        strings.add("t4_800_1000");
//        strings.add("t4_1000_1200");
//
//        ArrayList<String> strings1 = extractConfilicts(strings);
//
//        gout(strings1);


        //    s();
//        cap();
        System.exit(0);

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
            else if (paziresh.equals("پردیس خودگردان"))
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


    public static void dd() throws IOException, InvalidFormatException {


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
                    ArrayList<String> stringArrayList = hash.get(sepCourse);
                    stringArrayList.add(sepTadrisyar);
                    hash.put(sepCourse, stringArrayList);

                } else {
                    ArrayList<String> stringArrayList = new ArrayList<String>();
                    stringArrayList.add(sepTadrisyar);
                    hash.put(sepCourse, stringArrayList);
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
                String day = tarikh[2];

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


}