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

@WebServlet("/withdrawFromRoom.do")
public class WithdrawFromRoom extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public WithdrawFromRoom(){
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
		
		MemberRoomService mrService = new MemberRoomService();
		RoomService rService = new RoomService();
		MemberService mService = new MemberService();
		
		try{
			memNo = mService.memIdTOmemNo(memID);
			roomNo = rService.roomNameTOroomNo(roomName);
			
			int resultFlag = mrService.withdrawFromRoom(memNo, roomNo);
			HttpSession session = request.getSession();
			
			switch(resultFlag){
			case MemberRoomService.MEMBER_ROOM_DELETE_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case MemberRoomService.MEMBER_ROOM_DELETE_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}
