package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void resumeSort() {
        for (int j = 0; j < size; j++) {
            for (int i = j + 1; i < size; i++) {
                if (storage[i].compareTo(storage[j]) < 0) {
                    String uuid = storage[j].getUuid();
                    storage[j].setUuid(storage[i].getUuid());
                    storage[i].setUuid(uuid);
                }
            }
        }
    }
}
