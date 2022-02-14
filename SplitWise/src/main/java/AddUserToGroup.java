import java.io.IOException;
import java.sql.SQLException;

import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;

import com.mysql.cj.Session;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addusertogroup")
public class AddUserToGroup  extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		HttpSession httpSession = request.getSession();
		if(request.getParameter("username") == null || httpSession.getAttribute("userid") == null || httpSession.getAttribute("addusertogroupid") == null) {
			response.sendRedirect("profile");
		}
		try {
			addUserToGroup(request, response);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	doGet(request, response);	
	}

void addUserToGroup(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
	HttpSession httpSession = request.getSession();
	String username= (String) request.getParameter("username");
	String adminUsername = (String) request.getSession().getAttribute("username");
	int userId = (int) httpSession.getAttribute("userid");
	int groupid = Integer.parseInt((String) httpSession.getAttribute("addusertogroupid"));
	httpSession.removeAttribute("addusertogroupid");
	if(UserDAO.checkUserId(userId) && UserDAO.getUserDetails(adminUsername).getUserid() == userId) {
		if(UserDAO.checkUsernameAvailability(username)) {
			int nuserid = UserDAO.getUserDetails(username).getUserid();
			//System.out.println(nuserid);
			GroupDAO.addUserToGroup(nuserid, groupid);
			response.sendRedirect("group?groupid="+groupid);
		}
		else {
			httpSession.setAttribute("addusertogrouperror", "Error: Invalid User");
			response.sendRedirect("Addusertogroup.jsp?groupid="+groupid);
			
		}
	}
	else {
		response.sendRedirect("profile");
	}
}
}

