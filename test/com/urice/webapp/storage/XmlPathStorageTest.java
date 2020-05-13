package com.urice.webapp.storage;

import com.urice.webapp.storage.serializer.XmlStreamSerializer;

import java.nio.file.Paths;

public class XmlPathStorageTest extends AbstractStorageTest {

     public XmlPathStorageTest() {
       super(new PathStorage(Paths.get(STORAGE_DIR.getAbsolutePath()), new XmlStreamSerializer()));
    }
}
