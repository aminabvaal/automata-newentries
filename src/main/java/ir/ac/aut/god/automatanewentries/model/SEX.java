package ir.ac.aut.god.automatanewentries.model;

/**
 * created By aMIN on 5/19/2019 7:02 PM
 */

public enum SEX {
    MALE(2601),WOMEN(260),BOTH(2600);


    private int code;

    SEX(int i) {
        this.code = i;
    }

    public int getCode() {
        return code;
    }
}
