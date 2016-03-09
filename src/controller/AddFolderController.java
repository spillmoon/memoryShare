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
import service.RoomService;

@WebServlet("/addFolder.do")
public class AddFolderController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public AddFolderController(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("euc-kr");
		String roomName = request.getParameter("roomName");
		String folderName = request.getParameter("folderName");
		
		int roomNo;
		
		RoomService rService =  new RoomService();
		BoardFolderService bfService = new BoardFolderService();
		
		try{
			HttpSession session = request.getSession();
			
			roomNo = rService.roomNameTOroomNo(roomName);
			
			int resultFlag = bfService.addFolder(roomNo, folderName);
			
			switch(resultFlag){
			case BoardFolderService.ADD_FOLDER_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case BoardFolderService.ADD_FOLDER_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
