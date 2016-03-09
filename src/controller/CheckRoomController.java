package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.RoomService;

@WebServlet("/roomCheck.do")
public class CheckRoomController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public CheckRoomController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("euc-kr");
		String roomId = request.getParameter("roomName");
		RoomService service = new RoomService();
		
		try{
			int resultFlag = service.roomCheck(roomId);
			HttpSession session = request.getSession();
			
			switch(resultFlag){
			case RoomService.ROOM_ID_VALID:
				session.setAttribute("message", "valid");
				response.sendRedirect("sendMessage.jsp");
				break;
			case RoomService.ROOM_ID_INVALID:
				session.setAttribute("message", "invalid");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	
}
