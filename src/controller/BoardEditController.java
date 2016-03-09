package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ArchiveService;
import service.BoardService;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/boardEdit.do")
public class BoardEditController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public BoardEditController(){
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
		
		// �۹�ȣ ��������
		int boardNo = Integer.parseInt(multi.getParameter("boardNo"));
		String boardTitle = multi.getParameter("boardTitle");
		boardTitle = new String(boardTitle.getBytes("8859_1"),"utf-8");
		String boardContent = multi.getParameter("boardContent");
		boardContent = new String(boardContent.getBytes("8859_1"),"utf-8");
		
		String arcRoute = multi.getParameter("arcRoute");
		arcRoute = new String(arcRoute.getBytes("8859_1"),"utf-8");
		
		// DB���� ������ ���� �̸�
		String arcName = null;
		
		BoardService bService = new BoardService();
		ArchiveService aService = new ArchiveService();
		
		////////***************************************************************************************
		// ���� ������ ���� ��
		if(uploadFile.equals("null")){
			File file = new File(savePath + uploadFile);
			
			if(file.exists()){
				file.delete();
			}
			
			try {
				arcName = aService.getArcNameFromBoard(boardNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			////////***************************************************************************************
			// ������ ������ ���ٸ� -> �۸� ����
			if(arcName==null){
				try {
					bService.editBoard(boardNo, boardTitle, boardContent);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			////////***************************************************************************************	
			// ������ ������ �ִٸ�  -> ���� ���� & �� ����
			}else{
				// �������� �ڷ� ����
				File file2 = new File(savePath+arcName);
				
				if(file2.exists()){
					file2.delete();
				}
				
				try {
					// DB���� Data ����
					int arcNo = aService.getArcNoFromBoard(boardNo);
					aService.deleteArchive(arcNo);
					// �� ����
					bService.editBoard(boardNo, boardTitle, boardContent);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		////////***************************************************************************************	
		// ���� ������ ���� ��
		}else{		
			
			try {
				arcName = aService.getArcNameFromBoard(boardNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			////////***************************************************************************************
			// ������ ������ ���� �� -> ���� �߰� & �� ����
			if(arcName==null){
				try {
					aService.insertArcFromBoard(boardNo, uploadFile, arcRoute);
					bService.editBoard(boardNo, boardTitle, boardContent);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			////////***************************************************************************************	
			// ������ ������ ���� �� -> ���� ���ε�(���ο� ����), �������� ���������� ���� ����,
			//                 DB���� archive ����, �� ����
			}else{
				
				// �������� ���������� ���� ����(���� �ִ� ����)
				File file2 = new File(savePath+arcName);
				if(file2.exists()){
					file2.delete();
				}
				
				try {
					// DB���� Data ����
					int arcNo = aService.getArcNoFromBoard(boardNo);
					aService.editArchive(arcNo, uploadFile, arcRoute);
					// �� ����
					bService.editBoard(boardNo, boardTitle, boardContent);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			
			
		}
		////////***************************************************************************************		
		
	}
	
}
