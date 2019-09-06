package com.urice.webapp.storage;

import com.urice.webapp.exception.NotExistStorageException;
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

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Resume: " + resume.getUuid() + " updated");
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Resume - All removed");
    }


    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            System.out.println("Resume: " + uuid + " received");
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            size--;
            deleteElement(index);
            storage[size] = null;

            System.out.println("Resume: " + uuid + " deleted");
        } else {
            throw new NotExistStorageException(uuid);
        }
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

    @Override
    protected void makeSave(int index, Resume resume) {
        if (size < STORAGE_LIMIT) {
            insertElement(resume, index);
            size++;
            System.out.println("Resume: " + resume.getUuid() + " saved");
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected void makeUpdate(int index, Resume resume) {
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Resume: " + resume.getUuid() + " updated");
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected Resume makeGet(int index, String uuid) {
        if (index >= 0) {
            System.out.println("Resume: " + uuid + " received");
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    protected void makeDelete(int index, String uuid) {
        if (index >= 0) {
            size--;
            deleteElement(index);
            storage[size] = null;

            System.out.println("Resume: " + uuid + " deleted");
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertElement(Resume resume, int insertIndex);

    protected abstract void deleteElement(int deleteIndex);
}
