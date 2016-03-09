package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.BoardService;

@WebServlet("/boardDelete.do")
public class BoardDeleteController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public BoardDeleteController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		BoardService bService = new BoardService();
		
		try{
			HttpSession session = request.getSession();
			
			int resultFlag = bService.boardDelete(boardNo);
			
			switch(resultFlag){
			case BoardService.BOARD_DELETE_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case BoardService.BOARD_DELETE_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}


}
