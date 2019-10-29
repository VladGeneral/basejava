package com.urice.webapp.storage;

public class PathStorageTest extends AbstractStorageTest {

     public PathStorageTest() {
       super(new PathStorage(PATH_STORAGE_DIR, new ObjectStreamStorage()));
    }
}
