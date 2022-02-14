import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/signup")
public class SignupController extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) {}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {try {
		register(request, response);
	} catch (ClassNotFoundException | IOException | SQLException e) {
		
		e.printStackTrace();
	}}
	void register(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		if(username == ""|| username == null || password =="" || password == null || email == null || email == "") {
			HttpSession session = request.getSession();
			session.setAttribute("registererror", "Invalid Input");
			response.sendRedirect("Register.jsp");
			return;
		}
		password = CryptographyUtil.generateHash(password);
		User newUser =  new User(username, email, password, 0,0);
		HttpSession session = request.getSession();
		session.setAttribute("registererror", "noerror");
			if(!UserDAO.checkUsernameAvailability(username)) {
				UserDAO.createNewUser(newUser);
				response.sendRedirect("Login.jsp");
				session.removeAttribute("registererror");
			}
			else {
				session.setAttribute("registererror", "username not available");
				response.sendRedirect("Register.jsp");
			} 
	}
	
}
