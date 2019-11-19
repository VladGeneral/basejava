package com.urice.webapp.storage.serializer;

import com.urice.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContactMap();

            writeCollection(dataOutputStream, contacts.entrySet(), e -> {
                dataOutputStream.writeUTF(e.getKey().name());
                dataOutputStream.writeUTF(e.getValue());
            });

            writeCollection(dataOutputStream, resume.getSectionMap().entrySet(), e -> {
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
                        writeCollection(dataOutputStream, ((ListSection) section).getData(), dataOutputStream::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dataOutputStream, ((OrganizationSection) section).getData(), o -> {
                            dataOutputStream.writeUTF(o.getHomePage().getName());
                            dataOutputStream.writeUTF(o.getHomePage().getUrl());

                            writeCollection(dataOutputStream, o.getPositions(), pos -> {
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

    private <T> void writeCollection(DataOutputStream dataOutputStream, Collection<T> collection, writeOrganization<T> organization) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T t : collection) {
            organization.get(t);
        }
    }

    private interface writeOrganization<T> {
        void get(T t) throws IOException;
    }

    private interface readOrganization<T> {
        T read() throws IOException;
    }

    private interface contactMapAction<T> {
        void doIt() throws IOException;
    }

    private void writeYearMonth(DataOutputStream dataOutputStream, YearMonth yearMonth) throws IOException {
        dataOutputStream.writeInt(yearMonth.getYear());
        dataOutputStream.writeInt(yearMonth.getMonthValue());
    }

    private YearMonth getYearMonth(DataInputStream dataInputStream) throws IOException {
        return YearMonth.of(dataInputStream.readInt(), dataInputStream.readInt());
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
                resume.setSection(sectionType, readSection(dataInputStream, sectionType));
            });
            return resume;
        }
    }

    private AbstractSection readSection(DataInputStream dataInputStream, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dataInputStream.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readSomething(dataInputStream, dataInputStream::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readSomething(dataInputStream, () -> new Organization(
                        new Link(dataInputStream.readUTF(), dataInputStream.readUTF()),
                        readSomething(dataInputStream, () ->
                                new Organization.Position(getYearMonth(dataInputStream), getYearMonth(dataInputStream), dataInputStream.readUTF(), stringReadNonNull(dataInputStream.readUTF()))))));
            default:
                throw new IllegalArgumentException();

        }
    }

    private <T> List<T> readSomething(DataInputStream dataInputStream, readOrganization<T> readOrganization) throws IOException {
        int size = dataInputStream.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < list.size(); i++) {
            list.add(readOrganization.read());
        }
        return list;
    }

    private void readContactMap(DataInputStream dataInputStream, contactMapAction mapAction) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            mapAction.doIt();
        }
    }

    private String stringFillNonNull(String str) {
        String nullField = str;
        if (nullField == null) {
            str = "null";
        }
        return str;
    }

    private String stringReadNonNull(String str) {
        String s = "null";
        if (str.contains("null")) {
            s = null;
        }
        return s;
    }
}
