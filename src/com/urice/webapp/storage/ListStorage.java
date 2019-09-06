package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    ArrayList<Resume> list = new ArrayList<>();


    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return list.indexOf(resume);
    }

    @Override
    protected void makeUpdate(int index, Resume resume) {
        if (list.contains(resume)) {
            list.set(index, resume);
        }
    }

    @Override
    protected void makeSave(int index, Resume resume) {
        list.add(resume);
    }
}
