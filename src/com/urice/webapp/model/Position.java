package com.urice.webapp.model;

import java.time.YearMonth;

public class Position {
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String position;
    private final String description;

    public Position(YearMonth startDate, YearMonth endDate, String position, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position1 = (Position) o;

        if (!startDate.equals(position1.startDate)) return false;
        if (!endDate.equals(position1.endDate)) return false;
        if (!position.equals(position1.position)) return false;
        return description.equals(position1.description);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
