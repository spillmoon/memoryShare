package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import dto.Member;
import util.ConnectionProvider;
import util.JdbcUtil;

public class MemberDao {
	public MemberDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
			
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////
	// id 확인 메서드
	public Member isId(String id){
		//System.out.println("id : "+id);
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Member member = null;
		
		String sql = "select * from member where memID=?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				member = new Member(rs.getString("memID"), rs.getString("memPW"));
			}
			
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return member;
	}
	
	//회원가입 메서드
	public boolean insertMember(String memName, String memID, String memPW, String memInterest, String memPhone, String memEmail, String identiQ, String identiA, int alertChk){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
	
		boolean result=false;
		String sql = "INSERT INTO member VALUES (SEQ_MEMBER.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memName);
			pstmt.setString(2, memID);
			pstmt.setString(3, memPW);
			pstmt.setString(4, memInterest);
			pstmt.setString(5, memPhone);
			pstmt.setString(6, memEmail);
			pstmt.setString(7, identiQ);
			pstmt.setString(8, identiA);
			pstmt.setInt(9, alertChk);
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
	
	//memId to memNo
	public int memIdTOmemNo(String memId) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		int memNo=-1;

		try {

			conn = ConnectionProvider.getConnection();
			String sql = "SELECT memNo FROM member WHERE memID = ?";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, memId);
			rs = pStmt.executeQuery();

			if (rs.next()) {
				memNo = rs.getInt("memNo");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pStmt);
			JdbcUtil.close(conn);
		}
		return memNo;
	}
	
	// 본인 인증
	public Member memberIdentify(String memID){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Member member = null;
		
		String sql = "SELECT memID, identiQ, identiA FROM MEMBER WHERE memID = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memID);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				member = new Member(rs.getString("memID"), 
						String.valueOf(rs.getInt("identiQ")), 
						rs.getString("identiA"));
			}
			
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return member;
	}

	// 수정을 위해 선택된 정보 가져오기
	public JSONObject getMemberData(String memID){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		JSONObject jMain  = new JSONObject();
		JSONObject jObject = new JSONObject();
		
		// 이름, ID, 이메일, 전화번호, 알림
		String sql = "SELECT memName, memID, memEmail, memPhone, alertChk, identiQ, identiA FROM MEMBER WHERE memID = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memID);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				jObject.put("memName", rs.getString("memName"));
				jObject.put("memID", rs.getString("memID"));
				jObject.put("memEmail", rs.getString("memEmail"));
				jObject.put("memPhone", rs.getString("memPhone"));
				jObject.put("alertChk", rs.getInt("alertChk"));
				jObject.put("identiQ", rs.getInt("identiQ"));
				jObject.put("identiA", rs.getString("identiA"));
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
	
	// 멤버 정보 수정
	public boolean memberEdit(String memID, String memPW, String memEmail, 
			String memPhone, int alertChk, int identiQ, String identiA){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE MEMBER SET memPW = ?, memEmail = ?, "
				+ "memPhone = ?, alertChk =?, identiQ = ?, identiA = ? "
				+ "WHERE memID = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memPW);
			pstmt.setString(2, memEmail);
			pstmt.setString(3, memPhone);
			pstmt.setInt(4, alertChk);
			pstmt.setInt(5, identiQ);
			pstmt.setString(6, identiA);
			pstmt.setString(7, memID);
			
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
	public boolean memberWithdraw(int memNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM MEMBER WHERE memNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, memNo);
			
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

	// 관심 분야 가져오기
	public String getMemberInterest(String memID){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String memInterest = null;

		try {

			conn = ConnectionProvider.getConnection();
			String sql = "SELECT memInterest FROM MEMBER WHERE memID = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memID);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				memInterest	 = rs.getString("memInterest");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return memInterest;
	}
	
	// 관심 분야 수정
	public boolean updateMemberInterest(String memID, String memInterest){
		Connection conn = null;
		PreparedStatement pstmt= null;
		
		boolean result = false;
		String sql = "UPDATE MEMBER SET memInterest = ? WHERE memID = ?";
			
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memInterest);
			pstmt.setString(2, memID);
			
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