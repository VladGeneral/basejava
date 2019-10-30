package com.urice.webapp.storage;

import java.nio.file.Paths;

public class PathStorageTest extends AbstractStorageTest {

     public PathStorageTest() {
       super(new PathStorage(Paths.get(STORAGE_DIR), new ObjectStreamStorage()));
    }
}
