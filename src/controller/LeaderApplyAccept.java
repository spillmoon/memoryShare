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

@WebServlet("/leaderApplyAccept.do")
public class LeaderApplyAccept extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public LeaderApplyAccept(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("euc-kr");
		String memID = request.getParameter("memID");
		String roomName = request.getParameter("roomName");
		
		int memNo, roomNo, leader;
		
		MemberService mService = new MemberService();
		RoomService rService = new RoomService();
		MemberRoomService mrService = new MemberRoomService();
				
		try{
			HttpSession session = request.getSession();
			memNo = mService.memIdTOmemNo(memID);
			roomNo = rService.roomNameTOroomNo(roomName);
			
			// 현재 방의 리더가 누구인지 구함
			leader = rService.roomLeader(roomNo);
			
			// 일반 멤버를 방장으로 바꿈 leaderchk ==> 2->1
			int resultFlag1 = mrService.leaderApplyAccept(memNo, roomNo);
			
			// 방장을 일반 멤버로 바꿈
 			int resultFlag2 = mrService.leaderToMember(leader);
 			
 			int resultFlag = resultFlag1 + resultFlag2;
 			
			switch(resultFlag){
			case 2:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case 3:
			case 4:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
