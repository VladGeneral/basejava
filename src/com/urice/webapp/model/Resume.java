package com.urice.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;
    private String fullName;

    private Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSection> sectionMap = new EnumMap<>(SectionType.class);

    public Resume() {
    }

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

    public Map<ContactType, String> getContactMap() {
        return contactMap;
    }

    public Map<SectionType, AbstractSection> getSectionMap() {
        return sectionMap;
    }

    public String getContact(ContactType contactType) {
        return contactMap.get(contactType);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sectionMap.get(sectionType);
    }

    public void setContact(ContactType contactType, String value) {
        contactMap.put(contactType, value);
    }

    public void setSection(SectionType sectionType, AbstractSection value) {
        sectionMap.put(sectionType, value);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contactMap, resume.contactMap) &&
                Objects.equals(sectionMap, resume.sectionMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contactMap, sectionMap);
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
