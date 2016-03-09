package service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.BoardFolderDao;

public class BoardFolderService {
	
	public static final int CREATE_INFORMATION_FOLDER_SUCCESS = 1;
	public static final int CREATE_INFORMATION_FOLDER_FAIL = 2;
	
	public static final int ADD_FOLDER_SUCCESS = 1;
	public static final int ADD_FOLDER_FAIL = 2;
	
	public static final int BOARD_FOLDER_EDIT_SUCCESS = 1;
	public static final int BOARD_FOLDER_EDIT_FAIL = 2;
	
	public static final int BOARD_FOLDER_DELETE_SUCCESS = 1;
	public static final int BOARD_FOLDER_DELETE_FAIL = 2;
	
	BoardFolderDao bfDao = new BoardFolderDao();
	
	// 방 생성시 강제로 생성되는 '공지'폴더
	public int createInformationFolder(int roomNo)throws SQLException{
		boolean result = bfDao.createInformationFolder(roomNo);
		
		if(result == false){
			return CREATE_INFORMATION_FOLDER_FAIL;
		}else{
			return CREATE_INFORMATION_FOLDER_SUCCESS;
		}
	}
	
	// 폴더 리스트 가져오기
	public JSONObject showFolderList(int roomNo)throws SQLException{
		return bfDao.showFolderList(roomNo);
	}
	
	// 폴더 추가
	public int addFolder(int roomNo, String folderName)throws SQLException{
		boolean result = bfDao.addFolder(roomNo, folderName);
		
		if(result == false){
			return ADD_FOLDER_FAIL;
		}else{
			return ADD_FOLDER_SUCCESS;
		}
	}
	
	// 수정을 위해 폴더 정보 가져옴
	public JSONObject getBoardFolderForEdit(int folderNo)throws SQLException{
		return bfDao.getBoardFolderForEdit(folderNo);
	}
	
	// 폴더 이름 수정
	public int editFolder(int folderNo, String modifiedFolderName)throws SQLException{
		boolean result = bfDao.editFolder(folderNo, modifiedFolderName);
		
		if(result == false){
			return BOARD_FOLDER_EDIT_FAIL;
		}else{
			return BOARD_FOLDER_EDIT_SUCCESS;
		}
	}
	
	// 폴더 삭제(폴더에 딸린 게시글/댓글/자료 전부 다 삭제 한꺼번에 됨)
	public int deleteFolder(int folderNo)throws SQLException{
		boolean result = bfDao.deleteFolder(folderNo);
		
		if(result == false){
			return BOARD_FOLDER_DELETE_FAIL;
		}else{
			return BOARD_FOLDER_DELETE_SUCCESS;
		}
	}
	
	// 폴더의 No를 알아냄
	public int getBoardFolderNo(int roomNo, String folderName)throws SQLException{
		return bfDao.getBoardFolderNo(roomNo, folderName);
	}
	
}
