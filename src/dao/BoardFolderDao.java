package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.ConnectionProvider;
import util.JdbcUtil;

public class BoardFolderDao {
	public BoardFolderDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
		
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////
	// 방 생성시 강제로 추가되는 '공지'폴더 생성
	public boolean createInformationFolder(int roomNo){
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		boolean result=false;
		String sql="INSERT INTO boardFolder VALUES (SEQ_BOARDFOLDER.NEXTVAL, ?, '공지')";
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
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
	
	// 방에 관련된 모든 폴더를 공지리스트부터 생성된 순으로 뿌려줌
	public JSONObject showFolderList(int roomNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT folderNo, folderName FROM boardFolder WHERE roomNo = ? ORDER BY folderNo";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("folderNo", rs.getInt("folderNo"));
				obj.put("folderName", rs.getString("folderName"));
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
	
	// 게시판 폴더 추가
	public boolean addFolder(int roomNo, String folderName){
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		boolean result=false;
		String sql="INSERT INTO boardFolder VALUES (SEQ_BOARDFOLDER.NEXTVAL, ?, ?)";
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			pstmt.setString(2, folderName);
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
	
	// 수정을 위해 선택된 정보 가져오기
	public JSONObject getBoardFolderForEdit(int folderNo){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		JSONObject jMain  = new JSONObject();
		JSONObject jObject = new JSONObject();
		
		String sql = "SELECT folderNo, folderName FROM BOARDFOLDER "
				+ "WHERE folderNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, folderNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				jObject.put("folderNo", rs.getInt("folderNo"));
				jObject.put("folderName", rs.getString("folderName"));
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
	
	// 폴더 이름 수정
	public boolean editFolder(int folderNo, String modifiedFolderName){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE BOARDFOLDER SET folderName = ? WHERE folderNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, modifiedFolderName);
			pstmt.setInt(2, folderNo);
			
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
	
	// 폴더 관련 전부 삭제됨(글/댓글/자료)
	public boolean deleteFolder(int folderNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM BOARDFOLDER WHERE folderNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, folderNo);
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
	
	// 폴더의 No를 알아냄
	public int getBoardFolderNo(int roomNo, String folderName){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int folderNo = 0;

		try {

			conn = ConnectionProvider.getConnection();
			String sql = "SELECT folderNo FROM BOARDFOLDER WHERE roomNo = ? AND folderName = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			pstmt.setString(2, folderName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				folderNo = rs.getInt("folderNo");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return folderNo;
	}
}
