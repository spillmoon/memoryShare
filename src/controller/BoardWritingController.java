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
import service.BoardFolderService;
import service.BoardService;
import service.RoomService;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/boardWriting.do")
public class BoardWritingController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public BoardWritingController(){
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
		
		if(uploadFile.substring(0, 4).equals("null")){
			
			File file = new File(savePath + uploadFile);
			
			if(file.exists()){
				file.delete();
			}
			
			String roomName = multi.getParameter("roomName");
			roomName = new String(roomName.getBytes("8859_1"),"utf-8");
			String folderName = multi.getParameter("folderName");
			folderName = new String(folderName.getBytes("8859_1"),"utf-8");
			
			int roomNo, folderNo;
			
			String boardTitle = multi.getParameter("boardTitle");
			boardTitle = new String(boardTitle.getBytes("8859_1"),"utf-8");
			String boardMem = multi.getParameter("boardMem");
			boardMem = new String(boardMem.getBytes("8859_1"),"utf-8");
			String boardContent = multi.getParameter("boardContent");
			boardContent = new String(boardContent.getBytes("8859_1"),"utf-8");
			
			RoomService rService = new RoomService();
			BoardFolderService bfService = new BoardFolderService();
			BoardService bService = new BoardService();
			
			try{
				roomNo = rService.roomNameTOroomNo(roomName);
				folderNo = bfService.getBoardFolderNo(roomNo, folderName);
				
				bService.insertBoard(folderNo, boardTitle, boardMem, boardContent);
				
			}catch(SQLException e){
				e.printStackTrace();
			}
			
		}else{
			
			String roomName = multi.getParameter("roomName");
			roomName = new String(roomName.getBytes("8859_1"),"utf-8");
			String folderName = multi.getParameter("folderName");
			folderName = new String(folderName.getBytes("8859_1"),"utf-8");
			
			int roomNo, folderNo, boardNo;
			
			String boardTitle = multi.getParameter("boardTitle");
			boardTitle = new String(boardTitle.getBytes("8859_1"),"utf-8");
			String boardMem = multi.getParameter("boardMem");
			boardMem = new String(boardMem.getBytes("8859_1"),"utf-8");
			String boardContent = multi.getParameter("boardContent");
			boardContent = new String(boardContent.getBytes("8859_1"),"utf-8");
			
			String arcRoute = multi.getParameter("arcRoute");
			arcRoute = new String(arcRoute.getBytes("8859_1"),"utf-8");
			
			RoomService rService = new RoomService();
			BoardFolderService bfService = new BoardFolderService();
			BoardService bService = new BoardService();
			ArchiveService aService = new ArchiveService();
			
			try{
				roomNo = rService.roomNameTOroomNo(roomName);
				folderNo = bfService.getBoardFolderNo(roomNo, folderName);
				
				bService.insertBoard(folderNo, boardTitle, boardMem, boardContent);
				
				boardNo = bService.getBoardNo(roomNo, folderNo, boardTitle);
				
				aService.insertArcFromBoard(boardNo, uploadFile, arcRoute);
				
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		
		
	}
		
}
