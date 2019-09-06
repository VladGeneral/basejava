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
        Object searchKey = findSearchKey(uuid);
        if ((int) searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return (int) searchKey;
    }

    private int getNotExistSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if ((int) searchKey >= 0) {
            throw new ExistStorageException(uuid);
        }
        return (int) searchKey;
    }

    protected abstract Object findSearchKey(String uuid);

    protected abstract void makeSave(Object searchKey, Resume resume);

    protected abstract void makeUpdate(Object searchKey, Resume resume);

    protected abstract Resume makeGet(Object searchKey);

    protected abstract void makeDelete(Object searchKey);


}
