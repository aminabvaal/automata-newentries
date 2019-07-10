package ir.ac.aut.god.automatanewentries.io;

import java.io.*;

/**
 * is created by aMIN on 7/30/2018 at 9:15 PM
 */
public class MyWriter {


    private OutputStreamWriter outputStreamWriter;
    private FileOutputStream fileOutputStream;

    public MyWriter(String pathDirToSave, String childFileName, boolean append) {
        try {
            File dir = new File(pathDirToSave);
            dir.mkdirs();
            File fileTosave = new File(dir, childFileName);
            if (!fileTosave.exists())
                fileTosave.createNewFile();
            else System.out.println("file is existed");

            fileOutputStream = new FileOutputStream(fileTosave, append);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyWriter(String abthpathfile, boolean append) {
        try {
            File file = new File(abthpathfile);
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file, append);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MyWriter of(String pathfile, boolean appendble) {
        return new MyWriter(pathfile, appendble);
    }


    public void append(String s) {
        try {
            outputStreamWriter.write(s);
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void appendNewLine(String s) {
        try {
            outputStreamWriter.write(s + "\r\n");
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
