package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileSelector {
static public FileInputStream createFileStream(String name) throws FileNotFoundException {
    return new FileInputStream(name);

}
}
