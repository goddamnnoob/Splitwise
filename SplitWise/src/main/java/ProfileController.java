import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.Session;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
 @WebServlet("/profile")
public class ProfileController extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("username") == null || session.getAttribute("userid") == null){

			response.sendRedirect("Login.jsp");
		}
		displayUserDetails(request, response);
		try {
			displayUserGroups(request, response);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			
			e.printStackTrace();
		}
	}
	void displayUserDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter outPrintWriter = response.getWriter();
		HttpSession session = request.getSession();
		outPrintWriter.println("<h1>Hi "+session.getAttribute("username")+"</hi><br>");
		outPrintWriter.println("<a href=\"logout\">logout</a>");
	}
	
	void displayUserGroups(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		PrintWriter outPrintWriter = response.getWriter();
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userid");
		User user = UserDAO.getUserDetails(userId);
		outPrintWriter.println("<style>\n"
				+ "table, th, td {\n"
				+ "  border:1px solid black;\n"
				+ "}\n"
				+ "</style>");
		outPrintWriter.println("<br>");
		outPrintWriter.println("<p> Your Debt: "+user.getDebt()+"</p>");
		outPrintWriter.println("<p> Your Credit: "+user.getLiability()+" </p>");
		outPrintWriter.println("<br>");
		outPrintWriter.println("<h1> Your Groups: </h1>");
		outPrintWriter.println("<a href=\"NewGroup.jsp\"> Create New Group </a>");
		outPrintWriter.println("<br>");
		ArrayList<Group> groups = GroupDAO.getGroupsofUser(userId);
		outPrintWriter.println("<table style=\"width:100%\">");
		for(Group group  : groups) {
			group = GroupDAO.getGroupDetails(group.getGroupId());
			outPrintWriter.println("<tr>");
			outPrintWriter.println("<td><a href = \"group?groupid="+group.getGroupId()+"\">  "+group.getGroupName()+"  </a></td>");

			outPrintWriter.println("</tr>");
		}
		outPrintWriter.println("</table>");
		outPrintWriter.println("<h1> Your Debitors: </h1>");
		outPrintWriter.println("<br>");
		outPrintWriter.println("<table style=\"width:100%\">");
		outPrintWriter.println("<tr> <th>UserName</th> <th>Debt</th><th>Email</th> </tr>");
		ArrayList<User> debtors = UserDAO.peopleOwedToUser(userId);
		
		for(User debtor  : debtors) {
			outPrintWriter.println("<tr>");
			outPrintWriter.println("<td>"+debtor.getUserName()+"</td>");
			outPrintWriter.println("<td>"+debtor.getDebt()+"</td>");
			outPrintWriter.println("<td>"+debtor.getEmail()+"</td>");

			outPrintWriter.println("</tr>");
		}
		outPrintWriter.println("</table>");
		outPrintWriter.println("<h1> Your Creditors: </h1>");
		
		outPrintWriter.println("<br>");
		outPrintWriter.println("<table style=\"width:100%\">");
		outPrintWriter.println("<tr> <th>UserName</th> <th>Credit</th> <th>Email</th> </tr>");
		ArrayList<User> creditors = UserDAO.peopleOwedByUser(userId);
		for(User creditor  : creditors) {
			outPrintWriter.println("<tr>");
			outPrintWriter.println("<td>"+creditor.getUserName()+"</td>");
			outPrintWriter.println("<td>"+creditor.getLiability()+"</td>");
			outPrintWriter.println("<td>"+creditor.getEmail()+"</td>");

			outPrintWriter.println("</tr>");
		}
		outPrintWriter.println("</table>");
	}
}
