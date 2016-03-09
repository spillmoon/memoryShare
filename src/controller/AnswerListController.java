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

import service.AnswerService;

@WebServlet("/answerList.do")
public class AnswerListController extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	public AnswerListController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		AnswerService aService = new AnswerService();
		JSONObject answerList = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			answerList = aService.answerList(boardNo);
			
			if(answerList == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", answerList);
				response.sendRedirect("sendJSONArray.jsp");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
		
	}
	
}
