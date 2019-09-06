package com.urice.webapp.storage;

import com.urice.webapp.exception.ExistStorageException;
import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    public void update(Resume resume) {
        int index = getExistSearchKey(resume.getUuid());
        makeUpdate(index, resume);
    }

    public void save(Resume resume) {
        int index = getNotExistSearchKey(resume.getUuid());
        makeSave(index, resume);

    }

    public Resume get(String uuid) {
        int index = getExistSearchKey(uuid);
        makeGet(index, uuid);
    }

    public void delete(String uuid) {
        int index = getExistSearchKey(uuid);
        makeDelete(index, uuid);
    }

    private int getExistSearchKey(String uuid) {
        int searchKey = findIndex(uuid);
        if (searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private int getNotExistSearchKey(String uuid) {
        int searchKey = findIndex(uuid);
        if (searchKey >= 0) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void makeSave(int index, Resume resume);

    protected abstract void makeUpdate(int index, Resume resume);

    protected abstract Resume makeGet(int index, String uuid);

    protected abstract void makeDelete(int index, String uuid);


}
