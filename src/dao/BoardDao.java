package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.ConnectionProvider;
import util.JdbcUtil;

public class BoardDao {
	public BoardDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
		
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////
	// 글 리스트 (전체) 가져오기
	public JSONObject boardAllList(int roomNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT b.BOARDNO, bf.FOLDERNAME, b.BOARDTITLE, b.BOARDMEM, b.BOARDDATE "
					+ "FROM BOARDFOLDER bf, BOARD b WHERE bf.FOLDERNO = b.FOLDERNO "
					+ "AND bf.ROOMNO = ? ORDER BY bf.FOLDERNO";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("boardNo", rs.getString("boardNo"));
				obj.put("folderName", rs.getString("folderName"));
				obj.put("boardTitle", rs.getString("boardTitle"));
				obj.put("boardMem", rs.getString("boardMem"));
				obj.put("boardDate", rs.getString("boardDate"));
				
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
	
	// 글 을 게시함
	public void insertBoard(int folderNo, String boardTitle, 
		String boardMem, String boardContent){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		String sql="INSERT INTO BOARD VALUES(SEQ_BOARD.NEXTVAL, ?, ?, ?, SYSDATE, ?)";
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, folderNo);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardMem);
			pstmt.setString(4, boardContent);
			
			pstmt.executeQuery();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
	}
	
	// 글 번호 가져오기
	public int getBoardNo(int roomNo, int folderNo, String boardTitle){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int boardNo=0;

		try {

			conn = ConnectionProvider.getConnection();
			String sql = "SELECT boardNo FROM BOARD b, BOARDFOLDER bf "
					+ "WHERE b.FOLDERNO = bf.FOLDERNO AND bf.ROOMNO = ? "
					+ "AND bf.FOLDERNO = ? AND b.BOARDTITLE = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, roomNo);
			pstmt.setInt(2, folderNo);
			pstmt.setString(3, boardTitle);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				boardNo = rs.getInt("boardNo");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return boardNo;
	}
	
	// 글 내용 가져오기
	public JSONObject boardRead(int boardNo){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		JSONObject jMain  = new JSONObject();
		JSONObject jObject = new JSONObject();
		
		String sql = "SELECT b.BOARDNO, b.BOARDTITLE, bf.FOLDERNAME, "
				+ "b.BOARDMEM, b.BOARDDATE, a.ARCNAME, a.ARCROUTE, b.BOARDCONTENT "
				+ "FROM BOARDFOLDER bf, BOARD b, ARCHIVE a "
				+ "WHERE bf.FOLDERNO = b.FOLDERNO AND "
				+ "b.BOARDNO = a.BOARDNO(+) AND b.BOARDNO = ?";  		
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				jObject.put("boardNo", rs.getInt("boardNo"));
				jObject.put("boardTitle", rs.getString("boardTitle"));
				jObject.put("folderName", rs.getString("folderName"));
				jObject.put("boardMem", rs.getString("boardMem"));
				jObject.put("boardDate", rs.getString("boardDate"));
				jObject.put("arcName", rs.getString("arcName"));
				jObject.put("arcRoute", rs.getString("arcRoute"));
				jObject.put("boardContent", rs.getString("boardContent"));
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
	
	// 글 삭제하기
	public boolean boardDelete(int boardNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM BOARD WHERE boardNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			
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
	
	// 글 수정
	public void editBoard(int boardNo, String boardTitle, String boardContent){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE BOARD "
				+ "SET boardTitle = ?, boardDate = SYSDATE, boardContent = ? "
				+ "WHERE boardNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, boardNo);
			
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
	}
	
	// 글의 해당 파일 No를 알아냄
	public int getArchiveNo(int boardNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int arcNo = 0;

		try {

			conn = ConnectionProvider.getConnection();
			String sql = "SELECT a.ARCNO FROM BOARD b, ARCHIVE a "
					+ "WHERE b.BOARDNO = a.BOARDNO AND b.BOARDNO = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				arcNo = rs.getInt("arcNo");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return arcNo;
	}
	
	// *검색하기*
	// 게시글 이름으로 찾기
	public JSONObject boardSearchByTitle(int roomNo, String boardTitle){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT b.BOARDNO, bf.FOLDERNAME, b.BOARDTITLE, "
					+ "b.BOARDMEM, b.BOARDDATE "
					+ "FROM BOARDFOLDER bf, BOARD b "
					+ "WHERE bf.FOLDERNO = b.FOLDERNO "
					+ "AND bf.ROOMNO = ? AND b.BOARDTITLE like '%'||?||'%'";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			pstmt.setString(2, boardTitle);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("boardNo", rs.getString("boardNo"));
				obj.put("folderName", rs.getString("folderName"));
				obj.put("boardTitle", rs.getString("boardTitle"));
				obj.put("boardMem", rs.getString("boardMem"));
				obj.put("boardDate", rs.getString("boardDate"));
				
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
	
	// *검색하기*
	// 게시글 이름으로 찾기
	public JSONObject boardSearchByMem(int roomNo, String boardMem){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT b.BOARDNO, bf.FOLDERNAME, b.BOARDTITLE,"
					+ " b.BOARDMEM, b.BOARDDATE "
					+ "FROM BOARDFOLDER bf, BOARD b "
					+ "WHERE bf.FOLDERNO = b.FOLDERNO "
					+ "AND bf.ROOMNO = ? AND b.BOARDMEM like '%'||?||'%'";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			pstmt.setString(2, boardMem);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("boardNo", rs.getString("boardNo"));
				obj.put("folderName", rs.getString("folderName"));
				obj.put("boardTitle", rs.getString("boardTitle"));
				obj.put("boardMem", rs.getString("boardMem"));
				obj.put("boardDate", rs.getString("boardDate"));
				
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

	
	
	
}
