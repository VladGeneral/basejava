package com.urice.webapp.storage.serializer;

import com.urice.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            Map<ContactType, String> contactMap = resume.getContactMap();
            dataOutputStream.writeInt(contactMap.size());

            for (Map.Entry<ContactType, String> entry : contactMap.entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            }


            Map<SectionType, AbstractSection> sectionMap = resume.getSectionMap();
            dataOutputStream.writeInt(sectionMap.size());

            for (Map.Entry<SectionType, AbstractSection> entry : sectionMap.entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dataOutputStream, ((ListSection) section).getData(), text ->{
                            dataOutputStream.writeUTF(text);
                        });
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
                                if (pos.getDescription() == null) {
                                    dataOutputStream.writeUTF("null");
                                } else {
                                    dataOutputStream.writeUTF(pos.getDescription());
                                }
                            });
                        });
                        break;
                }
            }
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


    private <T> void writeList(DataOutputStream dataOutputStream, Collection<T> collection) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T t : collection) {
            dataOutputStream.writeUTF(t.toString());
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
            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }

            int sizeSections = dataInputStream.readInt();
            for (int i = 0; i < sizeSections; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                resume.setSection(sectionType, readSection(dataInputStream, sectionType));
            }
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
                return new ListSection(readList(dataInputStream));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readOrganization(dataInputStream));
            default:
                throw new IllegalArgumentException();

        }
    }

    private List<String> readList(DataInputStream dataInputStream) throws IOException {
        int size = dataInputStream.readInt();
        List<String> list = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            list.add(dataInputStream.readUTF());
        }
        return list;
    }

    private List<Organization> readOrganization(DataInputStream dts) throws IOException {
        int size = dts.readInt();
        List<Organization> list = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Link link = new Link(dts.readUTF(), dts.readUTF());
            List<Organization.Position> positions = readPosition(dts);
            list.add(new Organization(link, positions));
        }
        return list;
    }

    private List<Organization.Position> readPosition(DataInputStream dts) throws IOException {
        int size = dts.readInt();
        List<Organization.Position> positionList = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            YearMonth startYearMonth = YearMonth.of(dts.readInt(), dts.readInt());
            YearMonth endYearMonth = YearMonth.of(dts.readInt(), dts.readInt());
            String position = dts.readUTF();
            String description = dts.readUTF();
            if (description.contains("null")) {
                description = null;
            }
            positionList.add(new Organization.Position(startYearMonth, endYearMonth, position, description));
        }
        return positionList;
    }
}
