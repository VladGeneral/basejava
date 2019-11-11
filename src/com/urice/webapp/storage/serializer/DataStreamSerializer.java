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
                        writeList(dataOutputStream, ((ListSection) section).getData());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganization(dataOutputStream, ((OrganizationSection) section).getData());
                        break;
                }
            }
        }
    }

    private <T> void writeList(DataOutputStream dataOutputStream, Collection<T> collection) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T t : collection) {
            dataOutputStream.writeUTF(t.toString());
        }
    }

    private void writeOrganization(DataOutputStream dataOutputStream, List<Organization> organizations) throws IOException {
        dataOutputStream.writeInt(organizations.size());

        for (Organization o : organizations) {
            writeLink(dataOutputStream, o);
            writePosition(dataOutputStream, o.getPositions());

        }
    }

    private void writeLink(DataOutputStream dataOutputStream, Organization link) throws IOException {
        dataOutputStream.writeUTF(link.getHomePage().getName());
        dataOutputStream.writeUTF(link.getHomePage().getUrl());
    }

    private void writePosition(DataOutputStream dataOutputStream, List<Organization.Position> position) throws IOException {
        dataOutputStream.writeInt(position.size());

        for (Organization.Position p : position) {
            writeYearMonth(dataOutputStream, p.getStartDate());
            writeYearMonth(dataOutputStream, p.getEndDate());
            dataOutputStream.writeUTF(p.getPosition());
            if (p.getDescription() == null) {
                dataOutputStream.writeUTF("null");
            } else {
                dataOutputStream.writeUTF(p.getDescription());
            }

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
                   if (description.contains("null")){
                       description = null;
                   }
            positionList.add(new Organization.Position(startYearMonth,endYearMonth,position,description));
        }
        return positionList;
    }
}
