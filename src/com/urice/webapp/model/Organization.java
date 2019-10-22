package com.urice.webapp.model;

import com.urice.webapp.util.DateUtil;

import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urice.webapp.util.DateUtil.NOW;

public class Organization {

    private final Link homePage;
    private final List<Position> positions;

    public Organization(String name, String url, Position... positions) {
        this.homePage = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "Organization(" + homePage + ',' + positions + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    public static class Position {
        private final YearMonth startDate;
        private final YearMonth endDate;
        private final String position;
        private final String description;

        public Position(int year, Month month, String position, String description) {
            this(DateUtil.of(
                    Objects.requireNonNull(year, "year must not be null"),
                    Objects.requireNonNull(month, "month must not be null")),
                    NOW,
                    Objects.requireNonNull(position, "position must not be null"),
                    description);
        }

        public Position(YearMonth startDate, YearMonth endDate, String position, String description) {
            this.startDate = Objects.requireNonNull(startDate, "startDate must not be null");
            this.endDate = Objects.requireNonNull(endDate, "endDate must not be null");
            this.position = Objects.requireNonNull(position, "position must not be null");
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
            return "Position(" + startDate + ',' + endDate + ',' + position + ',' + description + ')';
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
}
