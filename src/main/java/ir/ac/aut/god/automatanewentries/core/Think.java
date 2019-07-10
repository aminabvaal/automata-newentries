package ir.ac.aut.god.automatanewentries.core;

/**
 * created By aMIN on 5/20/2019 6:13 PM
 */

public class Think {
    public static void main(String[] args) {
        new Thread(new Thread(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("kign in the north");
            }
        }))).start();

    }
}
