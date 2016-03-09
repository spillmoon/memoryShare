package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.MemberService;

@WebServlet("/login.do")
public class LoginController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public LoginController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("euc-kr");
		String id = request.getParameter("memID");
		String pw = request.getParameter("memPW");
		
		MemberService service = new MemberService();
		
		try{
			int resultFlag = service.login(id, pw);
			HttpSession session = request.getSession();
			
			switch(resultFlag){
			case MemberService.LOGIN_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case MemberService.LOGIN_ID_FAIL:
				session.setAttribute("message", "noid");
				response.sendRedirect("sendMessage.jsp");
				break;
			case MemberService.LOGIN_PASSWORD_FAIL:
				session.setAttribute("message", "nopw");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
}
