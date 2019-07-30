package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    final int ARRAY_LENGTH = 10_000;
    private Resume[] storage = new Resume[10000];
    private int size;
    private int index = -1;

    public void update(Resume resume) {
        if (findArrayIndex(resume.getUuid()) != -1) {
            storage[index].setUuid(resume.getUuid());
            System.out.println("Resume updated");
        } else {
            System.out.println("Resume not updated");
        }
    }

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
        System.out.println("Resume - All removed");
    }

    public void save(Resume resume) {
        if (findArrayIndex(resume.getUuid()) == -1) {
            if (size < ARRAY_LENGTH) {
                storage[size] = resume;
                size++;
                System.out.println("Resume saved");
            } else {
                System.out.println("Resume not saved - too many resumes in array");
            }
        }
    }

    public Resume get(String uuid) {
        if (findArrayIndex(uuid) != -1) {
            System.out.println("Resume recieved");
            return storage[index];
        } else {
            System.out.println("Resume not recieved");
            return null;
        }
    }

    public void delete(String uuid) {
        if (findArrayIndex(uuid) != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.println("Resume deleted");
        } else {
            System.out.println("Resume not deleted");
        }
    }

    int findArrayIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
