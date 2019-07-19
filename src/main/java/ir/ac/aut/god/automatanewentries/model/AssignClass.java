package ir.ac.aut.god.automatanewentries.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * created By aMIN on 5/12/2019 9:40 PM
 */


@Getter
@Setter
@Accessors(chain = true)
public class AssignClass {
    private String courseName;
    private String courseId;
    private String id;
    private String group;
    private int assignedCap;
    private ArrayList<String> times;


}
