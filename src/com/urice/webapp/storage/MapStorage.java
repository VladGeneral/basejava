package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void makeSave(Object searchKey, Resume resume) {
        map.put((String) searchKey, resume);

    }

    @Override
    protected void makeUpdate(Object searchKey, Resume resume) {
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            if (entry.getKey().equals(searchKey)) {
                entry.setValue(resume);
            }
        }
    }

    @Override
    protected Resume makeGet(Object searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected void makeDelete(Object searchKey) {
        map.keySet().removeIf(x -> x.equals(searchKey));
    }

    @Override
    protected boolean isNotNull(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[map.size()]);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected String findSearchKey(String uuid) {
        if (map.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }
}
