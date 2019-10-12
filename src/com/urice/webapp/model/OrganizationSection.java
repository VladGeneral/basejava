package com.urice.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private final List<Organization> data;

    public OrganizationSection(Organization... data) {
        Objects.requireNonNull(data, "organizations must ot be null");
        this.data = Arrays.asList(data);
    }

    public OrganizationSection(List<Organization> data) {
        Objects.requireNonNull(data, "organizations must ot be null");
        this.data = data;
    }

    public List<Organization> getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
