<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Living Patients Sorted By Age</title>
</head>
<body>
<h2>Living Patients Sorted By Age</h2>
<ul>
    <% List<String> patients = (List<String>) request.getAttribute("patientsSortedByAge");
        for (String patientInfo : patients) {
            String[] parts = patientInfo.split(" - ");
            String name = parts[0];
            String age = parts[1];
            String index = parts[2]; // Patient index for URL
    %>
    <li><a href="/patientDetails.html?index=<%=index%>"><%=name%></a> - <%=age%> </li>
    <% } %>
</ul>
</body>
</html>
