package service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.AnswerDao;

public class AnswerService {
	public static final int ANSWER_DELETE_SUCCESS = 1;
	public static final int ANSWER_DELETE_FAIL =2;
	
	public static final int ANS_ANSWER_WRITE_SUCCESS = 1;	
	public static final int ANS_ANSWER_WRITE_FAIL = 2;
	
	AnswerDao aDao = new AnswerDao();
	
	// 글에 달린 댓글들 가져오기
	public JSONObject answerList(int boardNo)throws SQLException{
		return aDao.answerList(boardNo);
	}
	
	// 댓글 달기
	public void answerWriting(String ansMem, String ansContent, int boardNo)throws SQLException{
		aDao.answerWriting(ansMem, ansContent, boardNo);
	}
	
	// 댓글 번호 가져오기
	public int getAnsNo(int boardNo, String ansMem, String ansContent)throws SQLException{
		return aDao.getAnsNo(boardNo, ansMem, ansContent);
	}
	
	// 댓글 삭제하기
	public int answerDelete(int ansNo)throws SQLException{
		boolean result = aDao.answerDelete(ansNo);
		
		if(result == false){
			return ANSWER_DELETE_FAIL;
		}else{
			return ANSWER_DELETE_SUCCESS;
		}
	}
	
	// 댓글 수정하기
	public void editAnswer(int ansNo, String ansContent)throws SQLException{
		aDao.editAnswer(ansNo, ansContent);
		
	}
	
	// 댓글에 달린 답댓글들 가져오기
	public JSONObject ansAnswerList(int answerAnsNo)throws SQLException{
		return aDao.ansAnswerList(answerAnsNo);
	}
	
	// 답 댓글 달기
	public int ansAnswerWriting(String ansMem, String ansContent, 
			int boardNo, int answerAnsNo)throws SQLException{
		boolean result = aDao.ansAnswerWriting(ansMem, ansContent, boardNo, answerAnsNo);
		
		if(result == false){
			return ANS_ANSWER_WRITE_FAIL;
		}else{
			return ANS_ANSWER_WRITE_SUCCESS;
		
		}
			
	}
}