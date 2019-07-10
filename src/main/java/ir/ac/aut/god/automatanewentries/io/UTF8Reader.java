package ir.ac.aut.god.automatanewentries.io;

import lombok.Getter;

import java.io.*;
import java.nio.charset.Charset;

/**
 * is created by aMIN on 8/6/2018 at 1:41 AM
 */

@Getter
public class UTF8Reader {
    Charset windows1252 = Charset.forName("windows-1252");
    private BufferedReader in;

    public UTF8Reader(String path) throws FileNotFoundException, UnsupportedEncodingException {
        File fileDir = new File(path);
        in = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileDir), windows1252));

    }

    public static void main(String[] args) throws IOException {

        final UTF8Reader utf8Reader = new UTF8Reader("C:\\Users\\AminAbvaal\\Desktop\\data\\DRCT_10105_iran__islamic_rep_2.csv");
        final String readline = utf8Reader.readline();
        System.out.println(readline);
        new MyWriter("C:\\Users\\AminAbvaal\\Desktop\\data", "ii.csv", true).appendNewLine(readline);
    }

    public String readline() throws IOException {
        return in.readLine();
    }

}
