package com.urice.webapp.storage;

import com.urice.webapp.exception.StorageException;
import com.urice.webapp.model.Resume;

import java.io.*;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage {

    protected ObjectStreamPathStorage(Path directory) {
        super(directory);
    }

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
