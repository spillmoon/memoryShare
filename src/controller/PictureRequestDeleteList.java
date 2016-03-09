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

import service.PictureService;
import service.RoomService;

@WebServlet("/pictureRequestDeleteList.do")
public class PictureRequestDeleteList extends HttpServlet{
	private static final long serialVersionUID = 1L;	
	
	public PictureRequestDeleteList(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("euc-kr");
		
		String roomName = request.getParameter("roomName");
		String memID = request.getParameter("memID");
		int roomNo;
		
		PictureService pService = new PictureService();
		RoomService rService = new RoomService();
		JSONObject requestList = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			
			roomNo = rService.roomNameTOroomNo(roomName);
			
			requestList = pService.pictureRequestDeleteList(roomNo, memID);
			
			if(requestList == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", requestList);
				response.sendRedirect("sendJSONArray.jsp");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
}
