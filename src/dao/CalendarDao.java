package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dto.Member;
import util.ConnectionProvider;
import util.JdbcUtil;

public class CalendarDao {
	public CalendarDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
		
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////
	// 캘린더 일정 추가
	public boolean insertCalendar(int roomNo,String calName,String calDate, 
			String calLoc,String calContent){
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		boolean result=false;
		String sql="INSERT INTO CALENDAR VALUES(SEQ_CALENDAR.NEXTVAL, ?, ?, TO_DATE(?, 'YYYY/MM/DD HH24:MI'), ?, ?)";
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, roomNo);
			pstmt.setString(2, calName);
			pstmt.setString(3, calDate);
			pstmt.setString(4, calLoc);
			pstmt.setString(5, calContent);
			
			pstmt.executeQuery();
			result=true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return result;
	}
	
	// 해당 일의 일정 모두 가져오기 date
	public JSONObject selectedDateInfo(String calDate, String roomName){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT c.CALNO, c.CALNAME, TO_CHAR(c.CALDATE,'YYYY/MM/DD HH24:MI') calDate, "
					+ "c.CALLOC, c.CALCONTENT FROM ROOM r, CALENDAR c "
					+ "WHERE r.ROOMNO = c.ROOMNO "
					+ "AND c.CALDATE LIKE TO_CHAR(TO_DATE(?, 'YYYY/MM/DD'))||'%' AND r.ROOMNAME = ?"; 
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, calDate);
			pstmt.setString(2, roomName);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("calNo", rs.getInt("calNo"));
				obj.put("calName", rs.getString("calName"));
				obj.put("calDate", rs.getString("calDate"));
				obj.put("calLoc", rs.getString("calLoc"));
				obj.put("calContent", rs.getString("calContent"));
				jArray.add(0, obj);
			}
			
			jMain.put("List", jArray);
		}catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return jMain;
	}

	// 달력 일정 삭제하기
	public boolean calendarDelete(int calNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM CALENDAR WHERE calNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, calNo);
			
			pstmt.executeUpdate();
			result = true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return result;
	}
	
	// 해당 일의 일정 하나 가져오기
	public JSONObject calendarRead(int calNo){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		JSONObject jMain  = new JSONObject();
		JSONObject jObject = new JSONObject();
		
		String sql = "SELECT calNo, calName, TO_CHAR(calDate,'YYYY/MM/DD hh24:mi') calDate, "
				+ "calLoc, calContent FROM CALENDAR WHERE calNo = ?";   		
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, calNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				jObject.put("calNo", rs.getInt("calNo"));
				jObject.put("calName", rs.getString("calName"));
				jObject.put("calDate", rs.getString("calDate"));
				jObject.put("calLoc", rs.getString("calLoc"));
				jObject.put("calContent", rs.getString("calContent"));		
			}
			jMain.put("info", jObject);
			
			//rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return jMain;
	}
	
	// 캘린더 일정 수정
	public boolean calendarEdit(int calNo, String calName, String calDate,
			String calLoc, String calContent){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE CALENDAR SET calName = ?, calDate = TO_DATE(?, 'YYYY/MM/DD HH24:MI'), "
				+ "calLoc = ?, calContent = ? WHERE calNo = ?"; 
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, calName);
			pstmt.setString(2, calDate);
			pstmt.setString(3, calLoc);
			pstmt.setString(4, calContent);
			pstmt.setInt(5, calNo);
			
			pstmt.executeUpdate();
			result = true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return result;
	}

	// 해당 월의 일정 모두 가져오기 month
	public JSONObject selectedMonthInfo(int year, String month, String roomName){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT c.CALNO, c.CALDATE FROM ROOM r, CALENDAR c "
					+ "WHERE r.ROOMNO = c.ROOMNO "
					+ "AND r.ROOMNAME = ? AND SUBSTR(TO_CHAR(calDate, 'YYYYMMDD'), 1, 6) = ?||?"; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, roomName);
			pstmt.setInt(2, year);
			pstmt.setString(3, month);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("calNo", rs.getInt("calNo"));
				obj.put("calDate", rs.getString("calDate"));
				
				jArray.add(0, obj);
			}
			
			jMain.put("List", jArray);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return jMain;
	}

}
