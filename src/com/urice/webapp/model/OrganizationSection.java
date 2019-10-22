package com.urice.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private final List<Organization> data;

    public OrganizationSection(Organization... data) {
        this.data = Objects.requireNonNull(Arrays.asList(data), "organizations must ot be null");
    }

    public OrganizationSection(List<Organization> data) {
        this.data = Objects.requireNonNull(data, "organizations must ot be null");
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
