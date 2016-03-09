package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.BoardFolderService;
import service.MemberRoomService;
import service.MemberService;
import service.RoomService;


@WebServlet("/roomRegister.do")
public class RoomRegisterController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public RoomRegisterController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("euc-kr");
		String memId = request.getParameter("memID");
		String roomName = request.getParameter("roomName");
		
		String roomCategory = request.getParameter("roomCategory");
		String roomKeyword = request.getParameter("roomKeyword");
		String roomLocate = request.getParameter("roomLocate");
		String roomContent = request.getParameter("roomContent");
		int roomMemCnt = Integer.parseInt(request.getParameter("roomMemCnt"));
		
		roomKeyword = roomKeyword.substring(0, roomKeyword.length()-1);
		
		int memNo, roomNo;
		
		MemberRoomService mrService = new MemberRoomService();
		RoomService rService = new RoomService();
		MemberService mService = new MemberService();
		BoardFolderService bfService = new BoardFolderService();
		
		try{
			int resultFlag1 = rService.insertRoom(roomName, roomCategory, roomKeyword, roomLocate, roomContent, roomMemCnt);
			
			// memid to memno 바꾸고
			memNo = mService.memIdTOmemNo(memId);
			// roomname to roomno 바꾸고
			roomNo = rService.roomNameTOroomNo(roomName);
			
			// member_room insert
			int resultFlag2 = mrService.insertMemberRoom(memNo, roomNo);
			// resultFlag insert 두개 값을 더하기!!!! (2-> success) (3, 4 -> fail)
			
			// room relational folder(공지) create
			int resultFlag3 = bfService.createInformationFolder(roomNo);
			
			int resultFlag = resultFlag1 + resultFlag2 + resultFlag3;
			
			HttpSession session = request.getSession();
			
			switch(resultFlag){
			case 3:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case 4:
			case 5:
			case 6:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
