package com.urice.webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

     public ObjectStreamPathStorageTest() {
       super(new PathStorage(PATH_STORAGE_DIR, new ObjectStreamPathStorage()));
    }
}
