package service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.MemberRoomDao;

public class MemberRoomService {
	
	public static final int MEMBER_ROOM_INSERT_SUCCESS = 1;	
	public static final int MEMBER_ROOM_INSERT_FAIL = 2;
	
	public static final int MEMBER_ROOM_APPLY_SUCCESS = 1;
	public static final int MEMBER_ROOM_APPLY_FAIL = 2;
	
	public static final int THERE_IS_MEMNO = 1;
	public static final int THERE_IS_NOMEMNO = 2;
	
	public static final int MEMBER_ROOM_DELETE_SUCCESS = 1;
	public static final int MEMBER_ROOM_DELETE_FAIL = 2;
	
	public static final int TRANSFER_AVAILABLE = 1;
	public static final int TRANSFER_NOT_AVAILABLE = 2;
	
	public static final int LEADER_APPLY_SUCCESS = 1;
	public static final int LEADER_APPLY_FAIL = 2;
	
	public static final int LEADER_APPLY_ACCEPT_SCCESS = 1;
	public static final int LEADER_APPLY_ACCEPT_FAIL = 2;
	
	public static final int LEADER_TO_MEMBER_SUCCESS = 1;
	public static final int LEADER_TO_MEMBER_FAIL = 2;
	
	public static final int LEADER_APPLY_REFUSAL_SUCCESS = 1;
	public static final int LEADER_APPLY_REFUSAL_FAIL = 2;
	
	public static final int APPLYING_MY_ROOM_ACCEPT_SUCCESS = 1;
	public static final int APPLYING_MY_ROOM_ACCEPT_FAIL = 2;
	
	public static final int APPLYING_MY_ROOM_REFUSAL_SUCCESS = 1;
	public static final int APPLYING_MY_ROOM_REFUSAL_FAIL = 2;
	
	public static final int TRANSFERED_MEMBER = 1;
	public static final int NOT_TRANSFERED_MEMBER = 2;
	
	public static final int ROOM_APPLY_CANCEL_SUCCESS = 1;
	public static final int ROOM_APPLY_CANCEL_FAIL =2;
	
	MemberRoomDao mrDao = new MemberRoomDao();
	
	// member room insert(방장)
	public int insertMemberRoom(int memNo, int roomNo)throws SQLException{
		
		boolean result = mrDao.insertMemberRoom(memNo, roomNo);
		
		if(result==false){	// 입력 실패
			return MEMBER_ROOM_INSERT_FAIL;
		}else{				// 입력 성공
			return  MEMBER_ROOM_INSERT_SUCCESS;
		}
	}
	
	// member room insert(신청자)
	public int applyMemberRoom(int memNo, int roomNo)throws SQLException{
		
		boolean result = mrDao.applyMemberRoom(memNo, roomNo);
		
		if(result==false){
			return MEMBER_ROOM_APPLY_FAIL;
		}else{
			return MEMBER_ROOM_APPLY_SUCCESS;
		}
	}
	
	// check member before apply
	public int chkMemberInRoom(int memNo, int roomNo)throws SQLException{
		int isMemNo = mrDao.chkMemberInRoom(memNo, roomNo);
		
		if(isMemNo==memNo){		// 이미 방장이거나 멤버 또는 신청중임
			return THERE_IS_MEMNO;
		}else{					// 넣을 수 있음
			return THERE_IS_NOMEMNO;
		}
	}
	
	// 회원 탈퇴하기
	public int withdrawFromRoom(int memNo, int roomNo)throws SQLException{
		
		boolean result = mrDao.withdrawFromRoom(memNo, roomNo);
		
		if(result == false){	//방에서 탈퇴 실패
			return MEMBER_ROOM_DELETE_FAIL;
		}else{					//방에서 탈퇴 성공
			return MEMBER_ROOM_DELETE_SUCCESS;
		}
	}
	
