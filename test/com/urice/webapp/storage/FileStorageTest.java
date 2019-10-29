package com.urice.webapp.storage;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(FILE_STORAGE_DIR, new ObjectStreamStorage()));
    }
}
