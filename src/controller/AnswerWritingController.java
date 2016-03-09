package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AnswerService;
import service.ArchiveService;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/answerWriting.do")
public class AnswerWritingController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public AnswerWritingController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/////////////////////////////////////////////////////////////////////////////////////////////
		int maxSize  = 1024*1024*1024;  															//�� ���� ���ε� �ѵ��� 1Giga
		String savePath = "D:\\workEE\\memoryShare\\WebContent\\data\\";	//���ε� �Ǵ� ������ ���� ���
		String uploadFile="";
		
		MultipartRequest multi= new MultipartRequest(request,savePath,maxSize, new DefaultFileRenamePolicy());
		
		uploadFile=multi.getFilesystemName("uploaded_file");
		uploadFile= new String(uploadFile.getBytes("8859_1"),"EUC-KR");		//���ε� �� ���� �̸� �ѱ�ȭ(���󿡼�)
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////

		if(uploadFile.substring(0, 4).equals("null")){	// ���� ���� ����
			
			File file = new File(savePath+uploadFile);
			
			if(file.exists()){
				file.delete();
			}
			
			String ansMem = multi.getParameter("ansMem");
			ansMem = new String(ansMem.getBytes("8859_1"),"utf-8");
			String ansContent = multi.getParameter("ansContent");
			ansContent = new String(ansContent.getBytes("8859_1"),"utf-8");
			String boardNumber = multi.getParameter("boardNo");
			int boardNo = Integer.parseInt(boardNumber);
			
			AnswerService aService = new AnswerService();
			
			try{
				aService.answerWriting(ansMem, ansContent, boardNo);
			}catch(SQLException e){
				e.printStackTrace();
			}
			
		}else{		// ���� ���� ��
			String ansMem = multi.getParameter("ansMem");
			ansMem = new String(ansMem.getBytes("8859_1"),"utf-8");
			String ansContent = multi.getParameter("ansContent");
			ansContent = new String(ansContent.getBytes("8859_1"),"utf-8");
			String boardNumber = multi.getParameter("boardNo");
			int boardNo = Integer.parseInt(boardNumber);
			
			String arcRoute = multi.getParameter("arcRoute");
			arcRoute = new String(arcRoute.getBytes("8859_1"),"utf-8");
			
			int ansNo;
			
			AnswerService anService = new AnswerService();
			ArchiveService arService = new ArchiveService();
			
			try{
				// ��۾���
				anService.answerWriting(ansMem, ansContent, boardNo);
				// boardno�� answerno��������
				ansNo = anService.getAnsNo(boardNo, ansMem, ansContent);
				// ��� ���� ���� DB ����
				arService.insertArcFromAnswer(ansNo, uploadFile, arcRoute);
				
			}catch(SQLException e){
				e.printStackTrace();
			}
			
			
		}
		
		
		
	}
	
}
