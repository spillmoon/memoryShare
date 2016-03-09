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

import service.RoomService;

@WebServlet("/getRoomInfoForEdit.do")
public class GetRoomInfoForEdit extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public GetRoomInfoForEdit(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("euc-kr");
		String roomName = request.getParameter("roomName");
		
		RoomService service = new RoomService();
		JSONObject room = new JSONObject();
		
		try{
			room = service.getRoomInfoForEdit(roomName);
			HttpSession session = request.getSession();
			
			if(room == null){
				session.setAttribute("info", "fail");
				response.sendRedirect("sendJSONObject.jsp");
			}else{
				session.setAttribute("info", room);
				response.sendRedirect("sendJSONObject.jsp");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
}
