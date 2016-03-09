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

import service.CalendarService;

@WebServlet("/calendarRead.do")
public class CalendarReadController extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	public CalendarReadController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		int calNo = Integer.parseInt(request.getParameter("calNo"));
		
		CalendarService cService = new CalendarService();
		JSONObject calendar = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			
			calendar = cService.calendarRead(calNo);
			
			if(calendar == null){
				session.setAttribute("info", "fail");
				response.sendRedirect("sendJSONObject.jsp");
			}else{
				session.setAttribute("info", calendar);
				response.sendRedirect("sendJSONObject.jsp");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
