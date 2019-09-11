package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<String,Resume> map = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        map.put(resume.getUuid(),resume);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        map.put(resume.getUuid(),resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> returnList = new ArrayList<>(map.values());
        Collections.sort(returnList);
        return returnList;
    }

    @Override
    public int size() {
        return map.size();
    }
}
