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
import service.RoomService;

@WebServlet("/boardSearch.do")
public class BoardSearchController extends HttpServlet{
private static final long serialVersionUID = 1L;	
	
	public BoardSearchController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("euc-kr");
		
		String roomName = request.getParameter("roomName");
		String boardSearch = request.getParameter("boardSearch");
		int flag = Integer.parseInt(request.getParameter("flag"));
		
		int roomNo;
		
		RoomService rService = new RoomService();
		JSONObject boardList = new JSONObject();
		BoardService bService = new BoardService();
		
		try{
			HttpSession session = request.getSession();
			roomNo = rService.roomNameTOroomNo(roomName);
			boardList = bService.boardSearch(roomNo, boardSearch, flag);
			
			if(boardList == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", boardList);
				response.sendRedirect("sendJSONArray.jsp");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
	}
	
}
