package com.urice.webapp.model;

import java.util.List;

public class OrganizationSection extends Section {
    private final List<Organization> list;

    public OrganizationSection(List<Organization> list) {
        this.list = list;
    }

    public List<Organization> getList() {
        return list;
    }
}
