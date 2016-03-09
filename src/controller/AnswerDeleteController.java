package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.AnswerService;

@WebServlet("/answerDelete.do")
public class AnswerDeleteController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public AnswerDeleteController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		int ansNo = Integer.parseInt(request.getParameter("ansNo"));
		
		AnswerService aService = new AnswerService();
		
		try{
			HttpSession session = request.getSession();
			
			int resultFlag = aService.answerDelete(ansNo);
			
			switch(resultFlag){
			case AnswerService.ANSWER_DELETE_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case AnswerService.ANSWER_DELETE_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
