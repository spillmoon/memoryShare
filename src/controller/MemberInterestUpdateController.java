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

@WebServlet("/memberInterestUpdate.do")
public class MemberInterestUpdateController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public MemberInterestUpdateController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		String memID = request.getParameter("memID");
		String memInterest = request.getParameter("memInterest");
		
		memInterest = memInterest.substring(0, memInterest.length()-1);
		
		MemberService mService = new MemberService();
		
		try{
			HttpSession session = request.getSession();
			
			int resultFlag = mService.updateMemberInterest(memID, memInterest);
			
			switch(resultFlag){
			case MemberService.MEMBER_INTEREST_UPDATE_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case MemberService.MEMBER_INTEREST_UPDATE_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
