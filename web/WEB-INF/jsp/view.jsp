<%@ page import="com.urice.webapp.model.ListSection" %>
<%@ page import="com.urice.webapp.model.TextSection" %>
<%@ page import="com.urice.webapp.model.OrganizationSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="./img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contactMap}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urice.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
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
        <c:when test="${type=='PERSONAL'}">
        <tr>
            <td>
                <%=((TextSection) section).getContent()%>
            </td>
        </tr>
        </c:when>
        <c:when test="${type=='OBJECTIVE'}">
        <tr>
            <td colspan="2">
                <%=((TextSection) section).getContent()%>
            </td>
        </tr>
        </c:when>
        <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
        <tr>
            <td colspan="2">
                <ol>
                    <c:forEach var="value" items="<%=((ListSection) section).getData()%>">
                        <li>${value}</li>
                    </c:forEach>
                </ol>
            </td>
        </tr>
        </c:when>
        <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
        <tr>
            <td>
                <ol>
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getData()%>">
                        <li><a href="${organization.homePage.url}">${organization.homePage.name}</a></li>
                        <c:forEach var="position" items="${organization.positions}">
                            <font size="4" face="Arial">${position.startDate} / ${position.endDate}</font><br>
                            <font size="3" face="Arial">${position.position}</font><br>
                            <font size="2" face="Arial">${position.description}</font><br><br>
                        </c:forEach>
                    </c:forEach>
                </ol>
            </td>
        </tr>
        </c:when>
        </c:choose>
        </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

