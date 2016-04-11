package cqupt.swxxxy.oyc.service.impl;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cqupt.swxxxy.oyc.bean.FileInfo;
import cqupt.swxxxy.oyc.bean.Page;
import cqupt.swxxxy.oyc.service.FileListService;
import cqupt.swxxxy.oyc.util.Constants;
import cqupt.swxxxy.oyc.util.ReadFileProperties;

public class FileListServiceImpl implements FileListService {

	public String getInputFilePath() {
		// 创建对象读取配置文件
		ReadFileProperties rfp = new ReadFileProperties();
		String path=rfp.getProperties().get("windowInputFilePath");
		return path;
	}

	public int getFileSize(String filePath) {
		File file = new File(filePath);
		return file.listFiles().length;
	}

	public Page getPageInfos(String filePath, int currentPage) {
		Page page = new Page();
		int pageCounts = Constants.PAGECOUNTS;
		int totalCounts = getFileSize(filePath);
		int currentPageSize = currentPage != 0 ? currentPage : 1;
		int pageSize = getPageSizes(totalCounts, pageCounts);
		page.setPageCounts(pageCounts);
		page.setPageSize(pageSize);
		page.setCurrentPage(currentPageSize);
		return page;
	}

	public List<FileInfo> getPreviouPage(List<FileInfo> fileInfoList, Page page) {
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		// 开始位置
		int begin = (page.getCurrentPage() - 2) * page.getPageCounts();
		if (begin < 0) {
			begin = 0;
		}
		// 结束位置
		int end = (page.getCurrentPage() - 1) * page.getPageCounts() - 1;
		if (end <= 0) {
			end = page.getPageCounts() - 1;
		}
		if (end >= fileInfoList.size()) {
			end = fileInfoList.size() - 1;
		}
		for (int i = begin; i <= end; i++) {
			fileList.add(fileInfoList.get(i));
		}
		return fileList;
	}

	public List<FileInfo> getNextPage(List<FileInfo> fileInfoList, Page page) {
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		// 开始位置
		int begin = page.getCurrentPage() * page.getPageCounts();
		if (begin >= fileInfoList.size()) {
			begin = (page.getCurrentPage() - 1) * page.getPageCounts();
		}
		// 结束位置
		int end = (page.getCurrentPage() - 1) * page.getPageCounts() - 1;
		if (end <= 0) {
			end = page.getPageCounts() - 1;
		}
		if (end >= fileInfoList.size()) {
			end = fileInfoList.size() - 1;
		}
		for (int i = begin; i <= end; i++) {
			fileList.add(fileInfoList.get(i));
		}
		return fileList;
	}

	public List<FileInfo> getFirstPage(List<FileInfo> fileInfoList, Page page) {
		int begin = 0;
		int end = page.getPageCounts() > fileInfoList.size() ? fileInfoList
				.size() - 1 : page.getPageCounts() - 1;
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		for (int i = begin; i <= end; i++) {
			fileList.add(fileInfoList.get(i));
		}
		return fileList;
	}

	public List<FileInfo> getLastPage(List<FileInfo> fileInfoList, Page page) {
		int begin = (page.getPageSize() - 1) * page.getPageCounts();
		int end = fileInfoList.size() - 1;
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		for (int i = begin; i <= end; i++) {
			fileList.add(fileInfoList.get(i));
		}
		return fileList;
	}

	public List<FileInfo> getAnyPage(List<FileInfo> fileInfoList, int index,
			Page page) {
		if (index <= 0 || index > page.getPageSize()) {
			return null;
		}
		int begin = (index - 1) * page.getPageCounts();
		int end = index == page.getPageSize() ? fileInfoList.size() - 1 : index
				* page.getPageCounts() - 1;
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		for (int i = begin; i <= end; i++) {
			fileList.add(fileInfoList.get(i));
		}
		return fileList;
	}

	/**
	 * 获取分页数
	 * 
	 * @param totalCounts
	 * @param pageCounts
	 * @return
	 */
	public int getPageSizes(int totalCounts, int pageCounts) {
		int size = 1;
		if (totalCounts <= pageCounts) {
			size = 1;
		} else if (totalCounts % pageCounts == 0) {
			size = totalCounts / pageCounts;
		} else {
			size = totalCounts / pageCounts + 1;
		}
		return size;
	}

	/*
	 * public static void main(String[] args) { PageServletImpl psi=new
	 * PageServletImpl(); String path=psi.getInputFilePath(); int
	 * fileSize=psi.getFileSize(path); List<FileInfo> fi=psi.getAllFile(path); }
	 */
}
