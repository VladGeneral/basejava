package com.urice.webapp.storage;

import com.urice.webapp.exception.ExistStorageException;
import com.urice.webapp.exception.NotExistStorageException;
import com.urice.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1,"Name_1");
        RESUME_2 = new Resume(UUID_2,"Name_2");
        RESUME_3 = new Resume(UUID_3,"Name_3");
        RESUME_4 = new Resume(UUID_4,"Name_4");

//        RESUME_1 = new Resume("UUID_1");
//        RESUME_2 = new Resume("UUID_2");
//        RESUME_3 = new Resume("UUID_3");
//        RESUME_4 = new Resume("UUID_4");
    }

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
    public void update() {
        Resume resume = new Resume(UUID_1, "UpdateName?");
        storage.update(resume);
        assertGet(resume);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        storage.get(RESUME_1.getUuid());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void getAll() {
        Resume[] testResumes = {RESUME_1, RESUME_2, RESUME_3};
        List<Resume> resumes = storage.getAllSorted();
        Collections.sort(resumes, Comparator.comparing(Resume::getUuid));
        assertSize(3);
        assertEquals(Arrays.asList(testResumes), resumes);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() {
        storage.save(RESUME_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

}
