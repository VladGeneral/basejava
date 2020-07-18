package com.urice.webapp.web;

import com.urice.webapp.Config;
import com.urice.webapp.model.*;
import com.urice.webapp.storage.Storage;
import com.urice.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean isExist = (uuid != null && uuid.trim().length() != 0);
        Resume r;
        if (!isExist) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && value.trim().length() != 0) {
                r.setContact(contactType, value);
            } else {
                r.getContactMap().remove(contactType);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                        r.setSection(type, new ListSection(value.split("\n")));
                        break;
                    case QUALIFICATIONS:
                        r.setSection(type, new ListSection(value.split("\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationsList = new ArrayList<>();
                        String[] names = request.getParameterValues(type.name());
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < names.length; i++) {
                            List<Organization.Position> positionList = new ArrayList<>();
                            String[] startDates = request.getParameterValues(type.name() + i + "startDate");
                            String[] endDates = request.getParameterValues(type.name() + i + "endDate");
                            String[] positions = request.getParameterValues(type.name() + i + "position");
                            String[] descriptions = request.getParameterValues(type.name() + i + "description");
                            for (int j = 0; j < positions.length; j++) {
                                if (positions != null) {
                                    positionList.add(new Organization.Position(
                                            DateUtil.parse(startDates[j]),
                                            DateUtil.parse(endDates[j]),
                                            positions[j],
                                            descriptions[j]));
                                }
                            }
                            organizationsList.add(new Organization(new Link(names[i], urls[i]), positionList));
                        }
                        r.setSection(type, new OrganizationSection(organizationsList));
                        break;
                }
            } else {
                r.getSectionMap().remove(type);
            }
        }
        if (!isExist) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "add":
                r = Resume.EMPTY_RESUME;
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType sectionType : SectionType.values()) {
                    AbstractSection section = r.getSection(sectionType);
                    switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> list = new ArrayList<>();
                            if (organizationSection == null) {
                                section = new OrganizationSection(Organization.EMPTY);
                            } else {
                                for (Organization organization : organizationSection.getData()) {
                                    List<Organization.Position> positions = new ArrayList<>();
                                    positions.addAll(organization.getPositions());
                                    list.add(new Organization(organization.getHomePage(), positions));
                                }
                                section = new OrganizationSection(list);
                            }
                            break;
                    }
                    r.setSection(sectionType, section);
                }
                break;
            default:
                throw new IllegalStateException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
