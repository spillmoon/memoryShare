package service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.PictureDao;

public class PictureService {
	public static final int FILE_INSERT_SUCCESS = 1;
	public static final int FILE_INSERT_FAILE = 2;
	
	public static final int FILE_DELETE_SUCCESS = 1;
	public static final int FILE_DELETE_FAIL = 2;
	
	public static final int FILE_DELETE_REQUEST_SUCCESS = 1;
	public static final int FILE_DELETE_REQUEST_FAIL = 2;
	
	public static final int PICTURE_DELETE_REQUEST_REFUSAL_SUCCESS = 1;
	public static final int PICTURE_DELETE_REQUEST_REFUSAL_FAIL = 2;
	
	PictureDao pDao = new PictureDao(); 
	
	// �������� ���� �߰�
	public void addPicture(int  galleryNo, String uploadFile, String picMem)throws SQLException{
		pDao.addPicture(galleryNo, uploadFile, picMem);
	}
	
	// ������ ���� ������ ��������
	public JSONObject pictureFromGallery(int galleryNo)throws SQLException{
		return pDao.pictureFromGallery(galleryNo);
	}
	
	// ���� ����
	public int deletePicture(int pictureNo)throws SQLException{
		boolean result = pDao.deletePicture(pictureNo);
		
		if(result== false){
			return FILE_DELETE_FAIL;
		}else{
			return FILE_DELETE_SUCCESS;
		}
	}
	
	// ������û ��� ��������
	public String getRequestMem(int pictureNo)throws SQLException{
		return pDao.getRequestMem(pictureNo);
	}
	
	// ���� ���� �䫊
	public int pictureRequestDelete(int pictureNo, String requestMem)throws SQLException{
		boolean result = pDao.pictureRequestDelete(pictureNo, requestMem);
		
		if(result == false){
			return FILE_DELETE_REQUEST_FAIL;
		}else{
			return FILE_DELETE_REQUEST_SUCCESS;
		}
	}
	
	// ���� ������ ��û ����Ʈ
	public JSONObject pictureRequestDeleteList(int roomNo, String memID)throws SQLException{
		return pDao.pictureRequestDeleteList(roomNo, memID);
	}
	
	// ���� ���� ��û ����
	public int pictureRequestDeleteRefusal(int pictureNo)throws SQLException{
		boolean result = pDao.pictureRequestDeleteRefusal(pictureNo);
		
		if(result == false){
			return PICTURE_DELETE_REQUEST_REFUSAL_FAIL; 
		}else{
			return PICTURE_DELETE_REQUEST_REFUSAL_SUCCESS;
		}
	}
	
	// ���� ������ ��û ����Ʈ
	public JSONObject pictureDeleteRequestingList(int roomNo, String memID)throws SQLException{
		return pDao.pictureDeleteRequestingList(roomNo, memID);
	}
}
