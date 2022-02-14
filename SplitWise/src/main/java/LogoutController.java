import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutController extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logout(request, response);
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logout(request, response);
		
	}
	void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("username")!= null) {
			session.invalidate();
		}
		response.sendRedirect("Login.jsp");
	}
}
