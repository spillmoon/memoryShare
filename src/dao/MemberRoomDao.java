package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.ConnectionProvider;
import util.JdbcUtil;

public class MemberRoomDao {
	public MemberRoomDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
			
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////
	// MemberRoom insert
	public boolean insertMemberRoom(int memNo, int roomNo){
		Connection conn=null;
		PreparedStatement pstmt=null;
	
		boolean result=false;
		String sql = "INSERT INTO MEMBER_ROOM VALUES(?, ?, 1, 0)";
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
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
	
	// MemberRoom apply
	public boolean applyMemberRoom(int memNo, int roomNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "INSERT INTO MEMBER_ROOM VALUES(?, ?, 0, 1)";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
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
	
	// check member in Room before apply
	public int chkMemberInRoom(int memNo, int roomNo){
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int isMemNo=0;
		String sql = "SELECT memno FROM MEMBER_ROOM WHERE memno = ? AND roomno = ?";
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
			rs=pstmt.executeQuery();
		
			if(rs.next()){
				isMemNo = rs.getInt("memNo");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return isMemNo;
	}
	
	// ȸ�� Ż�� (�濡��) 
	public boolean withdrawFromRoom(int memNo, int roomNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM MEMBER_ROOM WHERE LEADERCHK = 0 AND REQSTATUS = 0 AND MEMNO = ? AND ROOMNO = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
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
	
	// �絵�� �̹� �Ǿ��ִ��� Ȯ��
	public int isTransfer(int roomNo){
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int isTransfer = 0;
		String sql = "SELECT mr.MEMNO FROM ROOM r, MEMBER_ROOM mr WHERE r.ROOMNO = mr.ROOMNO AND mr.LEADERCHK = 2 AND mr.REQSTATUS = 0 AND r.ROOMNO = ?";
		try{
			conn=ConnectionProvider.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs=pstmt.executeQuery();
		
			if(rs.next()){
				isTransfer = rs.getInt("memNo");
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return isTransfer;
	}
	
	// �絵�� ��� ����Ʈ �ҷ�����
	public JSONObject leaderTransferList(int roomNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT m.MEMNAME, m.MEMID, mr.LEADERCHK "
					+ "FROM MEMBER m, MEMBER_ROOM mr "
					+ "WHERE m.MEMNO = mr.MEMNO AND mr.LEADERCHK = 0 "
					+ "AND mr.REQSTATUS = 0 AND mr.ROOMNO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("memName", rs.getString("memName"));
				obj.put("memID", rs.getString("memID"));
				obj.put("leaderChk", rs.getInt("leaderChk"));
				
				jArray.add(0, obj);
			}
			jMain.put("List", jArray);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return jMain;
		
	}
	
	// �絵�� ��� �ҷ�����
	public JSONObject leaderTransferedMem(int roomNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT m.MEMNAME, m.MEMID, mr.LEADERCHK "
					+ "FROM MEMBER m, MEMBER_ROOM mr "
					+ "WHERE m.MEMNO = mr.MEMNO AND mr.LEADERCHK = 2 "
					+ "AND mr.REQSTATUS = 0 AND mr.ROOMNO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("memName", rs.getString("memName"));
				obj.put("memID", rs.getString("memID"));
				obj.put("leaderChk", rs.getInt("leaderChk"));
				
				jArray.add(0, obj);
			}
			jMain.put("List", jArray);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return jMain;
		
	}
	
	
	// ���� ��û�ϱ�(leaderchk =-> 0 -> 2)
	public boolean leaderTransferApply(int memNo, int roomNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE MEMBER_ROOM SET leaderChk = 2 "
				+ "WHERE leaderChk = 0 AND reqStatus = 0 "
				+ "AND memNo = ? AND roomNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
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
	
	// ������� ��û�� ���� �絵 ��û ����Ʈ
	public JSONObject leaderTransReq(int memNo, int roomNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONObject jObject = new JSONObject();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			sql = "SELECT r.ROOMNAME, r.ROOMCATEGORY, r.ROOMKEYWORD, r.ROOMLOCATE, r.ROOMMEMCNT, "
					+ "(SELECT count(mr1.MEMNO) FROM MEMBER_ROOM mr1 "
					+ "WHERE r.ROOMNO = mr1.ROOMNO and mr1.REQSTATUS = 0) currentMemCnt, "
					+ "(SELECT m.MEMID FROM MEMBER m, MEMBER_ROOM mr2 "
					+ "WHERE m.MEMNO = mr2.MEMNO AND r.ROOMNO = mr2.ROOMNO "
					+ "AND mr2.LEADERCHK = 1) leader "
					+ "FROM ROOM r, MEMBER_ROOM mr "
					+ "WHERE r.ROOMNO = mr.ROOMNO AND mr.LEADERCHK = 2 "
					+ "AND mr.MEMNO = ? AND mr.ROOMNO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				jObject.put("roomName", rs.getString("roomName"));
				jObject.put("roomCategory", rs.getString("roomCategory"));
				jObject.put("roomKeyword", rs.getString("roomKeyword"));
				jObject.put("roomLocate", rs.getString("roomLocate"));
				jObject.put("roomMemCnt", rs.getInt("roomMemCnt"));
				jObject.put("currentMemCnt", rs.getInt("currentMemCnt"));
				jObject.put("leader", rs.getString("leader"));
			}

			jMain.put("info", jObject);
			
		}catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return jMain;
	}
	
	// ���� �̱� ��û ����(��� -> ����)
	public boolean leaderApplyAccept(int memNo, int roomNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE MEMBER_ROOM SET leaderChk = 1 WHERE leaderChk = 2 AND memNo = ? AND roomNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
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
	
	// ���忡�� ������ �ٲ�(���� -> ���) 
	public boolean leaderToMember(int leader){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE MEMBER_ROOM SET leaderChk = 0 "
				+ "WHERE leaderChk = 1 AND memNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, leader);
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
	
	// ���� ��û�� ������(leaderchk => 2->0 )
	public boolean leaderApplyRefusal(int memNo, int roomNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE MEMBER_ROOM SET leaderChk = 0 WHERE leaderChk = 2 AND memNo = ? AND roomNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
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
	
	// �츮�濡 ���� ��û�� ��û�� ����� ����Ʈ ��������
	public JSONObject applyingToMyRoomList(int roomNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT m.MEMID, m.MEMINTEREST, m.MEMPHONE, m.MEMEMAIL "
					+ "FROM ROOM r, MEMBER_ROOM mr, MEMBER m "
					+ "WHERE r.ROOMNO = mr.ROOMNO AND m.MEMNO = mr.MEMNO "
					+ "AND mr.LEADERCHK = 0 AND mr.REQSTATUS = 1 AND mr.ROOMNO = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("memID", rs.getString("memID"));
				obj.put("memInterest", rs.getString("memInterest"));
				obj.put("memPhone", rs.getString("memPhone"));
				obj.put("memEmail", rs.getString("memEmail"));
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
	
	// �츮�濡 ���� ��û�� ��û�� ������(update)
	public boolean applyingToMyRoomAccept(int memNo, int roomNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE MEMBER_ROOM SET reqStatus = 0 "
				+ "WHERE reqStatus = 1 AND memNo = ? AND roomNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
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
	
	// �츮�濡 ���� ��û�� ��û�� ������(delete)
	public boolean applyingToMyRoomRefusal(int memNo, int roomNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM MEMBER_ROOM "
				+ "WHERE LEADERCHK = 0 AND REQSTATUS = 1 "
				+ "AND MEMNO = ? AND ROOMNO = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
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
	
	// MemberRoom apply
	public boolean roomApplyCancel(int memNo, int roomNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM MEMBER_ROOM WHERE memNo = ? AND roomNo = ? "
				+ "AND leaderChk = 0 AND reqStatus = 1";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, roomNo);
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
