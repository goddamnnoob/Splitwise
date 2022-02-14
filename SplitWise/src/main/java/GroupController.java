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

@WebServlet("/group")
public class GroupController extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") == null || request.getParameter("groupid") == null) {
			response.sendRedirect("Login.jsp");
		}
		try {
			DisplayGroupDetails(request, response);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
	
	static void DisplayGroupDetails(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		PrintWriter outPrintWriter = response.getWriter();
		int groupId = Integer.parseInt((String) request.getParameter("groupid"));
		Group group = GroupDAO.getGroupDetails(groupId);
		ArrayList<User> users = new ArrayList<User>();
		outPrintWriter.println("<style>\n"
				+ "table, th, td {\n"
				+ "  border:1px solid black;\n"
				+ "}\n"
				+ "</style>");

		outPrintWriter.println("<a href=\"profile\">back to profile</a>" );
		outPrintWriter.println("<h1> Group Name: " + group.getGroupName()+"</h1>" );
		outPrintWriter.println("<a href=\"AddExpenseToGroup.jsp?groupid="+groupId+"\">"+"Add expense to this group </a>");
		outPrintWriter.println("<br>");
		outPrintWriter.println("<a href=\"Addusertogroup.jsp?groupid="+groupId+"\">"+"      Add user to this group </a>");
		
		outPrintWriter.println("<h2>Users in this group: </h2>");
		outPrintWriter.println("<table style=\"width:20%\">");
		outPrintWriter.println("<tr> <th>UserName</th> <th>Debt</th><th>Credit</th> </tr>");
		users = GroupDAO.getUsersInGroup(groupId);
		
		for(int i=0;i<users.size();i++) {
			outPrintWriter.println("</tr>");
			outPrintWriter.println("<td> "+users.get(i).getUserName() +"</td><td> "+ExpenseDAO.totalDebtByUserInGroup(users.get(i).getUserid(), groupId)+"</td><td> "+ExpenseDAO.totalDebtToUserInGroup(users.get(i).getUserid(), groupId)+"</td>");
			outPrintWriter.println("</tr>");
		}
		outPrintWriter.println("</table>");
		
	}
}
