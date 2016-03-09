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

import service.MemberRoomService;
import service.RoomService;

@WebServlet("/leaderTransferList.do")
public class LeaderTransferList extends HttpServlet{
	private static final long serialVersionUID = 1L;	
	
	public LeaderTransferList(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.setCharacterEncoding("euc-kr");
		String roomName = request.getParameter("roomName");
		
		int roomNo;
		int resultFlag;
		
		RoomService rService = new RoomService();
		MemberRoomService mrService = new MemberRoomService();
		JSONObject memberList = new JSONObject();
		
		try{
			
			HttpSession session = request.getSession();
			roomNo = rService.roomNameTOroomNo(roomName);
			resultFlag = mrService.isTransfer(roomNo);
			switch(resultFlag){
			case MemberRoomService.TRANSFER_AVAILABLE:
				memberList = mrService.leaderTransferList(roomNo);
				if(memberList == null){
					session.setAttribute("result", null);
					response.sendRedirect("sendJSONArray.jsp");
				}else{
					session.setAttribute("result", memberList);
					response.sendRedirect("sendJSONArray.jsp");
				}
				break;
			case MemberRoomService.TRANSFER_NOT_AVAILABLE:
				memberList = mrService.leaderTransferedMem(roomNo);
				session.setAttribute("result", memberList);
				response.sendRedirect("sendJSONArray.jsp");
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}
