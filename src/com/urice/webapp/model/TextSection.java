package com.urice.webapp.model;

public class TextSection extends Section {
        private final String data;

    public TextSection(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return data;
    }
}
