<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register page</title>
</head>
<body>
<%	

	if(session.getAttribute("username") != null){
		response.sendRedirect("Profile.jsp");
	}
	if(session.getAttribute("registererror") != null ){
		String error = session.getAttribute("registererror").toString();
		out.println(error);
		session.removeAttribute("registererror");
	}
%>
<form action="signup" method="post">
UserName: <input type="text" name="username"><br>

Password: <input type="text" name="password"><br>

Email: <input type="text" name="email"><br>
<input type="submit">
</form>
<a href="Login.jsp">To Login page</a>
</body>
</html>