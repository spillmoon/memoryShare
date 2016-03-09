package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.ConnectionProvider;
import util.JdbcUtil;

public class AnswerDao {
	public AnswerDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
		
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////
	// 글에 달린 댓글들 가져오기
	public JSONObject answerList(int boardNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "select an.ANSNO, an.ANSMEM, an.ANSDATE, an.ANSCONTENT, ar.ARCNAME, ar.ARCROUTE, "
					+ "( SELECT COUNT(*) FROM ANSWER a WHERE a.ANSWERANSNO = an.ANSNO )ansAnswerCount "
					+ "FROM ANSWER an, ARCHIVE ar "
					+ "WHERE an.ANSNO = ar.ANSNO(+) AND an.BOARDNO = ? AND an.ANSWERANSNO IS NULL";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("ansNo", rs.getInt("ansNo"));
				obj.put("ansMem", rs.getString("ansMem"));
				obj.put("ansDate", rs.getString("ansDate"));
				obj.put("ansContent", rs.getString("ansContent"));
				obj.put("arcName", rs.getString("arcName"));
				obj.put("arcRoute", rs.getString("arcRoute"));
				obj.put("ansAnswerCount", rs.getInt("ansAnswerCount"));
				
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
	
	// 댓글 달기
	public void answerWriting(String ansMem, String ansContent, int boardNo){
			
			Connection conn=null;
			PreparedStatement pstmt=null;
			
			String sql="INSERT INTO ANSWER(ansNo, ansMem, ansDate, "
					+ "ansContent, boardNo) "
					+ "VALUES(SEQ_ANSWER.NEXTVAL, ?, sysdate, ?, ?)"; 
			
			try{
				conn=ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, ansMem);
				pstmt.setString(2, ansContent);
				pstmt.setInt(3, boardNo);
				
				pstmt.executeQuery();
				
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JdbcUtil.close(pstmt);
				JdbcUtil.close(conn);
			}
	}
	
	// 댓글 번호 가져오기
	public int getAnsNo(int boardNo, String ansMem, String ansContent){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int ansNo=0;

		try {

			conn = ConnectionProvider.getConnection();
			String sql = "SELECT ansNo FROM ANSWER "
					+ "WHERE boardNo = ? AND ansMem = ? AND ansContent = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, ansMem);
			pstmt.setString(3, ansContent);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				ansNo = rs.getInt("ansNo");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return ansNo;
	}
	
	// 댓글 삭제하기
	public boolean answerDelete(int ansNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM ANSWER WHERE ansNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, ansNo);
			
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
	
	public void editAnswer(int ansNo, String ansContent){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE ANSWER SET ansContent = ? WHERE ansNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ansContent);
			pstmt.setInt(2, ansNo);
			
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
	}

	// 글에 달린 댓글들 가져오기
	public JSONObject ansAnswerList(int answerAnsNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT ansNo, ansMem, ansDate, ansContent FROM ANSWER WHERE answerAnsNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, answerAnsNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("ansNo", rs.getInt("ansNo"));
				obj.put("ansMem", rs.getString("ansMem"));
				obj.put("ansDate", rs.getString("ansDate"));
				obj.put("ansContent", rs.getString("ansContent"));
				
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
	
	// 답댓글 달기
	public boolean ansAnswerWriting(String ansMem, String ansContent,int boardNo, int answerAnsNo){
				
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		boolean result = false;
		String sql="INSERT INTO ANSWER "
				+ "VALUES(SEQ_ANSWER.NEXTVAL, ?, SYSDATE, ?, ?, ?)"; 
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ansMem);
			pstmt.setString(2, ansContent);
			pstmt.setInt(3, boardNo);
			pstmt.setInt(4, answerAnsNo);
			
			pstmt.executeQuery();
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