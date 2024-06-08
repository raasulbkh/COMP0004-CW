<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Patient Data App - Search Results</title>
</head>
<body>
<div class="main">
  <h1>Search Results</h1>
  <%
    // Retrieved the search results attribute which is a map of patient names to their indices
    Map<String, Integer> results = (Map<String, Integer>) request.getAttribute("result");

    if (results != null && !results.isEmpty()) {
  %>
  <ul>
    <%
      // Iterated over the map entries to create a list of clickable links/names
      for (Map.Entry<String, Integer> entry : results.entrySet()) {
    %>
    <li>
      <a href="${pageContext.request.contextPath}/patientDetails.html?index=<%=entry.getValue()%>"><%=entry.getKey()%></a>
    </li>
    <%
      }
    %>
  </ul>
  <%
  } else {
  %>
  <p>No results found.</p>
  <%
    }
  %>
</div>
</body>
</html>
