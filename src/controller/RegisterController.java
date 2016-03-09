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

@WebServlet("/register.do")
public class RegisterController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public RegisterController(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("euc-kr");
		String memName = request.getParameter("memName");
		String memID = request.getParameter("memID");
		String memPW = request.getParameter("memPW");
		String memInterest = request.getParameter("memInterest");
		String memPhone = request.getParameter("memPhone");
		String memEmail = request.getParameter("memEmail");
		String identiQ = request.getParameter("identiQ");
		String identiA = request.getParameter("identiA");
		int alertChk = Integer.parseInt(request.getParameter("alertChk"));
		
		memInterest = memInterest.substring(0, memInterest.length()-1);
	
		//System.out.println(memName+"/"+memID+"/"+memPW+"/"+memInterest+"/"+memPhone+"/"+memEmail+"/"+identiQ+"/"+identiA+"/"+alertChk);
		MemberService service = new MemberService();
		
		try{
			int resultFlag = service.insertMember(memName, memID, memPW, memInterest, memPhone, memEmail, identiQ, identiA, alertChk);
			HttpSession session = request.getSession();
			
			switch(resultFlag){
			case MemberService.MEM_INSERT_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case MemberService.MEM_INSERT_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
}
