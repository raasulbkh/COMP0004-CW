
<html>
<head>
  <title>Patient Data App</title>
</head>
<body>
<div class="main">
  <h1>Search</h1>
  <form method="POST" action="${pageContext.request.contextPath}/runsearch.html">
    <input type="text" name="searchstring" placeholder="Enter search keyword here"/>
    <input type="submit" value="Search"/>
  </form>
</div>
</body>
</html>