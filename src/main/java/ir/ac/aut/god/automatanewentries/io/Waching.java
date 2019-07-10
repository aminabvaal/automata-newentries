package ir.ac.aut.god.automatanewentries.io;

import java.io.IOException;
import java.nio.file.*;

/**
 * is created by aMIN on 10/5/2018 at 5:49 PM
 */

public class Waching {
    public static <T> void changefilelisnter(String root, String agterRootPath, Do aDo, WatchEvent.Kind<T> kind) {
        final Path path = FileSystems.getDefault().getPath(root, agterRootPath);
        System.out.println(path);
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final WatchKey watchKey = path.register(watchService, kind);

            while (true) {
                final WatchKey wk = watchService.take();
                for (WatchEvent<?> event : wk.pollEvents()) {
                    //we only register "ENTRY_MODIFY" so the context is always a Path.
                    final Path changed = (Path) event.context();
                    System.out.println(changed);
                    aDo.doNow(changed);
                }
                // reset the key
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("Key has been unregisterede");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        //todo i was satistified in this code here love (unified object) Event programming
//        Waching.changeFileLisnter(System.getProperty("user.home"), "Desktop", pathInChanging -> {
//
//            final File file = pathInChanging.toFile();
//            System.out.println(file.getAbsolutePath());
//            if (pathInChanging.endsWith("god.txt"))
//                System.out.println("god is changing");
//
//        }, StandardWatchEventKinds.ENTRY_MODIFY);


//
//        Waching.changeFileLisnter(System.getProperty("user.home"), "Desktop", pathInChanging -> {
//
//            final File file = pathInChanging.toFile();
//            System.out.println(file.getAbsolutePath());
//            if (pathInChanging.endsWith("god.txt"))
//                System.out.println("god is changing");
//
//        }, StandardWatchEventKinds.ENTRY_DELETE);


    }


    public interface Do {
        void doNow(Path pathInChanging);

    }
}

