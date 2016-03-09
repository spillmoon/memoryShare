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
	// �� ������ ������ �߰��Ǵ� '����'���� ����
	public boolean createInformationFolder(int roomNo){
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		boolean result=false;
		String sql="INSERT INTO boardFolder VALUES (SEQ_BOARDFOLDER.NEXTVAL, ?, '����')";
		
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
	
	// �濡 ���õ� ��� ������ ��������Ʈ���� ������ ������ �ѷ���
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
	
	// �Խ��� ���� �߰�
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
	
	// ������ ���� ���õ� ���� ��������
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
	
	// ���� �̸� ����
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
	
	// ���� ���� ���� ������(��/���/�ڷ�)
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
	
	// ������ No�� �˾Ƴ�
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
