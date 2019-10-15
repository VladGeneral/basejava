package com.urice.webapp;

import com.urice.webapp.model.*;

import java.time.YearMonth;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("ResumeTestModel");

        resume.setContactMap(ContactType.MOBILE, "89991236556");
        resume.setContactMap(ContactType.SKYPE, "vladgeneral");
        resume.setContactMap(ContactType.MAIL, "vladgeneral@gmail.com");
        resume.setContactMap(ContactType.LINKEDIN, "www.linkedin.com");
        resume.setContactMap(ContactType.GITHUB, "www.github.com");
        resume.setContactMap(ContactType.STACKOVERFLOW, "www.stackoverflow.com");
        resume.setContactMap(ContactType.HOMEPAGE, null);

        resume.setSectionMap(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры"));
        resume.setSectionMap(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSectionMap(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.", "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));

        resume.setSectionMap(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""));
        resume.setSectionMap(SectionType.EXPERIENCE, new OrganizationSection(new Organization("Java Online Projects", "www.Javaops.ru", new Position(YearMonth.of(2013, 10), YearMonth.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike", "www.wrike.com", new Position(YearMonth.of(2014, 10), YearMonth.of(2016, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."))));
        resume.setSectionMap(SectionType.EDUCATION, new OrganizationSection(new Organization("Coursera", "www.coursera.org", new Position(YearMonth.of(2013, 3), YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky", null), new Position(YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер (программист Fortran, C)", null))));

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + ": " + resume.getContactMap(type));
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + ": " + resume.getSectionMap(type));
        }

        resume.getSectionMap(SectionType.ACHIEVEMENT);

    }
}
