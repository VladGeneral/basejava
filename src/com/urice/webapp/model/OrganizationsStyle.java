package com.urice.webapp.model;

import java.time.LocalDate;

public class OrganizationsStyle {

    private final String organizationName;
    private final LocalDate dateBeginningWork;
    private final LocalDate dateEndingWork;
    private final String objective;  //position
    private final String descriptionOfWork;

    public OrganizationsStyle(String organizationName, LocalDate dateBeginningWork, LocalDate dateEndingWork, String objective, String descriptionOfWork) {
        this.organizationName = organizationName;
        this.dateBeginningWork = dateBeginningWork;
        this.dateEndingWork = dateEndingWork;
        this.objective = objective;
        this.descriptionOfWork = descriptionOfWork;
    }


    @Override
    public String toString() {
        return organizationName + " " + dateBeginningWork + " " + dateEndingWork + " " + objective + " " + descriptionOfWork;
    }
}
