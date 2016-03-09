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

@WebServlet("/roomDestroy.do")
public class RoomDestroyController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public RoomDestroyController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		String roomName = request.getParameter("roomName");
		
		RoomService rService =  new RoomService();
		
		try{
			HttpSession session = request.getSession();
			int resultFlag = rService.roomDestroy(roomName);
			
			switch(resultFlag){
			case RoomService.ROOM_DESTROY_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case RoomService.ROOM_DESTROY_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
		
}
