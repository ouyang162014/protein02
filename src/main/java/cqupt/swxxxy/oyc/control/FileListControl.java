package cqupt.swxxxy.oyc.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cqupt.swxxxy.oyc.bean.FileInfo;
import cqupt.swxxxy.oyc.bean.Page;
import cqupt.swxxxy.oyc.service.FileListService;
import cqupt.swxxxy.oyc.service.impl.FileListServiceBase;
import cqupt.swxxxy.oyc.service.impl.FileListServiceImpl;

public class FileListControl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		FileListService fls = new FileListServiceImpl();
		// ��ȡ�ļ����·��
		String filePath = fls.getInputFilePath();
		//��ȡ�ļ���
		String fileName=req.getParameter("fileName");
		//��ȡ�ļ��ϴ�ʱ��
		String inputTime=req.getParameter("inputTime");
		// ��ȡ�����ļ�����
		List<FileInfo> fileInfoList = FileListServiceBase.getAllFile(filePath,fileName,inputTime);
		// ��ȡ��ҳ����
		Page page = fls.getPageInfos(filePath, 0);
		req.setAttribute("fileList", fls.getFirstPage(fileInfoList, page));
		req.setAttribute("page", page);
		req.getRequestDispatcher("/parserpages/FileList.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

}
