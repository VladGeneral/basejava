package com.urice.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> data;

    public ListSection() {
    }

    public ListSection(String... data) {
        this.data = Objects.requireNonNull(Arrays.asList(data), " data must not be null");
    }

    public ListSection(List<String> data) {
        this.data = Objects.requireNonNull(data, "data must ot be null");
    }

    public List<String> getData() {
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

        ListSection that = (ListSection) o;

        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
