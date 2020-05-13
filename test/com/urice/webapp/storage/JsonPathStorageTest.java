package com.urice.webapp.storage;

import com.urice.webapp.storage.serializer.JsonStreamSerializer;

import java.nio.file.Paths;

public class JsonPathStorageTest extends AbstractStorageTest {

     public JsonPathStorageTest() {
       super(new PathStorage(Paths.get(STORAGE_DIR.getAbsolutePath()), new JsonStreamSerializer()));
    }
}
