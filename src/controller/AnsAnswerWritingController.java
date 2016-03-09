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

@WebServlet("/ansAnswerWriting.do")
public class AnsAnswerWritingController extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	public AnsAnswerWritingController(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		String ansMem = request.getParameter("memID");
		String ansContent = request.getParameter("ansContent");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int answerAnsNo = Integer.parseInt(request.getParameter("answerAnsNo"));
		
		AnswerService aService = new AnswerService();
		
		try{
			HttpSession session = request.getSession();
			
			int resultFlag = aService.ansAnswerWriting(ansMem, ansContent, boardNo, answerAnsNo);
			
			switch(resultFlag){
			case AnswerService.ANS_ANSWER_WRITE_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case AnswerService.ANS_ANSWER_WRITE_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	

}
