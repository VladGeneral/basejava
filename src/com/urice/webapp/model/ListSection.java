package com.urice.webapp.model;

import java.util.List;

public class ListSection extends Section {
   private final List<String> list;

    public ListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }
}
