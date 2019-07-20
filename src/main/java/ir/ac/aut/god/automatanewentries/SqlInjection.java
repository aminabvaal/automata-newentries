package ir.ac.aut.god.automatanewentries;

import java.util.ArrayList;

import static ir.ac.aut.god.automatanewentries.core.ExcelReader.extractConfilicts;
import static ir.ac.aut.god.automatanewentries.core.ExcelReader.gout;

/**
 * created By aMIN on 7/11/2019 1:43 AM
 */

public class SqlInjection {
    public static void main(String[] args) {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("t4_945_1045");
        strings.add("t4_800_1000");
        strings.add("t4_1000_1200");
        strings.add("t4_1000_1200");
        strings.add("t4_1000_1200");

        ArrayList<String> strings1 = extractConfilicts(strings);

        gout(strings1);
    }
}
