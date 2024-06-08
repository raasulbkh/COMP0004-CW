<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Age Distribution Chart</title>
</head>
<body>
<h2>Age Distribution Chart</h2>


<% if(request.getParameter("chartPath") != null && !request.getParameter("chartPath").isEmpty()) { %>
<img src="${pageContext.request.contextPath}/<%= request.getParameter("chartPath") %>" alt="Age Distribution Chart" />
<% } else { %>
<p>No chart available.</p>
<% } %>

</body>
</html>
