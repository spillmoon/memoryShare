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

@WebServlet("/galleryDelete.do")
public class GalleryDeleteController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public GalleryDeleteController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		int galleryNo = Integer.parseInt(request.getParameter("galleryNo"));
		
		GalleryService gService = new GalleryService();
		
		try{
			HttpSession session = request.getSession();
			
			int resultFlag = gService.galleryDelete(galleryNo);
			
			switch(resultFlag){
			case GalleryService.GALLERY_DELETE_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case GalleryService.GALLERY_DELETE_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
