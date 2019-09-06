package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected void makeSave(int searchKey, Resume resume) {
        list.add(resume);
    }

    @Override
    protected void makeUpdate(int searchKey, Resume resume) {
            list.set(searchKey, resume);
    }

    @Override
    protected Resume makeGet(int searchKey) {
        return list.get(searchKey);
    }

    @Override
    protected void makeDelete(int searchKey) {
        list.remove(searchKey);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[list.size()]);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected int findSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)){
                return i;
            }
        }
        return 0;
    }
}
