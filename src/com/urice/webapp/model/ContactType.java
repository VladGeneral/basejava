package com.urice.webapp.model;

public enum ContactType {
    MOBILE("Телефон"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype" + value + "'>" + value + "</a>";
        }
    },
    MAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml0(String value) {
        return  title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : title + ": " + value;
    }
}
