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

import service.GalleryService;
import service.MemberService;
import service.RoomService;

@WebServlet("/galleryListForSet.do")
public class GalleryListForSet extends HttpServlet{
	private static final long serialVersionUID = 1L;	
	
	public GalleryListForSet(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("euc-kr");
		String roomName = request.getParameter("roomName");
		String memID = request.getParameter("memID");
		
		int leaderNo;
		int roomNo, memNo;
		
		RoomService rService = new RoomService();
		MemberService mService = new MemberService();
		GalleryService gService = new GalleryService();
		
		JSONObject galleryList = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			roomNo = rService.roomNameTOroomNo(roomName);
			memNo = mService.memIdTOmemNo(memID);
			
			leaderNo = rService.roomLeader(roomNo);
			
			if(memNo == leaderNo){	// 방장 이다
				galleryList = gService.getGalleryForLeader(roomNo);
			}else{					// 방장이 아니다
				galleryList = gService.getGalleryForMakeMember(roomNo, memID);
			}
			
			if(galleryList == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", galleryList);
				response.sendRedirect("sendJSONArray.jsp");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}
