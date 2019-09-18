package com.urice.webapp.storage;

import com.urice.webapp.exception.ExistStorageException;
import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SearchKey> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SearchKey getSearchKey(String uuid);

    protected abstract void doSave(SearchKey searchKey, Resume resume);

    protected abstract void doUpdate(SearchKey searchKey, Resume resume);

    protected abstract Resume doGet(SearchKey searchKey);

    protected abstract void doDelete(SearchKey searchKey);

    protected abstract boolean isExist(SearchKey searchKey);

    protected abstract List<Resume> doCopyAll();

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SearchKey searchKey = getNotExistedSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SearchKey searchKey = getExistedSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SearchKey searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SearchKey searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    private SearchKey getExistedSearchKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + "not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SearchKey getNotExistedSearchKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + "exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }
}
