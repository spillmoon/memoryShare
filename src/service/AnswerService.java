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
	
	// �ۿ� �޸� ��۵� ��������
	public JSONObject answerList(int boardNo)throws SQLException{
		return aDao.answerList(boardNo);
	}
	
	// ��� �ޱ�
	public void answerWriting(String ansMem, String ansContent, int boardNo)throws SQLException{
		aDao.answerWriting(ansMem, ansContent, boardNo);
	}
	
	// ��� ��ȣ ��������
	public int getAnsNo(int boardNo, String ansMem, String ansContent)throws SQLException{
		return aDao.getAnsNo(boardNo, ansMem, ansContent);
	}
	
	// ��� �����ϱ�
	public int answerDelete(int ansNo)throws SQLException{
		boolean result = aDao.answerDelete(ansNo);
		
		if(result == false){
			return ANSWER_DELETE_FAIL;
		}else{
			return ANSWER_DELETE_SUCCESS;
		}
	}
	
	// ��� �����ϱ�
	public void editAnswer(int ansNo, String ansContent)throws SQLException{
		aDao.editAnswer(ansNo, ansContent);
		
	}
	
	// ��ۿ� �޸� ���۵� ��������
	public JSONObject ansAnswerList(int answerAnsNo)throws SQLException{
		return aDao.ansAnswerList(answerAnsNo);
	}
	
	// �� ��� �ޱ�
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