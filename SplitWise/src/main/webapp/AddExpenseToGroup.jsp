<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Expense To Group</title>
</head>
<body>
<% 
if(session.getAttribute("username") == null || request.getParameter("groupid") == null){
		response.sendRedirect("profile");
	}
	session.setAttribute("addexpensetogroupid", request.getParameter("groupid"));
	if(session.getAttribute("addexpensetogrouperror") != null ){
		String error = session.getAttribute("addexpensetogrouperror").toString();
		out.println(error);
		session.removeAttribute("addexpensetogrouperror");
	}
%>
	
<form action="addexpensetogroup" method="post">
Amount: <input type="number" step="any" name="amount"><br>
<input type="submit">
</form>
</body>
</html>