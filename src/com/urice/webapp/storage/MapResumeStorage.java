package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        map.put(resume.toString(), resume);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object resume) {
        return map.get(resume.toString());
    }

    @Override
    protected void doDelete(Object resume) {
        map.remove(resume);
    }

    @Override
    protected boolean isExist(Object resume) {
        return map.containsKey(resume);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> returnList = new ArrayList<>(map.values());
        Collections.sort(returnList);
        return returnList;
    }
}
