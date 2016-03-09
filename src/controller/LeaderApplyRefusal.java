package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.MemberRoomService;
import service.MemberService;
import service.RoomService;

@WebServlet("/leaderApplyRefusal.do")
public class LeaderApplyRefusal extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public LeaderApplyRefusal(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		String memID = request.getParameter("memID");
		String roomName = request.getParameter("roomName");
		
		int memNo, roomNo;
		
		MemberService mService = new MemberService();
		RoomService rService = new RoomService();
		MemberRoomService mrService = new MemberRoomService();
		
		try{
			HttpSession session = request.getSession();
			memNo = mService.memIdTOmemNo(memID);
			roomNo = rService.roomNameTOroomNo(roomName);
			
			int resultFlag = mrService.leaderApplyRefusal(memNo, roomNo);
			
			switch(resultFlag){
			case MemberRoomService.LEADER_APPLY_REFUSAL_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case MemberRoomService.LEADER_APPLY_REFUSAL_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}
