package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnectionProvider;
import util.JdbcUtil;

public class ArchiveDao {
	public ArchiveDao(){
		String jdbc_driver="oracle.jdbc.driver.OracleDriver";
		
		try{
			Class.forName(jdbc_driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////
	// ���� �̸� DB�� ���� (�Խñ� �ش�)
	public void insertArcFromBoard(int boardNo, String arcName, String arcRoute){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql="INSERT INTO ARCHIVE(arcNo, boardNo, arcName, arcRoute, arcDate) "
				+ "VALUES(SEQ_ARCHIVE.NEXTVAL, ?, ?, ?, sysdate)";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, arcName);
			pstmt.setString(3, arcRoute);
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
	}
	
	// ���� �̸� ������Ʈ
	public boolean editArchive(int arcNo, String modifiedArcName, String arcRoute){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		String sql = "UPDATE ARCHIVE SET arcName = ?, arcRoute = ?, arcDate = SYSDATE "
				+ "WHERE arcNo = ?"; 
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, modifiedArcName);
			pstmt.setString(2, arcRoute);
			pstmt.setInt(3, arcNo);
			
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
	
	// �ش� ���� �ڷḦ ������
	public String getArcNameFromBoard(int boardNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String arcName = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "select a.ARCNAME FROM BOARD b, ARCHIVE a "
					+ "WHERE b.BOARDNO = a.BOARDNO AND b.BOARDNO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			if(rs.next()){
				arcName = rs.getString("arcName");
			}
		}catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return arcName;
	}
	
	// �ش� ���� �ڷ� ��ȣ�� ������
	public int getArcNoFromBoard(int boardNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int arcNo = 0;
		
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "select a.ARCNO FROM BOARD b, ARCHIVE a "
					+ "WHERE b.BOARDNO = a.BOARDNO AND b.BOARDNO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			if(rs.next()){
				arcNo = rs.getInt("arcNo");
			}
		}catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return arcNo;
	}
	
	// ������ ����
	public void deleteArchive(int arcNo){
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM ARCHIVE WHERE arcNo = ?";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, arcNo);
			pstmt.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
	}
	
	// ���� �̸� DB�� ���� (�Խñ� �ش�)
	public void insertArcFromAnswer(int ansNo, String uploadFile, String arcRoute){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql="INSERT INTO ARCHIVE(arcNo, ansNo, arcName, arcRoute, arcDate) "
				+ "VALUES(SEQ_ARCHIVE.NEXTVAL, ?, ?, ?, sysdate)";
		
		try{
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ansNo);
			pstmt.setString(2, uploadFile);
			pstmt.setString(3, arcRoute);
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
			
	}
	
	// �ش� ����� �ڷḦ ������
	public String getArcNameFromAnswer(int ansNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String arcName = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "select ar.ARCNAME FROM ANSWER an, ARCHIVE ar "
					+ "WHERE an.ANSNO = ar.ANSNO AND an.ANSNO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ansNo);
			rs = pstmt.executeQuery();
			if(rs.next()){
				arcName = rs.getString("arcName");
			}
		}catch (Exception e) {
			e.printStackTrace();
			} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return arcName;
	}

	// �ش� ����� �ڷ� ��ȣ�� ������
	public int getArcNoFromAnswer(int ansNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int arcNo = 0;
		
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "select ar.ARCNO FROM ANSWER an, ARCHIVE ar "
					+ "WHERE an.ANSNO = ar.ANSNO AND an.ANSNO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ansNo);
			rs = pstmt.executeQuery();
			if(rs.next()){
				arcNo = rs.getInt("arcNo");
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return arcNo;
	}

}