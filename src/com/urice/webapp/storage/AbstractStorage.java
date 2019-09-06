package com.urice.webapp.storage;

import com.urice.webapp.exception.ExistStorageException;
import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        Object searchKey = getExistSearchKey(resume.getUuid());
        makeUpdate(searchKey, resume);
    }

    public void save(Resume resume) {
        Object searchKey = getNotExistSearchKey(resume.getUuid());
        makeSave(searchKey, resume);

    }

    public Resume get(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        return makeGet(searchKey);

    }

    public void delete(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        makeDelete(searchKey);
    }

    private Object getExistSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (searchKey == null) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (searchKey != null) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object findSearchKey(String uuid);

    protected abstract void makeSave(Object searchKey, Resume resume);

    protected abstract void makeUpdate(Object searchKey, Resume resume);

    protected abstract Resume makeGet(Object searchKey);

    protected abstract void makeDelete(Object searchKey);

   // protected abstract boolean isNotNull(Object searchKey);


}
