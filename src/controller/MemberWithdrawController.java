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
import service.RoomService;

@WebServlet("/memberWithdraw.do")
public class MemberWithdrawController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public MemberWithdrawController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		String memID = request.getParameter("memID");
		
		MemberService mService = new MemberService();
		RoomService rService = new RoomService();
		
		JSONObject joinList = new JSONObject();
		JSONObject createList = new JSONObject();
		JSONObject applyList = new JSONObject();
		
		
		try{
			HttpSession session = request.getSession();
			
			int memNo = mService.memIdTOmemNo(memID);
			
			joinList = rService.roomJoinList(memNo);
			createList = rService.roomCreateList(memNo);
			applyList = rService.roomApplyList(memNo);
			
			if((joinList.toJSONString().equals("{\"List\":[]}")) && (createList.toJSONString().equals("{\"List\":[]}")) && (applyList.toJSONString().equals("{\"List\":[]}"))){
				int resultFlag = mService.memberWithdraw(memNo);
				
				switch(resultFlag){
				case MemberService.MEMBER_WITHDRAW_SUCCESS:
					session.setAttribute("message", "success");
					response.sendRedirect("sendMessage.jsp");
					break;
				case MemberService.MEMBER_WITHDRAW_FAIL:
					session.setAttribute("message", "fail");
					response.sendRedirect("sendMessage.jsp");
					break;
				}
			}else{
				session.setAttribute("message", "haveRelationship");
				response.sendRedirect("sendMessage.jsp");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}
