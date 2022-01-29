package ro.tuiasi.uac.interfaces;

import java.io.IOException;

public interface FileIOManagerServiceInterface {
    byte[] readFile(String filename) throws IOException;

    void writeFile(String filename, byte[] bytes) throws IOException;

    boolean deleteFile(String filename) throws IOException;
}
