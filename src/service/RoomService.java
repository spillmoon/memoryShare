package service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.RoomDao;
import dto.Room;

public class RoomService {
	
	public static final int ROOM_INSERT_SUCCESS = 1;
	public static final int ROOM_INSERT_FAIL = 2;
	
	public static final int ROOM_ID_VALID = 1;
	public static final int ROOM_ID_INVALID = 2;

	public static final int REAL_LEADER_TRUE = 1;
	public static final int REAL_LEADER_FALSE = 2;
	
	public static final int ROOM_EDIT_SUCCESS = 1;
	public static final int ROOM_EDIT_FAIL = 2;
	
	public static final int ROOM_DESTROY_SUCCESS = 1;
	public static final int ROOM_DESTROY_FAIL = 2;
	
	RoomDao rDao = new RoomDao();
	
	// �� ���
	public int insertRoom(String roomName, 
			String roomCategory, String roomKeyword, 
			String roomLocate, String roomContent, int roomMemCnt)throws SQLException{
		boolean result = rDao.insertRoom(roomName, roomCategory, roomKeyword, roomLocate, roomContent, roomMemCnt);
		
		if(result == false){	// �� ��� ����
			return ROOM_INSERT_FAIL;
		}else{				// �� ��� ����
			return ROOM_INSERT_SUCCESS;
		}
	}
	
	// �� �̸� ��Ȥ Ȯ��
	public int roomCheck(String roomId)throws SQLException{
		Room room = rDao.isRoom(roomId);
		
		if(room == null){
			return ROOM_ID_VALID;
		}else{
			return ROOM_ID_INVALID;
		}
	}
	
	// �� ��ü �ҷ�����
	public JSONObject roomAllList(int memNo) throws SQLException{
		return rDao.roomAllList(memNo);
	}

	// roomname to roomno
	public int roomNameTOroomNo(String roomName)throws SQLException{
		return rDao.roomNameTOroomNo(roomName);
	}
	
	// room information view
	public JSONObject roomInfo(String roomName)throws SQLException{
		return rDao.roomInfo(roomName);
	}
	
	// ��� ���� �о߿� �����ִ� �� �ҷ�����
	public JSONObject roomInterestList(int memNo)throws SQLException{
		return rDao.roomInterestList(memNo);
	}
	
	// ��� ��û ����Ʈ �ҷ�����
	public JSONObject roomApplyList(int memNo)throws SQLException{
		return rDao.roomApplyList(memNo);
	}
	
	// �� ã�� �� ����Ʈ �ҷ�����
	public JSONObject roomFindList(int memNo, int flag, String search)throws SQLException{
		
		// 0 - Ű���� . 1 - ���̸�
		JSONObject roomList = null;
		if(flag == 0){			// Ű����� �˻�
			roomList = rDao.roomFindByKeyword(memNo, search);
		}else if(flag == 1){	// ���̸����� �˻�
			roomList = rDao.roomFindByTitle(memNo, search);
		}else{
			System.out.println("fault!");
		}
		
		return roomList;
	}
	
	// ���� ���� �� ����Ʈ ��������
	public JSONObject roomCreateList(int memNo)throws SQLException{
		return rDao.roomCreateList(memNo);
	}
	
	// ���� ���Ե� �� ����Ʈ ��������
	public JSONObject roomJoinList(int memNo)throws SQLException{
		return rDao.roomJoinList(memNo);
	}
	
	// ���õ� ���� ������ memNo�� �˾Ƴ�
	public int roomLeader(int roomNo)throws SQLException{
		return rDao.roomLeader(roomNo);
	}
	
	// ���� ���ϴ� �޼���
	public int compLeader(int memNo, int roomNo)throws SQLException{
		int realLeader = rDao.roomLeader(roomNo);
		
		if(realLeader == memNo){	// �����̴�
			return REAL_LEADER_TRUE;
		}else{						// ���� �ƴϴ�
			return REAL_LEADER_FALSE;
		}
	}
	
	
	// room information view for editing
	public JSONObject getRoomInfoForEdit(String roomName)throws SQLException{
		return rDao.getRoomInfoForEdit(roomName);
	}
	
	// ������ update
	public int editRoomInfo(String roomName, int roomMemCnt, String roomLocate, String roomContent)throws SQLException{
		boolean result = rDao.editRoomInfo(roomName, roomMemCnt, roomLocate, roomContent);
		
		if(result == false){	// �� ��� ����
			return ROOM_EDIT_FAIL;
		}else{				// �� ��� ����
			return ROOM_EDIT_SUCCESS;
		}
	}
	
	// �� ���� (member ���׺��� ������ ������ room�� ������ �������� ��� ���� cascade)
	public int roomDestroy(String roomName)throws SQLException{
		boolean result = rDao.roomDestroy(roomName);
		
		if(result == false){
			return ROOM_DESTROY_FAIL;
		}else{
			return ROOM_DESTROY_SUCCESS;
		}
	}
	
	
}
