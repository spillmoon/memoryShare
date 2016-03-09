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

@WebServlet("/roomApply.do")
public class RoomApplyController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public RoomApplyController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("euc-kr");
		String id = request.getParameter("memID");
		String roomName = request.getParameter("roomName");
		//System.out.println(id+"/"+roomName);
		
		int memNo, roomNo;
		
		MemberRoomService mrService = new MemberRoomService();
		RoomService rService = new RoomService();
		MemberService mService = new MemberService();
		
		try{
			// name to no
			memNo = mService.memIdTOmemNo(id);
			roomNo = rService.roomNameTOroomNo(roomName);
			HttpSession session = request.getSession();
			
			// 먼저 이 아이디가 이 방에 신청이 되어있는지 확인
			int resultFlag = mrService.chkMemberInRoom(memNo, roomNo);
			
			switch(resultFlag){
			case MemberRoomService.THERE_IS_NOMEMNO:	// 없다면 신청
				int resultFlag2 = mrService.applyMemberRoom(memNo, roomNo);
				
				switch(resultFlag2){
				case MemberRoomService.MEMBER_ROOM_APPLY_SUCCESS:
					session.setAttribute("message", "applySuccess");
					response.sendRedirect("sendMessage.jsp");
					break;
				case MemberRoomService.MEMBER_ROOM_APPLY_FAIL:
					session.setAttribute("message", "applyFail");
					response.sendRedirect("sendMessage.jsp");
					break;
				}
				
				break;
			case MemberRoomService.THERE_IS_MEMNO:		// 있다면 신청 불가
				session.setAttribute("message", "alreadyApply");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
