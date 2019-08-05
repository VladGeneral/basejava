package com.urice.webapp;

import com.urice.webapp.model.Resume;
import com.urice.webapp.storage.SortedArrayStorage;
import com.urice.webapp.storage.Storage;

/**
 * Test for your com.urice.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        r1.setUuid("uuid1");
        final Resume r2 = new Resume();
        r2.setUuid("uuid2");
        final Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);


        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

//        System.out.println("Index of r3: " + Arrays.binarySearch(ARRAY_STORAGE.storage,0,ARRAY_STORAGE.size(),r3));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

//        ARRAY_STORAGE.save(r1);
//        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
//        r1.setUuid("ss");
//        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
//        ARRAY_STORAGE.update(r1);
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
