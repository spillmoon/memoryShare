package service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.CalendarDao;

public class CalendarService {

	public static final int CALENDAR_INSERT_SUCCESS = 1;
	public static final int CALENDAR_INSERT_FAIL = 2;
	
	public static final int CALENDAR_DELETE_SUCCESS = 1;
	public static final int CALENDAR_DELETE_FAIL = 2;
	
	public static final int CALENDAR_EDIT_SUCCESS = 1;
	public static final int CALENDAR_EDIT_FAIL = 2;
	
	CalendarDao cDao = new CalendarDao();
	
	// ���� �߰�
	public int insertCalendar(int roomNo,String calName,String calDate, 
			String calLoc,String calContent)throws SQLException{
		boolean result = cDao.insertCalendar(roomNo, calName, calDate, 
				calLoc, calContent);
		
		if(result == false){
			return CALENDAR_INSERT_FAIL;
		}else{
			return CALENDAR_INSERT_SUCCESS;
		}
	}
	
	// �ش� ���� ���� ��� ��������
	public JSONObject selectedDateInfo(String calDate, String roomName)throws SQLException{
		return cDao.selectedDateInfo(calDate, roomName);
	}
	
	// �޷� ����
	public int calendarDelete(int calNo)throws SQLException{
		boolean result = cDao.calendarDelete(calNo);
		
		if(result == false){
			return CALENDAR_DELETE_FAIL;
		}else{
			return CALENDAR_DELETE_SUCCESS;
		}
	}
	
	// �ش� ���� ���� �ϳ� ��������
	public JSONObject calendarRead(int calNo)throws SQLException{
		return cDao.calendarRead(calNo);
	}
	
	// ���� �̸� ����
	public int calendarEdit(int calNo, String calName, String calDate, 
			 String calLoc, String calContent)throws SQLException{
		boolean result = cDao.calendarEdit(calNo, calName, 
				calDate, calLoc, calContent);
		
		if(result == false){
			return CALENDAR_EDIT_FAIL;
		}else{
			return CALENDAR_EDIT_SUCCESS;
		}
	}
	
	// �ش� ���� ���� ��� ��������
	public JSONObject selectedMonthInfo(int year, String month, String roomName)throws SQLException{
		return cDao.selectedMonthInfo(year, month, roomName);
	}
	
}
