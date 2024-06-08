<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Patients by City</title>
</head>
<body>
<h2>View Patients by City</h2>
<form action="patientsByCity" method="get">
    <select name="city" onchange="this.form.submit()">
        <option value="">--Select a City--</option>
        <c:forEach items="${cities}" var="city">
            <option value="${city}" ${city == selectedCity ? 'selected' : ''}>${city}</option>
        </c:forEach>
    </select>
</form>

<c:if test="${not empty selectedCity}">
    <h3>Patients in ${selectedCity}:</h3>
    <ul>
        <c:forEach items="${patients}" var="patient">
            <c:url var="detailLink" value="patientDetails.html">
                <c:param name="index" value="${fn:split(patient, '-')[1]}"/>
            </c:url>
            <li><a href="${detailLink}">${fn:split(patient, '-')[0]}</a></li>
        </c:forEach>
    </ul>
</c:if>

<c:if test="${empty patients and not empty selectedCity}">
    <p>No patients found in ${selectedCity}.</p>
</c:if>
</body>
</html>
