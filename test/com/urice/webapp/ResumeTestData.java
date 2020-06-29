package com.urice.webapp;

import com.urice.webapp.model.*;

import java.util.UUID;

public class ResumeTestData {

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.fillResume(UUID_1, "Name_1");
        RESUME_2 = ResumeTestData.fillResume(UUID_2, "Name_2");
        RESUME_3 = ResumeTestData.fillResume(UUID_3, "Name_3");
        RESUME_4 = ResumeTestData.fillResume(UUID_4, "Name_4");
    }

    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        /*
        Для меня:
        в JsonPathStorageTest методы getAllSorted/get/save падают, если в
        резюме какое то из значений в ContactType = null, что логично, ведь
        contactMap в equals не содержит null элементы.
        При get, если значение было равно одной секции null, то вся секция
        пропадает и при сравнении кидает AssertionError ожидается 7, а пришло допустим 6;

        java.lang.AssertionError: expected: java.util.Arrays$ArrayList<[uuid1(Name_1), uuid2(Name_2),
        uuid3(Name_3)]> but was: java.util.ArrayList<[uuid1(Name_1), uuid2(Name_2), uuid3(Name_3)]>
         */

        resume.setContact(ContactType.MOBILE, "89991236556");
        resume.setContact(ContactType.SKYPE, "vladgeneral");
        resume.setContact(ContactType.MAIL, "vladgeneral@gmail.com");
        resume.setContact(ContactType.LINKEDIN, "www.linkedin.com");
        resume.setContact(ContactType.GITHUB, "www.github.com");
        resume.setContact(ContactType.STACKOVERFLOW, "www.stackoverflow.com");
        resume.setContact(ContactType.HOMEPAGE, "www.github.com/123");

        resume.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, инициативность."));
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. ",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
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
        /*resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization( "Java Online Projects", "www.Javaops.ru", new Organization.Position(YearMonth.of(2013, 10), YearMonth.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike", "www.wrike.com", new Organization.Position(YearMonth.of(2014, 10), YearMonth.of(2016, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike."))));
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("Coursera", "www.coursera.org", new Organization.Position(YearMonth.of(2013, 3), YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky", null)),
                new Organization("Санкт-Петербургский исследовательский университет", "www.spb.ru", new Organization.Position(YearMonth.of(1993, 9), YearMonth.of(1996, 7), "Аспирантура (программист С, С++)", null), new Organization.Position(YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер (программист Fortran, C)", null))));
        */
        return resume;
    }
}
