package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume resume, Resume t1) {
            return resume.getUuid().compareTo(t1.getUuid());
        }
    };

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void insertElement(Resume resume, int insertIndex) {
        int index = -insertIndex - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteElement(int deleteIndex) {
        int shift = size - deleteIndex;
        System.arraycopy(storage, deleteIndex + 1, storage, deleteIndex, shift);
    }
}
