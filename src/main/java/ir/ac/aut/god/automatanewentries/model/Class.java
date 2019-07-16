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
public class Class {
    private String name;
    private String id;
    private String group;
    private EntranceType entranceType;
    private ArrayList<TimeOf> InvolovingTimes;
    private ArrayList<String> times;
    private TimeOf finalExamTime;
    private int capacity;
    private int maxCapacity;
    private int minCapacity;

}
