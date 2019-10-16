package com.urice.webapp;

import com.urice.webapp.model.*;

import java.time.YearMonth;

public class ResumeTestData {

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "ResumeTestModel");
        RESUME_2 = new Resume(UUID_2, "Name_2");
        RESUME_3 = new Resume(UUID_3, "Name_3");
        RESUME_4 = new Resume(UUID_4, "Name_4");

        RESUME_1.setContactMap(ContactType.MOBILE, "89991236556");
        RESUME_1.setContactMap(ContactType.SKYPE, "vladgeneral");
        RESUME_1.setContactMap(ContactType.MAIL, "vladgeneral@gmail.com");
        RESUME_1.setContactMap(ContactType.LINKEDIN, "www.linkedin.com");
        RESUME_1.setContactMap(ContactType.GITHUB, "www.github.com");
        RESUME_1.setContactMap(ContactType.STACKOVERFLOW, "www.stackoverflow.com");
        RESUME_1.setContactMap(ContactType.HOMEPAGE, null);

        RESUME_1.setSectionMap(SectionType.PERSONAL, new TextSection("Аналитический склад ума, инициативность."));
        RESUME_1.setSectionMap(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        RESUME_1.setSectionMap(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. ",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));
        RESUME_1.setSectionMap(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
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
        RESUME_1.setSectionMap(SectionType.EXPERIENCE, new OrganizationSection(new Organization("Java Online Projects", "www.Javaops.ru", new Position(YearMonth.of(2013, 10), YearMonth.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike", "www.wrike.com", new Position(YearMonth.of(2014, 10), YearMonth.of(2016, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike."))));
        RESUME_1.setSectionMap(SectionType.EDUCATION, new OrganizationSection(new Organization("Coursera", "www.coursera.org", new Position(YearMonth.of(2013, 3), YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky", null)),
                new Organization("Санкт-Петербургский исследовательский университет", "www.spb.ru", new Position(YearMonth.of(1993, 9), YearMonth.of(1996, 7), "Аспирантура (программист С, С++)", null), new Position(YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер (программист Fortran, C)", null))));
    }

    public static void main(String[] args) {

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + ": " + RESUME_1.getContactMap(type));
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + ": " + RESUME_1.getSectionMap(type));
        }

    }
}
