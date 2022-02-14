import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.naming.java.javaURLContextFactory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addexpensetogroup")
public class AddExpenseToGroup extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession();
		if(request.getParameter("amount") == null || httpSession.getAttribute("userid") == null || httpSession.getAttribute("addexpensetogroupid") == null) {
			response.sendRedirect("profile");
		}
		try {
			addExpenseToGroup(request, response);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
	
	void addExpenseToGroup(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		HttpSession httpSession = request.getSession();
		float amount= Float.parseFloat( (String) request.getParameter("amount"));
		String globlaUsername = (String) request.getSession().getAttribute("username");
		int userId = (int) httpSession.getAttribute("userid");
		int groupid = Integer.parseInt((String) httpSession.getAttribute("addexpensetogroupid"));
		httpSession.removeAttribute("addexpensetogroupid");
		User globalUser = UserDAO.getUserDetails(globlaUsername);
		if(UserDAO.checkUserId(userId) && globalUser.getUserid() == userId) {
			if(amount > 0) {
				 Expense expense = new Expense( userId, -1,groupid, amount, java.time.LocalDateTime.now().toString());
				 ExpenseDAO.addExpenseToGroup(expense);
				response.sendRedirect("group?groupid="+groupid);
			}
			else {
				httpSession.setAttribute("addepensetogrouperror", "Amount should be grater than 0");
				response.sendRedirect("AddExpenseToGroup.jsp?groupid="+groupid);
				
			}
		}
		else {
			response.sendRedirect("profile");
		}
	}
}
