package com.urice.webapp.model;

import java.util.Arrays;
import java.util.List;

public class OrganizationsSection extends Section {
    private final List<OrganizationsStyle> data;

    public OrganizationsSection(OrganizationsStyle... data) {
        this.data = Arrays.asList(data);
    }

    public OrganizationsSection(List<OrganizationsStyle> data) {
        this.data = data;
    }

    public List<OrganizationsStyle> getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
