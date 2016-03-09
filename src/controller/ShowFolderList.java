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

import service.BoardFolderService;
import service.RoomService;

@WebServlet("/showFolderList.do")
public class ShowFolderList extends HttpServlet{
	private static final long serialVersionUID = 1L;	
	
	public ShowFolderList(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("euc-kr");
		String roomName = request.getParameter("roomName");
		
		int roomNo;
		
		RoomService rService =  new RoomService();
		BoardFolderService bfService = new BoardFolderService();
		
		JSONObject folderList = new JSONObject();
		
		try{
			
			HttpSession session = request.getSession();
			roomNo = rService.roomNameTOroomNo(roomName);
			folderList = bfService.showFolderList(roomNo);
			
			if(folderList == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", folderList);
				response.sendRedirect("sendJSONArray.jsp");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
