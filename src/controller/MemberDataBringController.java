package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import service.MemberService;

@WebServlet("/memberDataBring.do")
public class MemberDataBringController extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	public MemberDataBringController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		String memID = request.getParameter("memID");
		
		// 이름, ID, 이메일, 전화번호, 알림
		
		MemberService mService = new MemberService();
		JSONObject member = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			
			member = mService.getMemberData(memID);
			
			if(member == null){
				session.setAttribute("info", "fail");
				response.sendRedirect("sendJSONObject.jsp");
			}else{
				session.setAttribute("info", member);
				response.sendRedirect("sendJSONObject.jsp");
			}
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
