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

import service.MemberService;
import service.RoomService;

@WebServlet("/roomInterestList.do")
public class RoomInterestList extends HttpServlet{
private static final long serialVersionUID = 1L;	
	
	public RoomInterestList(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		// 멤버의 memInterest와 룸의 rookCategory를 일치시켜야 함
		
		request.setCharacterEncoding("euc-kr");
		String memID = request.getParameter("memID");
		
		int memNo;
		
		MemberService mService = new MemberService();
		
		RoomService rService = new RoomService();
		JSONObject roomList = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			memNo = mService.memIdTOmemNo(memID);
			roomList = rService.roomInterestList(memNo);
			
			if(roomList == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", roomList);
				response.sendRedirect("sendJSONArray.jsp");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}
