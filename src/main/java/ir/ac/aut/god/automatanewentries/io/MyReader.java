package ir.ac.aut.god.automatanewentries.io;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * is created by aMIN on 8/6/2018 at 1:41 AM
 */

@Getter
public class MyReader {
    private FileReader reader;
    private Scanner scanner;

    public MyReader(FileReader reader, Scanner scanner) {
        this.reader = reader;
        this.scanner = scanner;
    }

    public MyReader(String path) {
        try {
            reader = new FileReader(path);
            scanner = new Scanner(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static MyReader of(String path){
        return new MyReader(path);}


    public static ArrayList<String> readFileLines(String path) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        FileReader reader = new FileReader(path);
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine())
            lines.add(scanner.nextLine());
        return lines;

    }

    public static String readFirstLine(String path) {
        FileReader reader = null;
        String s = null;
        try {
            reader = new FileReader(path);

            Scanner scanner = new Scanner(reader);
            s = scanner.nextLine();
            scanner.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void main(String[] args) {
        System.out.println(MyReader.readFirstLine("config/start.txt").equals("true"));
    }

    public ArrayList<String> readFileLines() {
        ArrayList<String> lines = new ArrayList<>();
        while (this.scanner.hasNextLine())
            lines.add(scanner.nextLine());
        return lines;
    }

    public String readFirstLine() {
        String s = null;
        try {
            s = scanner.nextLine();

        } catch (NoSuchElementException noSuchElementException) {
        } finally {
            return s;
        }
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

}
