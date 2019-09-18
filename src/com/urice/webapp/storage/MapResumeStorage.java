package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doSave(Object searchResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Object searchResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchResume) {
        return (Resume) searchResume;
    }

    @Override
    protected void doDelete(Object searchResume) {
        map.remove(((Resume) searchResume).getUuid());
    }

    @Override
    protected boolean isExist(Object searchResume) {
        return searchResume != null;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }
}
