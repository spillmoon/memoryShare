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

@WebServlet("/pictureFromGallery.do")
public class PictureFromGallery extends HttpServlet{
	private static final long serialVersionUID = 1L;	
	
	public PictureFromGallery(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("euc-kr");
		
		int galleryNo = Integer.parseInt(request.getParameter("galleryNo"));
		
		PictureService pService = new PictureService();
		JSONObject pictures = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			
			pictures = pService.pictureFromGallery(galleryNo);
			
			if(pictures == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", pictures);
				response.sendRedirect("sendJSONArray.jsp");
			}	
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}
