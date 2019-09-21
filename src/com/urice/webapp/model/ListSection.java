package com.urice.webapp.model;

import java.util.Arrays;
import java.util.List;

public class ListSection extends Section {
   private final List<String> data;


    public ListSection(String... data) {
        this.data = Arrays.asList(data);
    }

    public ListSection(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
