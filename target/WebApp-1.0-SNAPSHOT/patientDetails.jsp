<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Patient Details</title>
</head>
<body>
<h2>Patient Details:</h2>
<div>
  <p><strong>Name:</strong> <c:out value="${patientDetails.Name}" /></p>
  <p><strong>Birthdate:</strong> <c:out value="${patientDetails.Birthdate}" /></p>
  <c:choose>
    <c:when test="${not empty patientDetails['Death Date']}">
      <p><strong>Death Date:</strong> <c:out value="${patientDetails['Death Date']}" /></p>
    </c:when>
    <c:otherwise>
      <p><strong>Age:</strong> <c:out value="${patientDetails.Age}" /> years</p>
    </c:otherwise>
  </c:choose>
  <p><strong>Gender:</strong> <c:out value="${patientDetails.Gender}" /></p>
  <c:if test="${not empty patientDetails['Marital Status']}">
    <p><strong>Marital Status:</strong> <c:out value="${patientDetails['Marital Status']}" /></p>
  </c:if>
  <p><strong>Race:</strong> <c:out value="${patientDetails.Race}" /></p>
  <p><strong>Ethnicity:</strong> <c:out value="${patientDetails.Ethnicity}" /></p>
  <p><strong>Address:</strong> <c:out value="${patientDetails.Address}" /></p>
  <p><strong>Birthplace:</strong> <c:out value="${patientDetails.Birthplace}" /></p>
  <c:if test="${not empty patientDetails['Maiden Name']}">
    <p><strong>Maiden Name:</strong> <c:out value="${patientDetails['Maiden Name']}" /></p>
  </c:if>
  <c:if test="${not empty patientDetails.SSN}">
    <p><strong>SSN:</strong> <c:out value="${patientDetails.SSN}" /></p>
  </c:if>
  <c:if test="${not empty patientDetails.Passport}">
    <p><strong>Passport Number:</strong> <c:out value="${patientDetails.Passport}" /></p>
  </c:if>
  <c:if test="${not empty patientDetails['Drivers License']}">
    <p><strong>Driver's License:</strong> <c:out value="${patientDetails['Drivers License']}" /></p>
  </c:if>
</div>
</body>
</html>
