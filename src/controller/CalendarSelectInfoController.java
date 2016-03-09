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

@WebServlet("/calendarSelectInfo.do")
public class CalendarSelectInfoController extends HttpServlet{
	
private static final long serialVersionUID = 1L;
	
	public CalendarSelectInfoController(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");

		String calDate = request.getParameter("calDate");
		String roomName = request.getParameter("roomName");
		
		CalendarService cService = new CalendarService();
		JSONObject calDateInfo = new JSONObject();
		
		try{
			HttpSession session = request.getSession();
			
			calDateInfo = cService.selectedDateInfo(calDate, roomName);
			
			if(calDateInfo == null){
				session.setAttribute("result", null);
				response.sendRedirect("sendJSONArray.jsp");
			}else{
				session.setAttribute("result", calDateInfo);
				response.sendRedirect("sendJSONArray.jsp");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	

}
