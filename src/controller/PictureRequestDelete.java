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

@WebServlet("/pictureRequestDelete.do")
public class PictureRequestDelete extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public PictureRequestDelete(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		int pictureNo = Integer.parseInt(request.getParameter("pictureNo"));
		String requestMem = request.getParameter("requestMem");
		
		PictureService pService = new PictureService();
		
		String compMem = null;
		
		try{
			HttpSession session = request.getSession();
			compMem = pService.getRequestMem(pictureNo);
			
			if(compMem==null){		// 요청한 멤버가 없음 -> 요청 가능(요청!!)
				int resultFlag = pService.pictureRequestDelete(pictureNo, requestMem);
				
				switch(resultFlag){
				case PictureService.FILE_DELETE_REQUEST_SUCCESS:
					session.setAttribute("message", "success");
					response.sendRedirect("sendMessage.jsp");
					break;
				case PictureService.FILE_DELETE_REQUEST_FAIL:
					session.setAttribute("message", "fail");
					response.sendRedirect("sendMessage.jsp");
					break;
				}
			}else{						// 요청한 멤버가 있음 -> 요청 불가!
				session.setAttribute("message", "already");
				response.sendRedirect("sendMessage.jsp");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
