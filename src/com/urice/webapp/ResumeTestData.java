package com.urice.webapp;

import com.urice.webapp.model.*;

import java.time.YearMonth;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = fillResume("uuidTest", "nameTest");
        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + ": " + resume.getContactMap(type));
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + ": " + resume.getSectionMap(type));
        }
    }

    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.setContactMap(ContactType.MOBILE, "89991236556");
        resume.setContactMap(ContactType.SKYPE, "vladgeneral");
        resume.setContactMap(ContactType.MAIL, "vladgeneral@gmail.com");
        resume.setContactMap(ContactType.LINKEDIN, "www.linkedin.com");
        resume.setContactMap(ContactType.GITHUB, "www.github.com");
        resume.setContactMap(ContactType.STACKOVERFLOW, "www.stackoverflow.com");
        resume.setContactMap(ContactType.HOMEPAGE, null);

        resume.setSectionMap(SectionType.PERSONAL, new TextSection("Аналитический склад ума, инициативность."));
        resume.setSectionMap(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSectionMap(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. ",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));
        resume.setSectionMap(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor.",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования.",
                "Родной русский, английский \"upper intermediate\""));
        resume.setSectionMap(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization( "Java Online Projects", "www.Javaops.ru", new Organization.Position(YearMonth.of(2013, 10), YearMonth.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike", "www.wrike.com", new Organization.Position(YearMonth.of(2014, 10), YearMonth.of(2016, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike."))));
        resume.setSectionMap(SectionType.EDUCATION, new OrganizationSection(
                new Organization("Coursera", "www.coursera.org", new Organization.Position(YearMonth.of(2013, 3), YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky", null)),
                new Organization("Санкт-Петербургский исследовательский университет", "www.spb.ru", new Organization.Position(YearMonth.of(1993, 9), YearMonth.of(1996, 7), "Аспирантура (программист С, С++)", null), new Organization.Position(YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер (программист Fortran, C)", null))));
        return resume;
    }
}
