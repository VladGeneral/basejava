package com.urice.webapp.storage;

import com.urice.webapp.storage.serializer.DataStreamSerializer;

import java.nio.file.Paths;

public class DataPathStorageTest extends AbstractStorageTest {

     public DataPathStorageTest() {
       super(new PathStorage(Paths.get(STORAGE_DIR.getAbsolutePath()), new DataStreamSerializer()));
    }
}
