package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.ConnectionProvider;
import util.JdbcUtil;

public class PictureDao {
	public PictureDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
		
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////
	// 갤러리에 사진 추가
	public void addPicture(int  galleryNo, String uploadFile, String picMem){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql="INSERT INTO PICTURE(pictureNo, galleryNo, picName, picDate, picMem) "
				+ "VALUES(SEQ_PICTURE.NEXTVAL, ?, ?, sysdate, ?)";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, galleryNo);
			pstmt.setString(2, uploadFile);
			pstmt.setString(3, picMem);
			pstmt.executeUpdate();
		
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
	}
	
	// 해당 갤러리에서 사진들 가져오기
	public JSONObject pictureFromGallery(int galleryNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT pictureNo, picName, picMem FROM PICTURE WHERE galleryNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, galleryNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				
				obj.put("pictureNo", rs.getInt("pictureNo"));
				obj.put("picName", rs.getString("picName"));
				obj.put("picMem", rs.getString("picMem"));
				
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
	
	// 사진 삭제
	public boolean deletePicture(int pictureNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM PICTURE WHERE pictureNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pictureNo);
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
	
	// 삭제요청한 멤버 가져오기
	public String getRequestMem(int pictureNo){
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		String requestMem = null;
		
		try {

			conn = ConnectionProvider.getConnection();
			String sql = "SELECT requestMem FROM PICTURE WHERE pictureNo = ?";
			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, pictureNo);
			rs = pStmt.executeQuery();

			if (rs.next()) {
				requestMem = rs.getString("requestMem");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pStmt);
			JdbcUtil.close(conn);
		}
		return requestMem;
	}
	
	// 사진 삭제 요청
	public boolean pictureRequestDelete(int pictureNo, String requestMem){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE PICTURE SET requestMem = ? WHERE pictureNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, requestMem);
			pstmt.setInt(2, pictureNo);
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
	
	// 사진 삭제 요청된 리스트
	public JSONObject pictureRequestDeleteList(int roomNo, String memID){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT p.PICTURENO, p.PICNAME, g.GALLERYNAME, p.REQUESTMEM "
					+ "FROM GALLERY g, PICTURE p "
					+ "WHERE g.GALLERYNO = p.GALLERYNO "
					+ "AND g.ROOMNO = ? AND g.GALLERYMEM = ? "
					+ "AND p.REQUESTMEM IS NOT NULL";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, roomNo);
			pstmt.setString(2, memID);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("pictureNo", rs.getInt("pictureNo"));
				obj.put("picName", rs.getString("picName"));
				obj.put("galleryName", rs.getString("galleryName"));
				obj.put("requestMem", rs.getString("requestMem"));
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
	
	// 사진 삭제 요청 거절
	public boolean pictureRequestDeleteRefusal(int pictureNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE PICTURE SET requestMem = NULL WHERE pictureNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pictureNo);
			
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
	
	// 사진 삭제 요청한 리스트
	public JSONObject  pictureDeleteRequestingList(int roomNo, String memID){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT p.PICTURENO, g.GALLERYNAME, p.PICNAME "
					+ "FROM GALLERY g, PICTURE p "
					+ "WHERE g.GALLERYNO = p.GALLERYNO "
					+ "AND g.ROOMNO = ? AND p.REQUESTMEM = ?"; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, roomNo);
			pstmt.setString(2, memID);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("pictureNo", rs.getInt("pictureNo"));
				obj.put("galleryName", rs.getString("galleryName"));
				obj.put("picName", rs.getString("picName"));
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
