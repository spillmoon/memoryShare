package dto;

public class Member {
	private int memNo;
	private String memName;
	private String memID;
	private String memPW;
	private String memInterest;
	private String memPhone;
	private String memEmail;
	private String identiQ;
	private String identiA;
	private int alertChk;
	
	public Member(){
		super();
	}
	
	public Member(String memID, String memPW){
		super();
		this.memID = memID;
		this.memPW = memPW;
	}
	
	public Member(String memID, String identiQ, String identiA){
		super();
		this.memID = memID;
		this.identiQ = identiQ;
		this.identiA = identiA;
	}
	
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemID() {
		return memID;
	}
	public void setMemID(String memID) {
		this.memID = memID;
	}
	public String getMemPW() {
		return memPW;
	}
	public void setMemPW(String memPW) {
		this.memPW = memPW;
	}
	public String getMemInterest() {
		return memInterest;
	}
	public void setMemInterest(String memInterest) {
		this.memInterest = memInterest;
	}
	public String getMemPhone() {
		return memPhone;
	}
	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}
	public String getMemEmail() {
		return memEmail;
	}
	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}
	public String getIdentiQ() {
		return identiQ;
	}
	public void setIdentiQ(String identiQ) {
		this.identiQ = identiQ;
	}
	public String getIdentiA() {
		return identiA;
	}
	public void setIdentiA(String identiA) {
		this.identiA = identiA;
	}
	public int getAlertChk() {
		return alertChk;
	}
	public void setAlertChk(int alertChk) {
		this.alertChk = alertChk;
	}
	
}
