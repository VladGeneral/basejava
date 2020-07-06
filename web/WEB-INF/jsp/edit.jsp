<%@ page import="com.urice.webapp.model.ContactType" %>
<%@ page import="com.urice.webapp.model.ListSection" %>
<%@ page import="com.urice.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="./css/style.css">
    <jsp:useBean id="resume" type="com.urice.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=30 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionEntry" items="${resume.sectionMap}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urice.webapp.model.SectionType, com.urice.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urice.webapp.model.AbstractSection"/>
            <tr>
                <td><h3><a name="type.name">${type.title}</a></h3></td>
            </tr>
            <c:choose>
                <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                    <input type='text' name='${type}' size=100 value='<%=section%>'>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <textarea name='${type}' cols=100
                              rows=10><%=String.join("\n", ((ListSection) section).getData())%></textarea>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

