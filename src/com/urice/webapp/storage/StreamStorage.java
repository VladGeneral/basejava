package com.urice.webapp.storage;

import com.urice.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamStorage {

    void doWrite(OutputStream outputStream, Resume resume) throws IOException;

    Resume doRead(InputStream inputStream) throws IOException;

}
