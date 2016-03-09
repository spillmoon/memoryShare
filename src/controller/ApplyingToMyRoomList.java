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
import service.RoomService;

@WebServlet("/applyingToMyRoomList.do")
public class ApplyingToMyRoomList extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		String roomName = request.getParameter("roomName");
		
		int roomNo;
		
		JSONObject applyList = new JSONObject();
		MemberRoomService mrService = new MemberRoomService();
		RoomService rService = new RoomService();
		// 방에 가입 신청한 사람들 리스트 뿌려주는 것
		try{
			HttpSession session = request.getSession();
			roomNo = rService.roomNameTOroomNo(roomName);
			
			applyList = mrService.applyingToMyRoomList(roomNo);
			
			if(applyList == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", applyList);
				response.sendRedirect("sendJSONArray.jsp");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
