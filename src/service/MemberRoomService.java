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
	
	// member room insert(����)
	public int insertMemberRoom(int memNo, int roomNo)throws SQLException{
		
		boolean result = mrDao.insertMemberRoom(memNo, roomNo);
		
		if(result==false){	// �Է� ����
			return MEMBER_ROOM_INSERT_FAIL;
		}else{				// �Է� ����
			return  MEMBER_ROOM_INSERT_SUCCESS;
		}
	}
	
	// member room insert(��û��)
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
		
		if(isMemNo==memNo){		// �̹� �����̰ų� ��� �Ǵ� ��û����
			return THERE_IS_MEMNO;
		}else{					// ���� �� ����
			return THERE_IS_NOMEMNO;
		}
	}
	
	// ȸ�� Ż���ϱ�
	public int withdrawFromRoom(int memNo, int roomNo)throws SQLException{
		
		boolean result = mrDao.withdrawFromRoom(memNo, roomNo);
		
		if(result == false){	//�濡�� Ż�� ����
			return MEMBER_ROOM_DELETE_FAIL;
		}else{					//�濡�� Ż�� ����
			return MEMBER_ROOM_DELETE_SUCCESS;
		}
	}
	
	// �絵�� �̹� �ִ��� Ȯ��
	public int isTransfer(int roomNo)throws SQLException{
		
		int result = mrDao.isTransfer(roomNo);
		
		if(result == 0){	// �̹� �絵�� ����� ����.
			return TRANSFER_AVAILABLE;
		}else{			// �̹� �絵�� ����� �ִ�.
			return TRANSFER_NOT_AVAILABLE;
		}
		
	}
	
	// �絵�� ��� ����Ʈ ��������
	public JSONObject leaderTransferList(int roomNo)throws SQLException{
		return mrDao.leaderTransferList(roomNo);
	}

	// �絵�� ��� ���� ��������
	public JSONObject leaderTransferedMem(int roomNo)throws SQLException{
		return mrDao.leaderTransferedMem(roomNo);
	}
	
	// ���� �絵 ��û(0->2)
	public int leaderTransferApply(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.leaderTransferApply(memNo, roomNo);
		
		if(result == false){
			return LEADER_APPLY_FAIL;
		}else{
			return LEADER_APPLY_SUCCESS;
		}
	}
	
	// �Ϲ� ȸ������ �絵 ��û�̵� ����Ʈ
	public JSONObject leaderTransReq(int memNo, int roomNo)throws SQLException{
		return mrDao.leaderTransReq(memNo, roomNo);
	}
	
	// ���� ��û ����(��� -> ����)
	public int leaderApplyAccept(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.leaderApplyAccept(memNo, roomNo);
		
		if(result == false){
			return LEADER_APPLY_ACCEPT_FAIL; 
		}else{
			return LEADER_APPLY_ACCEPT_SCCESS;
		}
	}
	
	// ���� �Ϲ� ����� �ٲ�(���� => ���)
	public int leaderToMember(int leader)throws SQLException{
		boolean result = mrDao.leaderToMember(leader);
		
		if(result == false){
			return LEADER_TO_MEMBER_FAIL;
		}else{
			return LEADER_TO_MEMBER_SUCCESS;
		}
	}
	
	// ���� ��û ����(leadercheck ==> 2->0)
	public int leaderApplyRefusal(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.leaderApplyRefusal(memNo, roomNo);
		
		if(result == false){
			return LEADER_APPLY_REFUSAL_FAIL;
		}else{
			return LEADER_APPLY_REFUSAL_SUCCESS;
		}
	}
	
	// ���� ���� �����̰� ���濡 ���� ��û�� ��û�� ����� ����Ʈ ��������
	public JSONObject applyingToMyRoomList(int roomNo)throws SQLException{
		return mrDao.applyingToMyRoomList(roomNo);
	}
	
	// ���� ��û�� ����� ��û�� ������
	public int applyingToMyRoomAccept(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.applyingToMyRoomAccept(memNo, roomNo);
		
		if(result == false){
			return APPLYING_MY_ROOM_ACCEPT_FAIL;
		}else{
			return APPLYING_MY_ROOM_ACCEPT_SUCCESS;
		}
	}
	
	// ���� ��û�� ����� ��û�� ������
	public int applyingToMyRoomRefusal(int memNo, int roomNo)throws SQLException{
		boolean result = mrDao.applyingToMyRoomRefusal(memNo, roomNo);
		
		if(result == false){
			return APPLYING_MY_ROOM_REFUSAL_FAIL;
		}else{
			return APPLYING_MY_ROOM_REFUSAL_SUCCESS;
		}
	}
	
	// �絵�� ��� ã�� �޼���
	public int transferedMember(int memNo, int roomNo)throws SQLException{
		int memID = mrDao.isTransfer(roomNo);
		
		if(memNo == memID){
			return TRANSFERED_MEMBER;
		}else{
			return NOT_TRANSFERED_MEMBER;
		}
	}
	
	// �濡 ������ ���� ��û�� �� ����
	public int roomApplyCancel(int memNo, int roomNo)throws SQLException{
		
		boolean result = mrDao.roomApplyCancel(memNo, roomNo);
		
		if(result==false){
			return ROOM_APPLY_CANCEL_FAIL;
		}else{
			return ROOM_APPLY_CANCEL_SUCCESS;
		}
	}	
	
	
	
	
	
}



