package com.urice.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private Map<ContactType, String> contactMap = new HashMap<>();
    private Map<SectionType, Section> sectionMap = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

//    public Map<ContactType, String> getContactMap() {
//        return contactMap;
//    }

    public String getContactMap(ContactType contactType) {
        return contactMap.get(contactType);
    }

//    public Map<SectionType, Section> getSectionMap() {
//        return sectionMap;
//    }

    public Section getSectionMap(SectionType sectionType) {
        return sectionMap.get(sectionType);
    }

    public void setContactMap(ContactType contactType, String value) {
        contactMap.put(contactType, value);
    }

    public void setSectionMap(SectionType sectionType, Section value) {
        sectionMap.put(sectionType, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume resume) {
        int compare = fullName.compareTo(resume.getFullName());
        return compare == 0 ? uuid.compareTo(resume.getUuid()) : compare;
    }
}
