package com.urice.webapp.storage;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new FileStorage(FILE_STORAGE_DIR, new ObjectStreamStorage()));
    }
}
