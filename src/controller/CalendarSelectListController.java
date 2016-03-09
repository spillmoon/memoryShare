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

@WebServlet("/calendarSelectList.do")
public class CalendarSelectListController extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	public CalendarSelectListController(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		String roomName = request.getParameter("roomName");

		int year = Integer.parseInt(request.getParameter("year"));
		
		
		int month = Integer.parseInt(request.getParameter("month"));
		String formatNumber = String.format("%02d", month);
		
		CalendarService cService = new CalendarService();
		JSONObject calMonthInfo = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			
			calMonthInfo = cService.selectedMonthInfo(year, formatNumber, roomName);
			
			if(calMonthInfo == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", calMonthInfo);
				response.sendRedirect("sendJSONArray.jsp");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
