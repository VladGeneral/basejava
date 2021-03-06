package com.urice.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {
        File dir = new File("../basejava/src/com/urice/webapp");
        System.out.println("Find all elements");
        printStructure(dir, "");
    }

    public static void printStructure(File directory, String indent) {
        File[] files = Objects.requireNonNull(directory.listFiles(), "directory must not be null");
        for (File file : files) {
            if (!file.isDirectory()) {
                System.out.println(indent + file.getName());
            }
            if (file.isDirectory()) {
                System.out.println(indent + file.getName());
                printStructure(file, indent + "\t");
            }
        }
    }
}
