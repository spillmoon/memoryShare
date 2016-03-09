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
	
	// 글 전체 리스트 가져오기
	public JSONObject boardAllList(int roomNo)throws SQLException{
		return bDao.boardAllList(roomNo);
	}
	
	// 글 등록하기
	public void insertBoard(int folderNo, String boardTitle, 
			String boardMem, String boardContent)throws SQLException{
		bDao.insertBoard(folderNo, boardTitle, boardMem,boardContent);
		
	}
	
	// 글 번호 가져오기
	public int getBoardNo(int roomNo, int folderNo, String boardTitle)throws SQLException{
		return bDao.getBoardNo(roomNo, folderNo, boardTitle);
	}
	
	// 글 가져오기
	public JSONObject boardRead(int boardNo)throws SQLException{
		return bDao.boardRead(boardNo);
	}
	
	// 글 삭제하기
	public int boardDelete(int boardNo)throws SQLException{
		boolean result = bDao.boardDelete(boardNo);
		
		if(result == false){
			return BOARD_DELETE_FAIL;
		}else{
			return BOARD_DELETE_SUCCESS;
		}
	}
	
	// 글 수정
	public void editBoard(int boardNo, String boardTitle, String boardContent)throws SQLException{
		bDao.editBoard(boardNo, boardTitle, boardContent);
		
	}
	
	// 특정 글의 arcNo 알아내기
	public int getArchiveNo(int boardNo)throws SQLException{
		return bDao.getArchiveNo(boardNo);
	}
	
	// 게시글을 찾음
	public JSONObject boardSearch(int roomNo, String boardSearch, int flag)throws SQLException{
		
		// 0 - 글이름. 1 - 글쓴이
		JSONObject boardList = null;
		if(flag == 0){			// 글제목
			boardList = bDao.boardSearchByTitle(roomNo, boardSearch);
		}else if(flag == 1){	// 글쓴이
			boardList = bDao.boardSearchByMem(roomNo, boardSearch);
		}else{
			System.out.println("fault!");
		}
		
		return boardList;
	}



}
