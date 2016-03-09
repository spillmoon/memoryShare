package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.ConnectionProvider;
import util.JdbcUtil;

public class GalleryDao {
	public GalleryDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
		
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////
	// 갤러리 리스트 가져오기
	public JSONObject galleryList(int roomNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT galleryNo, galleryName, galleryMem FROM GALLERY WHERE roomNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("galleryNo", rs.getInt("galleryNo"));
				obj.put("galleryName", rs.getString("galleryName"));
				obj.put("galleryMem", rs.getString("galleryMem"));
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
	
	// 갤러리 폴더 추가
	public boolean addGallery(int roomNo, String folderName, String memID){
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		boolean result=false;
		String sql="INSERT INTO GALLERY VALUES(SEQ_GALLERY.NEXTVAL, ?, ?, ?)";
		
		try{
			conn=ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, roomNo);
			pstmt.setString(2, folderName);
			pstmt.setString(3, memID);
			
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
	
	// 방장이 방의 갤러리를 전체 가져옴
	public JSONObject getGalleryForLeader(int roomNo){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT DISTINCT g.GALLERYNO, g.GALLERYNAME, g.GALLERYMEM, "
					+ "( SELECT COUNT(*) FROM PICTURE p "
					+ "WHERE g.GALLERYNO = p.GALLERYNO ) galleryPictures "
					+ "FROM GALLERY g, ROOM r, MEMBER_ROOM mr "
					+ "WHERE g.ROOMNO = r.ROOMNO AND mr.ROOMNO = r.ROOMNO "
					+ "AND r.ROOMNO = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, roomNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("galleryNo", rs.getInt("galleryNo"));
				obj.put("galleryName", rs.getString("galleryName"));
				obj.put("galleryMem", rs.getString("galleryMem"));
				obj.put("galleryPictures", rs.getInt("galleryPictures"));
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

	// 멤버가 자신이 만든 갤러리 가져옴
	public JSONObject getGalleryForMakeMember(int roomNo, String memID){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONObject jMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		String sql = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			
			sql = "SELECT DISTINCT g.GALLERYNO, g.GALLERYNAME, g.GALLERYMEM, "
					+ "( SELECT COUNT(*) FROM PICTURE p "
					+ "WHERE g.GALLERYNO = p.GALLERYNO ) galleryPictures "
					+ "FROM GALLERY g, ROOM r, MEMBER_ROOM mr "
					+ "WHERE g.ROOMNO = r.ROOMNO AND mr.ROOMNO = r.ROOMNO "
					+ "AND r.ROOMNO = ? AND g.GALLERYMEM = ?";
			
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setInt(1, roomNo);
			pstmt.setString(2, memID);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("galleryNo", rs.getInt("galleryNo"));
				obj.put("galleryName", rs.getString("galleryName"));
				obj.put("galleryMem", rs.getString("galleryMem"));
				obj.put("galleryPictures", rs.getInt("galleryPictures"));
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

	// 갤러리 삭제
	public boolean galleryDelete(int galleryNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "DELETE FROM GALLERY WHERE galleryNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, galleryNo);
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
	
	// 갤러리 이름 수정
		public boolean editGallery(int galleryNo, String galleryName){
			Connection conn = null;
			PreparedStatement pstmt= null;
			
			boolean result = false;
			String sql = "UPDATE GALLERY SET galleryName = ? WHERE galleryNo = ?";
			
			try{
				conn = ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, galleryName);
				pstmt.setInt(2, galleryNo);
				
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
