package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.List;

public class MapResumeStorage extends AbstractStorage {
    @Override
    protected Object getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {

    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {

    }

    @Override
    protected Resume doGet(Object searchKey) {
        return null;
    }

    @Override
    protected void doDelete(Object searchKey) {

    }

    @Override
    protected boolean isExist(Object searchKey) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
