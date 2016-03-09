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

@WebServlet("/compLeader.do")
public class CompLeaderController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public CompLeaderController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("euc-kr");
		String roomName = request.getParameter("roomName");
		String memID = request.getParameter("memID");
		
		int memNo, roomNo;
		
		MemberService mService = new MemberService();
		RoomService rService = new RoomService();
		MemberRoomService mrservice = new MemberRoomService();
		
		try{
			
			memNo = mService.memIdTOmemNo(memID);
			roomNo = rService.roomNameTOroomNo(roomName);
			
			int resultFlag = rService.compLeader(memNo, roomNo);
			HttpSession session = request.getSession();
			
			switch(resultFlag){
			case RoomService.REAL_LEADER_TRUE:
				session.setAttribute("message", "leader");
				response.sendRedirect("sendMessage.jsp");
				break;
			case RoomService.REAL_LEADER_FALSE:
				int resultFlag2 = mrservice.transferedMember(memNo, roomNo);
				
				switch(resultFlag2){
				case MemberRoomService.TRANSFERED_MEMBER:
					session.setAttribute("message", "transfered");
					response.sendRedirect("sendMessage.jsp");
					break;
				case MemberRoomService.NOT_TRANSFERED_MEMBER:
					session.setAttribute("message", "normal");
					response.sendRedirect("sendMessage.jsp");
					break;
				}
				
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
