package service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.BoardDao;

public class BoardService {
	public static final int BOARD_INSERT_SUCCESS = 1;
	public static final int BOARD_INSERT_FAIL = 2;
	
	public static final int BOARD_DELETE_SUCCESS = 1;
	public static final int BOARD_DELETE_FAIL = 2;
	
	public static final int BOARD_EDIT_SUCCESS = 1;
	public static final int BOARD_EDIT_FAIL = 2;
	
	BoardDao bDao = new BoardDao();
	
	// �� ��ü ����Ʈ ��������
	public JSONObject boardAllList(int roomNo)throws SQLException{
		return bDao.boardAllList(roomNo);
	}
	
	// �� ����ϱ�
	public void insertBoard(int folderNo, String boardTitle, 
			String boardMem, String boardContent)throws SQLException{
		bDao.insertBoard(folderNo, boardTitle, boardMem,boardContent);
		
	}
	
	// �� ��ȣ ��������
	public int getBoardNo(int roomNo, int folderNo, String boardTitle)throws SQLException{
		return bDao.getBoardNo(roomNo, folderNo, boardTitle);
	}
	
	// �� ��������
	public JSONObject boardRead(int boardNo)throws SQLException{
		return bDao.boardRead(boardNo);
	}
	
	// �� �����ϱ�
	public int boardDelete(int boardNo)throws SQLException{
		boolean result = bDao.boardDelete(boardNo);
		
		if(result == false){
			return BOARD_DELETE_FAIL;
		}else{
			return BOARD_DELETE_SUCCESS;
		}
	}
	
	// �� ����
	public void editBoard(int boardNo, String boardTitle, String boardContent)throws SQLException{
		bDao.editBoard(boardNo, boardTitle, boardContent);
		
	}
	
	// Ư�� ���� arcNo �˾Ƴ���
	public int getArchiveNo(int boardNo)throws SQLException{
		return bDao.getArchiveNo(boardNo);
	}
	
	// �Խñ��� ã��
	public JSONObject boardSearch(int roomNo, String boardSearch, int flag)throws SQLException{
		
		// 0 - ���̸�. 1 - �۾���
		JSONObject boardList = null;
		if(flag == 0){			// ������
			boardList = bDao.boardSearchByTitle(roomNo, boardSearch);
		}else if(flag == 1){	// �۾���
			boardList = bDao.boardSearchByMem(roomNo, boardSearch);
		}else{
			System.out.println("fault!");
		}
		
		return boardList;
	}



}
