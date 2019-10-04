package com.urice.webapp.model;

import java.time.YearMonth;

public class Organization {

    private final String name;
    private final String url;
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String position;
    private final String description;

    public Organization(String name, String url, YearMonth startDate, YearMonth endDate, String position, String description) {
        this.name = name;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " - " + url + " " + startDate + "/" + endDate + " " + position + " " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!name.equals(that.name)) return false;
        if (!url.equals(that.url)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!position.equals(that.position)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
