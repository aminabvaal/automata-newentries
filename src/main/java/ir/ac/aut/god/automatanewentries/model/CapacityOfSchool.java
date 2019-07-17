package ir.ac.aut.god.automatanewentries.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public class CapacityOfSchool {
    private String chertCode;
    private String schoolName;
    private int cap;
    private int pardiscap;
    private int awdicap;
    private PazireshType pazireshType;

}
