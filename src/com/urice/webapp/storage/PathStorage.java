package com.urice.webapp.storage;

import com.urice.webapp.exception.StorageException;
import com.urice.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    public abstract void doWrite(OutputStream outputStream, Resume resume) throws IOException;

    public abstract Resume doRead(InputStream inputStream) throws IOException;

    protected PathStorage(String directory) {
        this.directory = Objects.requireNonNull(Paths.get(directory), "directory must not be null");
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + " is not directory");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path.toString(), path.getFileName().toString(), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error ", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            if (!Files.deleteIfExists(path)) {
                throw new StorageException("Path delete error", path.getFileName().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list;
        try (Stream<Path> pathStream = Objects.requireNonNull(Files.walk(directory), "directory must not be null")) {
            list = pathStream.map(this::doGet).collect(Collectors.toList());

        } catch (IOException e) {
            throw new StorageException("Copy directory error", directory.getFileName().toString());
        }
        return list;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        List<Resume> list;
        try (Stream<Path> pathStream = Objects.requireNonNull(Files.walk(directory), "directory must not be null")) {
            list = pathStream.map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Size directory error", directory.getFileName().toString());
        }
        return list.size();
    }
}
