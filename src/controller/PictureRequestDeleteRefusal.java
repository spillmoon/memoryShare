package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.PictureService;

@WebServlet("/pictureRequestDeleteRefusal.do")
public class PictureRequestDeleteRefusal extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public PictureRequestDeleteRefusal(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		int pictureNo = Integer.parseInt(request.getParameter("pictureNo"));
		
		PictureService pService= new PictureService();
		
		try{
			HttpSession session = request.getSession();
			
			int resultFlag = pService.pictureRequestDeleteRefusal(pictureNo);
			
			switch(resultFlag){
			case PictureService.PICTURE_DELETE_REQUEST_REFUSAL_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case PictureService.PICTURE_DELETE_REQUEST_REFUSAL_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
