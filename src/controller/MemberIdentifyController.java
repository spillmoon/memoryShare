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

@WebServlet("/memberIdentify.do")
public class MemberIdentifyController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public MemberIdentifyController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		String memID = request.getParameter("memID");
		int identiQ = Integer.parseInt(request.getParameter("identiQ"));
		String identiA = request.getParameter("identiA");
		
		MemberService mService = new MemberService();
		
		try{
			HttpSession session = request.getSession();
			
			int resultFlag = mService.memberIdentify(memID, identiQ, identiA);
			
			switch(resultFlag){
			case MemberService.MEMBER_IDENTIFY_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case MemberService.MEMBER_IDENTIFY_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
