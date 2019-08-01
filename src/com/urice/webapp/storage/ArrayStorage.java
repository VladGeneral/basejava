package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
            System.out.println("Resume: " + resume.getUuid() + " updated");
        } else {
            System.out.println("Resume: " + resume.getUuid() + " not updated");
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Resume - All removed");
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index == -1) {
            if (size < STORAGE_LIMIT) {
                storage[size] = resume;
                size++;
                System.out.println("Resume: " + resume.getUuid() + " saved");
            } else {
                System.out.println("Resume: " + resume.getUuid() + " not saved - too many resumes in array");
            }
        } else {
            System.out.println("Resume: " + resume.getUuid() + " not saved");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            System.out.println("Resume: " + uuid + " received");
            return storage[index];
        } else {
            System.out.println("Resume: " + uuid + " not received");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            size--;
            storage[index] = storage[size];
            storage[size] = null;
            System.out.println("Resume: " + uuid + " deleted");
        } else {
            System.out.println("Resume: " + uuid + " not deleted");
        }
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

}
