package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.CalendarService;

@WebServlet("/calendarDelete.do")
public class CalendarDeleteConrtoller extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public CalendarDeleteConrtoller(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		int calNo = Integer.parseInt(request.getParameter("calNo"));
		
		CalendarService cService = new CalendarService();
		
		try{
			HttpSession session = request.getSession();
			
			int resultFlag = cService.calendarDelete(calNo);
			
			switch(resultFlag){
			case CalendarService.CALENDAR_DELETE_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case CalendarService.CALENDAR_DELETE_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
