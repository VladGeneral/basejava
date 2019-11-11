package com.urice.webapp.storage.serializer;

import com.urice.webapp.model.*;

import java.io.*;
import java.util.Collection;
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

/////////////////


//            Map<SectionType, AbstractSection> sectionMap = resume.getSectionMap();
//            dataOutputStream.writeInt(sectionMap.size());
//
//            for (Map.Entry<SectionType, AbstractSection> entry: sectionMap.entrySet()) {
//
//                dataOutputStream.writeUTF(entry.getKey().name());
//
//                for (SectionType type : SectionType.values()) {
//                    System.out.println(type.getTitle() + ": " + resume.getSection(type));
//                }
//
//            }
//
//            for (SectionType type : SectionType.values()) {
//                System.out.println(type.getTitle() + ": " + resume.getSection(type));
//            }


            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSectionMap().entrySet()) {
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
                        writeSection(dataOutputStream, ((ListSection) section).getData());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeSection(dataOutputStream, ((OrganizationSection) section).getData());
                        break;

                }
            }

        }
    }

    private  <T> void writeSection(DataOutputStream dataOutputStream, Collection<T> collection) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T t : collection) {
            dataOutputStream.writeUTF(t.toString());
        }
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

            readSection(dataInputStream);

            return resume;
        }
    }

    private void readSection(DataInputStream dataInputStream){

    }

}
