package com.urice.webapp.storage.serializer;

import com.urice.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    private interface writeOrganizations<T> {
        void write(T t) throws IOException;
    }

    private interface readOrganizations<T> {
        T read() throws IOException;
    }

    private interface contactMapAction<T> {
        void action() throws IOException;
    }

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            writeCollections(dataOutputStream, resume.getContactMap().entrySet(), e -> {
                dataOutputStream.writeUTF(e.getKey().name());
                dataOutputStream.writeUTF(e.getValue());
            });

            writeCollections(dataOutputStream, resume.getSectionMap().entrySet(), e -> {
                SectionType sectionType = e.getKey();
                AbstractSection section = e.getValue();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollections(dataOutputStream, ((ListSection) section).getData(), dataOutputStream::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollections(dataOutputStream, ((OrganizationSection) section).getData(), org -> {
                            dataOutputStream.writeUTF(org.getHomePage().getName());
                            dataOutputStream.writeUTF(stringFillNonNull(org.getHomePage().getUrl()));
                            writeCollections(dataOutputStream, org.getPositions(), pos -> {
                                writeYearMonth(dataOutputStream, pos.getStartDate());
                                writeYearMonth(dataOutputStream, pos.getEndDate());
                                dataOutputStream.writeUTF(pos.getPosition());
                                dataOutputStream.writeUTF(stringFillNonNull(pos.getDescription()));
                            });
                        });
                        break;
                }
            });
        }
    }

    private <T> void writeCollections(DataOutputStream dataOutputStream, Collection<T> collection, writeOrganizations<T> organization) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T t : collection) {
            organization.write(t);
        }
    }

    private void writeYearMonth(DataOutputStream dataOutputStream, YearMonth yearMonth) throws IOException {
        dataOutputStream.writeInt(yearMonth.getYear());
        dataOutputStream.writeInt(yearMonth.getMonthValue());
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContactMap(dataInputStream, () -> resume.setContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF()));
            readContactMap(dataInputStream, () -> {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                resume.setSection(sectionType, readSections(dataInputStream, sectionType));
            });
            return resume;
        }
    }

    private AbstractSection readSections(DataInputStream dataInputStream, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dataInputStream.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readLists(dataInputStream, dataInputStream::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readLists(dataInputStream, () -> new Organization(
                        new Link(dataInputStream.readUTF(), stringReadNonNull(dataInputStream.readUTF())),
                        readLists(dataInputStream, () ->
                                new Organization.Position(getYearMonth(dataInputStream), getYearMonth(dataInputStream),
                                        dataInputStream.readUTF(), stringReadNonNull(dataInputStream.readUTF()))))));
            default:
                throw new IllegalArgumentException();

        }
    }

    private <T> List<T> readLists(DataInputStream dataInputStream, readOrganizations<T> readOrganization) throws IOException {
        int size = dataInputStream.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(readOrganization.read());
        }
        return list;
    }

    private void readContactMap(DataInputStream dataInputStream, contactMapAction mapAction) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            mapAction.action();
        }
    }

    private YearMonth getYearMonth(DataInputStream dataInputStream) throws IOException {
        return YearMonth.of(dataInputStream.readInt(), dataInputStream.readInt());
    }

    private String stringFillNonNull(String str) {
        if (str == null) {
            return "null";
        }
        return str;
    }

    private String stringReadNonNull(String str) {
        if (str.contains("null")) {
            return null;
        }
        return str;
    }
}
