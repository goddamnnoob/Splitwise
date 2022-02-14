import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet{
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.sendRedirect("Login.jsp");
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			login(request, response);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	void login(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username == ""|| username == null || password =="" || password == null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginerror", "Invalid Input");
			response.sendRedirect("Login.jsp");
			return;
		}
		password =CryptographyUtil.generateHash(password);
		HttpSession session = request.getSession();
		if(UserDAO.authenticate(username, password)) {
			User user = UserDAO.getUserDetails(username);
			session.setAttribute("userid", user.getUserid());
			session.setAttribute("username",username);
			response.sendRedirect("profile");
		}
		else {
			session.setAttribute("loginerror", "Invalid Credentials");
			response.sendRedirect("Login.jsp");
		}
	}
}
