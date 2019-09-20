package com.urice.webapp.model;

import java.time.LocalDate;

public class Organization {

    private final String organizationName;
    private final LocalDate dateBeginningWork;
    private final LocalDate dateEndingWork;
    private final String objective;  //position
    private final String descriptionOfWork;

    public Organization(String organizationName, LocalDate dateBeginningWork, LocalDate dateEndingWork, String objective, String descriptionOfWork) {
        this.organizationName = organizationName;
        this.dateBeginningWork = dateBeginningWork;
        this.dateEndingWork = dateEndingWork;
        this.objective = objective;
        this.descriptionOfWork = descriptionOfWork;
    }

}
