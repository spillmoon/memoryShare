package service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.GalleryDao;

public class GalleryService {
	public static final int GALLERY_ADD_SUCCESS = 1;
	public static final int GALLERY_ADD_FAIL = 2;
	
	public static final int GALLERY_DELETE_SUCCESS = 1;
	public static final int GALLERY_DELETE_FAIL = 2;
	
	public static final int GALLERY_EDIT_SUCCESS = 1;
	public static final int GALLERY_EDIT_FAIL = 2;

	GalleryDao gDao = new GalleryDao();
	
	// ������ ����Ʈ ��������
	public JSONObject galleryList(int roomNo)throws SQLException{
		return gDao.galleryList(roomNo);
	}
	
	// ������ ���� �߰�
	public int addGallery(int roomNo, String folderName, String memID)throws SQLException{
		boolean result = gDao.addGallery(roomNo, folderName, memID);
		
		if(result == false){
			return GALLERY_ADD_FAIL;
		}else{
			return GALLERY_ADD_SUCCESS;
		}
	}
	
	// ������ ���� ������ ������
	public JSONObject getGalleryForLeader(int roomNo)throws SQLException{
		return gDao.getGalleryForLeader(roomNo);
	}

	// ����� �ڽ��� ���� ������ ������
	public JSONObject getGalleryForMakeMember(int roomNo, String memID)throws SQLException{
		return gDao.getGalleryForMakeMember(roomNo, memID);
	}
	
	// ������ ����
	public int galleryDelete(int galleryNo)throws SQLException{
		boolean result = gDao.galleryDelete(galleryNo);
		
		if(result == false){
			return GALLERY_DELETE_FAIL;
		}else{
			return GALLERY_DELETE_SUCCESS;
		}
	}
	
	// ������ ����
	public int editGallery(int galleryNo, String galleryName)throws SQLException{
		boolean result = gDao.editGallery(galleryNo, galleryName);
		
		if(result == false){
			return GALLERY_EDIT_FAIL;
		}else{
			return GALLERY_EDIT_SUCCESS;
		}
	}
	
	
	
	
	
}
