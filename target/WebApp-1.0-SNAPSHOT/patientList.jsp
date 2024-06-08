<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patients:</h2>
  <ul>
    <%
      Map<String, Integer> patientDetails = (Map<String, Integer>) request.getAttribute("patientNames");
      for (Map.Entry<String, Integer> entry : patientDetails.entrySet()) {
        String patientName = entry.getKey();
        Integer index = entry.getValue();
        String href = "patientDetails.html?index=" + index;
    %>
    <li><a href="<%=href%>"><%=patientName%></a></li>
    <% } %>

  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
