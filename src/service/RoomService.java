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
	
	// 방 등록
	public int insertRoom(String roomName, 
			String roomCategory, String roomKeyword, 
			String roomLocate, String roomContent, int roomMemCnt)throws SQLException{
		boolean result = rDao.insertRoom(roomName, roomCategory, roomKeyword, roomLocate, roomContent, roomMemCnt);
		
		if(result == false){	// 방 등록 실패
			return ROOM_INSERT_FAIL;
		}else{				// 방 등록 성공
			return ROOM_INSERT_SUCCESS;
		}
	}
	
	// 방 이름 중혹 확인
	public int roomCheck(String roomId)throws SQLException{
		Room room = rDao.isRoom(roomId);
		
		if(room == null){
			return ROOM_ID_VALID;
		}else{
			return ROOM_ID_INVALID;
		}
	}
	
	// 방 전체 불러오기
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
	
	// 멤버 관심 분야와 관련있는 방 불러오기
	public JSONObject roomInterestList(int memNo)throws SQLException{
		return rDao.roomInterestList(memNo);
	}
	
	// 멤버 신청 리스트 불러오기
	public JSONObject roomApplyList(int memNo)throws SQLException{
		return rDao.roomApplyList(memNo);
	}
	
	// 방 찾기 후 리스트 불러오기
	public JSONObject roomFindList(int memNo, int flag, String search)throws SQLException{
		
		// 0 - 키워드 . 1 - 방이름
		JSONObject roomList = null;
		if(flag == 0){			// 키워드로 검색
			roomList = rDao.roomFindByKeyword(memNo, search);
		}else if(flag == 1){	// 방이름으로 검색
			roomList = rDao.roomFindByTitle(memNo, search);
		}else{
			System.out.println("fault!");
		}
		
		return roomList;
	}
	
	// 내가 만든 방 리스트 가져오기
	public JSONObject roomCreateList(int memNo)throws SQLException{
		return rDao.roomCreateList(memNo);
	}
	
	// 내가 가입된 방 리스트 가져오기
	public JSONObject roomJoinList(int memNo)throws SQLException{
		return rDao.roomJoinList(memNo);
	}
	
	// 선택된 방의 리더의 memNo를 알아냄
	public int roomLeader(int roomNo)throws SQLException{
		return rDao.roomLeader(roomNo);
	}
	
	// 방장 비교하는 메서드
	public int compLeader(int memNo, int roomNo)throws SQLException{
		int realLeader = rDao.roomLeader(roomNo);
		
		if(realLeader == memNo){	// 방장이다
			return REAL_LEADER_TRUE;
		}else{						// 방장 아니다
			return REAL_LEADER_FALSE;
		}
	}
	
	
	// room information view for editing
	public JSONObject getRoomInfoForEdit(String roomName)throws SQLException{
		return rDao.getRoomInfoForEdit(roomName);
	}
	
	// 방정보 update
	public int editRoomInfo(String roomName, int roomMemCnt, String roomLocate, String roomContent)throws SQLException{
		boolean result = rDao.editRoomInfo(roomName, roomMemCnt, roomLocate, roomContent);
		
		if(result == false){	// 방 등록 실패
			return ROOM_EDIT_FAIL;
		}else{				// 방 등록 성공
			return ROOM_EDIT_SUCCESS;
		}
	}
	
	// 방 삭제 (member 테잉블의 정보를 제외한 room에 연관된 정보들은 모두 삭제 cascade)
	public int roomDestroy(String roomName)throws SQLException{
		boolean result = rDao.roomDestroy(roomName);
		
		if(result == false){
			return ROOM_DESTROY_FAIL;
		}else{
			return ROOM_DESTROY_SUCCESS;
		}
	}
	
	
}
