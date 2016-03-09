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

import service.BoardService;

@WebServlet("/boardRead.do")
public class BoardReadController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public BoardReadController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo")); 
		
		BoardService bService = new BoardService();
		JSONObject board = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			board = bService.boardRead(boardNo);
			
			if(board == null){
				session.setAttribute("info", "fail");
				response.sendRedirect("sendJSONObject.jsp");
			}else{
				session.setAttribute("info", board);
				response.sendRedirect("sendJSONObject.jsp");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
