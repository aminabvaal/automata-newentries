package ir.ac.aut.god.automatanewentries.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;


@Getter
@Setter
@Accessors(chain = true)
public class NeededClass {

    private String courseName;
    private String courseId;
    private PazireshType pazireshType;
    private int priotryOfPardisType;
    private int priotryOfAwdiType;
    private ArrayList<Integer> possibleGroups;//needed or required groups must be considered


}
