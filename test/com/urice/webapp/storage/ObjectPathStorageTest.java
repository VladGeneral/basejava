package com.urice.webapp.storage;

import com.urice.webapp.storage.serializer.ObjectStreamSerializer;

import java.nio.file.Paths;

public class ObjectPathStorageTest extends AbstractStorageTest {

     public ObjectPathStorageTest() {
       super(new PathStorage(Paths.get(STORAGE_DIR.getAbsolutePath()), new ObjectStreamSerializer()));
    }
}
