package service;

import java.sql.SQLException;

import dao.ArchiveDao;

public class ArchiveService {
	public static final int FILE_INSERT_SUCCESS = 1;
	public static final int FILE_INSERT_FAIL = 2;
	
	public static final int FILE_EDIT_SUCCESS = 1;
	public static final int FILE_EDIT_FAIL = 2;
	
	ArchiveDao aDao = new ArchiveDao();
	
	// 글에서 자료를 업로드(하나만 가능)
	public void insertArcFromBoard(int boardNo, String arcName, String arcRoute)throws SQLException{
		aDao.insertArcFromBoard(boardNo, arcName, arcRoute);
		
	}
	
	// arcNo를 이용해 파일 바꿈
	public int editArchive(int arcNo, String modifiedArcName, String arcRoute)throws SQLException{
		boolean result = aDao.editArchive(arcNo, modifiedArcName, arcRoute);
		
		if(result == false){
			return FILE_EDIT_FAIL;
		}else{
			return FILE_EDIT_SUCCESS;
		}
	}
	
	// 게시글에서 boardNo를 통해 해당 글의 자료를 가져옴
	public String getArcNameFromBoard(int boardNo)throws SQLException{
		return aDao.getArcNameFromBoard(boardNo);
	}
	
	// 게시글에서 boardNo를 통해 해당 글의 자료 번호를 가져옴
	public int getArcNoFromBoard(int boardNo)throws SQLException{
		return aDao.getArcNoFromBoard(boardNo);
	}
	
	// 파일을 삭제
	public void deleteArchive(int arcNo)throws SQLException{
		aDao.deleteArchive(arcNo);
	}
	
	// 댓글에서 자료를 업로드(하나만 가능)
	public void insertArcFromAnswer(int ansNo, String uploadFile, String arcRoute)throws SQLException{
		aDao.insertArcFromAnswer(ansNo, uploadFile, arcRoute);
		
	}
	
	// 댓글에서 ansNo를 통해 해당 글의 자료를 가져옴
	public String getArcNameFromAnswer(int ansNo)throws SQLException{
		return aDao.getArcNameFromAnswer(ansNo);
	}
	
	// 게시글에서 boardNo를 통해 해당 글의 자료 번호를 가져옴
	public int getArcNoFromAnswer(int ansNo)throws SQLException{
		return aDao.getArcNoFromAnswer(ansNo);
	}
}
