package ir.ac.aut.god.automatanewentries.core;

import com.google.gson.Gson;
import ir.ac.aut.god.automatanewentries.model.*;
import ir.ac.aut.god.automatanewentries.model.Class;
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

    public static void exit() {
        System.exit(0);

    }

    public static void main(String[] args) throws IOException, InvalidFormatException {


        ArrayList<CapacityOfSchool> caps = capsOfSchools();

        ArrayList<Class> classes = freshEnrollCourses();
        ArrayList<School> schools = prepareSchools();

        caps.forEach(capacityOfSchool -> {

            String schoolName = capacityOfSchool.getSchoolName();

            schools.stream().filter(school -> school.getName().replaceAll(" ", "")
                    .equals(schoolName.replaceAll(" ", "")))
                    .forEach(school -> {
                        school.setCapacityOfSchool(capacityOfSchool);
                    });


        });


        ArrayList<Integer> prios = new ArrayList<>();
        for (int i = 0; i < schools.size(); i++) {
            if (schools.get(i).isFromFirstSchool())
                prios.add(i);
        }


        for (Integer prio : prios) {
            School school = schools.get(prio);

            ArrayList<Class> takableClasses = new ArrayList<>();


            ArrayList<NeededClass> neededClasses = school.getNeededClasses();
            for (NeededClass neededClass : neededClasses) {
                for (Class aClass : classes) {
                    if (aClass.getCourseId().equals(neededClass.getCourseId())) {
                        if (neededClass.getPossibleGroups() == null) {
                            takableClasses.add(aClass);
                        } else {
                            for (Integer possibleGroup : neededClass.getPossibleGroups()) {
                                if (possibleGroup == Integer.parseInt(aClass.getGroup())) {
                                    takableClasses.add(aClass);
                                }
                            }
                        }
                    }
                }
            }
            school.setTakableClasses(takableClasses);
        }
        gout(schools);


        for (Integer prio : prios) {
            School school = schools.get(prio);
            ArrayList<Class> takableClasses = school.getTakableClasses();
            ArrayList<NeededClass> neededClasses = school.getNeededClasses();

            for (NeededClass neededClass : neededClasses) {
                ArrayList<Class> newTakableClasses = getEarlyTimeOfCourses(takableClasses, neededClass);
//                school.setTakableClasses(newTakableClasses);

                int sizeOfTackables = newTakableClasses.size();

                int awdicapSchool = school.getCapacityOfSchool().getAwdicap();
                int pardiscap = school.getCapacityOfSchool().getPardiscap();


                if (sizeOfTackables==0)
                    System.out.println();
                int awdiDividedCap = awdicapSchool / sizeOfTackables;
                int pardisDividedCap = pardiscap / sizeOfTackables;

                PazireshType pazireshType = neededClass.getPazireshType();

                if (pazireshType == PazireshType.Both) {
                    int neededCap = awdicapSchool + pardiscap;
                    int neededCapPerPossibles = neededCap / sizeOfTackables;


                    ArrayList<Integer> capOfTakables = new ArrayList<>();


                    double taper=3;
                    while (true) {
                        int sum = 0;
                        int sumOfThis = 0;
                        for (Class newTakableClass : newTakableClasses) {
                            int capacity = newTakableClass.getCapacity();
                            int dividedCap = (int) (capacity / taper);

                            sumOfThis += dividedCap;
                            if (sumOfThis >= neededCap) {
                                break;
                            }
                            capOfTakables.add(capacity);
                            sum += capacity;
                        }

                        if (sumOfThis >= neededCap) {
                            break;
                        } else {
                            taper/=1.2;
                        }
                        System.out.println(sumOfThis);

                    }


                    gout(capOfTakables);

                }


            }


        }


        exit();


//        ArrayList<CapacityOfSchool> caps = capsOfSchools();


        gout(schools);

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

    private static ArrayList<Class> getEarlyTimeOfCourses(ArrayList<Class> takableClasses, NeededClass neededClass) {
        ArrayList<Integer> indexOfthem = new ArrayList<>();
        ArrayList<Class> bestOftakableClasses = new ArrayList<>();
        ArrayList<Integer> sortedwhitchtiomes = new ArrayList<>();
        ArrayList<Integer> wich = new ArrayList<>();

        for (int i3 = 0; i3 < takableClasses.size(); i3++) {
            Class takableClass = takableClasses.get(i3);
            if (takableClass.getCourseId().equals(neededClass.getCourseId())
                    && takableClass.getPazireshType() == neededClass.getPazireshType()
            ) {
                ArrayList<String> times = takableClass.getTimes();
                String time = times.get(0);
                String[] split = time.split("_");
                String t = split[0].split("t")[1];

                int i = Integer.parseInt(t);
                int i1 = Integer.parseInt(split[1]);
                int i2 = Integer.parseInt(split[2]);//not need now

                int timeofcourse = i * 2000 + i1;
                indexOfthem.add(i3);
                sortedwhitchtiomes.add(timeofcourse);
                wich.add(timeofcourse);
            }

        }


        Collections.sort(sortedwhitchtiomes);

        int o = 0;
        while (o < sortedwhitchtiomes.size()) {
            Integer timeofcourse = sortedwhitchtiomes.get(o);
            int i = 0;
            while (i < wich.size()) {
                Integer w = wich.get(i);
                if (w.equals(timeofcourse)) {
                    Integer integer = indexOfthem.get(i);
                    bestOftakableClasses.add(takableClasses.get(integer));
                    wich.remove(i);
                    indexOfthem.remove(i);
                    break;
                }
                i++;
            }
            o++;
        }

        gout(sortedwhitchtiomes);
        gout(indexOfthem);
        return bestOftakableClasses;

    }


    public static ArrayList<CapacityOfSchool> capsOfSchools() throws IOException, InvalidFormatException {

        ArrayList<CapacityOfSchool> capacityOfSchools = new ArrayList<>();
        ArrayList<String> shoolnames = new ArrayList<>();

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

            if (!shoolnames.contains(schoolName)) {
                shoolnames.add(schoolName);

                CapacityOfSchool capacityOfSchool = new CapacityOfSchool().setSchoolName(schoolName)
                        .setChertCode(cosCode);

                if (paziresh.equals("روزانه")) {
                    capacityOfSchool.setAwdicap(Integer.parseInt(cap));
                } else if (paziresh.equals("پردیس خودگردان")) {
                    capacityOfSchool.setPardiscap(Integer.parseInt(cap));
                } else throw new RuntimeException("what is type??????????????");


                capacityOfSchools.add(capacityOfSchool);

                System.err.println(new Gson().toJson(capacityOfSchool));

            } else {
                Optional<CapacityOfSchool> first = capacityOfSchools.stream()
                        .filter(capacityOfSchool -> capacityOfSchool.getSchoolName().equals(schoolName))
                        .findFirst();
                CapacityOfSchool capacityOfSchool = first.get();


                if (paziresh.equals("روزانه")) {
                    capacityOfSchool.setAwdicap(Integer.parseInt(cap));
                } else if (paziresh.equals("پردیس خودگردان")) {
                    capacityOfSchool.setPardiscap(Integer.parseInt(cap));
                } else throw new RuntimeException("what is type??????????????");

                capacityOfSchool.setCap(capacityOfSchool.getAwdicap() + capacityOfSchool.getPardiscap());
                System.err.println(new Gson().toJson(capacityOfSchool));

            }


        }


        return capacityOfSchools;

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


    public static ArrayList<Class> freshEnrollCourses() throws IOException, InvalidFormatException {


        String SAMPLE_XLSX_FILE_PATH = "conf/freshEnrollCourses.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
        Sheet sheetAt1 = workbook.getSheetAt(1);
        Sheet sheetAt0 = workbook.getSheetAt(0);
        HashMap<String, String> hashing = new HashMap<>();
        HashMap<String, ArrayList<String>> hash = new HashMap<>();
        DataFormatter dataFormatter = new DataFormatter();
        ArrayList<String> css = new ArrayList<>();
        ArrayList<String> tas = new ArrayList<>();

        for (Row row : sheetAt1) {
            try {
                String tadrisyarCourseId = dataFormatter.formatCellValue(row.getCell(1));
                String tadrisyarGroupId = dataFormatter.formatCellValue(row.getCell(2));

                String CourseId = dataFormatter.formatCellValue(row.getCell(3));
                String GroupId = dataFormatter.formatCellValue(row.getCell(4));

                String sepCourse = CourseId + "__" + GroupId;
                String sepTadrisyar = tadrisyarCourseId + "__" + tadrisyarGroupId;


                css.add(sepCourse);
                tas.add(sepTadrisyar);


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
//            System.out.println(hashing.keySet().size());
        }
//        System.out.println(css.size());
//        System.out.println(tas.size());
//        System.out.println(hash.keySet().size());

        ArrayList<Class> classes = new ArrayList<>();

        int kkk = 0;
        for (Row row : sheetAt0) {
            kkk++;
            try {
                ArrayList<String> times = new ArrayList<>();
                String courseName = dataFormatter.formatCellValue(row.getCell(0));
                String courseId = dataFormatter.formatCellValue(row.getCell(1));
                String groupId = dataFormatter.formatCellValue(row.getCell(2));
                String pazireshCode = dataFormatter.formatCellValue(row.getCell(5));
                String sexualCode = dataFormatter.formatCellValue(row.getCell(7));

                System.out.println(courseId);

                String id = courseId + "__" + groupId;


                if (!dataFormatter.formatCellValue(row.getCell(9)).equals("-1")) {

                    String firstTime = "t" + dataFormatter.formatCellValue(row.getCell(9))
                            + "_" + dataFormatter.formatCellValue(row.getCell(10))
                            + "_" + dataFormatter.formatCellValue(row.getCell(11));

                    times.add(firstTime);
                }

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

                if (dateOfExam.isEmpty()
                        && (courseName.contains("تدريس\u200Cيار")
                        || courseName.contains("تدريس يار"))
                ) {
                    continue;
                }

                Class aClass = new Class();


                String year = "";
                String month = "";
                String day = "";


                String[] tarikh = dateOfExam.split("/");
                ExamTime examTime = null;

                if (tarikh.length == 3) {
                    year = tarikh[0];
                    month = tarikh[1];
                    day = tarikh[2];

                    String timeofStartExam = dataFormatter.formatCellValue(row.getCell(19));
                    String timeofStopExam = dataFormatter.formatCellValue(row.getCell(20));
                    examTime = new ExamTime().setDay(Integer.parseInt(day))
                            .setFinishTime(Integer.parseInt(timeofStopExam))
                            .setStartTime(Integer.parseInt(timeofStartExam))
                            .setMonth(Integer.parseInt(month))
                            .setYear(Integer.parseInt(year));
                }


                String capacity = dataFormatter.formatCellValue(row.getCell(21));


                System.out.println(new Gson().toJson(times));
                System.out.println(new Gson().toJson(dateOfExam));


                aClass
                        .setCapacity(Integer.parseInt(capacity))
                        .setMaxCapacity(Integer.parseInt(capacity))
                        .setMinCapacity(Integer.parseInt(capacity))
                        .setBlankCapacity((0))
                        .setName(courseName)
                        .setId(id)
                        .setCourseId(courseId)
                        .setGroup(groupId)
                        .setExamTime(examTime)
                        .setTimes(times);


                if (sexualCode.equals(String.valueOf(SEX.BOTH.getCode()))) {
                    aClass.setSex(SEX.BOTH);

                } else if (sexualCode.equals(String.valueOf(SEX.MALE.getCode()))) {
                    aClass.setSex(SEX.MALE);

                } else {
                    aClass.setSex(SEX.WOMEN);
                }
                if (pazireshCode.equals(String.valueOf(PazireshType.Both.getCode()))) {
                    aClass.setPazireshType(PazireshType.Both);

                } else if (pazireshCode.equals(String.valueOf(PazireshType.Pardis.getCode()))) {
                    aClass.setPazireshType(PazireshType.Pardis);

                } else {
                    aClass.setPazireshType(PazireshType.Awdi);
                }

                gout(aClass);
                classes.add(aClass);

            } catch (Exception e) {
                System.err.println(e.toString());
            }

        }
        System.out.println("kkkk " + kkk);


        workbook.close();
        return classes;
    }


    public static int indexOfdmin(ArrayList<Double> doubles) {
        double m = 1.0D / 0.0;
        int var4 = doubles.size();
        int k = -1;

        for (int var5 = 0; var5 < var4; ++var5) {
            double v = doubles.get(var5);
            m = Math.min(v, m);
            if (m == v)
                k = var5;

        }
        return k;
    }


}