package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.GalleryService;
import service.RoomService;

@WebServlet("/galleryAdd.do")
public class GalleryAddController extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	public GalleryAddController(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		//아이디 방이름 폴더이름
		String memID = request.getParameter("memID");
		String roomName = request.getParameter("roomName");
		String folderName = request.getParameter("folderName");
		
		RoomService rService = new RoomService();
		GalleryService gService = new GalleryService();
		int roomNo;
		
		try{
			HttpSession session = request.getSession();
			roomNo = rService.roomNameTOroomNo(roomName);
			
			int resultFlag = gService.addGallery(roomNo, folderName, memID);
			
			switch(resultFlag){
			case GalleryService.GALLERY_ADD_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case GalleryService.GALLERY_ADD_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
