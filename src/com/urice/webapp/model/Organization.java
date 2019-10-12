package com.urice.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Organization {

    private final Link homePage;
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String position;
    private final String description;

    public Organization(String name, String url, YearMonth startDate, YearMonth endDate, String position, String description) {
        this.homePage = new Link(name, url);
        this.startDate = Objects.requireNonNull(startDate, "startDate must not be null");
        this.endDate = Objects.requireNonNull(endDate, "endDate must not be null");
        this.position = Objects.requireNonNull(position, "position must not be null");
        this.description = description;
    }

    @Override
    public String toString() {
        return homePage + " " + startDate + "/" + endDate + " " + position + " " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!position.equals(that.position)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
