package com.urice.webapp.storage;

import com.urice.webapp.Config;
import com.urice.webapp.ResumeTestData;
import com.urice.webapp.exception.ExistStorageException;
import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.urice.webapp.ResumeTestData.*;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void save() {
        assertSize(3);
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume resume = ResumeTestData.fillResume(UUID_1, "UpdateName?");
        storage.update(resume);
        assertGet(resume);

    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("NotExistResume"));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        assertSize(3);
        storage.delete(UUID_1);
        assertSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumes = storage.getAllSorted();
        assertSize(3);
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), resumes);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}
