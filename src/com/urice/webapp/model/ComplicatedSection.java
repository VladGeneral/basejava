package com.urice.webapp.model;

import java.util.List;

public class ComplicatedSection extends Section {
    private final List<Complicate> data;

    public ComplicatedSection(List<Complicate> data) {
        this.data = data;
    }

    public List<Complicate> getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
