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
    protected void resumeSaveElement(Resume resume, int binaryIndex) {
        int index = -binaryIndex - 1;
        Resume[] newStorage = storage;
        for (int i = size; i > index; i--) {
            newStorage[i] = storage[i - 1];
        }
        newStorage[index] = resume;
        storage = newStorage;

    }

    @Override
    protected void resumeDeleteElement(int binaryIndex) {
        Resume storageKey;
        storage[binaryIndex] = storage[size];
        storage[size] = null;
        for (int i = binaryIndex; i < size - 1; i++) {
            storageKey = storage[i + 1];
            storage[i + 1] = storage[i];
            storage[i] = storageKey;
        }
    }
}
