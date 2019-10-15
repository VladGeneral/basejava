package com.urice.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("/Users/vladgeneral/Documents/GitHub/basejava/src/com/urice/webapp");

        System.out.println("Find all elements");
        printStructure(dir);
    }

    public static void printStructure(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (!f.isDirectory()) {
                System.out.println(f.getName());
            }
            if (f.isDirectory()) {
                try {
                    printStructure(f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
