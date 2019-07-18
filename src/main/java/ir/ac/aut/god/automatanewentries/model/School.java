package ir.ac.aut.god.automatanewentries.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@Accessors(chain = true)
public class School {
    private String name;
    private String code;
    private boolean fromFirstSchool;
    private ArrayList<NeededClass> neededClasses;
    private HashMap<NeededClass, ArrayList<AssignClass>> assignCLassesOfNeedClass = new HashMap<>();
    private ArrayList<NeededClass> optionalMarefs;

    private CapacityOfSchool capacityOfSchool;
    private ArrayList<Class> takableClasses;


}
