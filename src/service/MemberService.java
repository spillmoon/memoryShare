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
	
	// 로그인
	public int login(String id, String pw) throws SQLException{
		
		Member member = mDao.isId(id);
		
		if(member == null){
			//System.out.println("id 가 없습닌다.");
			return LOGIN_ID_FAIL;
		}
		
		if(member.getMemPW().equals(pw)){
			//System.out.println("pw가 맞습니다.");
			return LOGIN_SUCCESS;
		}else{
			//System.out.println("pw가 틀립니다.");
			return LOGIN_PASSWORD_FAIL;
		}
	}
	
	// 회원가입
	public int insertMember(String memName, String memID, String memPW, 
			String memInterest, String memPhone, String memEmail, 
			String identiQ, String identiA, int alertChk) throws SQLException{
		
		boolean result = mDao.insertMember(memName, memID, memPW, memInterest, memPhone, memEmail, identiQ, identiA, alertChk);
		
		if(result==false){	// 입력 실패
			return MEM_INSERT_FAIL;
		}else{				// 입력 성공
			return  MEM_INSERT_SUCCESS;
		}
	}
	
	// id 중복확인	
	public int idCheck(String id) throws SQLException{
		
		Member member = mDao.isId(id);
		
		if(member == null){		//id 사용가능
			return ID_VALID;
		}else{					//id 사용불가능
			return ID_INVALID;
		}
	}

	// id to No
	public int memIdTOmemNo(String memID){
		return mDao.memIdTOmemNo(memID);
	}
	
	// 본인 인증
	public int memberIdentify(String memID, int identiQ, String identiA) throws SQLException{
		
		Member member = mDao.memberIdentify(memID);
		
		if(member == null){
			//System.out.println("id 가 없습닌다.");
			return MEMBER_IDENTIFY_FAIL;
		}
		
		if( (Integer.parseInt(member.getIdentiQ()) == identiQ) && 
				(member.getIdentiA().equals(identiA))){
			return MEMBER_IDENTIFY_SUCCESS;
		}else{
			return MEMBER_IDENTIFY_FAIL;
		}
	}
	
	// 수정을 위해 폴더 정보 가져옴
	public JSONObject getMemberData(String memID)throws SQLException{
		return mDao.getMemberData(memID);
	}
	
	// 멤버 정보 수정
	public int memberEdit(String memID, String memPW, String memEmail, 
			String memPhone, int alertChk, int identiQ, String identiA)throws SQLException{
		boolean result = mDao.memberEdit(memID, memPW, memEmail, memPhone, alertChk, identiQ, identiA);
		
		if(result == false){
			return MEMBER_EDIT_FAIL;
		}else{
			return MEMBER_EDIT_SUCCESS;
		}
	}
	
	// 회원 탈퇴
	public int memberWithdraw(int memNo)throws SQLException{
		boolean result = mDao.memberWithdraw(memNo);
		
		if(result == false){
			return MEMBER_WITHDRAW_FAIL;
		}else{
			return MEMBER_WITHDRAW_SUCCESS;
		}
	}
	
	// 관심 분야 가져오기
	public String getMemberInterest(String memID)throws SQLException{
		return mDao.getMemberInterest(memID);
	}
	
	// 관심 분야 수정
	public int updateMemberInterest(String memID, String memInterest)throws SQLException{
		boolean result = mDao.updateMemberInterest(memID, memInterest);
		
		if(result == false){
			return MEMBER_INTEREST_UPDATE_FAIL;
		}else{
			return MEMBER_INTEREST_UPDATE_SUCCESS;
		}
	}
	
}
