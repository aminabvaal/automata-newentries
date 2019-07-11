package ir.ac.aut.god.automatanewentries.model;

public enum PazireshType {
    Awdi(9001), Pardis(9002), Both(9000);

    private int code;

    PazireshType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
