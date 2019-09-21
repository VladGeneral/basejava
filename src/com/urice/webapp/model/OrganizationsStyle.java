package com.urice.webapp.model;

import java.time.YearMonth;

public class OrganizationsStyle {

    private final String organizationName;
    private final String organizationURL;
    private final YearMonth dateBeginningWork;
    private final YearMonth dateEndingWork;
    private final String objective;  //position
    private final String descriptionOfWork;

    public OrganizationsStyle(String organizationName, String organizationURL, YearMonth dateBeginningWork, YearMonth dateEndingWork, String objective, String descriptionOfWork) {
        this.organizationName = organizationName;
        this.organizationURL = organizationURL;
        this.dateBeginningWork = dateBeginningWork;
        this.dateEndingWork = dateEndingWork;
        this.objective = objective;
        this.descriptionOfWork = descriptionOfWork;
    }


    @Override
    public String toString() {
        return organizationName + " - " + organizationURL + " " + dateBeginningWork + "/" + dateEndingWork + " " + objective + " " + descriptionOfWork;
    }
}
