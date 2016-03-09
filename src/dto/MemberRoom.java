package dto;

public class MemberRoom {
	private int memNo;
	private int roomNo;
	private int leaderChk;
	private int reqStatus;
	
	public MemberRoom(){
		super();
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public int getLeaderChk() {
		return leaderChk;
	}

	public void setLeaderChk(int leaderChk) {
		this.leaderChk = leaderChk;
	}

	public int getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(int reqStatus) {
		this.reqStatus = reqStatus;
	}
	
	
}
