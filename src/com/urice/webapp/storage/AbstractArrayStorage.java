package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Resume: " + resume.getUuid() + " updated");
        } else {
            System.out.println("Resume: " + resume.getUuid() + " not updated");
        }
        resumeSort();
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Resume - All removed");
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT) {
                storage[size] = resume;
                size++;
                // System.out.println("Resume: " + resume.getUuid() + " saved");
            } else {
                // System.out.println("Resume: " + resume.getUuid() + " not saved - too many resumes in array");
            }
        } else {
            // System.out.println("Resume: " + resume.getUuid() + " not saved");
        }
        resumeSort();
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            System.out.println("Resume: " + uuid + " received");
            return storage[index];
        } else {
            System.out.println("Resume: " + uuid + " not received");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            size--;
            storage[index] = storage[size];
            storage[size] = null;
            System.out.println("Resume: " + uuid + " deleted");
        } else {
            System.out.println("Resume: " + uuid + " not deleted");
        }
        resumeSort();
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

    protected abstract int findIndex(String uuid);

    protected abstract void resumeSort();
}
