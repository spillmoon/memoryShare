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

@WebServlet("/idCheck.do")
public class CheckIDController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public CheckIDController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("euc-kr");
		String id=request.getParameter("isID");
		
		MemberService service = new MemberService();
		
		try{
			int resultFlag = service.idCheck(id);
			HttpSession session = request.getSession();
			
			switch(resultFlag){
			case MemberService.ID_VALID:
				session.setAttribute("message", "valid");
				response.sendRedirect("sendMessage.jsp");
				break;
			case MemberService.ID_INVALID:
				session.setAttribute("message", "invalid");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
	}
}
