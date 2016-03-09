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

@WebServlet("/getBoardFolderForEdit.do")
public class GetBoardFolderForEdit extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public GetBoardFolderForEdit(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("euc-kr");
		int folderNo = Integer.parseInt(request.getParameter("folderNo"));
		
		BoardFolderService bfService = new BoardFolderService();
		JSONObject folder = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			
			folder = bfService.getBoardFolderForEdit(folderNo);
			
			if(folder == null){
				session.setAttribute("info", "fail");
				response.sendRedirect("sendJSONObject.jsp");
			}else{
				session.setAttribute("info", folder);
				response.sendRedirect("sendJSONObject.jsp");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}
