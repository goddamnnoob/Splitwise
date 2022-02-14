import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/newgroup")
public class CreatGroupController extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			createNewGroup(request, response);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
	
	static void createNewGroup(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		String groupNameString = (String) request.getParameter("groupname");
		int createdBy =(int) request.getSession().getAttribute("userid");

		if(groupNameString == null || groupNameString == "") {
			response.sendRedirect("NewGroup.jsp");
		}
		else {
			Group group = new Group(createdBy,groupNameString);
			GroupDAO.createNewGroup(group);
			response.sendRedirect("profile");
		}
	}

}
