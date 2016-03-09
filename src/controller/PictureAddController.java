package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.PictureService;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/pictureAdd.do")
public class PictureAddController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public PictureAddController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/////////////////////////////////////////////////////////////////////////////////////////////
		int maxSize  = 1024*1024*1024;  															//�� ���� ���ε� �ѵ��� 1Giga
		String savePath = "D:\\workEE\\memoryShare\\WebContent\\data";	//���ε� �Ǵ� ������ ���� ���
		String uploadFile="";
		
		MultipartRequest multi= new MultipartRequest(request,savePath,maxSize, new DefaultFileRenamePolicy());
		
		uploadFile=multi.getFilesystemName("uploaded_file");
		uploadFile= new String(uploadFile.getBytes("8859_1"),"EUC-KR");		//���ε� �� ���� �̸� �ѱ�ȭ(���󿡼�)
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		// ���̸�, ������ȣ, �����̸�
		//int galleryNo = Integer.parseInt(multi.getParameter("galleryNo"));
		
		String galleryNumber = multi.getParameter("galleryNo");
		int galleryNo = Integer.parseInt(galleryNumber);
		
		String picMem = multi.getParameter("picMem");
		picMem = new String(picMem.getBytes("8859_1"), "UTF-8");
		
		PictureService pService = new PictureService();
		
	    try{
	    	pService.addPicture(galleryNo, uploadFile, picMem);
		}catch(SQLException e){
			e.printStackTrace();
		}
    }
		
		
		
	
	
}
