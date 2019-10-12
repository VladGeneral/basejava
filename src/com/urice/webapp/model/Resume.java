package com.urice.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSection> sectionMap = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {

        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName must not be null");
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContactMap(ContactType contactType) {
        return contactMap.get(contactType);
    }

    public AbstractSection getSectionMap(SectionType sectionType) {
        return sectionMap.get(sectionType);
    }

    public void setContactMap(ContactType contactType, String value) {
        contactMap.put(contactType, value);
    }

    public void setSectionMap(SectionType sectionType, AbstractSection value) {
        sectionMap.put(sectionType, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (!contactMap.equals(resume.contactMap)) return false;
        return sectionMap.equals(resume.sectionMap);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contactMap.hashCode();
        result = 31 * result + sectionMap.hashCode();
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
