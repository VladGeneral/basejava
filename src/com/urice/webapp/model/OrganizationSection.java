package com.urice.webapp.model;

import java.util.Date;

public class OrganizationSection extends Section {
    private final List<OrganizationSection>

    private final String organizationName;
    private final Date dateBeginningWork;
    private final Date dateEndingWork;
    private final String position;
    private final String descriptionOfWork;


    public OrganizationSection(String organizationName, Date dateBeginningWork, Date dateEndingWork, String position, String descriptionOfWork) {
        this.organizationName = organizationName;
        this.dateBeginningWork = dateBeginningWork;
        this.dateEndingWork = dateEndingWork;
        this.position = position;
        this.descriptionOfWork = descriptionOfWork;
    }


}
