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
	
	// 갤러리 리스트 가져오기
	public JSONObject galleryList(int roomNo)throws SQLException{
		return gDao.galleryList(roomNo);
	}
	
	// 갤러리 폴더 추가
	public int addGallery(int roomNo, String folderName, String memID)throws SQLException{
		boolean result = gDao.addGallery(roomNo, folderName, memID);
		
		if(result == false){
			return GALLERY_ADD_FAIL;
		}else{
			return GALLERY_ADD_SUCCESS;
		}
	}
	
	// 방장이 방의 폴더를 가져옴
	public JSONObject getGalleryForLeader(int roomNo)throws SQLException{
		return gDao.getGalleryForLeader(roomNo);
	}

	// 멤버가 자신이 만든 폴더를 가져옴
	public JSONObject getGalleryForMakeMember(int roomNo, String memID)throws SQLException{
		return gDao.getGalleryForMakeMember(roomNo, memID);
	}
	
	// 갤러리 삭제
	public int galleryDelete(int galleryNo)throws SQLException{
		boolean result = gDao.galleryDelete(galleryNo);
		
		if(result == false){
			return GALLERY_DELETE_FAIL;
		}else{
			return GALLERY_DELETE_SUCCESS;
		}
	}
	
	// 갤러리 수정
	public int editGallery(int galleryNo, String galleryName)throws SQLException{
		boolean result = gDao.editGallery(galleryNo, galleryName);
		
		if(result == false){
			return GALLERY_EDIT_FAIL;
		}else{
			return GALLERY_EDIT_SUCCESS;
		}
	}
	
	
	
	
	
}
