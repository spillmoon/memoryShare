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

@WebServlet("/memberEdit.do")
public class MemberEditController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public MemberEditController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		// 아이디, 패스워드, 이메일, 전화번호, 알림, 질문, 답
		String memID = request.getParameter("memID");
		String memPW = request.getParameter("memPW");
		String memEmail = request.getParameter("memEmail");
		String memPhone = request.getParameter("memPhone");
		int alertChk = Integer.parseInt(request.getParameter("alertChk"));
		int identiQ = Integer.parseInt(request.getParameter("identiQ"));
		String identiA = request.getParameter("identiA");
		
		MemberService mService = new MemberService();
		
		try{
			HttpSession session = request.getSession();
			
			int resultFlag = mService.memberEdit(memID, memPW, memEmail, memPhone, alertChk, identiQ, identiA);
			
			switch(resultFlag){
			case MemberService.MEMBER_EDIT_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case MemberService.MEMBER_EDIT_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}
