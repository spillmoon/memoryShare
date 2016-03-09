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

@WebServlet("/editFolder.do")
public class EditFolderController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public EditFolderController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		int folderNo = Integer.parseInt(request.getParameter("folderNo"));
		String modifiedFolderName = request.getParameter("modifiedFolderName");
		
		BoardFolderService bfService = new BoardFolderService();
		
		try{
			HttpSession session = request.getSession();
			int resultFlag = bfService.editFolder(folderNo, modifiedFolderName);
			
			switch(resultFlag){
			case BoardFolderService.BOARD_FOLDER_EDIT_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case BoardFolderService.BOARD_FOLDER_EDIT_FAIL:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
	}
	
}
