package ir.ac.aut.god.automatanewentries.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public class CapSchool {
    private String chertCode;
    private String schoolName;
    private int cap;
    private PazireshType pazireshType;


}
