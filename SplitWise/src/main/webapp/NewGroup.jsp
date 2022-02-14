<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create New Group</title>
</head>
<body>
<%
if(session.getAttribute("username") == null){
	response.sendRedirect("login.jsp");
}

%>
<form action="newgroup" method="post">
GroupName: <input type="text" name="groupname" required><br>
<input type="submit">
</form>
</body>
</html>