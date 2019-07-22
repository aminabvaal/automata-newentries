package ir.ac.aut.god.automatanewentries.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public class ProgramGroup {
    private int id;
    private ArrayList<AssignClass> hadAssignClasses;
}
