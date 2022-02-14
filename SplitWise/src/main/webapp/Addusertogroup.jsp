<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add user to Group</title>
</head>
<body>

<% 
if(session.getAttribute("username") == null || request.getParameter("groupid") == null){
		response.sendRedirect("profile");
	}
	session.setAttribute("addusertogroupid", request.getParameter("groupid"));
	if(session.getAttribute("addusertogrouperror") != null ){
		String error = session.getAttribute("addusertogrouperror").toString();
		out.println(error);
		session.removeAttribute("addusertogrouperror");
	}
%>
	
<form action="addusertogroup" method="post">
UserName: <input type="text" name="username"><br>
<input type="submit">
</form>
</body>
</html>