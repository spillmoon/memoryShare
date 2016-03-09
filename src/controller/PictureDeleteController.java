package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.PictureService;

@WebServlet("/pictureDelete.do")
public class PictureDeleteController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public PictureDeleteController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		String savePath = "C:\\Users\\LSH\\Documents\\workspace\\memoryShare\\WebContent\\data\\";	//업로드 되는 파일의 저장 경로
		String picName = request.getParameter("picName");
		int pictureNo = Integer.parseInt(request.getParameter("pictureNo"));
		// 물리적으로 서버에서 삭제(picName) + DB에서 사진 삭제(picNo);
		
		PictureService pService = new PictureService();
		
		File file = new File(savePath+picName);
		if(file.exists()){
			file.delete();
		}
		
		try{
			HttpSession session = request.getSession();
			int resultFlag = pService.deletePicture(pictureNo);
			
			switch(resultFlag){
			case PictureService.FILE_DELETE_SUCCESS:
				session.setAttribute("message", "success");
				response.sendRedirect("sendMessage.jsp");
				break;
			case PictureService.FILE_DELETE_FAIL:
				session.setAttribute("message", "fail");
				response.sendRedirect("sendMessage.jsp");
				break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
