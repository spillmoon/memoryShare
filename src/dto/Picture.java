package dto;

public class Picture {
	private int pictureNo;
	private int galleryNo;
	private String picName;
	private String picDate;
	private int removeReq;
	private String requestMem;
	
	public int getPictureNo() {
		return pictureNo;
	}
	public void setPictureNo(int pictureNo) {
		this.pictureNo = pictureNo;
	}
	public int getGalleryNo() {
		return galleryNo;
	}
	public void setGalleryNo(int galleryNo) {
		this.galleryNo = galleryNo;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicDate() {
		return picDate;
	}
	public void setPicDate(String picDate) {
		this.picDate = picDate;
	}
	public int getRemoveReq() {
		return removeReq;
	}
	public void setRemoveReq(int removeReq) {
		this.removeReq = removeReq;
	}
	public String getRequestMem() {
		return requestMem;
	}
	public void setRequestMem(String requestMem) {
		this.requestMem = requestMem;
	}
	
}
