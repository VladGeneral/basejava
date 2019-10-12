package com.urice.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> data;

    public ListSection(String... data) {
        Objects.requireNonNull(data, "items must ot be null");
        this.data = Arrays.asList(data);
    }

    public ListSection(List<String> data) {
        Objects.requireNonNull(data, "items must ot be null");
        this.data = data;
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
