package service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.MemberDao;
import dto.Member;

public class MemberService {
	
	public static final int LOGIN_SUCCESS=1;
	public static final int LOGIN_ID_FAIL=2;
	public static final int LOGIN_PASSWORD_FAIL=3;
	
	public static final int MEM_INSERT_SUCCESS = 1;
	public static final int MEM_INSERT_FAIL = 2;
	
	public static final int ID_VALID = 1;
	public static final int ID_INVALID = 2;
	
	public static final int MEMBER_IDENTIFY_SUCCESS = 1;
	public static final int MEMBER_IDENTIFY_FAIL = 2;
	
	public static final int MEMBER_EDIT_SUCCESS = 1;
	public static final int MEMBER_EDIT_FAIL = 2;
	
	public static final int MEMBER_WITHDRAW_SUCCESS = 1;
	public static final int MEMBER_WITHDRAW_FAIL = 2;
	
	public static final int MEMBER_INTEREST_UPDATE_SUCCESS = 1;
	public static final int MEMBER_INTEREST_UPDATE_FAIL = 2;
	

	MemberDao mDao = new MemberDao();
	
	// �α���
	public int login(String id, String pw) throws SQLException{
		
		Member member = mDao.isId(id);
		
		if(member == null){
			//System.out.println("id �� �����Ѵ�.");
			return LOGIN_ID_FAIL;
		}
		
		if(member.getMemPW().equals(pw)){
			//System.out.println("pw�� �½��ϴ�.");
			return LOGIN_SUCCESS;
		}else{
			//System.out.println("pw�� Ʋ���ϴ�.");
			return LOGIN_PASSWORD_FAIL;
		}
	}
	
	// ȸ������
	public int insertMember(String memName, String memID, String memPW, 
			String memInterest, String memPhone, String memEmail, 
			String identiQ, String identiA, int alertChk) throws SQLException{
		
		boolean result = mDao.insertMember(memName, memID, memPW, memInterest, memPhone, memEmail, identiQ, identiA, alertChk);
		
		if(result==false){	// �Է� ����
			return MEM_INSERT_FAIL;
		}else{				// �Է� ����
			return  MEM_INSERT_SUCCESS;
		}
	}
	
	// id �ߺ�Ȯ��	
	public int idCheck(String id) throws SQLException{
		
		Member member = mDao.isId(id);
		
		if(member == null){		//id ��밡��
			return ID_VALID;
		}else{					//id ���Ұ���
			return ID_INVALID;
		}
	}

	// id to No
	public int memIdTOmemNo(String memID){
		return mDao.memIdTOmemNo(memID);
	}
	
	// ���� ����
	public int memberIdentify(String memID, int identiQ, String identiA) throws SQLException{
		
		Member member = mDao.memberIdentify(memID);
		
		if(member == null){
			//System.out.println("id �� �����Ѵ�.");
			return MEMBER_IDENTIFY_FAIL;
		}
		
		if( (Integer.parseInt(member.getIdentiQ()) == identiQ) && 
				(member.getIdentiA().equals(identiA))){
			return MEMBER_IDENTIFY_SUCCESS;
		}else{
			return MEMBER_IDENTIFY_FAIL;
		}
	}
	
	// ������ ���� ���� ���� ������
	public JSONObject getMemberData(String memID)throws SQLException{
		return mDao.getMemberData(memID);
	}
	
	// ��� ���� ����
	public int memberEdit(String memID, String memPW, String memEmail, 
			String memPhone, int alertChk, int identiQ, String identiA)throws SQLException{
		boolean result = mDao.memberEdit(memID, memPW, memEmail, memPhone, alertChk, identiQ, identiA);
		
		if(result == false){
			return MEMBER_EDIT_FAIL;
		}else{
			return MEMBER_EDIT_SUCCESS;
		}
	}
	
	// ȸ�� Ż��
	public int memberWithdraw(int memNo)throws SQLException{
		boolean result = mDao.memberWithdraw(memNo);
		
		if(result == false){
			return MEMBER_WITHDRAW_FAIL;
		}else{
			return MEMBER_WITHDRAW_SUCCESS;
		}
	}
	
	// ���� �о� ��������
	public String getMemberInterest(String memID)throws SQLException{
		return mDao.getMemberInterest(memID);
	}
	
	// ���� �о� ����
	public int updateMemberInterest(String memID, String memInterest)throws SQLException{
		boolean result = mDao.updateMemberInterest(memID, memInterest);
		
		if(result == false){
			return MEMBER_INTEREST_UPDATE_FAIL;
		}else{
			return MEMBER_INTEREST_UPDATE_SUCCESS;
		}
	}
	
}
