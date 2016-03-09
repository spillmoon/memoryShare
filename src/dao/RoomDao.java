package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.ConnectionProvider;
import util.JdbcUtil;
import dto.Room;

public class RoomDao {
	public RoomDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
		
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////
	//방 등록 메서드
	public boolean insertRoom(String roomName, 
			String roomCategory, String roomKeyword, 
			String roomLocate, String roomContent, int roomMemCnt){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		boolean result=false;
		String sql="INSERT INTO room VALUES (SEQ_ROOM.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomName);
			pstmt.setString(2, roomCategory);
			pstmt.setString(3, roomKeyword);
			pstmt.setString(4, roomLocate);
			pstmt.setString(5, roomContent);
			pstmt.setInt(6, roomMemCnt);
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
	
	// 방 중복 확인 메서드
	public Room isRoom(String roomId){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Room room = null;
		
		String sql = "SELECT * FROM ROOM WHERE roomName=?";
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, roomId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				room=new Room(rs.getString("roomName"), rs.getString("roomCategory"), rs.getString("roomKeyword"), rs.getString("roomLocate"), rs.getString("roomContent"), rs.getInt("roomMemCnt"));
			}
			
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return room;
	}
	
	// roomName to roomNo
	public int roomNameTOroomNo(String roomName){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int roomNo=-1;

		try {

			conn = ConnectionProvider.getConnection();
			String sql = "SELECT roomNo FROM ROOM WHERE roomName = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				roomNo = rs.getInt("roomNo");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return roomNo;
	}
	
	//방 list 전체 부르기
	public JSONObject roomAllList(int memNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT r.ROOMNAME, r.ROOMCATEGORY, r.ROOMKEYWORD, r.ROOMLOCATE, r.ROOMMEMCNT, "
					+ "(SELECT count(mr.MEMNO) FROM MEMBER_ROOM mr WHERE r.ROOMNO = mr.ROOMNO and mr.REQSTATUS = 0) currentMemCnt, "
					+ "NVL((SELECT mr2.REQSTATUS FROM MEMBER_ROOM mr2, MEMBER m WHERE mr2.MEMNO = m.MEMNO AND mr2.ROOMNO = r.ROOMNO AND mr2.MEMNO=?), 2) reqStatus "
					+ "FROM ROOM r";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("roomName", rs.getString("roomName"));
				obj.put("roomCategory", rs.getString("roomCategory"));
				obj.put("roomKeyword", rs.getString("roomKeyword"));
				obj.put("roomLocate", rs.getString("roomLocate"));
				obj.put("roomMemCnt", rs.getInt("roomMemCnt"));
				obj.put("currentMemCnt", rs.getInt("currentMemCnt"));
				obj.put("reqStatus", rs.getInt("reqStatus"));
				jArray.add(0, obj);
				//System.out.println(rs.getString("roomName")+","+rs.getInt("reqStatus")+"/");
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
	
	// 선택된 방 정보 보내기
	public JSONObject roomInfo(String roomName){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		JSONObject jMain  = new JSONObject();
		JSONObject jObject = new JSONObject();
		
		String sql = "SELECT roomName, roomCategory, roomKeyword, "
				+ "(SELECT memID FROM MEMBER m, MEMBER_ROOM mr "
				+ "WHERE m.MEMNO = mr.MEMNO AND mr.ROOMNO = r.ROOMNO "
				+ "AND mr.LEADERCHK = 1) leaderID, roomLocate,	"
				+ "(SELECT count(memno) FROM MEMBER_ROOM kr "
				+ "WHERE r.ROOMNO = kr.ROOMNO) currentMemCnt, "
				+ "roomMemCnt, roomContent "
				+ "FROM ROOM r WHERE roomName = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, roomName);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				jObject.put("roomName", rs.getString("roomName"));
				jObject.put("roomCategory", rs.getString("roomCategory"));
				jObject.put("roomKeyword", rs.getString("roomKeyword"));
				jObject.put("leaderID", rs.getString("leaderID"));
				jObject.put("roomLocate", rs.getString("roomLocate"));
				jObject.put("currentMemCnt", rs.getInt("currentMemCnt"));
				jObject.put("roomMemCnt", rs.getInt("roomMemCnt"));
				jObject.put("roomContent", rs.getString("roomContent"));
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
	
	// 멤버 관심 분야와 관련있는 방 불러오기
	public JSONObject roomInterestList(int memNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT r.ROOMNAME, r.ROOMCATEGORY, r.ROOMKEYWORD, r.ROOMLOCATE, r.ROOMMEMCNT, "
					+ "(SELECT count(mr.MEMNO) FROM MEMBER_ROOM mr "
					+ "WHERE r.ROOMNO = mr.ROOMNO and mr.REQSTATUS = 0) currentMemCnt, "
					+ "NVL((SELECT mr2.REQSTATUS FROM MEMBER_ROOM mr2, MEMBER m "
					+ "WHERE mr2.MEMNO = m.MEMNO AND mr2.ROOMNO = r.ROOMNO AND mr2.MEMNO=?), 2) reqStatus "
					+ "FROM MEMBER m, ROOM r "
					+ "WHERE REPLACE(m.MEMINTEREST, ',', '') "
					+ "LIKE '%'||r.ROOMCATEGORY||'%' AND m.MEMNO = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("roomName", rs.getString("roomName"));
				obj.put("roomCategory", rs.getString("roomCategory"));
				obj.put("roomKeyword", rs.getString("roomKeyword"));
				obj.put("roomLocate", rs.getString("roomLocate"));
				obj.put("roomMemCnt", rs.getInt("roomMemCnt"));
				obj.put("currentMemCnt", rs.getInt("currentMemCnt"));
				obj.put("reqStatus", rs.getInt("reqStatus"));
				jArray.add(0, obj);
				//System.out.println(rs.getString("roomName")+","+rs.getInt("reqStatus")+"/");
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

	// 멤버 신청 리스트 불러오기
	public JSONObject roomApplyList(int memNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT r.ROOMNAME, r.ROOMCATEGORY, r.ROOMKEYWORD, r.ROOMLOCATE, r.ROOMMEMCNT, "
					+ "(SELECT count(mr.MEMNO) FROM MEMBER_ROOM mr "
					+ "WHERE r.ROOMNO = mr.ROOMNO and mr.REQSTATUS = 0) currentMemCnt, "
					+ "NVL((SELECT mr2.REQSTATUS FROM MEMBER_ROOM mr2, MEMBER m "
					+ "WHERE mr2.MEMNO = m.MEMNO AND mr2.ROOMNO = r.ROOMNO AND mr2.MEMNO=?), 2) reqStatus "
					+ "FROM MEMBER m, MEMBER_ROOM mr, ROOM r "
					+ "WHERE m.MEMNO = mr.MEMNO AND mr.ROOMNO = r.ROOMNO "
					+ "AND mr.REQSTATUS = 1 AND m.MEMNO = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("roomName", rs.getString("roomName"));
				obj.put("roomCategory", rs.getString("roomCategory"));
				obj.put("roomKeyword", rs.getString("roomKeyword"));
				obj.put("roomLocate", rs.getString("roomLocate"));
				obj.put("roomMemCnt", rs.getInt("roomMemCnt"));
				obj.put("currentMemCnt", rs.getInt("currentMemCnt"));
				obj.put("reqStatus", rs.getInt("reqStatus"));
				jArray.add(0, obj);
				//System.out.println(rs.getString("roomName")+","+rs.getInt("reqStatus")+"/");
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

	// *검색 하기*
	// 키워드로 검색 
	public JSONObject roomFindByKeyword(int memNo, String search){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT r.ROOMNAME, r.ROOMCATEGORY, r.ROOMKEYWORD, r.ROOMLOCATE, r.ROOMMEMCNT,"
					+ "(SELECT count(mr.MEMNO) FROM MEMBER_ROOM mr "
					+ "WHERE r.ROOMNO = mr.ROOMNO and mr.REQSTATUS = 0) currentMemCnt,"
					+ "NVL((SELECT mr2.REQSTATUS FROM MEMBER_ROOM mr2, MEMBER m "
					+ "WHERE mr2.MEMNO = m.MEMNO AND mr2.ROOMNO = r.ROOMNO "
					+ "AND mr2.MEMNO=?), 2) reqStatus "
					+ "FROM ROOM r "
					+ "WHERE r.ROOMKEYWORD like '%'||?||'%'";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setString(2, search);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("roomName", rs.getString("roomName"));
				obj.put("roomCategory", rs.getString("roomCategory"));
				obj.put("roomKeyword", rs.getString("roomKeyword"));
				obj.put("roomLocate", rs.getString("roomLocate"));
				obj.put("roomMemCnt", rs.getInt("roomMemCnt"));
				obj.put("currentMemCnt", rs.getInt("currentMemCnt"));
				obj.put("reqStatus", rs.getInt("reqStatus"));
				jArray.add(0, obj);
				//System.out.println(rs.getString("roomName")+","+rs.getInt("reqStatus")+"/");
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
	
	// *검색 하기*
	// 방이름으로 검색 
	public JSONObject roomFindByTitle(int memNo, String search){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT r.ROOMNAME, r.ROOMCATEGORY, r.ROOMKEYWORD, r.ROOMLOCATE, r.ROOMMEMCNT,"
					+ "(SELECT count(mr.MEMNO) FROM MEMBER_ROOM mr "
					+ "WHERE r.ROOMNO = mr.ROOMNO and mr.REQSTATUS = 0) currentMemCnt, "
					+ "NVL((SELECT mr2.REQSTATUS FROM MEMBER_ROOM mr2, MEMBER m "
					+ "WHERE mr2.MEMNO = m.MEMNO AND mr2.ROOMNO = r.ROOMNO "
					+ "AND mr2.MEMNO=?), 2) reqStatus "
					+ "FROM ROOM r "
					+ "WHERE r.ROOMNAME like '%'||?||'%'";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setString(2, search);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("roomName", rs.getString("roomName"));
				obj.put("roomCategory", rs.getString("roomCategory"));
				obj.put("roomKeyword", rs.getString("roomKeyword"));
				obj.put("roomLocate", rs.getString("roomLocate"));
				obj.put("roomMemCnt", rs.getInt("roomMemCnt"));
				obj.put("currentMemCnt", rs.getInt("currentMemCnt"));
				obj.put("reqStatus", rs.getInt("reqStatus"));
				jArray.add(0, obj);
				//System.out.println(rs.getString("roomName")+","+rs.getInt("reqStatus")+"/");
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
	
	// 만든 방 (방장 리스트)을 가져옴 
	public JSONObject roomCreateList(int memNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT r.ROOMNAME, r.ROOMCATEGORY, r.ROOMKEYWORD, r.ROOMLOCATE, r.ROOMMEMCNT, "
					+ "(SELECT count(mr.MEMNO) FROM MEMBER_ROOM mr "
					+ "WHERE r.ROOMNO = mr.ROOMNO and mr.REQSTATUS = 0) currentMemCnt "
					+ "FROM MEMBER m, MEMBER_ROOM mr, ROOM r "
					+ "WHERE mr.ROOMNO = r.ROOMNO AND m.MEMNO = mr.MEMNO "
					+ "AND mr.LEADERCHK = 1 AND m.MEMNO = ?";			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("roomName", rs.getString("roomName"));
				obj.put("roomCategory", rs.getString("roomCategory"));
				obj.put("roomKeyword", rs.getString("roomKeyword"));
				obj.put("roomLocate", rs.getString("roomLocate"));
				obj.put("roomMemCnt", rs.getInt("roomMemCnt"));
				obj.put("currentMemCnt", rs.getInt("currentMemCnt"));
				jArray.add(0, obj);
				//System.out.println(rs.getString("roomLocate")+","+rs.getString("roomKeyword")+"/");
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
	
	// 가입된 방(일원인 방) 리스트 가져오기
	public JSONObject roomJoinList(int memNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT r.ROOMNAME, r.ROOMCATEGORY, r.ROOMKEYWORD, r.ROOMLOCATE, r.ROOMMEMCNT, "
					+ "(SELECT count(mr.MEMNO) FROM MEMBER_ROOM mr WHERE r.ROOMNO = mr.ROOMNO and mr.REQSTATUS = 0) currentMemCnt "
					+ "FROM MEMBER m, MEMBER_ROOM mr, ROOM r "
					+ "WHERE mr.ROOMNO = r.ROOMNO AND m.MEMNO = mr.MEMNO "
					+ "AND (mr.LEADERCHK = 0 or mr.LEADERCHK = 2) "
					+ "AND mr.REQSTATUS = 0 AND m.MEMNO = ?";			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("roomName", rs.getString("roomName"));
				obj.put("roomCategory", rs.getString("roomCategory"));
				obj.put("roomKeyword", rs.getString("roomKeyword"));
				obj.put("roomLocate", rs.getString("roomLocate"));
				obj.put("roomMemCnt", rs.getInt("roomMemCnt"));
				obj.put("currentMemCnt", rs.getInt("currentMemCnt"));
				jArray.add(0, obj);
				//System.out.println(rs.getString("roomLocate")+","+rs.getString("roomKeyword")+"/");
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
	
	// 현재 방의 leader를 알아냄
	public int roomLeader(int roomNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int leaderNo = 0;

		try {

			conn = ConnectionProvider.getConnection();
			String sql = "SELECT memNo FROM MEMBER_ROOM "
					+ "WHERE roomNo = ? AND leaderChk = 1 AND reqStatus = 0";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				leaderNo = rs.getInt("memNo");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return leaderNo;
	}
	
	// 선택된 방 정보 보내기(for editing)
	public JSONObject getRoomInfoForEdit(String roomName){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		JSONObject jMain  = new JSONObject();
		JSONObject jObject = new JSONObject();
		
		String sql = "select roomName, roomCategory, roomKeyword, "
				+ "roomLocate, roomContent, roomMemCnt "
				+ "FROM ROOM WHERE roomName = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, roomName);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				jObject.put("roomName", rs.getString("roomName"));
				jObject.put("roomCategory", rs.getString("roomCategory"));
				jObject.put("roomKeyword", rs.getString("roomKeyword"));
				jObject.put("roomLocate", rs.getString("roomLocate"));
				jObject.put("roomContent", rs.getString("roomContent"));
				jObject.put("roomMemCnt", rs.getInt("roomMemCnt"));
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

	// update room info
	public boolean editRoomInfo(String roomName, int roomMemCnt, String roomLocate, String roomContent){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE ROOM SET roomLocate = ?, roomContent = ?, roomMemCnt = ? WHERE roomName = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, roomLocate);
			pstmt.setString(2, roomContent);
			pstmt.setInt(3, roomMemCnt);
			pstmt.setString(4, roomName);
			
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
	
	// 방 관련 정보들 전부다 삭제
	public boolean roomDestroy(String roomName){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM ROOM WHERE roomName = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomName);
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
	
		
}
