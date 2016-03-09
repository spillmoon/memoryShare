package dto;

public class Room {
	private int roomNo;
	private String roomName;
	private String roomCategory;
	private String roomKeyword;
	private String roomLocate;
	private String roomContent;
	private int roomMemCnt;
	private int currentMemCnt;
	private String leaderID;
	
	public Room(){
		super();
	}
	
	public Room(String roomName, String roomCategory, String roomKeyword, String roomLocate, String roomContent, int roomMemCnt){
		super();
		this.roomName = roomName;
		this.roomCategory = roomCategory;
		this.roomKeyword = roomKeyword;
		this.roomLocate = roomLocate;
		this.roomContent = roomContent;
		this.roomMemCnt = roomMemCnt;
	}
	
	public Room(String roomName, String roomCategory, String roomKeyword, 
			String leaderID, String roomLocate, int currentMemCnt, String roomContent){
		super();
		this.roomName = roomName;
		this.roomCategory = roomCategory;
		this.roomKeyword = roomKeyword;
		this.leaderID = leaderID;
		this.roomLocate = roomLocate;
		this.currentMemCnt = currentMemCnt;
		this.roomContent = roomContent;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}

	public String getRoomKeyword() {
		return roomKeyword;
	}

	public void setRoomKeyword(String roomKeyword) {
		this.roomKeyword = roomKeyword;
	}

	public String getRoomLocate() {
		return roomLocate;
	}

	public void setRoomLocate(String roomLocate) {
		this.roomLocate = roomLocate;
	}

	public String getRoomContent() {
		return roomContent;
	}

	public void setRoomContent(String roomContent) {
		this.roomContent = roomContent;
	}

	public int getRoomMemCnt() {
		return roomMemCnt;
	}

	public void setRoomMemCnt(int roomMemCnt) {
		this.roomMemCnt = roomMemCnt;
	}

	public int getCurrentMemCnt() {
		return currentMemCnt;
	}

	public void setCurrentMemCnt(int currentMemCnt) {
		this.currentMemCnt = currentMemCnt;
	}

	public String getLeaderID() {
		return leaderID;
	}

	public void setLeaderID(String leaderID) {
		this.leaderID = leaderID;
	}
	
	
}
