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
	
	// �� ������ ������ �����Ǵ� '����'����
	public int createInformationFolder(int roomNo)throws SQLException{
		boolean result = bfDao.createInformationFolder(roomNo);
		
		if(result == false){
			return CREATE_INFORMATION_FOLDER_FAIL;
		}else{
			return CREATE_INFORMATION_FOLDER_SUCCESS;
		}
	}
	
	// ���� ����Ʈ ��������
	public JSONObject showFolderList(int roomNo)throws SQLException{
		return bfDao.showFolderList(roomNo);
	}
	
	// ���� �߰�
	public int addFolder(int roomNo, String folderName)throws SQLException{
		boolean result = bfDao.addFolder(roomNo, folderName);
		
		if(result == false){
			return ADD_FOLDER_FAIL;
		}else{
			return ADD_FOLDER_SUCCESS;
		}
	}
	
	// ������ ���� ���� ���� ������
	public JSONObject getBoardFolderForEdit(int folderNo)throws SQLException{
		return bfDao.getBoardFolderForEdit(folderNo);
	}
	
	// ���� �̸� ����
	public int editFolder(int folderNo, String modifiedFolderName)throws SQLException{
		boolean result = bfDao.editFolder(folderNo, modifiedFolderName);
		
		if(result == false){
			return BOARD_FOLDER_EDIT_FAIL;
		}else{
			return BOARD_FOLDER_EDIT_SUCCESS;
		}
	}
	
	// ���� ����(������ ���� �Խñ�/���/�ڷ� ���� �� ���� �Ѳ����� ��)
	public int deleteFolder(int folderNo)throws SQLException{
		boolean result = bfDao.deleteFolder(folderNo);
		
		if(result == false){
			return BOARD_FOLDER_DELETE_FAIL;
		}else{
			return BOARD_FOLDER_DELETE_SUCCESS;
		}
	}
	
	// ������ No�� �˾Ƴ�
	public int getBoardFolderNo(int roomNo, String folderName)throws SQLException{
		return bfDao.getBoardFolderNo(roomNo, folderName);
	}
	
}
