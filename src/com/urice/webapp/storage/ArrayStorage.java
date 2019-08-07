package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume resume, int insertIndex) {
        storage[size] = resume;
    }

    @Override
    protected void deleteElement(int deleteIndex) {
        storage[deleteIndex] = storage[size];
    }


}
