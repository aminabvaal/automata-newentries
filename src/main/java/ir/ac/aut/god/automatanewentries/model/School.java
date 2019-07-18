package ir.ac.aut.god.automatanewentries.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public class School {
    private String name;
    private String code;
    private boolean fromFirstSchool;
    private ArrayList<NeededClass> neededClasses;
    private ArrayList<AssignClass>  assignedClasses;
    private ArrayList<NeededClass> optionalMarefs;

    private CapacityOfSchool capacityOfSchool;
    private ArrayList<Class> takableClasses;


}
