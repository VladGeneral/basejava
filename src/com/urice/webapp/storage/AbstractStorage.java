package com.urice.webapp.storage;

import com.urice.webapp.exception.ExistStorageException;
import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        int searchKey = getExistSearchKey(resume.getUuid());
        makeUpdate(searchKey, resume);
    }

    public void save(Resume resume) {
        int searchKey = getNotExistSearchKey(resume.getUuid());
        makeSave(searchKey, resume);

    }

    public Resume get(String uuid) {
        int searchKey = getExistSearchKey(uuid);
        return makeGet(searchKey);

    }

    public void delete(String uuid) {
        int searchKey = getExistSearchKey(uuid);
        makeDelete(searchKey);
    }

    private int getExistSearchKey(String uuid) {
        int searchKey = findSearchKey(uuid);
        if (searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private int getNotExistSearchKey(String uuid) {
        int searchKey = findSearchKey(uuid);
        if (searchKey >= 0) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract int findSearchKey(String uuid);

    protected abstract void makeSave(int searchKey, Resume resume);

    protected abstract void makeUpdate(int searchKey, Resume resume);

    protected abstract Resume makeGet(int searchKey);

    protected abstract void makeDelete(int searchKey);


}
