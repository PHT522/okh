package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=uTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		
		String command = request.getParameter("command");
		
		if(command.equals("login")) {
			response.sendRedirect("login.jsp");
		}else if(command.equals("loginAf")) {
			if(userID == null || userID.equals("") || userPassword == null || userPassword.equals("")) {
				request.getSession().setAttribute("messageType", "오류 메시지");
				request.getSession().setAttribute("messageContent", "모든 내용을 입력해주세요.");
				response.sendRedirect("login.jsp");
				return;
			}
			
			UserDao dao = UserDao.getInstance();
			UserDTO userDto =  dao.login(userID, userPassword);
			request.getSession().setAttribute("login", userDto);
			
			if(userDto != null) {
				request.getSession().setAttribute("messageType", "성공 메시지");
				request.getSession().setAttribute("messageContent", "로그인에 성공했습니다.");
				response.sendRedirect("index.jsp");
			}else {
				request.getSession().setAttribute("messageType", "오류 메시지");
				request.getSession().setAttribute("messageContent", "아이디 비밀번호를 확인해주세요.");
				response.sendRedirect("login.jsp");
			}
		}
	}

}
