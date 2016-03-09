package service;

import java.sql.SQLException;

import dao.ArchiveDao;

public class ArchiveService {
	public static final int FILE_INSERT_SUCCESS = 1;
	public static final int FILE_INSERT_FAIL = 2;
	
	public static final int FILE_EDIT_SUCCESS = 1;
	public static final int FILE_EDIT_FAIL = 2;
	
	ArchiveDao aDao = new ArchiveDao();
	
	// �ۿ��� �ڷḦ ���ε�(�ϳ��� ����)
	public void insertArcFromBoard(int boardNo, String arcName, String arcRoute)throws SQLException{
		aDao.insertArcFromBoard(boardNo, arcName, arcRoute);
		
	}
	
	// arcNo�� �̿��� ���� �ٲ�
	public int editArchive(int arcNo, String modifiedArcName, String arcRoute)throws SQLException{
		boolean result = aDao.editArchive(arcNo, modifiedArcName, arcRoute);
		
		if(result == false){
			return FILE_EDIT_FAIL;
		}else{
			return FILE_EDIT_SUCCESS;
		}
	}
	
	// �Խñۿ��� boardNo�� ���� �ش� ���� �ڷḦ ������
	public String getArcNameFromBoard(int boardNo)throws SQLException{
		return aDao.getArcNameFromBoard(boardNo);
	}
	
	// �Խñۿ��� boardNo�� ���� �ش� ���� �ڷ� ��ȣ�� ������
	public int getArcNoFromBoard(int boardNo)throws SQLException{
		return aDao.getArcNoFromBoard(boardNo);
	}
	
	// ������ ����
	public void deleteArchive(int arcNo)throws SQLException{
		aDao.deleteArchive(arcNo);
	}
	
	// ��ۿ��� �ڷḦ ���ε�(�ϳ��� ����)
	public void insertArcFromAnswer(int ansNo, String uploadFile, String arcRoute)throws SQLException{
		aDao.insertArcFromAnswer(ansNo, uploadFile, arcRoute);
		
	}
	
	// ��ۿ��� ansNo�� ���� �ش� ���� �ڷḦ ������
	public String getArcNameFromAnswer(int ansNo)throws SQLException{
		return aDao.getArcNameFromAnswer(ansNo);
	}
	
	// �Խñۿ��� boardNo�� ���� �ش� ���� �ڷ� ��ȣ�� ������
	public int getArcNoFromAnswer(int ansNo)throws SQLException{
		return aDao.getArcNoFromAnswer(ansNo);
	}
}
