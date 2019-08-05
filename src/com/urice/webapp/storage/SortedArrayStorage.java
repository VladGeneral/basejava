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
    protected Resume[] resumeSort(Resume resume, int binaryIndex) {
        int index = -binaryIndex - 1;
        storage[index + 1] = storage[index];
        storage[index] = resume;
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected Resume[] resumeDelete(int binaryIndex) {
        Resume storageKey;
        for (int i = binaryIndex; i < size - 1; i++) {
            storageKey = storage[i + 1];
            storage[i + 1] = storage[i];
            storage[i] = storageKey;
        }
        return Arrays.copyOfRange(storage, 0, size);
    }
}