	// 양도가 이미 있는지 확인
	public int isTransfer(int roomNo)throws SQLException{
		
		int result = mrDao.isTransfer(roomNo);
		
		if(result == 0){	// 이미 양도한 사람이 없다.
			return TRANSFER_AVAILABLE;
		}else{			// 이미 양도한 사람이 있다.
			return TRANSFER_NOT_AVAILABLE;
		}
		
	}
	
	// 양도할 멤버 리스트 가져오기
	public JSONObject leaderTransferList(int roomNo)throws SQLException{
		return mrDao.leaderTransferList(roomNo);
	}

	// 양도한 멤버 정보 가져오기
	public JSONObject leaderTransferedMem(int roomNo)throws SQLException{
		return mrDao.leaderTransferedMem(roomNo);
	}
	
	// 방장 양도 요청(0->2)
	public int leaderTransferApply(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.leaderTransferApply(memNo, roomNo);
		
		if(result == false){
			return LEADER_APPLY_FAIL;
		}else{
			return LEADER_APPLY_SUCCESS;
		}
	}
	
	// 일반 회원에게 양도 요청이된 리스트
	public JSONObject leaderTransReq(int memNo, int roomNo)throws SQLException{
		return mrDao.leaderTransReq(memNo, roomNo);
	}
	
	// 방장 요청 수락(멤버 -> 방장)
	public int leaderApplyAccept(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.leaderApplyAccept(memNo, roomNo);
		
		if(result == false){
			return LEADER_APPLY_ACCEPT_FAIL; 
		}else{
			return LEADER_APPLY_ACCEPT_SCCESS;
		}
	}
	
	// 방장 일반 멤버로 바꿈(방장 => 멤버)
	public int leaderToMember(int leader)throws SQLException{
		boolean result = mrDao.leaderToMember(leader);
		
		if(result == false){
			return LEADER_TO_MEMBER_FAIL;
		}else{
			return LEADER_TO_MEMBER_SUCCESS;
		}
	}
	
	// 방장 요청 거절(leadercheck ==> 2->0)
	public int leaderApplyRefusal(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.leaderApplyRefusal(memNo, roomNo);
		
		if(result == false){
			return LEADER_APPLY_REFUSAL_FAIL;
		}else{
			return LEADER_APPLY_REFUSAL_SUCCESS;
		}
	}
	
	// 현재 내가 방장이고 내방에 가입 신청을 요청한 사람들 리스트 가져오기
	public JSONObject applyingToMyRoomList(int roomNo)throws SQLException{
		return mrDao.applyingToMyRoomList(roomNo);
	}
	
	// 가입 신청한 사람의 요청을 수락함
	public int applyingToMyRoomAccept(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.applyingToMyRoomAccept(memNo, roomNo);
		
		if(result == false){
			return APPLYING_MY_ROOM_ACCEPT_FAIL;
		}else{
			return APPLYING_MY_ROOM_ACCEPT_SUCCESS;
		}
	}
	
	// 가입 신청한 사람의 요청을 거절함
	public int applyingToMyRoomRefusal(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.applyingToMyRoomRefusal(memNo, roomNo);
		
		if(result == false){
			return APPLYING_MY_ROOM_REFUSAL_FAIL;
		}else{
			return APPLYING_MY_ROOM_REFUSAL_SUCCESS;
		}
	}
	
	// 양도된 멤버 찾는 메서드
	public int transferedMember(int memNo, int roomNo)throws SQLException{
		int memID = mrDao.isTransfer(roomNo);
		
		if(memNo == memID){
			return TRANSFERED_MEMBER;
		}else{
			return NOT_TRANSFERED_MEMBER;
		}
	}
	
	// 방에 가입을 위해 신청한 것 삭제
	public int roomApplyCancel(int memNo, int roomNo)throws SQLException{
		
		boolean result = mrDao.roomApplyCancel(memNo, roomNo);
		
		if(result==false){
			return ROOM_APPLY_CANCEL_FAIL;
		}else{
			return ROOM_APPLY_CANCEL_SUCCESS;
		}
	}	
	
	
	
	
	
}



