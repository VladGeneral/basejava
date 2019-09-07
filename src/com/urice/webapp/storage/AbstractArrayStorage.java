package com.urice.webapp.storage;

import com.urice.webapp.exception.StorageException;
import com.urice.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    protected void makeSave(Object searchKey, Resume resume) {
        if (size < STORAGE_LIMIT) {
            insertElement(resume, (Integer) searchKey);
            size++;
//            System.out.println("Resume: " + resume.getUuid() + " saved");
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected void makeUpdate(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
//            System.out.println("Resume: " + resume.getUuid() + " updated");
    }

    @Override
    protected Resume makeGet(Object searchKey) {
//            System.out.println("Resume: " + uuid + " received");
        return storage[(int) searchKey];
    }

    @Override
    protected void makeDelete(Object searchKey) {
        size--;
        deleteElement((Integer) searchKey);
        storage[size] = null;
//            System.out.println("Resume: " + uuid + " deleted");
    }

    @Override
    protected boolean isNotNull(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
//        System.out.println("Resume - All removed");
    }

    public int size() {
        return size;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

//    protected abstract int findIndex(String uuid);

    protected abstract void insertElement(Resume resume, int insertIndex);

    protected abstract void deleteElement(int deleteIndex);
}
