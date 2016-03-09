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

@WebServlet("/answerEdit.do")
public class AnswerEditController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public AnswerEditController(){
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
		
		int ansNo = Integer.parseInt(multi.getParameter("ansNo"));
		
		String ansContent = multi.getParameter("ansContent");
		ansContent = new String(ansContent.getBytes("8859_1"),"utf-8");
		
		String arcRoute = multi.getParameter("arcRoute");
		arcRoute = new String(arcRoute.getBytes("8859_1"),"utf-8");
		
		System.out.println(ansNo+"/"+ansContent+"/"+arcRoute+"/"+uploadFile);
		
		String arcName = null;
		
		AnswerService anService = new AnswerService();
		ArchiveService arService = new ArchiveService();
		
		// ���� ������ ���� ��
		if(uploadFile.equals("null")){
			File file = new File(savePath+uploadFile);
			
			if(file.exists()){
				file.delete();
			}
			
			try{
				arcName = arService.getArcNameFromAnswer(ansNo);
			}catch(SQLException e){
				e.printStackTrace();
			}
			
			// ������ ������ ���ٸ� -> ��۸� ����
			if(arcName == null){
				try{
					anService.editAnswer(ansNo, ansContent);
				}catch(SQLException e){
					e.printStackTrace();
				}
			// ������ ������ ���� �� -> ���� ���� & �� ����
			}else{
				// �������� �ڷ� ����
				File file2 = new File(savePath+arcName);
				if(file2.exists()){
					file2.delete();
				}
				
				try{
					// DB���� DATA ����
					int arcNo = arService.getArcNoFromAnswer(ansNo);
					arService.deleteArchive(arcNo);
					
					// �� ����
					anService.editAnswer(ansNo, ansContent);
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			
		// ���� ������ ���� ��
		}else{
			try{
				arcName = arService.getArcNameFromAnswer(ansNo);
			}catch(SQLException e){
				e.printStackTrace();
			}
			
			// ������ ������ ���� �� -> ���� �߰� & �� ����
			if(arcName == null){
				try{
					arService.insertArcFromAnswer(ansNo, uploadFile, arcRoute);
					anService.editAnswer(ansNo, ansContent);
				}catch(SQLException e){
					e.printStackTrace();
				}
			
			// ������ ������ ���� �� -> ���� ���ε�(���ο� ����), �������� ���������� ���ϻ���,
			// 		            DB���� Archive ����, �� ����
			}else{
				
				// �������� ���������� ���� ����(���� ���� ����)
				File file2 = new File(savePath+arcName);
				if(file2.exists()){
					file2.delete();
				}
				
				try{
					// DB���� Data ����
					int arcNo = arService.getArcNoFromAnswer(ansNo);
					arService.editArchive(arcNo, uploadFile, arcRoute);
					
					// �� ����
					anService.editAnswer(ansNo, ansContent);
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
}
