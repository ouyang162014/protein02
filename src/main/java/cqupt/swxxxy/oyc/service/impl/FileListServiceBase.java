package cqupt.swxxxy.oyc.service.impl;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cqupt.swxxxy.oyc.bean.FileInfo;

public class FileListServiceBase{
	
	public static List<FileInfo> getAllFile(String filePath,String fileName,String inputTime) {
		// 创建集合对象，存储文件信息
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		// 目录文件
		File file = new File(filePath);
		// 获取该目录下的所有文件
		File[] files = file.listFiles();
		int id = 1;
		for (File fi : files) {
			//获取文件名
			String fName=fi.getName();
			//获取文件上传时间
			String fInputTime=getFileTime(fi.getAbsolutePath());
			//判断文件名和文件上传时间
			if(!("".equals(fileName)) && !(null==fileName) && !("".equals(inputTime)) && !(null==inputTime)){
				if(fileName.equals(fName) && inputTime.equals(fInputTime)){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setId(id++);
					fileInfo.setName(fName);
					fileInfo.setSize(fi.length());
					fileInfo.setTime(fInputTime);
					fileList.add(fileInfo);
				}
			}else if(!("".equals(fileName)) && !(null==fileName)){
				if(fileName.equals(fName)){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setId(id++);
					fileInfo.setName(fName);
					fileInfo.setSize(fi.length());
					fileInfo.setTime(fInputTime);
					fileList.add(fileInfo);
				}
			}else if(!("".equals(inputTime)) && !(null==inputTime)){
				if(inputTime.equals(fInputTime)){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setId(id++);
					fileInfo.setName(fName);
					fileInfo.setSize(fi.length());
					fileInfo.setTime(fInputTime);
					fileList.add(fileInfo);
				}
			}else{
				FileInfo fileInfo = new FileInfo();
				fileInfo.setId(id++);
				fileInfo.setName(fName);
				fileInfo.setSize(fi.length());
				fileInfo.setTime(fInputTime);
				fileList.add(fileInfo);
			}
			
		}
		return fileList;
	}

	/**
	 * 获取文件日期
	 * 
	 * @param fileAbsolutePath
	 * @return
	 */
	public static String getFileTime(String fileAbsolutePath) {
		File file = new File(fileAbsolutePath);
		// 获取文件最后修改时间
		Long time = file.lastModified();
		// 创建日期对象
		Date d = new Date(time);
		// 设置日期格式
		Format simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 将日期转化为指定格式
		String dateString = simpleFormat.format(d);
		return dateString;
	}
}
