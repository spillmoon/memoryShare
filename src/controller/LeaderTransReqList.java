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

import service.MemberRoomService;
import service.MemberService;
import service.RoomService;

@WebServlet("/leaderTransReq.do")
public class LeaderTransReqList extends HttpServlet{
private static final long serialVersionUID = 1L;	
	
	public LeaderTransReqList(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.setCharacterEncoding("euc-kr");
		String memID = request.getParameter("memID");
		String roomName = request.getParameter("roomName");
		
		int memNo, roomNo;
		
		MemberService mService = new MemberService();
		MemberRoomService mrService = new MemberRoomService();
		RoomService rService = new RoomService();
		JSONObject room = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			memNo = mService.memIdTOmemNo(memID);
			roomNo = rService.roomNameTOroomNo(roomName);
			
			room = mrService.leaderTransReq(memNo, roomNo);
			
			if(room == null){
				session.setAttribute("info", null);
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
